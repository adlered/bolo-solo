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
package org.b3log.solo.model;

import org.b3log.latke.Keys;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class defines option model relevant keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://github.com/hzchendou">hzchendou</a>
 * @since 0.6.0
 */
public final class Option {

    /**
     * Option.
     */
    public static final String OPTION = "option";

    /**
     * Options.
     */
    public static final String OPTIONS = "options";

    /**
     * Key of option value.
     */
    public static final String OPTION_VALUE = "optionValue";

    /**
     * Key of option category.
     */
    public static final String OPTION_CATEGORY = "optionCategory";

    // oId constants

    /**
     * Key of code block line num show flag. 支持代码块行号显示
     * https://github.com/88250/solo/issues/4
     */
    public static final String ID_C_SHOW_CODE_BLOCK_LN = "showCodeBlockLn";

    /**
     * Key of editor mode. 支持配置编辑器模式 https://github.com/88250/solo/issues/95
     * <ul>
     * <li>wysiwyg：所见即所得</li>
     * <li>ir：即时渲染</li>
     * <li>sv：分屏预览</li>
     * </ul>
     * 模式细节介绍详见 <a href="https://github.com/Vanessa219/vditor">Vditor</a> 编辑器文档。
     */
    public static final String ID_C_EDITOR_MODE = "editorMode";

    /**
     * Key of hljs theme. 在设置中可选择语法高亮主题 https://github.com/b3log/solo/issues/12722
     */
    public static final String ID_C_HLJS_THEME = "hljsTheme";

    /**
     * Key of enable backup public articles to HacPai. 备份公开文章到社区
     * https://ld246.com/article/1557238327458
     */
    public static final String ID_C_SYNC_GITHUB = "syncGitHub";

    /**
     * Key of enable sync (pull) GitHub. 拉取并展示仓库
     * https://ld246.com/article/1557238327458
     * https://github.com/b3log/solo/issues/12825
     */
    public static final String ID_C_PULL_GITHUB = "pullGitHub";

    /**
     * Key of favicon URL.
     */
    public static final String ID_C_FAVICON_URL = "faviconURL";

    /**
     * Key of custom vars.
     */
    public static final String ID_C_CUSTOM_VARS = "customVars";

    /**
     * Key of blog title.
     */
    public static final String ID_C_BLOG_TITLE = "blogTitle";

    /**
     * Key of blog subtitle.
     */
    public static final String ID_C_BLOG_SUBTITLE = "blogSubtitle";

    /**
     * Key of relevant articles display count.
     */
    public static final String ID_C_RELEVANT_ARTICLES_DISPLAY_CNT = "relevantArticlesDisplayCount";

    /**
     * Key of random articles display count.
     */
    public static final String ID_C_RANDOM_ARTICLES_DISPLAY_CNT = "randomArticlesDisplayCount";

    /**
     * Key of recent article display count.
     */
    public static final String ID_C_RECENT_ARTICLE_DISPLAY_CNT = "recentArticleDisplayCount";

    /**
     * Key of recent comment display count.
     */
    public static final String ID_C_RECENT_COMMENT_DISPLAY_CNT = "recentCommentDisplayCount";

    /**
     * Key of most used tag display count.
     */
    public static final String ID_C_MOST_USED_TAG_DISPLAY_CNT = "mostUsedTagDisplayCount";

    /**
     * Key of most comment article display count.
     */
    public static final String ID_C_MOST_COMMENT_ARTICLE_DISPLAY_CNT = "mostCommentArticleDisplayCount";

    /**
     * Key of most view article display count.
     */
    public static final String ID_C_MOST_VIEW_ARTICLE_DISPLAY_CNT = "mostViewArticleDisplayCount";

    /**
     * Key of article list display count.
     */
    public static final String ID_C_ARTICLE_LIST_DISPLAY_COUNT = "articleListDisplayCount";

    /**
     * Key of article list pagination window size.
     */
    public static final String ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE = "articleListPaginationWindowSize";

    /**
     * Key of locale string.
     */
    public static final String ID_C_LOCALE_STRING = "localeString";

    /**
     * Key of time zone id.
     */
    public static final String ID_C_TIME_ZONE_ID = "timeZoneId";

    /**
     * Key of notice board.
     */
    public static final String ID_C_NOTICE_BOARD = "noticeBoard";

    /**
     * Key of HTML head.
     */
    public static final String ID_C_HTML_HEAD = "htmlHead";

    /**
     * Key of meta keywords.
     */
    public static final String ID_C_META_KEYWORDS = "metaKeywords";

