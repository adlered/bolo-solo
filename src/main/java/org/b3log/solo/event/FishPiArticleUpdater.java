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
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Singleton;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.PluginUtil;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import java.util.Objects;

/**
 * This listener is responsible for sending updated article to fishpi community base Rhythm. Sees <a href="https://fishpi.cn"></a> for more details.
 *
 * @author <a href="https://github.com/gakkiyomi">Gakkiyomi (Bolo Contributor)</a>
 * @since 0.0.1
 */
@Singleton
public class FishPiArticleUpdater extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FishPiArticleUpdater.class);

    /**
     * Puts the specified article data to FishPi Rhythm.
     *
     * @param data the specified article data
     */
    public static void putArticleToFishPi(final JSONObject data) {
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
                LOGGER.log(Level.INFO, "Ignored put a draft [title={0}] to fishpi", title);
                return;
            }

            if (StringUtils.isNotBlank(originalArticle.optString(Article.ARTICLE_VIEW_PWD))) {
                LOGGER.log(Level.INFO, "Article [title={0}] is a password article, ignored put to fishpi", title);
                return;
            }

            final BeanManager beanManager = BeanManager.getInstance();

            UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);
            String userName = userQueryService.getB3username();
            String fishKey = userQueryService.getFishKey();

            final JSONObject article = new JSONObject().
                    put("articleTitle", originalArticle.getString(Article.ARTICLE_TITLE)).
                    put("articleTags", originalArticle.getString(Article.ARTICLE_TAGS_REF)).
                    put("articleType", 0).
                    put("articleEditorType", 0).
                    put("articleContent", originalArticle.getString(Article.ARTICLE_CONTENT));

            if (Option.DefaultPreference.DEFAULT_B3LOG_USERNAME.equals(userName)) {
                LOGGER.log(Level.INFO, "Article [title={0}] Is using the B3log default account, skipped put to Rhy", title);
                return;
            }
            final OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
            final JSONObject option = optionQueryService.getOptionById("article_" + originalArticle.getString(Keys.OBJECT_ID));
            final String fishPiArticleId = option.optString(Option.OPTION_VALUE);
            if (Objects.isNull(option) || "".equals(fishPiArticleId)) {
                LOGGER.log(Level.WARN, "Put an article to FishPi failed,Cannot find the reference between this article and fishpi article");
                return;
            }

            final HttpResponse response = HttpRequest.put(String.format("https://fishpi.cn/article/%s?apiKey=%s",
                            fishPiArticleId, fishKey))
                    .bodyText(article.toString()).
                    connectionTimeout(3000).timeout(7000).followRedirects(true).
                    contentTypeJson().header("User-Agent", Solos.BOLO_USER_AGENT).send();

            LOGGER.log(Level.INFO, "Put an article [title={0}] to FishPi, response [{1}]", title, response.toString());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Put an article to FishPi failed: " + e.getMessage());
        }
    }

    @Override
    public void action(final Event<JSONObject> event) {
        final JSONObject data = event.getData();
        LOGGER.log(Level.DEBUG, "Processing an event [type={0}, data={1}] in listener [className={2}]",
                event.getType(), data, FishPiArticleUpdater.class.getName());

        putArticleToFishPi(data);
    }

    /**
     * Gets the event type {@linkplain EventTypes#UPDATE_ARTICLE}.
     *
     * @return event type
     */
    @Override
    public String getEventType() {
        return EventTypes.UPDATE_ARTICLE;
    }
}
