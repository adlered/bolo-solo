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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.*;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Ids;
import org.b3log.solo.event.B3ArticleSender;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.event.FishPiArticleSender;
import org.b3log.solo.model.*;
import org.b3log.solo.repository.*;
import org.b3log.solo.util.GitHubs;
import org.b3log.solo.util.PluginUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.b3log.solo.model.Article.*;

/**
 * Article management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.5
 */

@Service
public class ArticleMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleMgmtService.class);

    /**
     * Category service.
     */
    @Inject
    private CategoryRepository categoryRepository;

    /**
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Page repository.
     */
    @Inject
    private PageRepository pageRepository;

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

    /**
     * Archive date repository.
     */
    @Inject
    private ArchiveDateRepository archiveDateRepository;

    /**
     * Archive date-Article repository.
     */
    @Inject
    private ArchiveDateArticleRepository archiveDateArticleRepository;

    /**
     * Tag-Article repository.
     */
    @Inject
    private TagArticleRepository tagArticleRepository;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * Category-tag repository.
     */
    @Inject
    private CategoryTagRepository categoryTagRepository;

    /**
     * Permalink query service.
     */
    @Inject
    private PermalinkQueryService permalinkQueryService;

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
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Statistic query service.
     */
    @Inject
    private StatisticQueryService statisticQueryService;

    /**
     * Init service.
     */
    @Inject
    private InitService initService;

    /**
     * Tag management service.
     */
    @Inject
    private TagMgmtService tagMgmtService;

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Option management service.
     */
    @Inject
    private OptionMgmtService optionMgmtService;

    /**
     * Category management service.
     */
    @Inject
    private CategoryMgmtService categoryMgmtService;

    /**
     * Category query service.
     */
    @Inject
    private CategoryQueryService categoryQueryService;

    /**
     * Refreshes GitHub repos. 同步拉取 GitHub 仓库 https://github.com/b3log/solo/issues/12514
     */
    public void refreshGitHub(String githubId) {
        if (!initService.isInited()) {
            return;
        }

        final JSONObject preference = optionQueryService.getPreference();
        if (null == preference) {
            return;
        }

        JSONObject admin;
        try {
            admin = userRepository.getAdmin();
        } catch (final Exception e) {
            return;
        }

        if (null == admin) {
            return;
        }

        final JSONArray gitHubRepos = GitHubs.getGitHubRepos(githubId);
        if (null == gitHubRepos || gitHubRepos.length() == 0) {
            return;
        }

        JSONObject githubReposOpt = optionQueryService.getOptionById(Option.ID_C_GITHUB_REPOS);
        if (null == githubReposOpt) {
            githubReposOpt = new JSONObject();
            githubReposOpt.put(Keys.OBJECT_ID, Option.ID_C_GITHUB_REPOS);
            githubReposOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_GITHUB);
        }
        githubReposOpt.put(Option.OPTION_VALUE, gitHubRepos.toString());

        try {
            optionMgmtService.addOrUpdateOption(githubReposOpt);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Updates github repos option failed", e);
            return;
        }

        final StringBuilder contentBuilder = new StringBuilder();
        String stats = "\n![Github Stats](https://github-readme-stats.vercel.app/api?username={username}&show_icons=true) \n\n";
        stats = stats.replace("{username}", githubId);
        contentBuilder.append("![GitHub Repo](/images/github_repo.jpg)\n\n");
        contentBuilder.append("## Github Stats\n").append(stats);
        contentBuilder.append("## 所有开源项目\n");
        contentBuilder.append("| 仓库 |  项目简介 | Stars | fork | 编程语言 |\n");
        contentBuilder.append("| ---- | ---- | ---- | ---- | ---- |\n");
        for (int i = 0; i < gitHubRepos.length(); i++) {
            final JSONObject repo = gitHubRepos.optJSONObject(i);
            final String url = repo.optString("githubrepoHTMLURL");
            final String desc = repo.optString("githubrepoDescription");
            final String name = repo.optString("githubrepoName");
            final String stars = repo.optString("githubrepoStargazersCount");
            final String forks = repo.optString("githubrepoForksCount");
            final String lang = repo.optString("githubrepoLanguage");
            final String hp = repo.optString("githubrepoHomepage");
            contentBuilder.append("| [").append(name).append("](").append(url).append(") | ")
                .append(desc).append(" | ")
                .append(stars).append(" | ")
                .append(forks).append(" | ")
                .append(lang).append("|\n");
        }
        final String content = contentBuilder.toString();

        try {
            final String permalink = "/github";
            JSONObject article = articleRepository.getByPermalink(permalink);
            if (null == article) {
                try {
                    // 新建文章时，删除有可能存在的旧 permalink （用户手动删除文章后残留）
                    JSONObject page = pageRepository.getByPermalink(permalink);
                    final Transaction transaction = pageRepository.beginTransaction();
                    pageRepository.remove(page.optString("oId"));
                    transaction.commit();
                } catch (NullPointerException ignored) {
                }
                article = new JSONObject();
                article.put(Article.ARTICLE_AUTHOR_ID, admin.optString(Keys.OBJECT_ID));
                article.put(Article.ARTICLE_TITLE, "我在 GitHub 上的开源项目");
                article.put(Article.ARTICLE_ABSTRACT, Article.getAbstractText(content));
                article.put(Article.ARTICLE_TAGS_REF, "开源,GitHub");
                article.put(Article.ARTICLE_PERMALINK, permalink);
                article.put(Article.ARTICLE_CONTENT, content);
                article.put(Article.ARTICLE_VIEW_PWD, "");
                article.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
                article.put(Common.POST_TO_COMMUNITY, false);
                article.put(Article.ARTICLE_COMMENTABLE, true);

                final JSONObject addArticleReq = new JSONObject();
                addArticleReq.put(Article.ARTICLE, article);
                addArticle(addArticleReq);
            } else {
                article.put(Article.ARTICLE_CONTENT, content);

                final String articleId = article.optString(Keys.OBJECT_ID);
                final Transaction transaction = articleRepository.beginTransaction();
                articleRepository.update(articleId, article);
                transaction.commit();
            }

            final Transaction transaction = pageRepository.beginTransaction();
            JSONObject page = pageRepository.getByPermalink(permalink);
            if (null == page) {
                page = new JSONObject();
                final int maxOrder = pageRepository.getMaxOrder();
                page.put(Page.PAGE_ORDER, maxOrder + 1);
                page.put(Page.PAGE_TITLE, "我的开源");
                page.put(Page.PAGE_OPEN_TARGET, "_self");
                page.put(Page.PAGE_PERMALINK, permalink);
                page.put(Page.PAGE_ICON, Latkes.getServePath() + "/images/github-icon.png");
                pageRepository.add(page);
            } else {
                page.put(Page.PAGE_OPEN_TARGET, "_self");
                page.put(Page.PAGE_ICON, Latkes.getServePath() + "/images/github-icon.png");
                pageRepository.update(page.optString(Keys.OBJECT_ID), page);
            }
            transaction.commit();
            LOGGER.log(Level.INFO, "My github repos page has been generated.");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Updates github repos page failed", e);
        }
    }

    /**
     * Pushes an article specified by the given article id to community.
     *
     * @param articleId the given article id
     */
    public void pushArticleToCommunity(final String articleId) {
        try {
            final JSONObject article = articleRepository.get(articleId);
            if (null == article) {
                return;
            }

            article.put(Common.POST_TO_COMMUNITY, true);

            final JSONObject data = new JSONObject().put(ARTICLE, article);
            new Thread(() -> {
                if (PluginUtil.b3logPluginEnabled()) {
                    B3ArticleSender.pushArticleToRhy(data);
                }
                if (PluginUtil.fishpiPluginEnabled()) {
                    FishPiArticleSender.pushArticleToFishPi(data);
                }
            }).start();

        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Pushes an article [id=" + articleId + "] to community failed", e);
        }
    }

    /**
     * Article comment count +1 for an article specified by the given article id.
     *
     * @param articleId the given article id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    public void incArticleCommentCount(final String articleId) throws JSONException, RepositoryException {
        final JSONObject article = articleRepository.get(articleId);
        final JSONObject newArticle = new JSONObject(article, JSONObject.getNames(article));
        final int commentCnt = article.getInt(Article.ARTICLE_COMMENT_COUNT);
        newArticle.put(Article.ARTICLE_COMMENT_COUNT, commentCnt + 1);
        articleRepository.update(articleId, newArticle, ARTICLE_COMMENT_COUNT);
    }

    /**
     * Cancels publish an article by the specified article id.
     *
     * @param articleId the specified article id
     * @throws ServiceException service exception
     */
    public void cancelPublishArticle(final String articleId) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject article = articleRepository.get(articleId);
            article.put(ARTICLE_STATUS, ARTICLE_STATUS_C_DRAFT);
            articleRepository.update(articleId, article, ARTICLE_STATUS);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Cancels publish article failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Puts an article specified by the given article id to top or cancel top.
     *
     * @param articleId the given article id
     * @param top       the specified flag, {@code true} to top, {@code false} to
     *                  cancel top
     * @throws ServiceException service exception
     */
    public void topArticle(final String articleId, final boolean top) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject topArticle = articleRepository.get(articleId);
            topArticle.put(ARTICLE_PUT_TOP, top);
            articleRepository.update(articleId, topArticle, ARTICLE_PUT_TOP);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Can't put the article[oId{0}] to top", articleId);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates an article by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "article": {
     *                          "oId": "",
     *                          "articleTitle": "",
     *                          "articleAbstract": "",
     *                          "articleContent": "",
     *                          "articleTags": "tag1,tag2,tag3", // optional, default set "待分类"
     *                          "categories": "cat1,cat2,cat3",
     *                          "articlePermalink": "", // optional
     *                          "articleStatus": int, // 0: published, 1: draft
     *                          "articleSignId": "", // optional
     *                          "articleCommentable": boolean,
     *                          "articleViewPwd": ""
     *                          }
     *                          }
     * @throws ServiceException service exception
     */
    public void updateArticle(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject article = requestJSONObject.getJSONObject(ARTICLE);
            String tagsString = article.optString(Article.ARTICLE_TAGS_REF);
            tagsString = Tag.formatTags(tagsString, 4);
            if (StringUtils.isBlank(tagsString)) {
                tagsString = "待分类";
            }
            article.put(Article.ARTICLE_TAGS_REF, tagsString);

            final String articleId = article.getString(Keys.OBJECT_ID);
            // Set permalink
            final JSONObject oldArticle = articleRepository.get(articleId);
            final String permalink = getPermalinkForUpdateArticle(oldArticle, article, oldArticle.optLong(ARTICLE_CREATED));
            article.put(ARTICLE_PERMALINK, permalink);

            processTagsForArticleUpdate(oldArticle, article);

            archiveDate(article);

            if (!oldArticle.getString(Article.ARTICLE_PERMALINK).equals(permalink)) { // The permalink has been updated
                // Updates related comments' links
                processCommentsForArticleUpdate(article);
            }

            // Fill auto properties
            fillAutoProperties(oldArticle, article);
            // Set date
            article.put(ARTICLE_UPDATED, oldArticle.getLong(ARTICLE_UPDATED));
            final long now = System.currentTimeMillis();

            // The article to update has no sign
            if (!article.has(Article.ARTICLE_SIGN_ID)) {
                article.put(Article.ARTICLE_SIGN_ID, "0");
            }

            article.put(ARTICLE_UPDATED, now);

            final String articleImg1URL = getArticleImg1URL(article);
            article.put(ARTICLE_IMG1_URL, articleImg1URL);

            final String articleAbstractText = Article.getAbstractText(article);
            article.put(ARTICLE_ABSTRACT_TEXT, articleAbstractText);

            final boolean postToCommunity = article.optBoolean(Common.POST_TO_COMMUNITY);
            article.remove(Common.POST_TO_COMMUNITY);

            // Bolo category
            try {
                String category = String.valueOf(article.get(CATEGORY_REF));
                final JSONObject categoryTag = new JSONObject();
                categoryTag.put(Category.CATEGORY + "_" + Keys.OBJECT_ID, category);
                categoryTag.put(Tag.TAG + "_" + Keys.OBJECT_ID, article.optString(Keys.OBJECT_ID));
                try {
                    categoryTagRepository.removeByTagId(article.optString(Keys.OBJECT_ID));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                categoryMgmtService.addCategoryTag(categoryTag);
                article.remove(CATEGORY_REF);
            } catch (Exception ignored) {
            }

            articleRepository.update(articleId, article);
            article.put(Common.POST_TO_COMMUNITY, postToCommunity);

            final boolean publishNewArticle = Article.ARTICLE_STATUS_C_DRAFT == oldArticle.optInt(ARTICLE_STATUS) && Article.ARTICLE_STATUS_C_PUBLISHED == article.optInt(ARTICLE_STATUS);
            final JSONObject eventData = new JSONObject();
            eventData.put(ARTICLE, article);
            if (publishNewArticle) {
                eventManager.fireEventAsynchronously(new Event<>(EventTypes.ADD_ARTICLE, eventData));
            } else {
                eventManager.fireEventAsynchronously(new Event<>(EventTypes.UPDATE_ARTICLE, eventData));
            }

            transaction.commit();
        } catch (final ServiceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Updates an article failed", e);

            throw e;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Updates an article failed", e);

            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Adds an article from the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "article": {
     *                          "articleAuthorId": "",
     *                          "articleTitle": "",
     *                          "articleAbstract": "",
     *                          "articleContent": "",
     *                          "articleTags": "tag1,tag2,tag3",
     *                          "articleStatus": int, // 0: published, 1: draft
     *                          "articlePermalink": "", // optional
     *                          "postToCommunity": boolean, // optional
     *                          "articleSignId": "" // optional, default is "0",
     *                          "articleCommentable": boolean,
     *                          "articleCommentCount": int, // optional, default is 0
     *                          "articleViewPwd": "",
     *                          "oId": "" // optional, generate it if not exists this key
     *                          }
     *                          }
     * @return generated article id
     * @throws ServiceException service exception
     */
    public String addArticle(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject article = requestJSONObject.getJSONObject(Article.ARTICLE);
            String ret = article.optString(Keys.OBJECT_ID);
            if (StringUtils.isBlank(ret)) {
                ret = Ids.genTimeMillisId();
                article.put(Keys.OBJECT_ID, ret);
            }

            String tagsString = article.optString(Article.ARTICLE_TAGS_REF);
            tagsString = Tag.formatTags(tagsString, 4);
            if (StringUtils.isBlank(tagsString)) {
                tagsString = "待分类";
            }
            article.put(Article.ARTICLE_TAGS_REF, tagsString);
            final String[] tagTitles = tagsString.split(",");
            final JSONArray tags = tag(tagTitles, article);

            article.put(Article.ARTICLE_COMMENT_COUNT, article.optInt(Article.ARTICLE_COMMENT_COUNT));
            article.put(Article.ARTICLE_VIEW_COUNT, 0);
            if (!article.has(Article.ARTICLE_CREATED)) {
                article.put(Article.ARTICLE_CREATED, System.currentTimeMillis());
            }
            article.put(Article.ARTICLE_UPDATED, article.optLong(Article.ARTICLE_CREATED));
            article.put(Article.ARTICLE_PUT_TOP, false);

            addTagArticleRelation(tags, article);

            archiveDate(article);

            final String permalink = getPermalinkForAddArticle(article);
            article.put(Article.ARTICLE_PERMALINK, permalink);

            final String signId = article.optString(Article.ARTICLE_SIGN_ID, "1");
            article.put(Article.ARTICLE_SIGN_ID, signId);

            article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());

            final String articleImg1URL = getArticleImg1URL(article);
            article.put(ARTICLE_IMG1_URL, articleImg1URL);

            final String articleAbstractText = Article.getAbstractText(article);
            article.put(ARTICLE_ABSTRACT_TEXT, articleAbstractText);

            final boolean postToCommunity = article.optBoolean(Common.POST_TO_COMMUNITY);
            article.remove(Common.POST_TO_COMMUNITY);

            // Bolo category
            try {
                String category = String.valueOf(article.get(CATEGORY_REF));
                final JSONObject categoryTag = new JSONObject();
                categoryTag.put(Category.CATEGORY + "_" + Keys.OBJECT_ID, category);
                categoryTag.put(Tag.TAG + "_" + Keys.OBJECT_ID, article.optString(Keys.OBJECT_ID));
                categoryMgmtService.addCategoryTag(categoryTag);
                article.remove(CATEGORY_REF);
            } catch (Exception ignored) {
            }

            articleRepository.add(article);
            transaction.commit();

            article.put(Common.POST_TO_COMMUNITY, postToCommunity);
            if (Article.ARTICLE_STATUS_C_PUBLISHED == article.optInt(ARTICLE_STATUS)) {
                final JSONObject eventData = new JSONObject();
                eventData.put(Article.ARTICLE, article);
                eventManager.fireEventAsynchronously(new Event<>(EventTypes.ADD_ARTICLE, eventData));
            }

            return ret;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Removes the article specified by the given id.
     *
     * @param articleId the given id
     * @throws ServiceException service exception
     */
    public void removeArticle(final String articleId) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();
        try {
            unArchiveDate(articleId);
            removeTagArticleRelations(articleId);
            articleRepository.remove(articleId);
            commentRepository.removeComments(articleId);
            transaction.commit();
            eventManager.fireEventAsynchronously(new Event<>(EventTypes.DELETE_ARTICLE, articleId));
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes an article[id=" + articleId + "] failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates the random values of articles fetched with the specified update
     * count.
     *
     * @param updateCnt the specified update count
     * @throws ServiceException service exception
     */
    public void updateArticlesRandomValue(final int updateCnt) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final List<JSONObject> randomArticles = articleRepository.getRandomly(updateCnt);

            for (final JSONObject article : randomArticles) {
                article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());

                articleRepository.update(article.getString(Keys.OBJECT_ID), article, ARTICLE_RANDOM_DOUBLE);
            }

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.WARN, "Updates article random value failed");

            throw new ServiceException(e);
        }
    }

    /**
     * Increments the view count of the article specified by the given article id.
     *
     * @param articleId the given article id
     * @throws ServiceException service exception
     */
    public void incViewCount(final String articleId) throws ServiceException {
        JSONObject article;

        try {
            article = articleRepository.get(articleId);

            if (null == article) {
                return;
            }
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets article [id=" + articleId + "] failed", e);

            return;
        }

        final Transaction transaction = articleRepository.beginTransaction();

        try {
            article.put(Article.ARTICLE_VIEW_COUNT, article.getInt(Article.ARTICLE_VIEW_COUNT) + 1);
            articleRepository.update(articleId, article, ARTICLE_VIEW_COUNT);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.WARN, "Updates article view count failed");

            throw new ServiceException(e);
        }
    }

    /**
     * Un-archive an article specified by the given specified article id.
     *
     * @param articleId the given article id
     * @throws ServiceException service exception
     */
    private void unArchiveDate(final String articleId) throws ServiceException {
        try {
            final JSONObject archiveDateArticleRelation = archiveDateArticleRepository.getByArticleId(articleId);
            if (null == archiveDateArticleRelation) {
                // 草稿不生成存档，所以需要判空
                return;
            }

            final String archiveDateId = archiveDateArticleRelation.getString(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID);
            final int publishedArticleCount = archiveDateArticleRepository.getPublishedArticleCount(archiveDateId);
            if (1 > publishedArticleCount) {
                archiveDateRepository.remove(archiveDateId);
            }

            archiveDateArticleRepository.remove(archiveDateArticleRelation.getString(Keys.OBJECT_ID));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Unarchive date for article[id=" + articleId + "] failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Processes comments for article update.
     *
     * @param article the specified article to update
     * @throws Exception exception
     */
    private void processCommentsForArticleUpdate(final JSONObject article) throws Exception {
        final String articleId = article.getString(Keys.OBJECT_ID);

        final List<JSONObject> comments = commentRepository.getComments(articleId, 1, Integer.MAX_VALUE);
        for (final JSONObject comment : comments) {
            final String commentId = comment.getString(Keys.OBJECT_ID);
            final String sharpURL = Comment.getCommentSharpURLForArticle(article, commentId);
            comment.put(Comment.COMMENT_SHARP_URL, sharpURL);
            if (StringUtils.isBlank(comment.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID))) {
                comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, "");
            }
            if (StringUtils.isBlank(comment.optString(Comment.COMMENT_ORIGINAL_COMMENT_NAME))) {
                comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, "");
            }

            commentRepository.update(commentId, comment);
        }
    }

    /**
     * Processes tags for article update.
     * <p>
     * <ul>
     * <li>Un-tags old article, decrements tag reference count</li>
     * <li>Removes old article-tag relations</li>
     * <li>Saves new article-tag relations with tag reference count</li>
     * </ul>
     *
     * @param oldArticle the specified old article
     * @param newArticle the specified new article
     * @throws Exception exception
     */
    private void processTagsForArticleUpdate(final JSONObject oldArticle, final JSONObject newArticle) throws Exception {
        final String oldArticleId = oldArticle.getString(Keys.OBJECT_ID);
        final List<JSONObject> oldTags = tagRepository.getByArticleId(oldArticleId);
        final String tagsString = newArticle.getString(Article.ARTICLE_TAGS_REF);
        String[] tagStrings = tagsString.split(",");
        final List<JSONObject> newTags = new ArrayList<>();

        for (int i = 0; i < tagStrings.length; i++) {
            final String tagTitle = tagStrings[i].trim();
            JSONObject newTag = tagRepository.getByTitle(tagTitle);

            if (null == newTag) {
                newTag = new JSONObject();
                newTag.put(Tag.TAG_TITLE, tagTitle);
            }
            newTags.add(newTag);
        }

        final List<JSONObject> tagsDropped = new ArrayList<>();
        final List<JSONObject> tagsNeedToAdd = new ArrayList<>();
        final List<JSONObject> tagsUnchanged = new ArrayList<>();

        for (final JSONObject newTag : newTags) {
            final String newTagTitle = newTag.getString(Tag.TAG_TITLE);

            if (!tagExists(newTagTitle, oldTags)) {
                LOGGER.log(Level.DEBUG, "Tag need to add[title={0}]", newTagTitle);
                tagsNeedToAdd.add(newTag);
            } else {
                tagsUnchanged.add(newTag);
            }
        }
        for (final JSONObject oldTag : oldTags) {
            final String oldTagTitle = oldTag.getString(Tag.TAG_TITLE);

            if (!tagExists(oldTagTitle, newTags)) {
                LOGGER.log(Level.DEBUG, "Tag dropped[title={0}]", oldTag);
                tagsDropped.add(oldTag);
            } else {
                tagsUnchanged.remove(oldTag);
            }
        }

        LOGGER.log(Level.DEBUG, "Tags unchanged [{0}]", tagsUnchanged);

        final String[] tagIdsDropped = new String[tagsDropped.size()];
        for (int i = 0; i < tagIdsDropped.length; i++) {
            final JSONObject tag = tagsDropped.get(i);
            final String id = tag.getString(Keys.OBJECT_ID);
            tagIdsDropped[i] = id;
        }

        removeTagArticleRelations(oldArticleId, 0 == tagIdsDropped.length ? new String[]{"l0y0l"} : tagIdsDropped);

        tagStrings = new String[tagsNeedToAdd.size()];
        for (int i = 0; i < tagStrings.length; i++) {
            final JSONObject tag = tagsNeedToAdd.get(i);
            final String tagTitle = tag.getString(Tag.TAG_TITLE);
            tagStrings[i] = tagTitle;
        }
        final JSONArray tags = tag(tagStrings, newArticle);

        addTagArticleRelation(tags, newArticle);
    }

    /**
     * Removes tag-article relations by the specified article id and tag ids of the relations to be removed.
     * <p>
     * Removes all relations if not specified the tag ids.
     * </p>
     *
     * @param articleId the specified article id
     * @param tagIds    the specified tag ids of the relations to be removed
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    private void removeTagArticleRelations(final String articleId, final String... tagIds) throws JSONException, RepositoryException {
        final List<String> tagIdList = Arrays.asList(tagIds);
        final List<JSONObject> tagArticleRelations = tagArticleRepository.getByArticleId(articleId);

        for (int i = 0; i < tagArticleRelations.size(); i++) {
            final JSONObject tagArticleRelation = tagArticleRelations.get(i);
            String relationId;
            if (tagIdList.isEmpty()) { // Removes all if un-specified
                relationId = tagArticleRelation.getString(Keys.OBJECT_ID);
                tagArticleRepository.remove(relationId);
            } else {
                if (tagIdList.contains(tagArticleRelation.getString(Tag.TAG + "_" + Keys.OBJECT_ID))) {
                    relationId = tagArticleRelation.getString(Keys.OBJECT_ID);
                    tagArticleRepository.remove(relationId);
                }
            }

            final String tagId = tagArticleRelation.optString(Tag.TAG + "_" + Keys.OBJECT_ID);
            final int articleCount = tagArticleRepository.getArticleCount(tagId);
            if (1 > articleCount) {
                // Bolo
                // categoryTagRepository.removeByTagId(tagId);
                tagRepository.remove(tagId);
            }
        }
    }

    /**
     * Adds relation of the specified tags and article.
     *
     * @param tags    the specified tags
     * @param article the specified article
     * @throws RepositoryException repository exception
     */
    private void addTagArticleRelation(final JSONArray tags, final JSONObject article) throws RepositoryException {
        for (int i = 0; i < tags.length(); i++) {
            final JSONObject tag = tags.optJSONObject(i);
            final JSONObject tagArticleRelation = new JSONObject();

            tagArticleRelation.put(Tag.TAG + "_" + Keys.OBJECT_ID, tag.optString(Keys.OBJECT_ID));
            tagArticleRelation.put(Article.ARTICLE + "_" + Keys.OBJECT_ID, article.optString(Keys.OBJECT_ID));

            tagArticleRepository.add(tagArticleRelation);
        }
    }

    /**
     * Tags the specified article with the specified tag titles.
     *
     * @param tagTitles the specified tag titles
     * @param article   the specified article
     * @return an array of tags
     * @throws RepositoryException repository exception
     */
    private JSONArray tag(final String[] tagTitles, final JSONObject article) throws RepositoryException {
        final JSONArray ret = new JSONArray();

        for (int i = 0; i < tagTitles.length; i++) {
            final String tagTitle = tagTitles[i].trim();
            JSONObject tag = tagRepository.getByTitle(tagTitle);
            String tagId;

            if (null == tag) {
                LOGGER.log(Level.TRACE, "Found a new tag[title={0}] in article[title={1}]",
                        tagTitle, article.optString(Article.ARTICLE_TITLE));
                tag = new JSONObject();
                tag.put(Tag.TAG_TITLE, tagTitle);
                tagId = tagRepository.add(tag);
                tag.put(Keys.OBJECT_ID, tagId);
            } else {
                tagId = tag.optString(Keys.OBJECT_ID);
                LOGGER.log(Level.TRACE, "Found a existing tag[title={0}, id={1}] in article[title={2}]",
                        tag.optString(Tag.TAG_TITLE), tag.optString(Keys.OBJECT_ID), article.optString(Article.ARTICLE_TITLE));
                final JSONObject tagTmp = new JSONObject();
                tagTmp.put(Keys.OBJECT_ID, tagId);
                tagTmp.put(Tag.TAG_TITLE, tagTitle);
                tagRepository.update(tagId, tagTmp);
            }

            ret.put(tag);
        }

        return ret;
    }

    /**
     * Archive the create date with the specified article.
     *
     * @param article the specified article, for example,
     *                {
     *                "oId": "",
     *                ....
     *                }
     * @throws RepositoryException repository exception
     */
    private void archiveDate(final JSONObject article) throws RepositoryException {
        if (Article.ARTICLE_STATUS_C_PUBLISHED != article.optInt(ARTICLE_STATUS)) {
            // 草稿不生成存档
            return;
        }

        final long created = article.optLong(ARTICLE_CREATED);
        final String createDateString = DateFormatUtils.format(created, "yyyy/MM");
        JSONObject archiveDate = archiveDateRepository.getByArchiveDate(createDateString);
        if (null == archiveDate) {
            archiveDate = new JSONObject();
            try {
                archiveDate.put(ArchiveDate.ARCHIVE_TIME, DateUtils.parseDate(createDateString, new String[]{"yyyy/MM"}).getTime());
                archiveDateRepository.add(archiveDate);
            } catch (final ParseException e) {
                LOGGER.log(Level.ERROR, e.getMessage(), e);
                throw new RepositoryException(e);
            }
        }

        final String articleId = article.optString(Keys.OBJECT_ID);
        if (null == archiveDateArticleRepository.getByArticleId(articleId)) {
            final JSONObject archiveDateArticleRelation = new JSONObject();
            archiveDateArticleRelation.put(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID, archiveDate.optString(Keys.OBJECT_ID));
            archiveDateArticleRelation.put(Article.ARTICLE + "_" + Keys.OBJECT_ID, articleId);
            archiveDateArticleRepository.add(archiveDateArticleRelation);
        }
    }

    /**
     * Fills 'auto' properties for the specified article and old article.
     * <p>
     * Some properties of an article are not been changed while article
     * updating, these properties are called 'auto' properties.
     * </p>
     * <p>
     * The property(named {@value org.b3log.solo.model.Article#ARTICLE_RANDOM_DOUBLE}) of the specified
     * article will be regenerated.
     * </p>
     *
     * @param oldArticle the specified old article
     * @param article    the specified article
     * @throws JSONException json exception
     */
    private void fillAutoProperties(final JSONObject oldArticle, final JSONObject article) throws JSONException {
        final long created = oldArticle.getLong(ARTICLE_CREATED);
        try {
            final long newCreated = article.getLong(ARTICLE_CREATED);
            // 测试时间戳是否合法
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newDate = simpleDateFormat.format(new Date(newCreated));
            LOGGER.log(Level.INFO, "New article date: " + newDate);
            article.put(ARTICLE_CREATED, newCreated);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Customize article create time: timestamp syntax error, using earlier...");
            article.put(ARTICLE_CREATED, created);
        }
        article.put(ARTICLE_COMMENT_COUNT, oldArticle.getInt(ARTICLE_COMMENT_COUNT));
        article.put(ARTICLE_VIEW_COUNT, oldArticle.getInt(ARTICLE_VIEW_COUNT));
        article.put(ARTICLE_PUT_TOP, oldArticle.getBoolean(ARTICLE_PUT_TOP));
        article.put(ARTICLE_AUTHOR_ID, oldArticle.getString(ARTICLE_AUTHOR_ID));
        article.put(ARTICLE_RANDOM_DOUBLE, Math.random());
    }

    /**
     * Gets article permalink for adding article with the specified article.
     *
     * @param article the specified article
     * @return permalink
     * @throws ServiceException if invalid permalink occurs
     */
    private String getPermalinkForAddArticle(final JSONObject article) throws ServiceException {
        final long date = article.optLong(Article.ARTICLE_CREATED);
        String ret = article.optString(Article.ARTICLE_PERMALINK);
        if (StringUtils.isBlank(ret)) {
            ret = "/articles/" + DateFormatUtils.format(date, "yyyy/MM/dd") + "/" + article.optString(Keys.OBJECT_ID) + ".html";
        }

        if (!ret.startsWith("/")) {
            ret = "/" + ret;
        }

        if (PermalinkQueryService.invalidArticlePermalinkFormat(ret)) {
            throw new ServiceException(langPropsService.get("invalidPermalinkFormatLabel"));
        }

        if (permalinkQueryService.exist(ret)) {
            throw new ServiceException(langPropsService.get("duplicatedPermalinkLabel"));
        }

        return ret.replaceAll(" ", "-");
    }

    /**
     * Gets article permalink for updating article with the specified old article, article, created at.
     *
     * @param oldArticle the specified old article
     * @param article    the specified article
     * @param created    the specified created
     * @return permalink
     * @throws ServiceException if invalid permalink occurs
     * @throws JSONException    json exception
     */
    private String getPermalinkForUpdateArticle(final JSONObject oldArticle, final JSONObject article, final long created)
            throws ServiceException, JSONException {
        final String articleId = article.getString(Keys.OBJECT_ID);
        String ret = article.optString(ARTICLE_PERMALINK).trim();
        final String oldPermalink = oldArticle.getString(ARTICLE_PERMALINK);

        if (!oldPermalink.equals(ret)) {
            if (StringUtils.isBlank(ret)) {
                ret = "/articles/" + DateFormatUtils.format(created, "yyyy/MM/dd") + "/" + articleId + ".html";
            }

            if (!ret.startsWith("/")) {
                ret = "/" + ret;
            }

            if (PermalinkQueryService.invalidArticlePermalinkFormat(ret)) {
                throw new ServiceException(langPropsService.get("invalidPermalinkFormatLabel"));
            }

            if (!oldPermalink.equals(ret) && permalinkQueryService.exist(ret)) {
                throw new ServiceException(langPropsService.get("duplicatedPermalinkLabel"));
            }
        }

        return ret.replaceAll(" ", "-");
    }

    /**
     * Determines whether the specified tag title exists in the specified tags.
     *
     * @param tagTitle the specified tag title
     * @param tags     the specified tags
     * @return {@code true} if it exists, {@code false} otherwise
     * @throws JSONException json exception
     */
    private static boolean tagExists(final String tagTitle, final List<JSONObject> tags) throws JSONException {
        for (final JSONObject tag : tags) {
            if (tag.getString(Tag.TAG_TITLE).equals(tagTitle)) {
                return true;
            }
        }

        return false;
    }
}
