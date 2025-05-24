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
package org.b3log.solo.service;

import static org.b3log.solo.model.Article.ARTICLE_CONTENT;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventManager;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.Plugin;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.plugin.ViewLoadEventData;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.latke.util.Dates;
import org.b3log.latke.util.Locales;
import org.b3log.latke.util.Paginator;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.latke.util.Templates;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.bolo.Global;
import org.b3log.solo.bolo.prop.Options;
import org.b3log.solo.model.ArchiveDate;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Category;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Follow;
import org.b3log.solo.model.Link;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Tag;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.ArchiveDateRepository;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CategoryRepository;
import org.b3log.solo.repository.CategoryTagRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.FollowRepository;
import org.b3log.solo.repository.LinkRepository;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.PageRepository;
import org.b3log.solo.repository.TagArticleRepository;
import org.b3log.solo.repository.TagRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.util.Markdowns;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.Solos;
import org.json.JSONException;
import org.json.JSONObject;

import freemarker.template.Template;

/**
 * Data model service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @since 0.3.1
 */
@Service
public class DataModelService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DataModelService.class);

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * Archive date repository.
     */
    @Inject
    private ArchiveDateRepository archiveDateRepository;

    /**
     * Category repository.
     */
    @Inject
    private CategoryRepository categoryRepository;

    /**
     * Tag-Article repository.
     */
    @Inject
    private TagArticleRepository tagArticleRepository;

    /**
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

    /**
     * Category-Tag repository.
     */
    @Inject
    private CategoryTagRepository categoryTagRepository;

    /**
     * Link repository.
     */
    @Inject
    private LinkRepository linkRepository;

    /**
     * Follow repository.
     */
    @Inject
    private FollowRepository followRepository;

    /**
     * Page repository.
     */
    @Inject
    private PageRepository pageRepository;

    /**
     * Statistic query service.
     */
    @Inject
    private StatisticQueryService statisticQueryService;

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Option query service..
     */
    @Inject
    private OptionQueryService optionQueryService;

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
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Event manager.
     */
    @Inject
    private EventManager eventManager;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    @Inject
    private CategoryQueryService categoryQueryService;

    /**
     * Fills articles in index.ftl.
     *
     * @param context        the specified HTTP servlet request context
     * @param dataModel      data model
     * @param currentPageNum current page number
     * @param preference     the specified preference
     * @throws ServiceException service exception
     */
    public void fillIndexArticles(final RequestContext context, final Map<String, Object> dataModel,
            final int currentPageNum, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Index Articles");

        try {
            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);
            final int windowSize = preference.getInt(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE);

            final Query query = new Query().setPage(currentPageNum, pageSize).setFilter(new PropertyFilter(
                    Article.ARTICLE_STATUS, FilterOperator.EQUAL, Article.ARTICLE_STATUS_C_PUBLISHED));

            final Template template = Skins.getSkinTemplate(context, "index.ftl");
            boolean isArticles1 = false;
            if (null == template) {
                LOGGER.debug("The skin dose not contain [index.ftl] template");
            } else // See https://github.com/b3log/solo/issues/179 for more details
            if (Templates.hasExpression(template, "<#list articles1 as article>")) {
                isArticles1 = true;
                query.addSort(Article.ARTICLE_CREATED, SortDirection.DESCENDING);
                LOGGER.trace("Query ${articles1} in index.ftl");
            } else { // <#list articles as article>
                query.addSort(Article.ARTICLE_PUT_TOP, SortDirection.DESCENDING);
                if (preference.getBoolean(Option.ID_C_ENABLE_ARTICLE_UPDATE_HINT)) {
                    query.addSort(Article.ARTICLE_UPDATED, SortDirection.DESCENDING);
                } else {
                    query.addSort(Article.ARTICLE_CREATED, SortDirection.DESCENDING);
                }
            }

            final JSONObject articlesResult = articleRepository.get(query);
            List<JSONObject> articles = CollectionUtils.jsonArrayToList(articlesResult.optJSONArray(Keys.RESULTS));

            final int pageCount = articlesResult.optJSONObject(Pagination.PAGINATION)
                    .optInt(Pagination.PAGINATION_PAGE_COUNT);
            setArticlesExProperties(context, articles, preference);

            final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);
            if (!pageNums.isEmpty()) {
                dataModel.put(Pagination.PAGINATION_FIRST_PAGE_NUM, pageNums.get(0));
                dataModel.put(Pagination.PAGINATION_LAST_PAGE_NUM, pageNums.get(pageNums.size() - 1));
            }
            dataModel.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            dataModel.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

            if (!isArticles1) {
                dataModel.put(Article.ARTICLES, articles);
            } else {
                dataModel.put(Article.ARTICLES + "1", articles);
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills index articles failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills links.
     *
     * @param dataModel data model
     * @throws ServiceException service exception
     */
    public void fillLinks(final Map<String, Object> dataModel) throws ServiceException {
        Stopwatchs.start("Fill Links");
        try {
            final Map<String, SortDirection> sorts = new HashMap<>();

            sorts.put(Link.LINK_ORDER, SortDirection.ASCENDING);
            final Query query = new Query().addSort(Link.LINK_ORDER, SortDirection.ASCENDING).setPageCount(1);
            final List<JSONObject> links = linkRepository.getList(query);

            dataModel.put(Link.LINKS, links);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Fills links failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
        Stopwatchs.end();
    }

    /**
     * Fills Follows.
     *
     * @param dataModel data model
     * @throws ServiceException service exception
     */
    public void fillFollows(final Map<String, Object> dataModel) throws ServiceException {
        Stopwatchs.start("Fill Follows");
        try {
            final Map<String, SortDirection> sorts = new HashMap<>();

            sorts.put(Follow.FOLLOW_ORDER, SortDirection.ASCENDING);
            final Query query = new Query().addSort(Follow.FOLLOW_ORDER, SortDirection.ASCENDING).setPageCount(1);
            final List<JSONObject> follows = followRepository.getList(query);

            dataModel.put(Follow.FOLLOWS, follows);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Fills follows failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
        Stopwatchs.end();
    }

    /**
     * Fills tags.
     *
     * @param dataModel data model
     * @throws ServiceException service exception
     */
    public void fillTags(final Map<String, Object> dataModel) throws ServiceException {
        Stopwatchs.start("Fill Tags");
        try {
            final List<JSONObject> tags = tagQueryService.getTagsOfPublishedArticles();
            dataModel.put(Tag.TAGS, tags);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills tags failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }

        Stopwatchs.end();
    }

    /**
     * Fills categories.
     *
     * @param dataModel data model
     * @throws ServiceException service exception
     */
    public void fillCategories(final Map<String, Object> dataModel) throws ServiceException {
        Stopwatchs.start("Fill Categories");

        try {
            LOGGER.debug("Filling categories....");
            final List<JSONObject> categories = categoryRepository.getMostUsedCategories(Integer.MAX_VALUE);
            dataModel.put(Category.CATEGORIES, categories);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Fills categories failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills most used categories.
     *
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillMostUsedCategories(final Map<String, Object> dataModel, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Most Used Categories");

        try {
            LOGGER.debug("Filling most used categories....");
            final int mostUsedCategoryDisplayCnt = Integer.MAX_VALUE; // XXX: preference instead
            final List<JSONObject> categories = categoryRepository.getMostUsedCategories(mostUsedCategoryDisplayCnt);
            dataModel.put(Common.MOST_USED_CATEGORIES, categories);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Fills most used categories failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills most used tags.
     *
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillMostUsedTags(final Map<String, Object> dataModel, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Most Used Tags");

        try {
            LOGGER.debug("Filling most used tags....");
            final int mostUsedTagDisplayCnt = preference.getInt(Option.ID_C_MOST_USED_TAG_DISPLAY_CNT);
            final List<JSONObject> tags = tagArticleRepository.getMostUsedTags(mostUsedTagDisplayCnt);
            dataModel.put(Common.MOST_USED_TAGS, tags);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills most used tags failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills archive dates.
     *
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillArchiveDates(final Map<String, Object> dataModel, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Archive Dates");

        try {
            LOGGER.debug("Filling archive dates....");
            final List<JSONObject> archiveDates = archiveDateRepository.getArchiveDates();
            final List<JSONObject> archiveDates2 = new ArrayList<>();
            dataModel.put(ArchiveDate.ARCHIVE_DATES, archiveDates2);
            if (archiveDates.isEmpty()) {
                return;
            }

            int maxArchiveResult = -1;

            try {
                BeanManager beanManager = BeanManager.getInstance();
                OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);
                JSONObject maxArchiveOpt = optionRepository.get(Option.ID_C_MAX_ARCHIVE);
                String maxArchive = (String) maxArchiveOpt.get(Option.OPTION_VALUE);
                maxArchiveResult = Integer.parseInt(maxArchive);
            } catch (NullPointerException | NumberFormatException NPE) {
            }

            // 合法性检查
            if (maxArchiveResult < -1 || maxArchiveResult > archiveDates.size()) {
                maxArchiveResult = -1;
            }

            if (maxArchiveResult == -1) {
                maxArchiveResult = archiveDates.size();
            }

            if (maxArchiveResult != 0) {
                archiveDates2.add(archiveDates.get(0));

                if (1 < archiveDates.size()) { // XXX: Workaround, remove the duplicated archive dates
                    for (int i = 1; i < maxArchiveResult; i++) {
                        final JSONObject archiveDate = archiveDates.get(i);

                        final long time = archiveDate.getLong(ArchiveDate.ARCHIVE_TIME);
                        final String dateString = DateFormatUtils.format(time, "yyyy/MM");

                        final JSONObject last = archiveDates2.get(archiveDates2.size() - 1);
                        final String lastDateString = DateFormatUtils.format(last.getLong(ArchiveDate.ARCHIVE_TIME),
                                "yyyy/MM");

                        if (!dateString.equals(lastDateString)) {
                            archiveDates2.add(archiveDate);
                        } else {
                            LOGGER.log(Level.DEBUG, "Found a duplicated archive date [{0}]", dateString);
                        }
                    }
                }
            }

            final String localeString = preference.getString(Option.ID_C_LOCALE_STRING);
            final String language = Locales.getLanguage(localeString);

            for (final JSONObject archiveDate : archiveDates2) {
                final long time = archiveDate.getLong(ArchiveDate.ARCHIVE_TIME);
                final String dateString = DateFormatUtils.format(time, "yyyy/MM");
                final String[] dateStrings = dateString.split("/");
                final String year = dateStrings[0];
                final String month = dateStrings[1];

                archiveDate.put(ArchiveDate.ARCHIVE_DATE_YEAR, year);

                archiveDate.put(ArchiveDate.ARCHIVE_DATE_MONTH, month);
                if ("en".equals(language)) {
                    final String monthName = Dates.EN_MONTHS.get(month);

                    archiveDate.put(Common.MONTH_NAME, monthName);
                }
            }

            dataModel.put(ArchiveDate.ARCHIVE_DATES, archiveDates2);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills archive dates failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills most view count articles.
     *
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillMostViewCountArticles(final Map<String, Object> dataModel, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Most View Articles");
        try {
            LOGGER.debug("Filling the most view count articles....");
            final int mostCommentArticleDisplayCnt = preference.getInt(Option.ID_C_MOST_VIEW_ARTICLE_DISPLAY_CNT);
            final List<JSONObject> mostViewCountArticles = articleRepository
                    .getMostViewCountArticles(mostCommentArticleDisplayCnt);

            dataModel.put(Common.MOST_VIEW_COUNT_ARTICLES, mostViewCountArticles);

        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills most view count articles failed", e);
            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills most comments articles.
     *
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillMostCommentArticles(final Map<String, Object> dataModel, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Most CMMTs Articles");

        try {
            LOGGER.debug("Filling most comment articles....");
            final int mostCommentArticleDisplayCnt = preference.getInt(Option.ID_C_MOST_COMMENT_ARTICLE_DISPLAY_CNT);
            final List<JSONObject> mostCommentArticles = articleRepository
                    .getMostCommentArticles(mostCommentArticleDisplayCnt);

            dataModel.put(Common.MOST_COMMENT_ARTICLES, mostCommentArticles);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills most comment articles failed", e);
            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills post articles recently.
     *
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillRecentArticles(final Map<String, Object> dataModel, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Recent Articles");

        try {
            final int recentArticleDisplayCnt = preference.getInt(Option.ID_C_RECENT_ARTICLE_DISPLAY_CNT);
            final List<JSONObject> recentArticles = articleRepository.getRecentArticles(recentArticleDisplayCnt);
            dataModel.put(Common.RECENT_ARTICLES, recentArticles);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills recent articles failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills post comments recently.
     *
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillRecentComments(final Map<String, Object> dataModel, final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Recent Comments");
        try {
            LOGGER.debug("Filling recent comments....");
            final int recentCommentDisplayCnt = preference.getInt(Option.ID_C_RECENT_COMMENT_DISPLAY_CNT);
            final List<JSONObject> recentComments = commentRepository.getRecentComments(recentCommentDisplayCnt);
            for (final JSONObject comment : recentComments) {
                String commentContent = comment.optString(Comment.COMMENT_CONTENT);
                commentContent = Markdowns.toHTML(commentContent);
                commentContent = Markdowns.clean(commentContent);
                comment.put(Comment.COMMENT_CONTENT, commentContent);
                comment.put(Comment.COMMENT_NAME, comment.getString(Comment.COMMENT_NAME));
                comment.put(Comment.COMMENT_URL, comment.getString(Comment.COMMENT_URL));
                comment.put(Common.IS_REPLY, false);
                comment.put(Comment.COMMENT_T_DATE, new Date(comment.optLong(Comment.COMMENT_CREATED)));
                comment.put("commentDate2", new Date(comment.optLong(Comment.COMMENT_CREATED)));
            }

            dataModel.put(Common.RECENT_COMMENTS, recentComments);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills recent comments failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills favicon URL. 可配置 favicon 图标路径
     * https://github.com/b3log/solo/issues/12706
     *
     * @param dataModel  the specified data model
     * @param preference the specified preference
     */
    public void fillFaviconURL(final Map<String, Object> dataModel, final JSONObject preference) {
        if (null == preference) {
            dataModel.put(Common.FAVICON_URL, Option.DefaultPreference.DEFAULT_FAVICON_URL);
        } else {
            dataModel.put(Common.FAVICON_URL, preference.optString(Option.ID_C_FAVICON_URL));
        }
    }

    /**
     * Fills usite. 展示站点连接 https://github.com/b3log/solo/issues/12719
     *
     * @param dataModel the specified data model
     */
    public void fillUsite(final Map<String, Object> dataModel) {
        try {
            final JSONObject usiteOpt = optionQueryService.getOptionById(Option.ID_C_USITE);
            if (null == usiteOpt) {
                return;
            }

            dataModel.put(Option.ID_C_USITE, new JSONObject(usiteOpt.optString(Option.OPTION_VALUE)));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills usite failed", e);
        }
    }

    /**
     * Fills common parts (header, side and footer).
     *
     * @param context    the specified HTTP servlet request context
     * @param dataModel  the specified data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillCommon(final RequestContext context, final Map<String, Object> dataModel,
            final JSONObject preference) throws ServiceException {
        fillSide(context, dataModel, preference);
        fillBlogHeader(context, dataModel, preference);
        fillBlogFooter(context, dataModel, preference);

        // 支持配置自定义模板变量 https://github.com/b3log/solo/issues/12535
        final Map<String, String> customVars = new HashMap<>();
        final String customVarsStr = preference.optString(Option.ID_C_CUSTOM_VARS);
        final String[] customVarsArray = customVarsStr.split("\\|");
        for (int i = 0; i < customVarsArray.length; i++) {
            final String customVarPair = customVarsArray[i];
            if (StringUtils.isNotBlank(customVarsStr)) {
                final String customVarKey = customVarPair.split("=")[0];
                final String customVarVal = customVarPair.split("=")[1];
                if (StringUtils.isNotBlank(customVarKey) && StringUtils.isNotBlank(customVarVal)) {
                    customVars.put(customVarKey, customVarVal);
                }
            }
        }
        dataModel.put("customVars", customVars);

        dataModel.put(Common.LUTE_AVAILABLE, Markdowns.LUTE_AVAILABLE);
        String hljsTheme = preference.optString(Option.ID_C_HLJS_THEME);
        if (StringUtils.isBlank(hljsTheme)) {
            hljsTheme = Option.DefaultPreference.DEFAULT_HLJS_THEME;
        }
        dataModel.put(Option.ID_C_HLJS_THEME, hljsTheme);

        String showCodeBlockLn = preference.optString(Option.ID_C_SHOW_CODE_BLOCK_LN);
        if (StringUtils.isBlank(showCodeBlockLn)) {
            showCodeBlockLn = Option.DefaultPreference.DEFAULT_SHOW_CODE_BLOCK_LN;
        }
        dataModel.put(Option.ID_C_SHOW_CODE_BLOCK_LN, showCodeBlockLn);

        // 链滴域名设定
        dataModel.put("hacpaiDomain", Global.HACPAI_DOMAIN);
        dataModel.put("fishpiDomain", Global.FISH_PI_DOMAIN);
    }

    /**
     * Fill Admin-Index Charts.
     *
     * @param context   the specified HTTP servlet request context
     * @param dataModel the specified data model
     */
    public void fillCharts(final RequestContext context, final Map<String, Object> dataModel,
            final JSONObject preference) {
        // 每月文章数量统计
        try {
            List<JSONObject> archiveDates = archiveDateRepository.getArchiveDates();
            List<JSONObject> archiveDates2 = new ArrayList<>();
            dataModel.put(ArchiveDate.ARCHIVE_DATES, archiveDates2);
            if (archiveDates.isEmpty()) {
                return;
            }

            int maxArchiveResult = -1;

            try {
                BeanManager beanManager = BeanManager.getInstance();
                OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);
                JSONObject maxArchiveOpt = optionRepository.get(Option.ID_C_MAX_ARCHIVE);
                String maxArchive = (String) maxArchiveOpt.get(Option.OPTION_VALUE);
                maxArchiveResult = Integer.parseInt(maxArchive);
            } catch (NullPointerException | NumberFormatException NPE) {
            }

            // 合法性检查
            if (maxArchiveResult < -1 || maxArchiveResult > archiveDates.size()) {
                maxArchiveResult = -1;
            }

            if (maxArchiveResult == -1) {
                maxArchiveResult = archiveDates.size();
            }

            if (maxArchiveResult != 0) {
                archiveDates2.add(archiveDates.get(0));

                if (1 < archiveDates.size()) { // XXX: Workaround, remove the duplicated archive dates
                    for (int i = 1; i < maxArchiveResult; i++) {
                        final JSONObject archiveDate = archiveDates.get(i);

                        final long time = archiveDate.getLong(ArchiveDate.ARCHIVE_TIME);
                        final String dateString = DateFormatUtils.format(time, "yyyy/MM");

                        final JSONObject last = archiveDates2.get(archiveDates2.size() - 1);
                        final String lastDateString = DateFormatUtils.format(last.getLong(ArchiveDate.ARCHIVE_TIME),
                                "yyyy/MM");

                        if (!dateString.equals(lastDateString)) {
                            archiveDates2.add(archiveDate);
                        } else {
                            LOGGER.log(Level.DEBUG, "Found a duplicated archive date [{0}]", dateString);
                        }
                    }
                }
            }

            final String localeString = preference.getString(Option.ID_C_LOCALE_STRING);
            final String language = Locales.getLanguage(localeString);

            for (final JSONObject archiveDate : archiveDates2) {
                final long time = archiveDate.getLong(ArchiveDate.ARCHIVE_TIME);
                final String dateString = DateFormatUtils.format(time, "yyyy/MM");
                final String[] dateStrings = dateString.split("/");
                final String year = dateStrings[0];
                final String month = dateStrings[1];

                archiveDate.put(ArchiveDate.ARCHIVE_DATE_YEAR, year);

                archiveDate.put(ArchiveDate.ARCHIVE_DATE_MONTH, month);
                if ("en".equals(language)) {
                    final String monthName = Dates.EN_MONTHS.get(month);

                    archiveDate.put(Common.MONTH_NAME, monthName);
                }
            }

            Collections.reverse(archiveDates2);
            List<JSONObject> archiveDates3 = new ArrayList<>();
            // 填补没有发文记录的月份
            int lastYear = -1;
            int lastMonth = -1;
            for (final JSONObject archiveDate : archiveDates2) {
                final long time = archiveDate.getLong(ArchiveDate.ARCHIVE_TIME);
                final String dateString = DateFormatUtils.format(time, "yyyy/MM");
                final String[] dateStrings = dateString.split("/");
                final int year = Integer.parseInt(dateStrings[0]);
                final int month = Integer.parseInt(dateStrings[1]);
                if (lastYear == -1 && lastMonth == -1) {
                    lastYear = year;
                    lastMonth = month;
                    archiveDates3.add(archiveDate);
                    continue;
                }
                if (year == lastYear) {
                    if (month - lastMonth > 1) {
                        // 补全月份
                        for (int i = lastMonth + 1; i < month; i++) {
                            JSONObject addArchiveDate = new JSONObject();
                            addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_T_PUBLISHED_ARTICLE_COUNT, 0);
                            addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_YEAR, "" + year);
                            String addMonth = String.valueOf(i).length() == 1 ? 0 + "" + i : String.valueOf(i);
                            addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_MONTH, addMonth);
                            archiveDates3.add(addArchiveDate);
                        }
                    }
                } else {
                    // 跨年
                    if (year - lastYear == 1) {
                        // 相隔一年
                        if (!(lastMonth == 12 && month == 1)) {
                            // 空出了几个月份，先补去年的
                            for (int i = lastMonth + 1; i <= 12; i++) {
                                JSONObject addArchiveDate = new JSONObject();
                                addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_T_PUBLISHED_ARTICLE_COUNT, 0);
                                addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_YEAR, "" + lastYear);
                                String addMonth = String.valueOf(i).length() == 1 ? 0 + "" + i : String.valueOf(i);
                                addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_MONTH, addMonth);
                                archiveDates3.add(addArchiveDate);
                            }
                            // 再补今年的
                            for (int i = 1; i < month; i++) {
                                JSONObject addArchiveDate = new JSONObject();
                                addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_T_PUBLISHED_ARTICLE_COUNT, 0);
                                addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_YEAR, "" + year);
                                String addMonth = String.valueOf(i).length() == 1 ? 0 + "" + i : String.valueOf(i);
                                addArchiveDate.put(ArchiveDate.ARCHIVE_DATE_MONTH, addMonth);
                                archiveDates3.add(addArchiveDate);
                            }
                        }
                    }
                }
                archiveDates3.add(archiveDate);
                lastYear = year;
                lastMonth = month;
            }

            Collections.reverse(archiveDates3);

            if (archiveDates3.size() > 20) {
                archiveDates3 = archiveDates3.subList(0, 20);
            }
            dataModel.put("latestArchives", archiveDates3);
        } catch (Exception ignored) {
        }

        // 标签 Top5
        // tagPublishedRefCount 总计
        // tagTitle 标签名称
        try {
            List<JSONObject> tags = tagQueryService.getTags();
            Collections.sort(tags,
                    Comparator.comparingInt(o -> Integer.parseInt(o.optString(Tag.TAG_T_PUBLISHED_REFERENCE_COUNT))));
            Collections.reverse(tags);
            if (tags.size() > 5) {
                tags = tags.subList(0, 5);
            }
            dataModel.put("tagsTop5", tags);
        } catch (Exception ignored) {
        }
    }

    /**
     * Fills footer.ftl.
     *
     * @param context    the specified HTTP servlet request context
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    private void fillBlogFooter(final RequestContext context, final Map<String, Object> dataModel,
            final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Footer");
        try {
            LOGGER.debug("Filling footer....");
            final String blogTitle = preference.getString(Option.ID_C_BLOG_TITLE);
            dataModel.put(Option.ID_C_BLOG_TITLE, blogTitle);
            dataModel.put("blogHost", Latkes.getServePath());
            dataModel.put(Common.VERSION, SoloServletListener.VERSION);
            dataModel.put(Common.BOLO_VERSION, SoloServletListener.BOLO_VERSION);
            dataModel.put(Common.STATIC_RESOURCE_VERSION, Latkes.getStaticResourceVersion());
            dataModel.put(Common.YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            String footerContent = "";
            final JSONObject opt = optionQueryService.getOptionById(Option.ID_C_FOOTER_CONTENT);
            if (null != opt) {
                footerContent = opt.optString(Option.OPTION_VALUE);
            }
            dataModel.put(Option.ID_C_FOOTER_CONTENT, footerContent);
            dataModel.put(Keys.Server.STATIC_SERVER, Latkes.getStaticServer());
            dataModel.put(Keys.Server.SERVER, Latkes.getServer());
            dataModel.put(Common.IS_INDEX, "/".equals(context.requestURI()));
            dataModel.put(User.USER_NAME, "");
            final JSONObject currentUser = Solos.getCurrentUser(context.getRequest(), context.getResponse());
            if (null != currentUser) {
                final String userAvatar = currentUser.optString(UserExt.USER_AVATAR);
                dataModel.put(Common.GRAVATAR, userAvatar);
                dataModel.put(User.USER_NAME, currentUser.optString(User.USER_NAME));
            }

            // Activates plugins
            final ViewLoadEventData data = new ViewLoadEventData();
            data.setViewName("footer.ftl");
            data.setDataModel(dataModel);
            eventManager.fireEventSynchronously(new Event<>(Keys.FREEMARKER_ACTION, data));
            if (StringUtils.isBlank((String) dataModel.get(Plugin.PLUGINS))) {
                // There is no plugin for this template, fill ${plugins} with blank.
                dataModel.put(Plugin.PLUGINS, "");
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills blog footer failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills header.ftl.
     *
     * @param context    the specified HTTP servlet request context
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    private void fillBlogHeader(final RequestContext context, final Map<String, Object> dataModel,
            final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Header");
        try {
            LOGGER.debug("Filling header....");
            final String topBarHTML = getTopBarHTML(context);
            dataModel.put(Common.LOGIN_URL, userQueryService.getLoginURL(Common.ADMIN_INDEX_URI));
            dataModel.put(Common.LOGOUT_URL, userQueryService.getLogoutURL());
            dataModel.put(Common.ONLINE_VISITOR_CNT, StatisticQueryService.getOnlineVisitorCount());
            dataModel.put(Common.TOP_BAR, topBarHTML);
            dataModel.put(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT,
                    preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT));
            dataModel.put(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE,
                    preference.getInt(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE));
            dataModel.put(Option.ID_C_LOCALE_STRING, preference.getString(Option.ID_C_LOCALE_STRING));
            dataModel.put(Option.ID_C_BLOG_TITLE, preference.getString(Option.ID_C_BLOG_TITLE));
            dataModel.put(Option.ID_C_BLOG_SUBTITLE, preference.getString(Option.ID_C_BLOG_SUBTITLE));
            dataModel.put(Option.ID_C_HTML_HEAD, preference.getString(Option.ID_C_HTML_HEAD));
            String metaKeywords = preference.getString(Option.ID_C_META_KEYWORDS);
            if (StringUtils.isBlank(metaKeywords)) {
                metaKeywords = "";
            }
            dataModel.put(Option.ID_C_META_KEYWORDS, metaKeywords);
            String metaDescription = preference.getString(Option.ID_C_META_DESCRIPTION);
            if (StringUtils.isBlank(metaDescription)) {
                metaDescription = "";
            }
            dataModel.put(Option.ID_C_META_DESCRIPTION, metaDescription);
            dataModel.put(Common.YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            dataModel.put(Common.IS_LOGGED_IN,
                    null != Solos.getCurrentUser(context.getRequest(), context.getResponse()));
            dataModel.put(Common.FAVICON_API, Solos.FAVICON_API);
            final String noticeBoard = preference.getString(Option.ID_C_NOTICE_BOARD);
            dataModel.put(Option.ID_C_NOTICE_BOARD, noticeBoard);
            // 皮肤不显示访客用户 https://github.com/b3log/solo/issues/12752
            final Query query = new Query().setPageCount(1)
                    .setFilter(new PropertyFilter(User.USER_ROLE, FilterOperator.NOT_EQUAL, Role.VISITOR_ROLE));
            final List<JSONObject> userList = userRepository.getList(query);
            dataModel.put(User.USERS, userList);
            final JSONObject admin = userRepository.getAdmin();
            dataModel.put(Common.ADMIN_USER, admin);
            // 返回 Bolo 设定的链滴用户名
            String userName = "";
            try {
                BeanManager beanManager = BeanManager.getInstance();
                UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);
                userName = userQueryService.getB3username();
                if (Option.DefaultPreference.DEFAULT_B3LOG_USERNAME.equals(userName)) {
                    userName = "";
                }
            } catch (NullPointerException NPE) {
            }
            dataModel.put(Option.ID_C_HACPAI_USER, userName);
            // 交互式开关是否开启
            try {
                String interactive = Options.get(Option.ID_C_INTERACTIVE);
                if (!interactive.isEmpty()) {
                    dataModel.put(Option.ID_C_INTERACTIVE, interactive);
                } else {
                    dataModel.put(Option.ID_C_INTERACTIVE, "on");
                }

            } catch (Exception e) {
                dataModel.put(Option.ID_C_INTERACTIVE, "on");
            }
            // 链滴域名设定
            dataModel.put("hacpaiDomain", Global.HACPAI_DOMAIN);
            dataModel.put("fishpiDomain", Global.FISH_PI_DOMAIN);
            final String skinDirName = (String) context.attr(Keys.TEMAPLTE_DIR_NAME);
            dataModel.put(Option.ID_C_SKIN_DIR_NAME, skinDirName);
            Keys.fillRuntime(dataModel);
            fillMinified(dataModel);
            fillPageNavigations(dataModel);
            fillStatistic(dataModel);
            fillMostUsedTags(dataModel, preference);
            fillArchiveDates(dataModel, preference);
            fillMostUsedCategories(dataModel, preference);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills blog header failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills minified directory and file postfix for static JavaScript, CSS.
     *
     * @param dataModel the specified data model
     */
    public void fillMinified(final Map<String, Object> dataModel) {
        switch (Latkes.getRuntimeMode()) {
            case DEVELOPMENT:
                dataModel.put(Common.MINI_POSTFIX, "");
                break;

            case PRODUCTION:
                dataModel.put(Common.MINI_POSTFIX, Common.MINI_POSTFIX_VALUE);
                break;

            default:
                throw new AssertionError();
        }
    }

    /**
     * Fills side.ftl.
     *
     * @param context    the specified HTTP servlet request context
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    private void fillSide(final RequestContext context, final Map<String, Object> dataModel,
            final JSONObject preference)
            throws ServiceException {
        Stopwatchs.start("Fill Side");
        try {
            LOGGER.debug("Filling side....");

            Template template = Skins.getSkinTemplate(context, "side.ftl");
            if (null == template) {
                LOGGER.debug("The skin dose not contain [side.ftl] template");

                template = Skins.getSkinTemplate(context, "index.ftl");
                if (null == template) {
                    LOGGER.debug("The skin dose not contain [index.ftl] template");
                    return;
                }
            }

            if (Templates.hasExpression(template, "<#list recentArticles as article>")) {
                fillRecentArticles(dataModel, preference);
            }

            if (Templates.hasExpression(template, "<#list links as link>")) {
                fillLinks(dataModel);
            }

            if (Templates.hasExpression(template, "<#list recentComments as comment>")) {
                fillRecentComments(dataModel, preference);
            }

            if (Templates.hasExpression(template, "<#list mostCommentArticles as article>")) {
                fillMostCommentArticles(dataModel, preference);
            }

            if (Templates.hasExpression(template, "<#list mostViewCountArticles as article>")) {
                fillMostViewCountArticles(dataModel, preference);
            }
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, "Fills side failed", e);
            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills the specified template.
     *
     * @param context    the specified HTTP servlet request context
     * @param template   the specified template
     * @param dataModel  data model
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void fillUserTemplate(final RequestContext context, final Template template,
            final Map<String, Object> dataModel, final JSONObject preference) throws ServiceException {
        Stopwatchs.start("Fill User Template[name=" + template.getName() + "]");
        try {
            LOGGER.log(Level.DEBUG, "Filling user template[name{0}]", template.getName());

            if (Templates.hasExpression(template, "<#list links as link>")) {
                fillLinks(dataModel);
            }

            if (Templates.hasExpression(template, "<#list follows as follow>")) {
                fillFollows(dataModel);
            }

            if (Templates.hasExpression(template, "<#list tags as tag>")) {
                fillTags(dataModel);
            }

            if (Templates.hasExpression(template, "<#list categories as category>")) {
                fillCategories(dataModel);
            }

            if (Templates.hasExpression(template, "<#list recentComments as comment>")) {
                fillRecentComments(dataModel, preference);
            }

            if (Templates.hasExpression(template, "<#list mostCommentArticles as article>")) {
                fillMostCommentArticles(dataModel, preference);
            }

            if (Templates.hasExpression(template, "<#list mostViewCountArticles as article>")) {
                fillMostViewCountArticles(dataModel, preference);
            }

            if (Templates.hasExpression(template, "<#include \"side.ftl\"/>")) {
                fillSide(context, dataModel, preference);
            }

            final String noticeBoard = preference.getString(Option.ID_C_NOTICE_BOARD);

            dataModel.put(Option.ID_C_NOTICE_BOARD, noticeBoard);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills user template failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills page navigations.
     *
     * @param dataModel data model
     * @throws ServiceException service exception
     */
    private void fillPageNavigations(final Map<String, Object> dataModel) throws ServiceException {
        Stopwatchs.start("Fill Navigations");
        try {
            LOGGER.debug("Filling page navigations....");
            final List<JSONObject> pages = pageRepository.getPages();
            dataModel.put(Common.PAGE_NAVIGATIONS, pages);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Fills page navigations failed", e);
            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Fills statistic.
     *
     * @param dataModel the specified data model
     */
    private void fillStatistic(final Map<String, Object> dataModel) {
        Stopwatchs.start("Fill Statistic");
        try {
            LOGGER.debug("Filling statistic....");
            final JSONObject statistic = statisticQueryService.getStatistic();
            dataModel.put(Option.CATEGORY_C_STATISTIC, statistic);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Sets some extra properties into the specified article with the specified
     * preference, performs content and abstract editor processing.
     * <p>
     * Article ext properties:
     * 
     * <pre>
     * {
     *     ....,
     *     "authorName": "",
     *     "authorId": "",
     *     "authorThumbnailURL": "",
     *     "hasUpdated": boolean
     * }
     * </pre>
     * </p>
     *
     * @param context    the specified HTTP servlet request context
     * @param article    the specified article
     * @param preference the specified preference
     * @throws ServiceException service exception
     * @see #setArticlesExProperties(RequestContext, List, JSONObject)
     */
    private void setArticleExProperties(final RequestContext context, final JSONObject article,
            final JSONObject preference) throws ServiceException {
        try {
            final JSONObject author = articleQueryService.getAuthor(article);
            final String authorName = author.getString(User.USER_NAME);
            article.put(Common.AUTHOR_NAME, authorName);
            final String authorId = author.getString(Keys.OBJECT_ID);
            article.put(Common.AUTHOR_ID, authorId);
            article.put(Article.ARTICLE_T_CREATE_DATE, new Date(article.optLong(Article.ARTICLE_CREATED)));
            article.put(Article.ARTICLE_T_UPDATE_DATE, new Date(article.optLong(Article.ARTICLE_UPDATED)));

            final String userAvatar = author.optString(UserExt.USER_AVATAR);
            article.put(Common.AUTHOR_THUMBNAIL_URL, userAvatar);

            if (preference.getBoolean(Option.ID_C_ENABLE_ARTICLE_UPDATE_HINT)) {
                article.put(Common.HAS_UPDATED, articleQueryService.hasUpdated(article));
            } else {
                article.put(Common.HAS_UPDATED, false);
            }

            if (Solos.needViewPwd(context, article)) {
                final String content = langPropsService.get("articleContentPwd");
                article.put(ARTICLE_CONTENT, content);
            }

            processArticleAbstract(preference, article);

            articleQueryService.markdown(article);

            fillCategory(article);

            try {
                JSONObject cate = categoryTagRepository.getByTagId(article.optString("oId"), 1, Integer.MAX_VALUE);
                int size = cate.optJSONArray("rslts").length();
                JSONObject cateS = (JSONObject) cate.optJSONArray("rslts").get(size - 1);
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
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Sets article extra properties failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Fills category for the specified article.
     *
     * @param article the specified article
     */
    public void fillCategory(final JSONObject article) {
        final String tagsStr = article.optString(Article.ARTICLE_TAGS_REF);
        final String[] tags = tagsStr.split(",");
        JSONObject category = null;
        for (final String tagTitle : tags) {
            final JSONObject c = getCategoryOfTag(tagTitle);
            if (null != c) {
                category = c;
                break;
            }
        }
        article.put(Category.CATEGORY, category);
    }

    /**
     * Gets a category for a tag specified by the given tag title.
     *
     * @param tagTitle the given tag title
     * @return category, returns {@code null} if not found
     */
    private JSONObject getCategoryOfTag(final String tagTitle) {
        try {
            final JSONObject tag = tagRepository.getByTitle(tagTitle);
            if (null == tag) {
                return null;
            }

            final String tagId = tag.optString(Keys.OBJECT_ID);
            final Query query = new Query()
                    .setFilter(new PropertyFilter(Tag.TAG + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, tagId))
                    .setPage(1, 1).setPageCount(1);
            final JSONObject tagCategory = categoryTagRepository.getFirst(query);
            if (null == tagCategory) {
                return null;
            }

            final String categoryId = tagCategory.optString(Category.CATEGORY + "_" + Keys.OBJECT_ID);

            return categoryRepository.get(categoryId);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets category of tag [" + tagTitle + "] failed", e);

            return null;
        }
    }

    /**
     * Sets some extra properties into the specified article with the specified
     * preference.
     * <p>
     * The batch version of method
     * {@linkplain #setArticleExProperties(RequestContext, JSONObject, JSONObject)}.
     * </p>
     * <p>
     * Article ext properties:
     * 
     * <pre>
     * {
     *     ....,
     *     "authorName": "",
     *     "authorId": "",
     *     "hasUpdated": boolean
     * }
     * </pre>
     * </p>
     *
     * @param context    the specified HTTP servlet request context
     * @param articles   the specified articles
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void setArticlesExProperties(final RequestContext context, final List<JSONObject> articles,
            final JSONObject preference)
            throws ServiceException {
        for (final JSONObject article : articles) {
            setArticleExProperties(context, article, preference);
        }
    }

    /**
     * Processes the abstract of the specified article with the specified
     * preference.
     * <ul>
     * <li>If the abstract is {@code null}, sets it with ""</li>
     * <li>If user configured preference "titleOnly", sets the abstract with ""</li>
     * <li>If user configured preference "titleAndContent", sets the abstract with
     * the content of the article</li>
     * </ul>
     *
     * @param preference the specified preference
     * @param article    the specified article
     */
    private void processArticleAbstract(final JSONObject preference, final JSONObject article) {
        final String articleAbstract = article.optString(Article.ARTICLE_ABSTRACT);
        if (StringUtils.isBlank(articleAbstract)) {
            article.put(Article.ARTICLE_ABSTRACT, "");
        }
        final String articleAbstractText = article.optString(Article.ARTICLE_ABSTRACT_TEXT);
        if (StringUtils.isBlank(articleAbstractText)) {
            // 发布文章时会自动提取摘要文本，其中如果文章加密且没有写摘要，则自动提取文本会返回空字符串 Article#getAbstractText()
            // 所以当且仅当文章加密且没有摘要的情况下 articleAbstractText 会为空
            final LangPropsService langPropsService = BeanManager.getInstance().getReference(LangPropsService.class);
            article.put(Article.ARTICLE_ABSTRACT_TEXT, langPropsService.get("articleContentPwd"));
        }

        final String articleListStyle = preference.optString(Option.ID_C_ARTICLE_LIST_STYLE);
        if ("titleOnly".equals(articleListStyle)) {
            article.put(Article.ARTICLE_ABSTRACT, "");
        } else if ("titleAndContent".equals(articleListStyle)) {
            article.put(Article.ARTICLE_ABSTRACT, article.optString(Article.ARTICLE_CONTENT));
        }
    }

    /**
     * Generates top bar HTML.
     *
     * @param context the specified request context
     * @return top bar HTML
     * @throws ServiceException service exception
     */
    public String getTopBarHTML(final RequestContext context) throws ServiceException {
        Stopwatchs.start("Gens Top Bar HTML");

        try {
            final Template topBarTemplate = Skins.getTemplate("common-template/top-bar.ftl");
            final StringWriter stringWriter = new StringWriter();
            final Map<String, Object> topBarModel = new HashMap<>();
            final JSONObject currentUser = Solos.getCurrentUser(context.getRequest(), context.getResponse());

            Keys.fillServer(topBarModel);
            topBarModel.put(Common.IS_LOGGED_IN, false);
            topBarModel.put(Common.IS_MOBILE_REQUEST, Solos.isMobile(context.getRequest()));
            topBarModel.put("mobileLabel", langPropsService.get("mobileLabel"));
            topBarModel.put("onlineVisitor1Label", langPropsService.get("onlineVisitor1Label"));
            topBarModel.put(Common.ONLINE_VISITOR_CNT, StatisticQueryService.getOnlineVisitorCount());
            if (null == currentUser) {
                topBarModel.put(Common.LOGIN_URL, userQueryService.getLoginURL(Common.ADMIN_INDEX_URI));
                topBarModel.put("startToUseLabel", langPropsService.get("startToUseLabel"));
                topBarTemplate.process(topBarModel, stringWriter);

                return stringWriter.toString();
            }

            topBarModel.put(Common.IS_LOGGED_IN, true);
            topBarModel.put(Common.LOGOUT_URL, userQueryService.getLogoutURL());
            topBarModel.put(Common.IS_ADMIN, Role.ADMIN_ROLE.equals(currentUser.getString(User.USER_ROLE)));
            topBarModel.put(Common.IS_VISITOR, Role.VISITOR_ROLE.equals(currentUser.getString(User.USER_ROLE)));
            topBarModel.put("adminLabel", langPropsService.get("adminLabel"));
            topBarModel.put("logoutLabel", langPropsService.get("logoutLabel"));
            final String userName = currentUser.getString(User.USER_NAME);
            topBarModel.put(User.USER_NAME, userName);
            topBarTemplate.process(topBarModel, stringWriter);

            return stringWriter.toString();
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gens top bar HTML failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }
}
