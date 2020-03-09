/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
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

import freemarker.template.Template;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JsonRenderer;
import org.b3log.solo.bolo.prop.CommentMailService;
import org.b3log.solo.bolo.prop.MailService;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.service.CommentMgmtService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.service.UserMgmtService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.Solos;
import org.json.JSONException;
import org.json.JSONObject;
import pers.adlered.simplecurrentlimiter.main.SimpleCurrentLimiter;

import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Comment processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="https://hacpai.com/member/armstrong">ArmstrongCN</a>
 * @version 1.4.0.0, Apr 18, 2019
 * @since 0.3.1
 */
@RequestProcessor
public class CommentProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentProcessor.class);
    SimpleCurrentLimiter simpleCurrentLimiter = new SimpleCurrentLimiter(60, 2);
    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;
    /**
     * Comment management service.
     */
    @Inject
    private CommentMgmtService commentMgmtService;
    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;
    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;
    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;
    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Adds a comment to an article.
     *
     * <p>
     * Request json:
     * <pre>
     * {
     *     "captcha": "",
     *     "oId": articleId,
     *     "commentName": "",
     *     "commentURL": "",
     *     "commentContent": "",
     *     "commentOriginalCommentId": "" // optional, if exists this key, the comment is an reply
     * }
     * </pre>
     * </p>
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "oId": generatedCommentId,
     *     "sc": "COMMENT_ARTICLE_SUCC",
     *     "commentDate": "", // yyyy/MM/dd HH:mm:ss
     *     "commentSharpURL": "",
     *     "commentThumbnailURL": "",
     *     "commentOriginalCommentName": "", // if exists this key, the comment is an reply
     *     "commentContent": ""
     * }
     * </pre>
     * </p>
     *
     * @param context the specified context, including a request json object
     */
    @RequestProcessing(value = "/article/comments", method = HttpMethod.POST)
    public void addArticleComment(final RequestContext context) {
        final JSONObject requestJSONObject = context.requestJSON();
        requestJSONObject.put(Common.TYPE, Article.ARTICLE);

        String site = requestJSONObject.getString("boloSite");
        if (site.isEmpty()) {
            site = Latkes.getServePath();
        }
        requestJSONObject.put(Comment.COMMENT_URL, site);

        String username = requestJSONObject.getString("boloUser");
        if (username.isEmpty()) {
            username = Solos.getCurrentUser(context.getRequest(), context.getResponse()).optString(User.USER_NAME);

            if (username.isEmpty()) {
                context.sendError(HttpServletResponse.SC_FORBIDDEN);

                return ;
            }
        }
        requestJSONObject.put(Comment.COMMENT_NAME, username);

        final JSONObject jsonObject = commentMgmtService.checkAddCommentRequest(requestJSONObject);
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);

        // 禁止冒用管理员评论
        JSONObject admin = new JSONObject();
        try {
            admin = userRepository.getAdmin();
        } catch (RepositoryException RE) {
            RE.printStackTrace();
        }
        if (admin.optString(User.USER_NAME).equals(username)) {
            if (!Solos.isAdminLoggedIn(context)) {
                jsonObject.put(Keys.STATUS_CODE, false);
                jsonObject.put(Keys.MSG, "你输入了管理员的昵称，但并没有登录！");

                return ;
            }
        }

        String ip = context.remoteAddr();
        if (!simpleCurrentLimiter.access(ip)) {
            LOGGER.log(Level.ERROR, "Can not add comment on article");
            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("addTimeoutLabel"));

            return ;
        }

        try {
            final JSONObject addResult = commentMgmtService.addArticleComment(requestJSONObject);

            // 用户关系表
            String commentId = addResult.optString("oId");
            String commentEmail = requestJSONObject.getString("email");
            if (!commentEmail.isEmpty()) {
                MailService.addCommentMailContext(commentId, username, commentEmail);
            }

            // 提醒被回复的用户
            String originalCommentId = "";
            String blogSite = requestJSONObject.getString("URI");
            String blogTitle = optionQueryService.getPreference().getString(Option.ID_C_BLOG_TITLE);
            try {
                originalCommentId = requestJSONObject.getString("commentOriginalCommentId");
                CommentMailService.remindCommentedGuy(
                        originalCommentId,
                        blogSite,
                        username,
                        blogTitle
                );
            } catch (JSONException JSONE) {
                LOGGER.log(Level.DEBUG, "No originalCommentId for [from=" + commentId + ", to=" + originalCommentId + "]");
            }

            // 提醒博主
            String replyRemindMailBoxAddress = optionRepository.get(Option.ID_C_REPLY_REMIND).optString(Option.OPTION_VALUE);
            String user = requestJSONObject.getString("commentName");
            String comment = requestJSONObject.getString("commentContent");
            try {
                CommentMailService.remindAdmin(
                        replyRemindMailBoxAddress,
                        blogSite,
                        user,
                        comment,
                        blogTitle
                );
            } catch (JSONException JSONE) {
                LOGGER.log(Level.DEBUG, "Send admin mail remind failed [replyRemindMailBoxAddress=" + replyRemindMailBoxAddress + "]");
            }

            final Map<String, Object> dataModel = new HashMap<>();
            dataModel.put(Comment.COMMENT, addResult);
            final JSONObject article = addResult.optJSONObject(Article.ARTICLE);
            article.put(Common.COMMENTABLE, addResult.opt(Common.COMMENTABLE));
            article.put(Common.PERMALINK, addResult.opt(Common.PERMALINK));
            dataModel.put(Article.ARTICLE, article);

            // 添加评论优化 https://github.com/b3log/solo/issues/12246
            try {
                final String skinDirName = (String) context.attr(Keys.TEMAPLTE_DIR_NAME);
                final Template template = Skins.getSkinTemplate(context, "common-comment.ftl");
                final JSONObject preference = optionQueryService.getPreference();
                Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING), skinDirName, dataModel);
                Keys.fillServer(dataModel);
                final StringWriter stringWriter = new StringWriter();
                template.process(dataModel, stringWriter);
                stringWriter.close();
                final String cmtTpl = stringWriter.toString();

                addResult.put("cmtTpl", cmtTpl);
            } catch (final Exception e) {
                // 1.9.0 向后兼容
            }

            addResult.put(Keys.STATUS_CODE, true);

            renderer.setJSONObject(addResult);
        } catch (final Exception e) {

            LOGGER.log(Level.ERROR, "Can not add comment on article", e);
            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("addFailLabel"));
        }
    }

    /**
     * Fills commenter info if logged in.
     *
     * @param requestJSONObject the specified request json object
     * @param context           the specified HTTP servlet request context
     */
    private void fillCommenter(final JSONObject requestJSONObject, final RequestContext context) {
        final JSONObject currentUser = Solos.getCurrentUser(context.getRequest(), context.getResponse());
        if (null == currentUser) {
            return ;
        }

        requestJSONObject.put(Comment.COMMENT_NAME, currentUser.optString(User.USER_NAME));
        requestJSONObject.put(Comment.COMMENT_URL, currentUser.optString(User.USER_URL));
    }
}
