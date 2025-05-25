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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.User;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.AbstractFreeMarkerRenderer;
import org.b3log.latke.servlet.renderer.JsonRenderer;
import org.b3log.latke.servlet.renderer.TextHtmlRenderer;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.latke.util.Dates;
import org.b3log.latke.util.Locales;
import org.b3log.latke.util.Paginator;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.cache.FollowArticleCache;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.model.ArchiveDate;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Sign;
import org.b3log.solo.model.Tag;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.processor.console.ConsoleRenderer;
import org.b3log.solo.repository.CategoryTagRepository;
import org.b3log.solo.service.ArchiveDateQueryService;
import org.b3log.solo.service.ArticleMgmtService;
import org.b3log.solo.service.ArticleQueryService;
import org.b3log.solo.service.CategoryQueryService;
import org.b3log.solo.service.CommentQueryService;
import org.b3log.solo.service.DataModelService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.service.StatisticMgmtService;
import org.b3log.solo.service.TagQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.Markdowns;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.Solos;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

/**
 * Article processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="http://zephyr.b3log.org">Zephyr</a>
 * @since 0.3.1
 */
@RequestProcessor
public class ArticleProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleProcessor.class);

    /**
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

    /**
     * Tag query service.
     */
    @Inject
    private TagQueryService tagQueryService;

    /**
     * Comment query service.
     */
    @Inject
    private CommentQueryService commentQueryService;

    /**
     * DataModelService.
     */
    @Inject
    private DataModelService dataModelService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Archive date query service.
     */
    @Inject
    private ArchiveDateQueryService archiveDateQueryService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

    @Inject
    private FollowArticleCache followArticleCache;

    /**
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Event manager.
     */
    @Inject
    private EventManager eventManager;

    /**
     * Category tag repository.
     */
    @Inject
    private CategoryTagRepository categoryTagRepository;

    /**
     * Category query service.
     */
    @Inject
    private CategoryQueryService categoryQueryService;

    /**
     * Markdowns.
     * <p>
     * Renders the response with a json object, for example,
     * 
     * <pre>
     * {
     *     "html": ""
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     */
    @RequestProcessing(value = "/console/markdown/2html", method = HttpMethod.POST)
    public void markdown2HTML(final RequestContext context) {
        final JSONObject result = Solos.newSucc();
        context.renderJSON(result);

        final String markdownText = context.requestJSON().optString("markdownText");
        if (StringUtils.isBlank(markdownText)) {
            result.put(Common.DATA, "");

            return;
        }

        /*
         * if (!Solos.isLoggedIn(context)) {
         * result.put(Keys.CODE, -1);
         * result.put(Keys.MSG, langPropsService.get("getFailLabel"));
         * 
         * return;
         * }
         */

        try {
            final String html = Markdowns.toHTML(markdownText);
            result.put(Common.DATA, html);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            result.put(Keys.CODE, -1);
            result.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Shows the article view password form.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/console/article-pwd", method = HttpMethod.GET)
    public void showArticlePwdForm(final RequestContext context) {
        final String articleId = context.param("articleId");
        if (StringUtils.isBlank(articleId)) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        final JSONObject article = articleQueryService.getArticleById(articleId);
        if (null == article) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer(context, "article-pwd.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        dataModel.put("articleId", articleId);
        dataModel.put("articlePermalink", article.optString(Article.ARTICLE_PERMALINK));
        dataModel.put("articleTitle", article.optString(Article.ARTICLE_TITLE));
        dataModel.put("articleAbstract", article.optString(Article.ARTICLE_ABSTRACT));
        final String msg = context.param(Keys.MSG);

        if (StringUtils.isNotBlank(msg)) {
            dataModel.put(Keys.MSG, langPropsService.get("passwordNotMatchLabel"));
        }

        final Map<String, String> langs = langPropsService.getAll(Latkes.getLocale());
        dataModel.putAll(langs);

        final JSONObject preference = optionQueryService.getPreference();
        dataModel.put(Option.ID_C_BLOG_TITLE, preference.getString(Option.ID_C_BLOG_TITLE));
        dataModel.put(Common.VERSION, SoloServletListener.VERSION);
        dataModel.put(Common.BOLO_VERSION, SoloServletListener.BOLO_VERSION);
        dataModel.put(Common.STATIC_RESOURCE_VERSION, Latkes.getStaticResourceVersion());
        dataModel.put(Common.YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        Keys.fillRuntime(dataModel);
        dataModelService.fillMinified(dataModel);
        dataModelService.fillFaviconURL(dataModel, preference);
        dataModelService.fillUsite(dataModel);
    }

    /**
     * Processes the article view password form submits.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/console/article-pwd", method = HttpMethod.POST)
    public void onArticlePwdForm(final RequestContext context) {
        try {
            final HttpServletRequest request = context.getRequest();
            final String articleId = context.param("articleId");
            final String pwdTyped = context.param("pwdTyped");

            final JSONObject article = articleQueryService.getArticleById(articleId);

            if (article.getString(Article.ARTICLE_VIEW_PWD).equals(pwdTyped)) {
                final HttpSession session = request.getSession();
                if (null != session) {
                    Map<String, String> viewPwds = (Map<String, String>) session.getAttribute(Common.ARTICLES_VIEW_PWD);
                    if (null == viewPwds) {
                        viewPwds = new HashMap<>();
                    }

                    viewPwds.put(articleId, pwdTyped);
                    session.setAttribute(Common.ARTICLES_VIEW_PWD, viewPwds);
                }

                context.sendRedirect(Latkes.getServePath() + article.getString(Article.ARTICLE_PERMALINK));

                return;
            }

            context.sendRedirect(Latkes.getServePath() + "/console/article-pwd?articleId="
                    + article.optString(Keys.OBJECT_ID) + "&msg=1");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Processes article view password form submits failed", e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Gets random articles with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/articles/random", method = HttpMethod.POST)
    public void getRandomArticles(final RequestContext context) {
        final JSONObject jsonObject = new JSONObject();

        final JSONObject preference = optionQueryService.getPreference();
        final int displayCnt = preference.getInt(Option.ID_C_RANDOM_ARTICLES_DISPLAY_CNT);
        if (0 == displayCnt) {
            jsonObject.put(Common.RANDOM_ARTICLES, new ArrayList<JSONObject>());

            final JsonRenderer renderer = new JsonRenderer();
            context.setRenderer(renderer);
            renderer.setJSONObject(jsonObject);

            return;
        }

        Stopwatchs.start("Get Random Articles");
        final List<JSONObject> randomArticles = getRandomArticles(preference);

        jsonObject.put(Common.RANDOM_ARTICLES, randomArticles);

        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);

        Stopwatchs.end();
    }

    /**
     * Gets relevant articles with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/article/id/{id}/relevant/articles", method = HttpMethod.GET)
    public void getRelevantArticles(final RequestContext context) {
        final JSONObject jsonObject = new JSONObject();

        final JSONObject preference = optionQueryService.getPreference();

        final int displayCnt = preference.getInt(Option.ID_C_RELEVANT_ARTICLES_DISPLAY_CNT);
        if (0 == displayCnt) {
            jsonObject.put(Common.RANDOM_ARTICLES, new ArrayList<JSONObject>());

            final JsonRenderer renderer = new JsonRenderer();
            context.setRenderer(renderer);
            renderer.setJSONObject(jsonObject);

            return;
        }

        final HttpServletRequest request = context.getRequest();
        Stopwatchs.start("Get Relevant Articles");
        final String articleId = context.pathVar("id");
        if (StringUtils.isBlank(articleId)) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        final JSONObject article = articleQueryService.getArticleById(articleId);
        if (null == article) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        final List<JSONObject> relevantArticles = articleQueryService.getRelevantArticles(article, preference);
        jsonObject.put(Common.RELEVANT_ARTICLES, relevantArticles);

        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);

        Stopwatchs.end();
    }

    /**
     * Gets article content with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/get-article-content", method = HttpMethod.GET)
    public void getArticleContent(final RequestContext context) {
        final HttpServletRequest request = context.getRequest();
        final String articleId = context.param("id");
        if (StringUtils.isBlank(articleId)) {
            return;
        }

        final TextHtmlRenderer renderer = new TextHtmlRenderer();
        context.setRenderer(renderer);

        String content;
        try {
            content = articleQueryService.getArticleContent(context, articleId);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, "Can not get article content", e);
            return;
        }

        if (null == content) {
            return;
        }

        renderer.setContent(content);
    }

    /**
     * Gets articles paged with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/articles", method = HttpMethod.GET)
    public void getArticlesByPage(final RequestContext context) {
        final JSONObject jsonObject = new JSONObject();
        final HttpServletRequest request = context.getRequest();
        final int currentPageNum = Paginator.getPage(request);
        Stopwatchs.start("Get Articles Paged [pageNum=" + currentPageNum + ']');
        try {
            jsonObject.put(Keys.STATUS_CODE, true);

            final JSONObject preference = optionQueryService.getPreference();
            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);
            final int windowSize = preference.getInt(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE);

            final StringBuilder pathBuilder = new StringBuilder();
            pathBuilder.append(currentPageNum).append('/').append(pageSize).append('/').append(windowSize);

            final JSONObject requestJSONObject = Solos.buildPaginationRequest(pathBuilder.toString());
            requestJSONObject.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
            requestJSONObject.put(Option.ID_C_ENABLE_ARTICLE_UPDATE_HINT,
                    preference.optBoolean(Option.ID_C_ENABLE_ARTICLE_UPDATE_HINT));
            final JSONObject result = articleQueryService.getArticles(requestJSONObject);
            final List<JSONObject> articles = org.b3log.latke.util.CollectionUtils
                    .jsonArrayToList(result.getJSONArray(Article.ARTICLES));
            dataModelService.setArticlesExProperties(context, articles, preference);

            jsonObject.put(Keys.RESULTS, result);
        } catch (final Exception e) {
            jsonObject.put(Keys.STATUS_CODE, false);
            LOGGER.log(Level.ERROR, "Gets article paged failed", e);
        } finally {
            Stopwatchs.end();
        }

        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);
    }

    /**
     * Gets tag articles paged with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/articles/tags/{tagTitle}", method = HttpMethod.GET)
    public void getTagArticlesByPage(final RequestContext context) {
        final JSONObject jsonObject = new JSONObject();

        final HttpServletRequest request = context.getRequest();
        final String tagTitle = context.pathVar("tagTitle");
        final int currentPageNum = Paginator.getPage(request);
        Stopwatchs.start("Get Tag-Articles Paged [tagTitle=" + tagTitle + ", pageNum=" + currentPageNum + ']');
        try {
            jsonObject.put(Keys.STATUS_CODE, true);

            final JSONObject preference = optionQueryService.getPreference();
            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);

            final JSONObject tagQueryResult = tagQueryService.getTagByTitle(tagTitle);
            if (null == tagQueryResult) {
                throw new Exception("Can not found tag [title=" + tagTitle + "]");
            }

            final JSONObject tag = tagQueryResult.getJSONObject(Tag.TAG);
            final String tagId = tag.getString(Keys.OBJECT_ID);
            final JSONObject tagArticleResult = articleQueryService.getArticlesByTag(tagId, currentPageNum, pageSize);
            if (null == tagArticleResult) {
                throw new Exception("Can not found tag [title=" + tagTitle + "]'s articles");
            }

            final List<JSONObject> articles = (List<JSONObject>) tagArticleResult.opt(Keys.RESULTS);
            final int pageCount = tagArticleResult.optJSONObject(Pagination.PAGINATION)
                    .optInt(Pagination.PAGINATION_PAGE_COUNT);
            dataModelService.setArticlesExProperties(context, articles, preference);

            final JSONObject result = new JSONObject();
            final JSONObject pagination = new JSONObject();
            pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            result.put(Pagination.PAGINATION, pagination);
            result.put(Article.ARTICLES, articles);
            jsonObject.put(Keys.RESULTS, result);
        } catch (final Exception e) {
            jsonObject.put(Keys.STATUS_CODE, false);
            LOGGER.log(Level.ERROR, "Gets article paged failed", e);
        } finally {
            Stopwatchs.end();
        }

        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);
    }

    /**
     * Gets tag articles paged with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/articles/archives/{yyyy}/{MM}", method = HttpMethod.GET)
    public void getArchivesArticlesByPage(final RequestContext context) {
        final JSONObject jsonObject = new JSONObject();

        final HttpServletRequest request = context.getRequest();
        final String archiveDateString = context.pathVar("yyyy") + "/" + context.pathVar("MM");
        final int currentPageNum = Paginator.getPage(request);

        Stopwatchs.start(
                "Get Archive-Articles Paged [archive=" + archiveDateString + ", pageNum=" + currentPageNum + ']');
        try {
            jsonObject.put(Keys.STATUS_CODE, true);

            final JSONObject preference = optionQueryService.getPreference();
            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);

            final JSONObject archiveQueryResult = archiveDateQueryService.getByArchiveDateString(archiveDateString);
            if (null == archiveQueryResult) {
                throw new Exception("Can not found archive [archiveDate=" + archiveDateString + "]");
            }

            final JSONObject archiveDate = archiveQueryResult.getJSONObject(ArchiveDate.ARCHIVE_DATE);
            final String archiveDateId = archiveDate.getString(Keys.OBJECT_ID);

            final int articleCount = archiveDateQueryService.getArchiveDatePublishedArticleCount(archiveDateId);
            final int pageCount = (int) Math.ceil((double) articleCount / (double) pageSize);

            final List<JSONObject> articles = articleQueryService.getArticlesByArchiveDate(archiveDateId,
                    currentPageNum, pageSize);
            dataModelService.setArticlesExProperties(context, articles, preference);

            final JSONObject result = new JSONObject();
            final JSONObject pagination = new JSONObject();
            pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            result.put(Pagination.PAGINATION, pagination);
            result.put(Article.ARTICLES, articles);
            jsonObject.put(Keys.RESULTS, result);
        } catch (final Exception e) {
            jsonObject.put(Keys.STATUS_CODE, false);
            LOGGER.log(Level.ERROR, "Gets article paged failed", e);
        } finally {
            Stopwatchs.end();
        }

        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);
    }

    /**
     * Gets author articles paged with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/articles/authors/{author}", method = HttpMethod.GET)
    public void getAuthorsArticlesByPage(final RequestContext context) {
        final JSONObject jsonObject = new JSONObject();

        final HttpServletRequest request = context.getRequest();
        final String authorId = context.pathVar("author");
        final int currentPageNum = Paginator.getPage(request);

        Stopwatchs.start("Get Author-Articles Paged [authorId=" + authorId + ", pageNum=" + currentPageNum + ']');
        try {
            jsonObject.put(Keys.STATUS_CODE, true);

            final JSONObject preference = optionQueryService.getPreference();
            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);

            final JSONObject authorRet = userQueryService.getUser(authorId);
            if (null == authorRet) {
                context.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            final JSONObject articlesResult = articleQueryService.getArticlesByAuthorId(authorId, currentPageNum,
                    pageSize);
            final List<JSONObject> articles = CollectionUtils
                    .jsonArrayToList(articlesResult.optJSONArray(Keys.RESULTS));
            dataModelService.setArticlesExProperties(context, articles, preference);
            final int pageCount = articlesResult.optJSONObject(Pagination.PAGINATION)
                    .optInt(Pagination.PAGINATION_PAGE_COUNT);

            final JSONObject result = new JSONObject();
            final JSONObject pagination = new JSONObject();
            pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            result.put(Pagination.PAGINATION, pagination);
            result.put(Article.ARTICLES, articles);
            jsonObject.put(Keys.RESULTS, result);
        } catch (final Exception e) {
            jsonObject.put(Keys.STATUS_CODE, false);
            LOGGER.log(Level.ERROR, "Gets article paged failed", e);
        } finally {
            Stopwatchs.end();
        }

        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        renderer.setJSONObject(jsonObject);
    }

    /**
     * Shows author articles with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/authors/{author}", method = HttpMethod.GET)
    public void showAuthorArticles(final RequestContext context) {
        final HttpServletRequest request = context.getRequest();
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "author-articles.ftl");

        try {
            final String authorId = context.pathVar("author");
            final int currentPageNum = Paginator.getPage(request);
            LOGGER.log(Level.DEBUG, "Request author articles [authorId={0}, currentPageNum={1}]", authorId,
                    currentPageNum);

            final JSONObject preference = optionQueryService.getPreference();
            if (null == preference) {
                context.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);
            final int windowSize = preference.getInt(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE);

            final JSONObject result = userQueryService.getUser(authorId);
            if (null == result) {
                context.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            final JSONObject articlesResult = articleQueryService.getArticlesByAuthorId(authorId, currentPageNum,
                    pageSize);
            if (null == articlesResult) {
                context.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            final List<JSONObject> articles = CollectionUtils
                    .jsonArrayToList(articlesResult.optJSONArray(Keys.RESULTS));
            articles.forEach(article -> article.put("isRss", false));
            dataModelService.setArticlesExProperties(context, articles, preference);
            final int pageCount = articlesResult.optJSONObject(Pagination.PAGINATION)
                    .optInt(Pagination.PAGINATION_PAGE_COUNT);
            final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

            final Map<String, Object> dataModel = renderer.getDataModel();
            final JSONObject author = result.getJSONObject(User.USER);
            prepareShowAuthorArticles(pageNums, dataModel, pageCount, currentPageNum, articles, author);
            final HttpServletResponse response = context.getResponse();
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);
            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING),
                    (String) context.attr(Keys.TEMAPLTE_DIR_NAME), dataModel);

            statisticMgmtService.incBlogViewCount(context, response);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Shows archive articles with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/archives/{yyyy}/{MM}", method = HttpMethod.GET)
    public void showArchiveArticles(final RequestContext context) {
        final HttpServletRequest request = context.getRequest();
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "archive-articles.ftl");

        try {
            final int currentPageNum = Paginator.getPage(request);
            final String archiveDateString = context.pathVar("yyyy") + "/" + context.pathVar("MM");
            LOGGER.log(Level.DEBUG, "Request archive date [string={0}, currentPageNum={1}]", archiveDateString,
                    currentPageNum);
            final JSONObject result = archiveDateQueryService.getByArchiveDateString(archiveDateString);
            if (null == result) {
                LOGGER.log(Level.DEBUG, "Can not find articles for the specified archive date[string={0}]",
                        archiveDateString);
                context.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            final JSONObject archiveDate = result.getJSONObject(ArchiveDate.ARCHIVE_DATE);
            final String archiveDateId = archiveDate.getString(Keys.OBJECT_ID);

            final JSONObject preference = optionQueryService.getPreference();
            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);

            final int articleCount = archiveDateQueryService.getArchiveDatePublishedArticleCount(archiveDateId);
            final int pageCount = (int) Math.ceil((double) articleCount / (double) pageSize);

            final List<JSONObject> articles = articleQueryService.getArticlesByArchiveDate(archiveDateId,
                    currentPageNum, pageSize);
            if (articles.isEmpty()) {
                context.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            }
            articles.forEach(article -> article.put("isRss", false));
            dataModelService.setArticlesExProperties(context, articles, preference);

            final Map<String, Object> dataModel = renderer.getDataModel();
            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING),
                    (String) context.attr(Keys.TEMAPLTE_DIR_NAME), dataModel);
            prepareShowArchiveArticles(preference, dataModel, articles, currentPageNum, pageCount, archiveDateString,
                    archiveDate);
            final HttpServletResponse response = context.getResponse();
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);

            statisticMgmtService.incBlogViewCount(context, response);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @RequestProcessing(value = "/follow/articles/{followName}", method = HttpMethod.GET)
    public void showFollowUserArticles(final RequestContext context) {
        final String followName = (String) context.pathVar("followName");
        final HttpServletRequest request = context.getRequest();
        final HttpServletResponse response = context.getResponse();
        if (null == followName || "".equals(followName)) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            final JSONObject preference = optionQueryService.getPreference();
            String specifiedSkin = Skins.getSkinDirName(context);
            if (StringUtils.isBlank(specifiedSkin)) {
                final JSONObject skinOpt = optionQueryService.getSkin();
                specifiedSkin = Solos.isMobile(request) ? skinOpt.optString(Option.ID_C_MOBILE_SKIN_DIR_NAME)
                        : skinOpt.optString(Option.ID_C_SKIN_DIR_NAME);
            }
            request.setAttribute(Keys.TEMAPLTE_DIR_NAME, specifiedSkin);
            final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "index.ftl");
            final Map<String, Object> dataModel = renderer.getDataModel();

            Cookie cookie;
            if (!Solos.isMobile(request)) {
                cookie = new Cookie(Common.COOKIE_NAME_SKIN, specifiedSkin);
            } else {
                cookie = new Cookie(Common.COOKIE_NAME_MOBILE_SKIN, specifiedSkin);
            }
            cookie.setMaxAge(60 * 60); // 1 hour
            cookie.setPath("/");
            response.addCookie(cookie);

            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING),
                    (String) context.attr(Keys.TEMAPLTE_DIR_NAME), dataModel);
            final List<JSONObject> articles = followArticleCache.getFollowArticles(followName).values().stream()
                    .sorted(Comparator
                            .comparingLong(o -> {
                                JSONObject article = (JSONObject) o;
                                return article.optLong(Article.ARTICLE_CREATED);
                            }).reversed())
                    .collect(Collectors.toList());
            dataModel.put(Article.ARTICLES, articles);
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);
            dataModel.put("paginationPageCount", 0);
            if (null != articles && !articles.isEmpty()) {
                final JSONObject article = articles.get(0);
                dataModel.put(Option.ID_C_BLOG_TITLE, article.getString(Option.ID_C_BLOG_TITLE));
                dataModel.put(Option.ID_C_BLOG_SUBTITLE, article.getString(Option.ID_C_BLOG_SUBTITLE));
                JSONObject admin = new JSONObject(dataModel.get(Common.ADMIN_USER).toString());
                admin.put("userAvatar", article.getString(Common.AUTHOR_THUMBNAIL_URL));
                dataModel.put(Common.ADMIN_USER, admin);
            }

            dataModel.put(Common.PATH, "");
            statisticMgmtService.incBlogViewCount(context, response);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Shows an article with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/follow/{followName}/article/{articleTitle}", method = HttpMethod.GET)
    public void showRssArticle(final RequestContext context) {
        // See PermalinkHandler#dispatchToArticleProcessor()
        final String followName = (String) context.pathVar("followName");
        final String articleTitle = (String) context.pathVar("articleTitle");
        if ((null == followName || "".equals(followName))
                || null == articleTitle || "".equals(articleTitle)) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        LOGGER.log(Level.DEBUG, "Rss Article Feed [author=[{0}], id={1}]", followName, articleTitle);

        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "rss-article.ftl");

        try {
            final Map<String, JSONObject> cache = followArticleCache.getFollowArticles(followName);
            if (null == cache) {
                context.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            final JSONObject article = cache.get(articleTitle);
            if (null == article) {
                context.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            dataModelService.fillCategory(article);
            final Map<String, Object> dataModel = renderer.getDataModel();
            final JSONObject preference = optionQueryService.getPreference();
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);
            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING),
                    (String) context.attr(Keys.TEMAPLTE_DIR_NAME), dataModel);
            // Bolo 告诉前端用户是否已经登录
            String logged = String.valueOf(Solos.isLoggedIn(context));
            article.put("logged", logged);
            dataModel.put(Article.ARTICLE, article);
            dataModel.put(Article.ARTICLE_COMMENTS_REF, Collections.emptyList());
            dataModel.put(Option.ID_C_RANDOM_ARTICLES_DISPLAY_CNT, 0);
            dataModel.put(Option.ID_C_RELEVANT_ARTICLES_DISPLAY_CNT, 0);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Shows an article with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/article", method = HttpMethod.GET)
    public void showArticle(final RequestContext context) {
        // See PermalinkHandler#dispatchToArticleProcessor()
        final JSONObject article = (JSONObject) context.attr(Article.ARTICLE);
        if (null == article) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        final String articleId = article.optString(Keys.OBJECT_ID);
        LOGGER.log(Level.DEBUG, "Article [id={0}]", articleId);

        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "article.ftl");

        try {
            LOGGER.log(Level.TRACE, "Article [title={0}]", article.getString(Article.ARTICLE_TITLE));
            articleQueryService.markdown(article);

            article.put(Article.ARTICLE_T_CREATE_DATE, new Date(article.optLong(Article.ARTICLE_CREATED)));
            article.put(Article.ARTICLE_T_UPDATE_DATE, new Date(article.optLong(Article.ARTICLE_UPDATED)));
            // For <meta name="description" content="${article.articleAbstract}"/>
            final String metaDescription = Jsoup.parse(article.optString(Article.ARTICLE_ABSTRACT)).text();
            article.put(Article.ARTICLE_ABSTRACT, metaDescription);
            final JSONObject preference = optionQueryService.getPreference();
            if (preference.getBoolean(Option.ID_C_ENABLE_ARTICLE_UPDATE_HINT)) {
                article.put(Common.HAS_UPDATED, articleQueryService.hasUpdated(article));
            } else {
                article.put(Common.HAS_UPDATED, false);
            }

            final JSONObject author = articleQueryService.getAuthor(article);
            final String authorName = author.getString(User.USER_NAME);
            article.put(Common.AUTHOR_NAME, authorName);
            final String authorId = author.getString(Keys.OBJECT_ID);
            article.put(Common.AUTHOR_ID, authorId);
            article.put(Common.AUTHOR_ROLE, author.getString(User.USER_ROLE));
            final String userAvatar = author.optString(UserExt.USER_AVATAR);
            article.put(Common.AUTHOR_THUMBNAIL_URL, userAvatar);
            dataModelService.fillCategory(article);
            final Map<String, Object> dataModel = renderer.getDataModel();

            prepareShowArticle(preference, dataModel, article);

            final HttpServletResponse response = context.getResponse();
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);
            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING),
                    (String) context.attr(Keys.TEMAPLTE_DIR_NAME), dataModel);

            if (!StatisticMgmtService.hasBeenServed(context, response)) {
                articleMgmtService.incViewCount(articleId);
            }

            statisticMgmtService.incBlogViewCount(context, response);

            // Fire [Before Render Article] event
            final JSONObject eventData = new JSONObject();

            JSONObject cateS = null;
            try {
                JSONObject cate = categoryTagRepository.getByTagId(articleId, 1, Integer.MAX_VALUE);
                int size = cate.optJSONArray("rslts").length();
                cateS = (JSONObject) cate.optJSONArray("rslts").get(size - 1);
                String categoryOId = cateS.optString("category_oId");
                cateS = categoryQueryService.getCategory(categoryOId);
                article.put("articleCategory", cateS.opt("categoryTitle"));
                try {
                    article.put("categoryURI", cateS.opt("categoryURI"));
                } catch (JSONException | NullPointerException e) {
                    article.put("categoryURI", cateS.opt(""));
                }
            } catch (JSONException | NullPointerException e) {
                article.put("articleCategory", "");
            }

            // Bolo 告诉前端用户是否已经登录
            String logged = String.valueOf(Solos.isLoggedIn(context));
            article.put("logged", logged);

            eventData.put(Article.ARTICLE, article);
            eventManager.fireEventSynchronously(new Event<>(EventTypes.BEFORE_RENDER_ARTICLE, eventData));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Gets the random articles.
     *
     * @param preference the specified preference
     * @return a list of articles, returns an empty list if not found
     */
    private List<JSONObject> getRandomArticles(final JSONObject preference) {
        try {
            final int displayCnt = preference.getInt(Option.ID_C_RANDOM_ARTICLES_DISPLAY_CNT);
            final List<JSONObject> ret = articleQueryService.getArticlesRandomly(displayCnt);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            return Collections.emptyList();
        }
    }

    /**
     * Prepares the specified data model for rendering author articles.
     *
     * @param pageNums       the specified page numbers
     * @param dataModel      the specified data model
     * @param pageCount      the specified page count
     * @param currentPageNum the specified current page number
     * @param articles       the specified articles
     * @param author         the specified author
     */
    private void prepareShowAuthorArticles(final List<Integer> pageNums,
            final Map<String, Object> dataModel,
            final int pageCount,
            final int currentPageNum,
            final List<JSONObject> articles,
            final JSONObject author) {
        if (0 != pageNums.size()) {
            dataModel.put(Pagination.PAGINATION_FIRST_PAGE_NUM, pageNums.get(0));
            dataModel.put(Pagination.PAGINATION_LAST_PAGE_NUM, pageNums.get(pageNums.size() - 1));
        }
        dataModel.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
        dataModel.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

        dataModel.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, currentPageNum);
        final String previousPageNum = Integer.toString(currentPageNum > 1 ? currentPageNum - 1 : 0);

        dataModel.put(Pagination.PAGINATION_PREVIOUS_PAGE_NUM, "0".equals(previousPageNum) ? "" : previousPageNum);
        if (pageCount == currentPageNum + 1) { // The next page is the last page
            dataModel.put(Pagination.PAGINATION_NEXT_PAGE_NUM, "");
        } else {
            dataModel.put(Pagination.PAGINATION_NEXT_PAGE_NUM, currentPageNum + 1);
        }

        dataModel.put(Article.ARTICLES, articles);
        final String authorId = author.optString(Keys.OBJECT_ID);

        dataModel.put(Common.PATH, "/authors/" + authorId);
        dataModel.put(Keys.OBJECT_ID, authorId);

        dataModel.put(Common.AUTHOR_NAME, author.optString(User.USER_NAME));

        final String userAvatar = author.optString(UserExt.USER_AVATAR);
        dataModel.put(Common.AUTHOR_THUMBNAIL_URL, userAvatar);

        dataModel.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, currentPageNum);
    }

    /**
     * Prepares the specified data model for rendering archive articles.
     *
     * @param preference        the specified preference
     * @param dataModel         the specified data model
     * @param articles          the specified articles
     * @param currentPageNum    the specified current page number
     * @param pageCount         the specified page count
     * @param archiveDateString the specified archive data string
     * @param archiveDate       the specified archive date
     * @return page title for caching
     */
    private String prepareShowArchiveArticles(final JSONObject preference,
            final Map<String, Object> dataModel,
            final List<JSONObject> articles,
            final int currentPageNum,
            final int pageCount,
            final String archiveDateString,
            final JSONObject archiveDate) {
        final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);
        final int windowSize = preference.getInt(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE);

        final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

        dataModel.put(Article.ARTICLES, articles);
        final String previousPageNum = Integer.toString(currentPageNum > 1 ? currentPageNum - 1 : 0);

        dataModel.put(Pagination.PAGINATION_PREVIOUS_PAGE_NUM, "0".equals(previousPageNum) ? "" : previousPageNum);
        if (pageCount == currentPageNum + 1) { // The next page is the last page
            dataModel.put(Pagination.PAGINATION_NEXT_PAGE_NUM, "");
        } else {
            dataModel.put(Pagination.PAGINATION_NEXT_PAGE_NUM, currentPageNum + 1);
        }
        dataModel.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, currentPageNum);
        dataModel.put(Pagination.PAGINATION_FIRST_PAGE_NUM, pageNums.get(0));
        dataModel.put(Pagination.PAGINATION_LAST_PAGE_NUM, pageNums.get(pageNums.size() - 1));
        dataModel.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
        dataModel.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);
        dataModel.put(Common.PATH, "/archives/" + archiveDateString);
        dataModel.put(Keys.OBJECT_ID, archiveDate.getString(Keys.OBJECT_ID));

        final long time = archiveDate.getLong(ArchiveDate.ARCHIVE_TIME);
        final String dateString = DateFormatUtils.format(time, "yyyy/MM");
        final String[] dateStrings = dateString.split("/");
        final String year = dateStrings[0];
        final String month = dateStrings[1];

        archiveDate.put(ArchiveDate.ARCHIVE_DATE_YEAR, year);
        final String language = Locales.getLanguage(preference.getString(Option.ID_C_LOCALE_STRING));
        String ret;

        if ("en".equals(language)) {
            archiveDate.put(ArchiveDate.ARCHIVE_DATE_MONTH, Dates.EN_MONTHS.get(month));
            ret = Dates.EN_MONTHS.get(month) + " " + year;
        } else {
            archiveDate.put(ArchiveDate.ARCHIVE_DATE_MONTH, month);
            ret = year + " " + dataModel.get("yearLabel") + " " + month + " " + dataModel.get("monthLabel");
        }
        dataModel.put(ArchiveDate.ARCHIVE_DATE, archiveDate);

        return ret;
    }

    /**
     * Prepares the specified data model for rendering article.
     *
     * @param preference the specified preference
     * @param dataModel  the specified data model
     * @param article    the specified article
     * @throws Exception exception
     */
    private void prepareShowArticle(final JSONObject preference, final Map<String, Object> dataModel,
            final JSONObject article)
            throws Exception {
        article.put(Common.COMMENTABLE,
                preference.getBoolean(Option.ID_C_COMMENTABLE) && article.getBoolean(Article.ARTICLE_COMMENTABLE));
        article.put(Common.PERMALINK, article.getString(Article.ARTICLE_PERMALINK));
        dataModel.put(Article.ARTICLE, article);
        final String articleId = article.getString(Keys.OBJECT_ID);

        Stopwatchs.start("Get Article Sign");

        LOGGER.debug("Getting article sign....");
        final JSONObject sign = articleQueryService.getSign(article.getString(Article.ARTICLE_SIGN_ID), preference);
        final String articleTitle = article.optString(Article.ARTICLE_TITLE);
        final String author = article.optString(Common.AUTHOR_NAME);
        final String url = Latkes.getServePath() + article.optString(Article.ARTICLE_PERMALINK);
        String signHtml = sign.optString(Sign.SIGN_HTML);
        // 签名档内置模板变量 https://github.com/b3log/solo/issues/12758
        signHtml = StringUtils.replace(signHtml, "{title}", articleTitle);
        signHtml = StringUtils.replace(signHtml, "{author}", author);
        signHtml = StringUtils.replace(signHtml, "{url}", url);
        signHtml = StringUtils.replace(signHtml, "{blog}", Latkes.getServePath());
        sign.put(Sign.SIGN_HTML, signHtml);
        article.put(Common.ARTICLE_SIGN, sign);
        LOGGER.debug("Got article sign");
        Stopwatchs.end();

        Stopwatchs.start("Get Next Article");
        LOGGER.debug("Getting the next article....");
        final JSONObject nextArticle = articleQueryService.getNextArticle(articleId);

        if (null != nextArticle) {
            dataModel.put(Common.NEXT_ARTICLE_PERMALINK, nextArticle.getString(Article.ARTICLE_PERMALINK));
            dataModel.put(Common.NEXT_ARTICLE_TITLE, nextArticle.getString(Article.ARTICLE_TITLE));
            dataModel.put(Common.NEXT_ARTICLE_ABSTRACT, nextArticle.getString(Article.ARTICLE_ABSTRACT));
            LOGGER.debug("Got the next article");
        }
        Stopwatchs.end();

        Stopwatchs.start("Get Previous Article");
        LOGGER.debug("Getting the previous article....");
        final JSONObject previousArticle = articleQueryService.getPreviousArticle(articleId);
        if (null != previousArticle) {
            dataModel.put(Common.PREVIOUS_ARTICLE_PERMALINK, previousArticle.getString(Article.ARTICLE_PERMALINK));
            dataModel.put(Common.PREVIOUS_ARTICLE_TITLE, previousArticle.getString(Article.ARTICLE_TITLE));
            dataModel.put(Common.PREVIOUS_ARTICLE_ABSTRACT, previousArticle.getString(Article.ARTICLE_ABSTRACT));
            LOGGER.debug("Got the previous article");
        }
        Stopwatchs.end();

        Stopwatchs.start("Get Article CMTs");
        LOGGER.debug("Getting article's comments....");
        final int cmtCount = article.getInt(Article.ARTICLE_COMMENT_COUNT);
        if (0 != cmtCount) {
            final List<JSONObject> articleComments = commentQueryService.getComments(articleId);
            dataModel.put(Article.ARTICLE_COMMENTS_REF, articleComments);
        } else {
            dataModel.put(Article.ARTICLE_COMMENTS_REF, Collections.emptyList());
        }
        LOGGER.debug("Got article's comments");
        Stopwatchs.end();

        dataModel.put(Option.ID_C_RANDOM_ARTICLES_DISPLAY_CNT,
                preference.getInt(Option.ID_C_RANDOM_ARTICLES_DISPLAY_CNT));
        dataModel.put(Option.ID_C_RELEVANT_ARTICLES_DISPLAY_CNT,
                preference.getInt(Option.ID_C_RELEVANT_ARTICLES_DISPLAY_CNT));
    }
}
