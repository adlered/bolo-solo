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

import java.sql.Connection;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.plugin.PluginManager;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.repository.jdbc.util.Connections;
import org.b3log.latke.repository.jdbc.util.JdbcRepositories;
import org.b3log.latke.repository.jdbc.util.JdbcRepositories.CreateTableResult;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Ids;
import org.b3log.solo.bolo.prop.Options;
import org.b3log.solo.model.ArchiveDate;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Tag;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.ArchiveDateArticleRepository;
import org.b3log.solo.repository.ArchiveDateRepository;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.LinkRepository;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.TagArticleRepository;
import org.b3log.solo.repository.TagRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.util.Images;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Solo initialization service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.4.0
 */
@Service
public class InitService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(InitService.class);

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Tag-Article repository.
     */
    @Inject
    private TagArticleRepository tagArticleRepository;

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
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

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
     * Link repository.
     */
    @Inject
    private LinkRepository linkRepository;

    /**
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Plugin manager.
     */
    @Inject
    private PluginManager pluginManager;

    /**
     * Flag of init status.
     */
    private static boolean inited;

    /**
     * Flag of printed init prompt.
     */
    private static boolean printedInitMsg;

    /**
     * Determines Solo had been initialized.
     *
     * @return {@code true} if it had been initialized, {@code false} otherwise
     */
    public boolean isInited() {
        if (inited) {
            return true;
        }

        try {
            inited = null != optionRepository.get(Option.ID_C_VERSION);
            if (!inited && !printedInitMsg) {
                LOGGER.log(Level.WARN, "Bolo has not been initialized, please open your browser to init Bolo");
                printedInitMsg = true;
            }

            return inited;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Check init failed", e);

            System.exit(-1);
            return false;
        }
    }

    /**
     * Initializes database tables.
     */
    public void initTables() {
        try (final Connection chk = Connections.getConnection()) {
            final String tablePrefix = Latkes.getLocalProperty("jdbc.tablePrefix") + "_";
            final boolean userTableExist = JdbcRepositories.existTable(tablePrefix + User.USER);
            final boolean optionTableExist = JdbcRepositories.existTable(tablePrefix + Option.OPTION);
            if (userTableExist && optionTableExist) {
                return;
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR,
                    "Check tables failed, please make sure database existed and database configuration [jdbc.*] in local.props is correct [msg="
                            + e.getMessage() + "]");

            System.exit(-1);
        }

        LOGGER.info(
                "It's your first time setup Bolo, initialize tables in database [" + Latkes.getRuntimeDatabase() + "]");

        if (Latkes.RuntimeDatabase.H2 == Latkes.getRuntimeDatabase()) {
            String dataDir = Latkes.getLocalProperty("jdbc.URL");
            dataDir = dataDir.replace("~", System.getProperty("user.home"));
            LOGGER.log(Level.INFO, "Your DATA will be stored in directory [" + dataDir + "], "
                    + "please pay more attention on it!");
        }

        final List<CreateTableResult> createTableResults = JdbcRepositories.initAllTables();
        for (final CreateTableResult createTableResult : createTableResults) {
            LOGGER.log(Level.DEBUG, "Creates table result [tableName={0}, isSuccess={1}]",
                    createTableResult.getName(), createTableResult.isSuccess());
        }
    }

    /**
     * Initializes Solo.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "userName": "",
     *                          "userAvatar": "", // optional
     *                          "userB3Key": "", // optional
     *                          "userGitHubId": "" // optional
     *                          }
     */
    public void init(final JSONObject requestJSONObject) {
        if (isInited()) {
            return;
        }

        final Transaction transaction = userRepository.beginTransaction();
        try {
            initStatistic();
            initOptions(requestJSONObject);
            initAdmin(requestJSONObject);
            initLink();
            helloWorld();

            transaction.commit();
        } catch (final Throwable e) {
            LOGGER.log(Level.ERROR, "Initializes Solo failed", e);

            System.exit(-1);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        pluginManager.load();
    }

    /**
     * Publishes the first article "Hello World" and the first comment with the
     * specified locale.
     *
     * @throws Exception exception
     */
    private void helloWorld() throws Exception {
        final JSONObject article = new JSONObject();

        article.put(Article.ARTICLE_TITLE, langPropsService.get("helloWorld.title"));
        final String content = "![]("
                + Images.imageSize(Images.randImage(), Article.ARTICLE_THUMB_IMG_WIDTH,
                        Article.ARTICLE_THUMB_IMG_HEIGHT)
                + ") \n\n" +
                langPropsService.get("helloWorld.content");

        article.put(Article.ARTICLE_ABSTRACT_TEXT, Article.getAbstractText(content));
        article.put(Article.ARTICLE_ABSTRACT, content);
        article.put(Article.ARTICLE_CONTENT, content);
        article.put(Article.ARTICLE_TAGS_REF, "Bolo");
        article.put(Article.ARTICLE_PERMALINK, "/hello-bolo");
        article.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
        article.put(Article.ARTICLE_SIGN_ID, "1");
        article.put(Article.ARTICLE_COMMENT_COUNT, 1);
        article.put(Article.ARTICLE_VIEW_COUNT, 0);
        final JSONObject admin = userRepository.getAdmin();
        final long now = System.currentTimeMillis();
        article.put(Article.ARTICLE_CREATED, now);
        article.put(Article.ARTICLE_UPDATED, now);
        article.put(Article.ARTICLE_PUT_TOP, false);
        article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
        article.put(Article.ARTICLE_AUTHOR_ID, admin.optString(Keys.OBJECT_ID));
        article.put(Article.ARTICLE_COMMENTABLE, true);
        article.put(Article.ARTICLE_VIEW_PWD, "");
        final String articleImg1URL = Article.getArticleImg1URL(article);
        article.put(Article.ARTICLE_IMG1_URL, articleImg1URL);

        final String articleId = addHelloWorldArticle(article);

        final JSONObject comment = new JSONObject();
        comment.put(Keys.OBJECT_ID, articleId);
        comment.put(Comment.COMMENT_NAME, "adlered");
        comment.put(Comment.COMMENT_URL, "https://github.com/adlered");
        comment.put(Comment.COMMENT_CONTENT, langPropsService.get("helloWorld.comment.content"));
        comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, "");
        comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, "");
        comment.put(Comment.COMMENT_THUMBNAIL_URL,
                "https://avatars1.githubusercontent.com/u/6754458?s=400&u=a8d4a1321a2f140d66a7c229ecd510c5560f1148&v=4");
        comment.put(Comment.COMMENT_CREATED, now);
        comment.put(Comment.COMMENT_ON_ID, articleId);
        final String commentId = Ids.genTimeMillisId();
        comment.put(Keys.OBJECT_ID, commentId);
        final String commentSharpURL = Comment.getCommentSharpURLForArticle(article, commentId);
        comment.put(Comment.COMMENT_SHARP_URL, commentSharpURL);

        commentRepository.add(comment);

        LOGGER.info("Hello World!");
    }

    /**
     * Adds the specified "Hello World" article.
     *
     * @param article the specified "Hello World" article
     * @return generated article id
     * @throws RepositoryException repository exception
     */
    private String addHelloWorldArticle(final JSONObject article) throws RepositoryException {
        final String ret = Ids.genTimeMillisId();

        try {
            article.put(Keys.OBJECT_ID, ret);

            final String tagsString = article.optString(Article.ARTICLE_TAGS_REF);
            final String[] tagTitles = tagsString.split(",");
            final JSONArray tags = tag(tagTitles, article);
            addTagArticleRelation(tags, article);
            archiveDate(article);
            articleRepository.add(article);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Adds an article failed", e);

            throw new RepositoryException(e);
        }

        return ret;
    }

    /**
     * Import article.
     *
     * @param article the specified "Hello World" article
     * @return generated article id
     * @throws RepositoryException repository exception
     */
    public void importArticle(final JSONObject article) throws RepositoryException {
        try {
            final String tagsString = article.optString(Article.ARTICLE_TAGS_REF);
            final String[] tagTitles = tagsString.split(",");
            final JSONArray tags = tag(tagTitles, article);
            addTagArticleRelation(tags, article);
            archiveDate(article);
            articleRepository.add(article);
        } catch (final RepositoryException e) {
            System.out.println("该文章导入失败，请手动导入！");
        }
    }

    /**
     * Archive the create date with the specified article.
     *
     * @param article the specified article, for example,
     *                {
     *                ....,
     *                "oId": "",
     *                "articleCreateDate": java.util.Date,
     *                ....
     *                }
     * @throws RepositoryException repository exception
     */
    public void archiveDate(final JSONObject article) throws RepositoryException {
        final long created = article.optLong(Article.ARTICLE_CREATED);
        final String createDateString = DateFormatUtils.format(created, "yyyy/MM");
        final JSONObject archiveDate = new JSONObject();

        try {
            archiveDate.put(ArchiveDate.ARCHIVE_TIME,
                    DateUtils.parseDate(createDateString, new String[] { "yyyy/MM" }).getTime());
            archiveDateRepository.add(archiveDate);
        } catch (final ParseException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            throw new RepositoryException(e);
        }

        final JSONObject archiveDateArticleRelation = new JSONObject();
        archiveDateArticleRelation.put(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID,
                archiveDate.optString(Keys.OBJECT_ID));
        archiveDateArticleRelation.put(Article.ARTICLE + "_" + Keys.OBJECT_ID, article.optString(Keys.OBJECT_ID));
        archiveDateArticleRepository.add(archiveDateArticleRelation);
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

        for (String tagTitle1 : tagTitles) {
            final String tagTitle = tagTitle1.trim();
            final JSONObject tag = new JSONObject();

            LOGGER.log(Level.TRACE, "Found a new tag[title={0}] in article[title={1}]", tagTitle,
                    article.optString(Article.ARTICLE_TITLE));
            tag.put(Tag.TAG_TITLE, tagTitle);
            final String tagId = tagRepository.add(tag);
            tag.put(Keys.OBJECT_ID, tagId);
            ret.put(tag);
        }

        return ret;
    }

    /**
     * Initializes administrator with the specified request json object, and then
     * logins it.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "userName": "",
     *                          "userAvatar": "", // optional
     *                          "userB3Key": "", // optional
     *                          "userGitHubId": "" // optional
     *                          }
     * @throws Exception exception
     */
    private void initAdmin(final JSONObject requestJSONObject) throws Exception {
        LOGGER.debug("Initializing admin....");
        final JSONObject admin = new JSONObject();

        admin.put(User.USER_NAME, requestJSONObject.getString(User.USER_NAME));
        admin.put(User.USER_URL, Latkes.getServePath());
        admin.put(User.USER_ROLE, Role.ADMIN_ROLE);
        admin.put(UserExt.USER_AVATAR, requestJSONObject.optString(UserExt.USER_AVATAR));
        admin.put(UserExt.USER_B3_KEY, requestJSONObject.optString(UserExt.USER_B3_KEY));
        admin.put(UserExt.USER_GITHUB_ID, requestJSONObject.optString(UserExt.USER_GITHUB_ID));
        userRepository.add(admin);

        LOGGER.info("Initialized admin");
    }

    /**
     * Initializes link.
     *
     * @throws Exception exception
     */
    private void initLink() throws Exception {
        LOGGER.debug("Initializing link....");
        LOGGER.info("Initialized link");
    }

    /**
     * Initializes statistic.
     *
     * @throws RepositoryException repository exception
     * @throws JSONException       json exception
     */
    private void initStatistic() throws RepositoryException, JSONException {
        LOGGER.debug("Initializing statistic....");

        final JSONObject statisticBlogViewCountOpt = new JSONObject();
        statisticBlogViewCountOpt.put(Keys.OBJECT_ID, Option.ID_C_STATISTIC_BLOG_VIEW_COUNT);
        statisticBlogViewCountOpt.put(Option.OPTION_VALUE, "0");
        statisticBlogViewCountOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_STATISTIC);
        optionRepository.add(statisticBlogViewCountOpt);

        LOGGER.info("Initialized statistic");
    }

    /**
     * Initializes options.
     *
     * @param requestJSONObject the specified json object
     * @throws Exception exception
     */
    private void initOptions(final JSONObject requestJSONObject) throws Exception {
        LOGGER.debug("Initializing preference....");

        List<Object[]> optList = Options.loadOptList(requestJSONObject);
        for (Object[] i : optList) {
            saveOption(i);
        }

        LOGGER.info("Initialized preference");
    }

    private void saveOption(Object[] opt) throws Exception {
        final JSONObject optOpt = new JSONObject();
        optOpt
                .put(Keys.OBJECT_ID, opt[0])
                .put(Option.OPTION_CATEGORY, opt[1])
                .put(Option.OPTION_VALUE, opt[2]);
        optionRepository.add(optOpt);
    }
}
