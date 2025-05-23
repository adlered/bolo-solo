/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020-present, https://github.com/bolo-blog
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
import org.b3log.solo.service.ArticleQueryService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

/**
 * This listener is responsible for sending article to B3log Rhythm. Sees <a href="https://ld246.com/b3log">B3log 构思</a> for more details.
 * <p>
 * API spec: https://ld246.com/article/1457158841475
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://ld246.com/member/armstrong">ArmstrongCN</a>
 * @since 0.3.1
 */
@Singleton
public class B3ArticleSender extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(B3ArticleSender.class);

    /**
     * Pushes the specified article data to B3log Rhythm.
     *
     * @param data the specified article data
     */
    public static void pushArticleToRhy(final JSONObject data) {
        try {
            final JSONObject originalArticle = data.getJSONObject(Article.ARTICLE);
            final String title = originalArticle.getString(Article.ARTICLE_TITLE);
            if (Article.ARTICLE_STATUS_C_PUBLISHED != originalArticle.optInt(Article.ARTICLE_STATUS)) {
                LOGGER.log(Level.INFO, "Ignored push a draft [title={0}] to Rhy", title);

                return;
            }

            if (StringUtils.isNotBlank(originalArticle.optString(Article.ARTICLE_VIEW_PWD))) {
                LOGGER.log(Level.INFO, "Article [title={0}] is a password article, ignored push to Rhy", title);

                return;
            }

            if (!originalArticle.optBoolean(Common.POST_TO_COMMUNITY)) {

                return;
            }

            // 注释本地关闭推送功能
            /* if (isLocalServer()) {
                LOGGER.log(Level.INFO, "Solo is running on a local server [servePath=" + Latkes.getServePath() +
                        ", serverHost=" + Latkes.getServerHost() + ", serverPort=" + Latkes.getServerPort() + "], ignored push article [title=" + title + "] to Rhy");
                return;
            } */

            final BeanManager beanManager = BeanManager.getInstance();
            final OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
            final ArticleQueryService articleQueryService = beanManager.getReference(ArticleQueryService.class);
            final JSONObject preference = optionQueryService.getPreference();

            final JSONObject article = new JSONObject().
                    put("id", originalArticle.getString(Keys.OBJECT_ID)).
                    put("title", originalArticle.getString(Article.ARTICLE_TITLE)).
                    put("permalink", originalArticle.getString(Article.ARTICLE_PERMALINK)).
                    put("tags", originalArticle.getString(Article.ARTICLE_TAGS_REF)).
                    put("content", originalArticle.getString(Article.ARTICLE_CONTENT));
            final JSONObject author = articleQueryService.getAuthor(originalArticle);

            UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);
            String userName = userQueryService.getB3username();
            String userB3Key = userQueryService.getB3password();

            if (Option.DefaultPreference.DEFAULT_B3LOG_USERNAME.equals(userName)) {
                LOGGER.log(Level.INFO, "Article [title={0}] Is using the B3log default account, skipped push to Rhy", title);

                return;
            }

            final JSONObject client = new JSONObject().
                    put("title", preference.getString(Option.ID_C_BLOG_TITLE)).
                    put("host", Latkes.getServePath()).
                    put("name", "Solo").
                    put("ver", SoloServletListener.VERSION).
                    put("userName", userName).
                    put("userB3Key", userB3Key);
            final JSONObject requestJSONObject = new JSONObject().
                    put("article", article).
                    put("client", client);
            final HttpResponse response = HttpRequest.post("https://rhythm.b3log.org/api/article").bodyText(requestJSONObject.toString()).
                    connectionTimeout(3000).timeout(7000).followRedirects(true).
                    contentTypeJson().header("User-Agent", Solos.USER_AGENT).send();

            LOGGER.log(Level.INFO, "Pushed an article [title={0}] to Rhy, response [{1}]", title, response.toString());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Pushes an article to Rhy failed: " + e.getMessage());
        }
    }

    static boolean isLocalServer() {
        return StringUtils.containsIgnoreCase(Latkes.getServePath(), "localhost") || Strings.isIPv4(Latkes.getServerHost()) ||
                (StringUtils.isNotBlank(Latkes.getServerPort())) && !"80".equals(Latkes.getServerPort()) && !"443".equals(Latkes.getServerPort());
    }

    @Override
    public void action(final Event<JSONObject> event) {
        final JSONObject data = event.getData();
        LOGGER.log(Level.DEBUG, "Processing an event [type={0}, data={1}] in listener [className={2}]",
                event.getType(), data, B3ArticleSender.class.getName());

        pushArticleToRhy(data);
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