    /**
     * Key of meta description.
     */
    public static final String ID_C_META_DESCRIPTION = "metaDescription";

    /**
     * Key of welfare lute service.
     */
    public static final String ID_C_WELFARE_LUTE_SERVICE = "welfareLuteService";

    /**
     * Key of article update hint flag.
     */
    public static final String ID_C_ENABLE_ARTICLE_UPDATE_HINT = "enableArticleUpdateHint";

    /**
     * Key of signs.
     */
    public static final String ID_C_SIGNS = "signs";

    /**
     * Key of allow visit draft via permalink.
     */
    public static final String ID_C_ALLOW_VISIT_DRAFT_VIA_PERMALINK = "allowVisitDraftViaPermalink";

    /**
     * Key of version.
     */
    public static final String ID_C_VERSION = "version";

    /**
     * Key of article list display style.
     * <p>
     * Optional values:
     * <ul>
     * <li>"titleOnly"</li>
     * <li>"titleAndContent"</li>
     * <li>"titleAndAbstract"</li>
     * </ul>
     * </p>
     */
    public static final String ID_C_ARTICLE_LIST_STYLE = "articleListStyle";

    /**
     * Article Image Upload Compress.
     */
    public static final String ID_C_IMAGE_UPLOAD_COMPRESS = "imageUploadCompress";

    /**
     * Article Thumb Compress.
     */
    public static final String ID_C_THUMB_COMPRESS = "thumbCompress";

    /**
     * Key of article/page comment-able.
     */
    public static final String ID_C_COMMENTABLE = "commentable";

    /**
     * Key of feed (Atom/RSS) output mode.
     * <p>
     * Optional values:
     * <ul>
     * <li>"abstract"</li>
     * <li>"fullContent"</li>
     * </ul>
     * </p>
     */
    public static final String ID_C_FEED_OUTPUT_MODE = "feedOutputMode";

    /**
     * Key of feed (Atom/RSS) output entry count.
     */
    public static final String ID_C_FEED_OUTPUT_CNT = "feedOutputCnt";

    /**
     * Key of skin dir name.
     */
    public static final String ID_C_SKIN_DIR_NAME = "skinDirName";

    /**
     * Key of mobile skin dir name.
     */
    public static final String ID_C_MOBILE_SKIN_DIR_NAME = "mobileSkinDirName";

    /**
     * Key of footer content.
     */
    public static final String ID_C_FOOTER_CONTENT = "footerContent";

    /**
     * Key of statistic blog view count.
     */
    public static final String ID_C_STATISTIC_BLOG_VIEW_COUNT = "statisticBlogViewCount";

    /**
     * Key of GitHub repos.
     */
    public static final String ID_C_GITHUB_REPOS = "githubRepos";

    /**
     * Key of USite.
     */
    public static final String ID_C_USITE = "usite";

    // Category constants
    /**
     * Category - Preference.
     */
    public static final String CATEGORY_C_PREFERENCE = "preference";

    /**
     * Category - Statistic.
     */
    public static final String CATEGORY_C_STATISTIC = "statistic";

    /**
     * Category - GitHub.
     */
    public static final String CATEGORY_C_GITHUB = "github";

    /**
     * Category - HacPai.
     */
    public static final String CATEGORY_C_HACPAI = "hacpai";

    /**
     * Category - Skin.
     */
    public static final String CATEGORY_C_SKIN = "skin";

    //// Transient ////
    /**
     * Key of statistic blog published article count.
     */
    public static final String ID_T_STATISTIC_PUBLISHED_ARTICLE_COUNT = "statisticPublishedBlogArticleCount";

    /**
     * Key of statistic blog comment(published article) count.
     */
    public static final String ID_T_STATISTIC_PUBLISHED_BLOG_COMMENT_COUNT = "statisticPublishedBlogCommentCount";

    /**
     * Key of GitHub PAT (personal access token).
     */
    public static final String ID_C_GITHUB_PAT = "githubPAT";

    // Bolo

    /**
     * Key of hacpai username.
     */
    public static final String ID_C_HACPAI_USER = "hacpaiUser";

    /**
     * Key of FishPi key.
     */
    public static final String ID_C_FISH_KEY = "fishKey";

    /**
     * Key of B3log key.
     */
    public static final String ID_C_B3LOG_KEY = "b3logKey";

    /**
     * Key of max archive showing on index page.
     */
    public static final String ID_C_MAX_ARCHIVE = "maxArchive";

    /**
     * Key of Mailbox.
     */
    public static final String ID_C_MAIL_BOX = "mailBox";

