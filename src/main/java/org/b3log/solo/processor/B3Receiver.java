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
package org.b3log.solo.processor;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.model.*;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.service.*;
import org.json.JSONObject;

import java.util.Date;

/**
 * Receiving articles and comments from B3log community. Visits <a href="https://ld246.com/b3log">B3log 构思</a> for more details.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.5.5
 */
@RequestProcessor
public class B3Receiver {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(B3Receiver.class);

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Comment repository.
     */
    @Inject
    private static CommentRepository commentRepository;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Comment management service.
     */
    @Inject
    private CommentMgmtService commentMgmtService;

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

    /**
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    /**
     * Adds or updates an article with the specified request.
     * <p>
     * Request json:
     * <pre>
     * {
     *     "article": {
     *         "id": "",
     *          "title": "",
     *          "content": "",
     *          "contentHTML": "",
     *          "tags": "tag1,tag2,tag3"
     *     },
     *     "client": {
     *         "userName": "",
     *         "userB3Key": ""
     *     }
     * }
     * </pre>
     * </p>
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "oId": "", // Generated article id
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     */
    @RequestProcessing(value = "/apis/symphony/article", method = {HttpMethod.POST, HttpMethod.PUT})
    public void postArticle(final RequestContext context) {
        final JSONObject ret = new JSONObject().put(Keys.CODE, 0);
        context.renderJSON(ret);

        final JSONObject requestJSONObject = context.requestJSON();
        LOGGER.log(Level.INFO, "Adds an article from Sym [" + requestJSONObject.toString() + "]");

        try {
            final JSONObject client = requestJSONObject.optJSONObject("client");
            final String articleAuthorName = client.optString(User.USER_NAME);
            final JSONObject articleAuthor = userRepository.getByUserName(articleAuthorName);
            if (null == articleAuthor) {
                ret.put(Keys.CODE, 1);
                final String msg = "Not found user [" + articleAuthorName + "]";
                ret.put(Keys.MSG, msg);
                LOGGER.log(Level.WARN, msg);

                return;
            }

            final String b3Key = client.optString(UserExt.USER_B3_KEY);

            // Bolo B3key 校验
            final BeanManager beanManager = BeanManager.getInstance();
            UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);
            String key = userQueryService.getB3password();

            if (!StringUtils.equals(key, b3Key)) {
                ret.put(Keys.CODE, 1);
                final String msg = "Wrong key";
                ret.put(Keys.MSG, msg);
                LOGGER.log(Level.WARN, msg);

                return;
            }

            final JSONObject symArticle = requestJSONObject.optJSONObject(Article.ARTICLE);
            final String title = symArticle.optString("title");
            final String articleId = symArticle.optString("id");
            final JSONObject oldArticle = articleQueryService.getArticleById(articleId);
            if (null == oldArticle) {
                final JSONObject article = new JSONObject().
                        put(Keys.OBJECT_ID, symArticle.optString("id")).
                        put(Article.ARTICLE_TITLE, title).
                        put(Article.ARTICLE_CONTENT, symArticle.optString("content")).
                        put(Article.ARTICLE_TAGS_REF, symArticle.optString("tags"));
                article.put(Article.ARTICLE_AUTHOR_ID, articleAuthor.getString(Keys.OBJECT_ID));
                final String articleContent = article.optString(Article.ARTICLE_CONTENT);
                article.put(Article.ARTICLE_ABSTRACT, Article.getAbstractText(articleContent));
                article.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
                article.put(Common.POST_TO_COMMUNITY, false); // Do not send to rhythm
                article.put(Article.ARTICLE_COMMENTABLE, true);
                article.put(Article.ARTICLE_VIEW_PWD, "");
                final String content = article.getString(Article.ARTICLE_CONTENT);
                article.put(Article.ARTICLE_CONTENT, content);
                final JSONObject addRequest = new JSONObject().put(Article.ARTICLE, article);
                articleMgmtService.addArticle(addRequest);
                LOGGER.log(Level.INFO, "Added an article [" + title + "] via Sym");

                return;
            }

            final String articleContent = symArticle.optString("content");
            oldArticle.put(Article.ARTICLE_ABSTRACT, Article.getAbstractText(articleContent));
            oldArticle.put(Article.ARTICLE_CONTENT, articleContent);
            oldArticle.put(Article.ARTICLE_TITLE, symArticle.optString("title"));
            oldArticle.put(Article.ARTICLE_TAGS_REF, symArticle.optString("tags"));
            oldArticle.put(Common.POST_TO_COMMUNITY, false); // Do not send to rhythm
            final JSONObject updateRequest = new JSONObject().put(Article.ARTICLE, oldArticle);
            articleMgmtService.updateArticle(updateRequest);
            LOGGER.log(Level.INFO, "Updated an article [" + title + "] via Sym");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            ret.put(Keys.CODE, 1).put(Keys.MSG, e.getMessage());
        }
    }
}
