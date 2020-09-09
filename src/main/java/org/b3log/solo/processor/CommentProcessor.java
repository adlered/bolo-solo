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

import freemarker.template.Template;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.*;
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
import org.b3log.solo.bolo.tool.AntiXSS;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.service.*;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.Solos;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pers.adlered.simplecurrentlimiter.main.SimpleCurrentLimiter;

import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.*;
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

            return ;
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
                        blogTitle
                );
            } catch (JSONException jsonException) {
                LOGGER.log(Level.DEBUG, "No originalCommentId for [from=" + commentId + ", to=" + originalCommentId + "]");
            }

            // 提醒博主
            if (sendEmailToAdmin) {
                String replyRemindMailBoxAddress = "";
                try {
                    replyRemindMailBoxAddress = optionRepository.get(Option.ID_C_REPLY_REMIND).optString(Option.OPTION_VALUE);
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
                            blogTitle
                    );
                } catch (JSONException jsonException) {
                    LOGGER.log(Level.DEBUG, "Send admin mail remind failed [replyRemindMailBoxAddress=" + replyRemindMailBoxAddress + "]");
                }
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

    @RequestProcessing(value = "/article/commentSync/getList", method = HttpMethod.GET)
    public void commentGetArticleList(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final Query query = new Query().setFilter(CompositeFilterOperator.and(
                new PropertyFilter(Article.ARTICLE_STATUS, FilterOperator.EQUAL, Article.ARTICLE_STATUS_C_PUBLISHED),
                new PropertyFilter(Article.ARTICLE_STATUS, FilterOperator.EQUAL, Article.ARTICLE_STATUS_C_PUBLISHED)
        )).
                setPageCount(1).
                addSort(Keys.OBJECT_ID, SortDirection.DESCENDING);
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
    @RequestProcessing(value = "/article/commentSync/{localaid}/{remoteaid}", method = HttpMethod.GET)
    public void commentSync(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final Transaction transaction = commentRepository.beginTransaction();
        long localaid = Long.parseLong(context.pathVar("localaid"));
        long remoteaid = Long.parseLong(context.pathVar("remoteaid"));
        // 获取本地文章信息
        final JSONObject article = articleQueryService.getArticleById(String.valueOf(localaid));
        String authorId = article.optString(Article.ARTICLE_AUTHOR_ID);
        final JSONObject authorRet = userQueryService.getUser(authorId);
        String username = authorRet.getJSONObject(User.USER).optString("userName");
        String fetchURL = "https://" + Global.HACPAI_DOMAIN + "/apis/vcomment?aid=" + remoteaid + "&p=1&un=" + username;
        // 从远程拉取评论列表
        final HttpResponse res = HttpRequest.get(fetchURL).trustAllCerts(true).
                connectionTimeout(3000).timeout(7000).header("User-Agent", Solos.USER_AGENT)
                .send();
        if (200 != res.statusCode()) {
            return;
        }
        res.charset("UTF-8");
        final JSONObject result = new JSONObject(res.bodyText());
        String html = "";
        try {
            html = result.optJSONObject("data").optString("html");
        } catch (Exception e) {
            context.renderJSON().renderMsg("评论同步失败！");

            return;
        }
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("li");
        for (Element element : elements) {
            String id = element.attr("id");
            if (!id.isEmpty()) {
                String link = element.getElementsByClass("vcomment__avatarlink").get(0).attr("href");
                String avatar = element.getElementsByClass("vcomment__avatar").get(0).attr("src");
                String comment = element.getElementsByClass("vditor-reset").get(0).html();
                String author = element.getElementsByClass("vditor-reset").get(0).attr("data-author");
                String srcLink = element.getElementsByClass("vditor-reset").get(0).attr("data-link");
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
                commentObject.put(Comment.COMMENT_SHARP_URL, "/articles/" + DateFormatUtils.format(date, "yyyy/MM/dd") + "/" + article.optString(Keys.OBJECT_ID) + ".html#" + Long.parseLong(id));
                try {
                    commentRepository.add(commentObject);
                    articleMgmtService.incArticleCommentCount(article.optString(Keys.OBJECT_ID));
                } catch (RepositoryException repositoryException) {
                    repositoryException.printStackTrace();
                }
            }
        }
        transaction.commit();

        context.renderJSON().renderMsg("评论同步成功。");
    }
}