    /**
     * Key of Mailbox Username.
     */
    public static final String ID_C_MAIL_USERNAME = "mailUsername";

    /**
     * Key of Mailbox Password.
     */
    public static final String ID_C_MAIL_PASSWORD = "mailPassword";

    /**
     * Key of Mail User Context.
     */
    public static final String ID_C_MAIL_USER_CONTEXT = "mailUserContext";

    /**
     * Key of TuChuang Config.
     */
    public static final String ID_C_TUCHUANG_CONFIG = "tuChuangConfig";

    /**
     * Key of replyRemind.
     */
    public static final String ID_C_REPLY_REMIND = "replyRemind";

    /**
     * Key of KanBanNiang selector.
     */
    public static final String ID_C_KANBANNIANG_SELECTOR = "kanbanniangSelector";

    /**
     * Key of spam words.
     */
    public static final String ID_C_SPAM = "spam";

    /**
     * Key of interactive switch.
     */
    public static final String ID_C_INTERACTIVE = "interactive";

    /**
     * Key of WAF.
     */
    public static final String ID_C_WAF_POWER = "wafPower";

    /**
     * Key of WAF current limit by second.
     */
    public static final String ID_C_WAF_CURRENT_LIMIT_SECOND = "wafCurrentLimitSecond";

    /**
     * Key of WAF current limit by times.
     */
    public static final String ID_C_WAF_CURRENT_LIMIT_TIMES = "wafCurrentLimitTimes";

    /**
     * Key of admin Active Sent To Mailbox.
     */
    public static final String ID_C_ADMIN_ACTIVE_SENT_TO_MAILBOX = "adminActiveSentToMailbox";

    /**
     * Key of enable auto flush github.
     */
    public static final String ID_C_ENABLE_AUTO_FLUSH_GITHUB = "enableAutoFlushGitHub";

    /**
     * Key of enable auto flush blog to github profile repo.
     */
    public static final String ID_C_ENABLE_AUTO_FLUSH_BLOG_TO_GITHUB_PROFILE = "enableAutoFlushGitHubProfile";

    /**
     * Key of my github id.
     */
    public static final String ID_C_MY_GITHUB_ID = "myGitHubID";

    /**
     * Key of server jiang send key.
     */
    public static final String ID_C_SEND_KEY = "sendKey";

    /**
     * Key of Improve Helping Plan.
     */
    public static final String ID_C_HELP_IMPROVE_PLAN = "helpImprovePlan";

    /**
     * Private constructor.
     */
    private Option() {
    }

    /**
     * Default preference.
     *
     * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
     * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
     * @since 0.3.1
     */
    public static final class DefaultPreference {

        /**
         * Default show code block line num.
         */
        public static final String DEFAULT_SHOW_CODE_BLOCK_LN = "false";

        /**
         * Default hljs theme.
         */
        public static final String DEFAULT_HLJS_THEME = "github";

        /**
         * Default enable sync push GitHub.
         */
        public static final String DEFAULT_SYNC_GITHUB = "true";

        /**
         * Default enable sync pull GitHub.
         */
        public static final String DEFAULT_PULL_GITHUB = "true";

        /**
         * Default favicon URL.
         */
        public static final String DEFAULT_FAVICON_URL = "https://pic.stackoverflow.wiki/uploadImages/114/246/231/87/2020/06/06/16/41/3e4a3ce8-8882-4258-9860-a337bf859605.png";

        /**
         * Default custom vars.
         */
        public static final String DEFAULT_CUSTOM_VARS = "key0=val0|key1=list|key2=val2";

        /**
         * Default recent article display count.
         */
        public static final int DEFAULT_RECENT_ARTICLE_DISPLAY_COUNT = 10;

        /**
         * Default recent comment display count.
         */
        public static final int DEFAULT_RECENT_COMMENT_DISPLAY_COUNT = 10;

        /**
         * Default most used tag display count.
         */
        public static final int DEFAULT_MOST_USED_TAG_DISPLAY_COUNT = 20;

        /**
         * Default article list display count.
         */
        public static final int DEFAULT_ARTICLE_LIST_DISPLAY_COUNT = 20;

        /**
         * Default article list pagination window size.
         */
        public static final int DEFAULT_ARTICLE_LIST_PAGINATION_WINDOW_SIZE = 15;

        /**
         * Default most comment article display count.
         */
        public static final int DEFAULT_MOST_COMMENT_ARTICLE_DISPLAY_COUNT = 5;

        /**
         * Default blog subtitle.
         */
        public static final String DEFAULT_BLOG_SUBTITLE = "记录精彩的程序人生";

