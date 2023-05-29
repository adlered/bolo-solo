/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.event;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Singleton;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.OptionMgmtService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.PluginUtil;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

/**
 * This listener is responsible for sending article to fishpi community base Rhythm. Sees <a href="https://fishpi.cn"></a> for more details.
 *
 * @author <a href="https://github.com/gakkiyomi">Gakkiyomi (Bolo Contributor)</a>
 * @since 0.0.1
 */
@Singleton
public class FishPiArticleSender extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FishPiArticleSender.class);

    /**
     * Pushes the specified article data to FishPi Rhythm.
     *
     * @param data the specified article data
     */
    public static void pushArticleToFishPi(final JSONObject data) {
        try {
            final JSONObject originalArticle = data.getJSONObject(Article.ARTICLE);

            if (!originalArticle.optBoolean(Common.POST_TO_COMMUNITY)) {
                return;
            }

            if (!PluginUtil.fishpiPluginEnabled()) {
                return;
            }

            final String title = originalArticle.getString(Article.ARTICLE_TITLE);
            if (Article.ARTICLE_STATUS_C_PUBLISHED != originalArticle.optInt(Article.ARTICLE_STATUS)) {
                LOGGER.log(Level.INFO, "Ignored push a draft [title={0}] to fishpi", title);
                return;
            }

            if (StringUtils.isNotBlank(originalArticle.optString(Article.ARTICLE_VIEW_PWD))) {
                LOGGER.log(Level.INFO, "Article [title={0}] is a password article, ignored push to fishpi", title);
                return;
            }

            final BeanManager beanManager = BeanManager.getInstance();

            UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);
            String userName = userQueryService.getB3username();
            String fishKey = userQueryService.getFishKey();

            final JSONObject article = new JSONObject().
                    put("id", originalArticle.getString(Keys.OBJECT_ID)).
                    put("articleTitle", originalArticle.getString(Article.ARTICLE_TITLE)).
                    put("articleTags", originalArticle.getString(Article.ARTICLE_TAGS_REF)).
                    put("articleType", 0).
                    put("isGoodArticle","yes").
                    put("articleCommentable",true).
                    put("articleNotifyFollowers", false).
                    put("articleRewardPoint", "").
                    put("articleContent", originalArticle.getString(Article.ARTICLE_CONTENT));

            if (Option.DefaultPreference.DEFAULT_B3LOG_USERNAME.equals(userName)) {
                LOGGER.log(Level.INFO, "Article [title={0}] Is using the B3log default account, skipped push to Rhy", title);
                return;
            }

            final HttpResponse response = HttpRequest.post(String.format("https://fishpi.cn/article?apiKey=%s", fishKey)).bodyText(article.toString()).
                    connectionTimeout(3000).timeout(7000).followRedirects(true).
                    contentTypeJson().header("User-Agent", Solos.BOLO_USER_AGENT).send();

            if (response.statusCode() != HttpStatus.HTTP_OK) {
                LOGGER.log(Level.ERROR, "Pushes an article to FishPi failed: " + response.bodyText());
                return;
            }
            final JSONObject respJson = new JSONObject(response.bodyText());
            if (respJson.has("code") && respJson.optInt("code") == 0) {
                final String articleId = respJson.optString("articleId");
                final OptionMgmtService optionMgmtService = beanManager.getReference(OptionMgmtService.class);
                final JSONObject option = new JSONObject();
                option.put(Keys.OBJECT_ID, "article_" + originalArticle.getString(Keys.OBJECT_ID));
                option.put(Option.OPTION_CATEGORY, "fishPiArticleRef");
                option.put(Option.OPTION_VALUE, articleId);
                optionMgmtService.addOrUpdateOption(option);
            }
            LOGGER.log(Level.INFO, "Pushed an article [title={0}] to FishPi, response [{1}]", title, response.toString());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Pushes an article to FishPi failed: " + e.getMessage());
        }
    }

    @Override
    public void action(final Event<JSONObject> event) {
        final JSONObject data = event.getData();
        LOGGER.log(Level.DEBUG, "Processing an event [type={0}, data={1}] in listener [className={2}]",
                event.getType(), data, FishPiArticleSender.class.getName());

        pushArticleToFishPi(data);
    }

    /**
     * Gets the event type {@linkplain EventTypes#ADD_ARTICLE}.
     *
     * @return event type
     */
    @Override
    public String getEventType() {
        return EventTypes.ADD_ARTICLE;
    }
}
