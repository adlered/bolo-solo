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
package org.b3log.solo.processor;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.CompositeFilterOperator;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JsonRenderer;
import org.b3log.solo.bolo.Global;
import org.b3log.solo.bolo.prop.CommentMailService;
import org.b3log.solo.bolo.prop.MailService;
import org.b3log.solo.bolo.prop.Options;
import org.b3log.solo.bolo.prop.ServerJiangService;
import org.b3log.solo.bolo.tool.AntiXSS;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.service.ArticleMgmtService;
import org.b3log.solo.service.ArticleQueryService;
import org.b3log.solo.service.CommentMgmtService;
import org.b3log.solo.service.CommentQueryService;
import org.b3log.solo.service.OptionMgmtService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.service.UserMgmtService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.Solos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import freemarker.template.Template;
import jodd.http.Cookie;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import pers.adlered.simplecurrentlimiter.main.SimpleCurrentLimiter;

/**
 * Comment processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://ld246.com/member/armstrong">ArmstrongCN</a>
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

    @Inject
    private OptionMgmtService optionMgmtService;
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
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * Comment service.
     */
    @Inject
    private CommentQueryService commentQueryService;

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Adds a comment to an article.
     *
     * <p>
     * Request json:
     * 
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
     * 
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
        // 为 false 时不发送提醒邮件至管理员邮箱
        boolean sendEmailToAdmin = true;

        final JSONObject requestJSONObject = context.requestJSON();
        requestJSONObject.put(Common.TYPE, Article.ARTICLE);

        String site = requestJSONObject.getString("boloSite");
        // XSS 处理
        site = AntiXSS.getSafeStringXSS(site);
        if (site.isEmpty()) {
            site = Latkes.getServePath();
        }
        requestJSONObject.put(Comment.COMMENT_URL, site);

        String username = requestJSONObject.getString("boloUser");
        // XSS 处理
        username = AntiXSS.getSafeStringXSS(username);
        // 替换空格
        username = username.replaceAll(" ", "_");
        if (username.isEmpty()) {
            username = Solos.getCurrentUser(context.getRequest(), context.getResponse()).optString(User.USER_NAME);

            if (username.isEmpty()) {
                context.sendError(HttpServletResponse.SC_FORBIDDEN);

                return;
            }
        }
        requestJSONObject.put(Comment.COMMENT_NAME, username);

        final JSONObject jsonObject = commentMgmtService.checkAddCommentRequest(requestJSONObject);
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);
        if (!jsonObject.optBoolean("sc")) {
            return;
        }

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

                return;
            } else {
                // 管理员回复不发送邮件提醒
                try {
                    String adminActiveSentToMailbox = Options.get(Option.ID_C_ADMIN_ACTIVE_SENT_TO_MAILBOX);
                    if (adminActiveSentToMailbox.equals("off")) {
                        sendEmailToAdmin = false;
                    }
                } catch (Exception ignored) {
                }
            }
        }

        String ip = context.remoteAddr();
        if (!simpleCurrentLimiter.access(ip)) {
            LOGGER.log(Level.ERROR, "Can not add comment on article");
            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("addTimeoutLabel"));

            return;
        }

        // 评论过滤检查
        String filterComment = requestJSONObject.getString("commentContent");
        // 读取用户禁言设置
        String spamList = "";
        try {
            spamList = optionQueryService.getPreference().getString(Option.ID_C_SPAM);
        } catch (Exception e) {
            spamList = "";
        }
        List<String> filterCommentList;
        try {
            filterCommentList = Arrays.asList(spamList.split(","));
        } catch (Exception e) {
            filterCommentList = new ArrayList<>();
        }
        for (String i : filterCommentList) {
            if (!i.isEmpty()) {
                if (filterComment.contains(i)) {
                    LOGGER.log(Level.ERROR, "Can not add comment on article because it has spam words");
                    jsonObject.put(Keys.STATUS_CODE, false);
                    jsonObject.put(Keys.MSG, "系统维护中，请 00:00 后再试！");

                    return;
                }
            }
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
                        blogTitle);
            } catch (JSONException jsonException) {
                LOGGER.log(Level.DEBUG,
                        "No originalCommentId for [from=" + commentId + ", to=" + originalCommentId + "]");
            }

            // 提醒博主
            if (sendEmailToAdmin) {
                // 邮件提醒
                String replyRemindMailBoxAddress = "";
                try {
                    replyRemindMailBoxAddress = optionRepository.get(Option.ID_C_REPLY_REMIND)
                            .optString(Option.OPTION_VALUE);
                } catch (NullPointerException e) {
                }
                String user = requestJSONObject.getString("commentName");
                String comment = requestJSONObject.getString("commentContent");
                try {
                    CommentMailService.remindAdmin(
                            replyRemindMailBoxAddress,
                            blogSite,
                            user,
                            comment,
                            blogTitle);
                } catch (JSONException jsonException) {
                    LOGGER.log(Level.DEBUG, "Send admin mail remind failed [replyRemindMailBoxAddress="
                            + replyRemindMailBoxAddress + "]");
                }
                // Server酱提醒
                ServerJiangService.remindAdmin(
                        blogSite,
                        user,
                        comment,
                        blogTitle);
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
            return;
        }

        requestJSONObject.put(Comment.COMMENT_NAME, currentUser.optString(User.USER_NAME));
        requestJSONObject.put(Comment.COMMENT_URL, currentUser.optString(User.USER_URL));
    }

    @RequestProcessing(value = "/article/commentSync/getList", method = HttpMethod.GET)
    public void commentGetArticleList(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final Query query = new Query().setFilter(CompositeFilterOperator.and(
                new PropertyFilter(Article.ARTICLE_STATUS, FilterOperator.EQUAL, Article.ARTICLE_STATUS_C_PUBLISHED),
                new PropertyFilter(Article.ARTICLE_STATUS, FilterOperator.EQUAL, Article.ARTICLE_STATUS_C_PUBLISHED)))
                .setPageCount(1).addSort(Keys.OBJECT_ID, SortDirection.DESCENDING);
        List<JSONObject> articlesList = new ArrayList<>();
        try {
            final List<JSONObject> articles = articleRepository.getList(query);
            for (final JSONObject article : articles) {
                JSONObject articleInfo = new JSONObject();
                String oId = article.optString("oId");
                String title = article.optString(Article.ARTICLE_TITLE);
                articleInfo.put("oId", oId);
                articleInfo.put("title", title);
                articlesList.add(articleInfo);
            }
            context.renderJSON().renderData(articlesList);
        } catch (RepositoryException repositoryException) {
            repositoryException.printStackTrace();
            context.renderJSON().renderCode(1);
        }
    }

    /**
     * Sync comment from HacPai to article
     *
     * @param context
     */
    @RequestProcessing(value = "/article/commentSync/{localaid}/{remoteaid}/{symphony}", method = HttpMethod.GET)
    public void commentSync(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final Transaction transaction = commentRepository.beginTransaction();
        long localaid = Long.parseLong(context.pathVar("localaid"));
        long remoteaid = Long.parseLong(context.pathVar("remoteaid"));
        String symphony = context.pathVar("symphony");

        System.out.println("===> PAGE 1 <===");
        int pageCount = syncComment(
                localaid,
                remoteaid,
                symphony,
                1,
                context);
        if (pageCount == -1) {
            context.renderJSON().renderMsg("评论同步失败，无法连接到链滴服务器或 Cookie 错误。");
            return;
        } else if (pageCount > 1) {
            for (int i = 2; i <= pageCount; i++) {
                System.out.println("===> PAGE " + i + " <===");
                syncComment(
                        localaid,
                        remoteaid,
                        symphony,
                        i,
                        context);
            }
        }

        transaction.commit();
        context.renderJSON().renderMsg("评论同步成功。");
    }

    /**
     * Sync comment from HacPai to article
     *
     * @param context
     */
    @RequestProcessing(value = "/article/fishpi/commentSync/{localaid}/{remoteaid}", method = HttpMethod.GET)
    public void commentSyncFromFishPI(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final Transaction transaction = commentRepository.beginTransaction();
        long localaid = Long.parseLong(context.pathVar("localaid"));
        long remoteaid = Long.parseLong(context.pathVar("remoteaid"));
        try {
            System.out.println("===> PAGE 1 <===");
            int pageCount = commentMgmtService.syncCommentFromFishPI(
                    localaid,
                    remoteaid,
                    1);
            if (pageCount == -1) {
                context.renderJSON().renderMsg("评论同步失败，无法连接到摸鱼派服务器或 apiKey 错误。");
                return;
            } else if (pageCount == 0) {
                context.renderJSON().renderMsg("远端文章评论为空");
                return;
            } else if (pageCount > 1) {
                for (int i = 2; i <= pageCount; i++) {
                    System.out.println("===> PAGE " + i + " <===");
                    commentMgmtService.syncCommentFromFishPI(
                            localaid,
                            remoteaid,
                            i);
                }
            }
            transaction.commit();
            final JSONObject option = new JSONObject();
            option.put(Keys.OBJECT_ID, "article_" + String.valueOf(localaid));
            option.put(Option.OPTION_CATEGORY, "fishPiArticleRef");
            option.put(Option.OPTION_VALUE, String.valueOf(remoteaid));
            optionMgmtService.addOrUpdateOption(option);
            context.renderJSON().renderMsg("评论同步成功。");
        } catch (Exception exception) {
            transaction.rollback();
        }
    }

    private int syncComment(long localaid, long remoteaid, String symphony, int page, RequestContext context) {
        // 获取本地文章信息
        final JSONObject article = articleQueryService.getArticleById(String.valueOf(localaid));
        String fetchURL = "https://" + Global.HACPAI_DOMAIN + "/api/v2/article/" + remoteaid + "?p=" + page;
        // 从远程拉取评论列表
        final HttpResponse res = HttpRequest.get(fetchURL).connectionTimeout(3000).timeout(7000)
                .header("User-Agent", Solos.USER_AGENT).cookies(new Cookie("symphony=" + symphony)).send();
        if (200 != res.statusCode()) {
            return -1;
        }
        res.charset("UTF-8");
        final JSONObject result = new JSONObject(res.bodyText());
        JSONArray rslt;
        try {
            rslt = result.optJSONObject("data").optJSONObject("article").optJSONArray("articleComments");
        } catch (Exception e) {
            return -1;
        }
        for (Object o : rslt) {
            try {
                JSONObject object = (JSONObject) o;
                System.out.println("Import content: " + object.opt("commentContent"));
                String link = object.optString("commentArticlePermalink");
                String avatar = object.optString("commentAuthorThumbnailURL");
                String comment = object.optString("commentContent");
                String author = object.optString("commentAuthorName");
                String originalId = object.optString("commentOriginalCommentId");
                String id = object.optString("oId");
                final JSONObject commentObject = new JSONObject();
                commentObject.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, "");
                commentObject.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, "");
                commentObject.put(Comment.COMMENT_NAME, author);
                commentObject.put(Comment.COMMENT_URL, link);
                commentObject.put(Comment.COMMENT_CONTENT, comment);
                commentObject.put(Comment.COMMENT_CREATED, Long.parseLong(id));
                commentObject.put(Comment.COMMENT_THUMBNAIL_URL, avatar);
                commentObject.put(Comment.COMMENT_ON_ID, localaid);
                commentObject.put(Keys.OBJECT_ID, id);
                final long date = article.optLong(Article.ARTICLE_CREATED);
                commentObject.put(Comment.COMMENT_SHARP_URL, "/articles/" + DateFormatUtils.format(date, "yyyy/MM/dd")
                        + "/" + article.optString(Keys.OBJECT_ID) + ".html#" + Long.parseLong(id));
                try {
                    commentRepository.add(commentObject);
                    articleMgmtService.incArticleCommentCount(article.optString(Keys.OBJECT_ID));
                } catch (RepositoryException ignored) {
                }
            } catch (Exception ignored) {
            }
        }
        return Integer
                .parseInt(result.optJSONObject("data").optJSONObject("pagination").optString("paginationPageCount"));
    }

}