        /**
         * Default skin directory name.
         */
        public static final String DEFAULT_SKIN_DIR_NAME = "bolo-nexmoe";

        /**
         * Default mobile skin directory name.
         */
        public static final String DEFAULT_MOBILE_SKIN_DIR_NAME = "bolo-nexmoe";

        /**
         * Default language.
         */
        public static final String DEFAULT_LANGUAGE = "zh_CN";

        /**
         * Default time zone.
         *
         * @see java.util.TimeZone#getAvailableIDs()
         */
        public static final String DEFAULT_TIME_ZONE = "Asia/Shanghai";

        /**
         * Default welfare lute service.
         */
        public static final String DEFAULT_WELFARE_LUTE_SERVICE = "false";

        /**
         * Default enable article update hint.
         */
        public static final String DEFAULT_ENABLE_ARTICLE_UPDATE_HINT = "false";

        /**
         * Default notice board.
         */
        public static final String DEFAULT_NOTICE_BOARD = "Open Source, Open Mind, <br/>Open Sight, Open Future!\n\n<!-- 公告栏可使用 HTML、JavaScript，比如可以在此加入第三方统计 js -->";

        /**
         * Default meta keywords..
         */
        public static final String DEFAULT_META_KEYWORDS = "Solo,Java,博客,开源";

        /**
         * Default meta description..
         */
        public static final String DEFAULT_META_DESCRIPTION = "A small and beautiful blogging system. 一款小而美的博客系统。";

        /**
         * Default HTML head to append.
         */
        public static final String DEFAULT_HTML_HEAD = "";

        /**
         * Default footer content.
         */
        public static final String DEFAULT_FOOTER_CONTENT = "";

        /**
         * Default relevant articles display count.
         */
        public static final int DEFAULT_RELEVANT_ARTICLES_DISPLAY_COUNT = 5;

        /**
         * Default random articles display count.
         */
        public static final int DEFAULT_RANDOM_ARTICLES_DISPLAY_COUNT = 5;

        /**
         * Default external relevant articles display count.
         */
        public static final int DEFAULT_EXTERNAL_RELEVANT_ARTICLES_DISPLAY_COUNT = 0;

        /**
         * Most view articles display count.
         */
        public static final int DEFAULT_MOST_VIEW_ARTICLES_DISPLAY_COUNT = 5;

        /**
         * Default signs.
         */
        public static final String DEFAULT_SIGNS;

        /**
         * Default allow visit draft via permalink.
         */
        public static final String DEFAULT_ALLOW_VISIT_DRAFT_VIA_PERMALINK = "false";

        /**
         * Default allow comment article/page.
         */
        public static final String DEFAULT_COMMENTABLE = "true";

        /**
         * Default article list display style.
         */
        public static final String DEFAULT_ARTICLE_LIST_STYLE = "titleAndAbstract";

        /**
         * Default feed output mode.
         */
        public static final String DEFAULT_FEED_OUTPUT_MODE = "abstract";

        /**
         * Default feed output entry count.
         */
        public static final int DEFAULT_FEED_OUTPUT_CNT = 10;

        // Bolo
        /**
         * Default max archive.
         */
        public static final String DEFAULT_MAX_ARCHIVE = "-1";

        public static final String DEFAULT_IMAGE_UPLOAD_COMPRESS = "10";

        public static final String DEFAULT_THUMB_COMPRESS = "10";

        /**
         * Default B3log username.
         */
        public static final String DEFAULT_B3LOG_USERNAME = "BoloDefault";

        /**
         * Default B3log password.
         */
        public static final String DEFAULT_B3LOG_PASSWORD = "123456";

        static {
            final JSONArray signs = new JSONArray();
            for (int i = 0; i < 4; i++) {
                final JSONObject sign = new JSONObject();
                sign.put(Keys.OBJECT_ID, i);
                signs.put(sign);
                String html = "<hr>\n\n";
                html += "标题：{title}<br>\n";
                html += "作者：<a href=\"{blog}\" target=\"_blank\">{author}</a><br>\n";
                html += "地址：<a href=\"{url}\" target=\"_blank\">{url}</a><br>\n\n";
                html += "<!-- 签名档内可使用 HTML、JavaScript -->\n<br>";
                sign.put(Sign.SIGN_HTML, html);
            }

            // Sign(id=0) is the 'empty' sign, used for article user needn't a sign
            DEFAULT_SIGNS = signs.toString();
        }

        /**
         * Private constructor.
         */
        private DefaultPreference() {
        }
    }
}
