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
package org.b3log.solo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.latke.util.Crypts;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.bolo.Global;
import org.b3log.solo.bolo.prop.Options;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.service.UserQueryService;
import org.json.JSONObject;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/**
 * Solo utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 2.8.0
 */
public final class Solos {

    /**
     * Favicon API.
     */
    public static final String FAVICON_API;
    /**
     * Solo User-Agent.
     */
    public static final String USER_AGENT = "Solo/" + SoloServletListener.VERSION + "; +https://github.com/b3log/solo";

    /**
     * Bolo User-Agent.
     */
    public static final String BOLO_USER_AGENT = "Bolo/" + SoloServletListener.BOLO_VERSION_SOURCE
            + "; +https://github.com/adlered/bolo-solo";
    /**
     * Cookie name.
     */
    public static final String COOKIE_NAME;
    /**
     * Cookie secret.
     */
    public static final String COOKIE_SECRET;
    /**
     * Cookie HTTP only.
     */
    public static final boolean COOKIE_HTTP_ONLY;
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Solos.class);
    /**
     * Cookie expiry in 30 days.
     */
    private static final int COOKIE_EXPIRY = 60 * 60 * 24 * 30;
    /**
     * Default page size.
     */
    private static final int DEFAULT_PAGE_SIZE = 15;
    /**
     * Default window size.
     */
    private static final int DEFAULT_WINDOW_SIZE = 20;
    private static long uploadTokenCheckTime;
    private static long uploadTokenTime;
    private static String uploadToken = "";
    private static String uploadURL = "https://" + Global.HACPAI_DOMAIN + "/upload/client";
    private static String uploadMsg = "";

    static {
        ResourceBundle solo;
        try {
            solo = ResourceBundle.getBundle("solo");
        } catch (final MissingResourceException e) {
            solo = ResourceBundle.getBundle("b3log"); // 2.8.0 向后兼容
        }

        FAVICON_API = solo.getString("faviconAPI");

        if (!Markdowns.LUTE_AVAILABLE) {
            try {
                Markdowns.LUTE_ENGINE_URL = solo.getString("luteHttp");
                String luteHttp2 = System.getProperty("lute_http");
                if (luteHttp2 != null && !luteHttp2.isEmpty()) {
                    Markdowns.LUTE_ENGINE_URL = luteHttp2;
                    Markdowns.LUTE_AVAILABLE = true;
                } else {
                    throw new Exception("No Lute-Http Service at local.");
                }
                if (Markdowns.LUTE_AVAILABLE) {
                    LOGGER.log(Level.INFO, "lute_http configure detected [url=" + Markdowns.LUTE_ENGINE_URL + "]");
                }
            } catch (final Exception e) {
                enableWelfareLuteService();
            }
        }
    }

    static {
        String cookieNameConf = Latkes.getLatkeProperty("cookieName");
        if (StringUtils.isBlank(cookieNameConf)) {
            cookieNameConf = "solo";
        }
        COOKIE_NAME = cookieNameConf;

        String cookieSecret = Latkes.getLatkeProperty("cookieSecret");
        if (StringUtils.isBlank(cookieSecret)) {
            cookieSecret = RandomStringUtils.randomAlphanumeric(8);
        }
        COOKIE_SECRET = cookieSecret;

        COOKIE_HTTP_ONLY = Boolean.valueOf(Latkes.getLocalProperty("cookieHttpOnly"));
    }

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Private constructor.
     */
    private Solos() {
    }

    public static void enableWelfareLuteService() {
        ResourceBundle solo;
        try {
            solo = ResourceBundle.getBundle("solo");
        } catch (final MissingResourceException e) {
            solo = ResourceBundle.getBundle("b3log"); // 2.8.0 向后兼容
        }
        boolean status = Boolean.parseBoolean(Options.get(Option.ID_C_WELFARE_LUTE_SERVICE));
        // 使用公益Lute-Http服务(Bolo专享)
        final String LUTE_URL = "http://lute.stackoverflow.wiki:8249";
        boolean needTest = false;
        String testErrorMsg = "";
        if (status) {
            Markdowns.LUTE_ENGINE_URL = LUTE_URL;
            Markdowns.LUTE_AVAILABLE = status;
            needTest = true;
            testErrorMsg = "The connection to the public welfare Lute service failed. Lute rendering will be disabled and the built-in renderer will be used.";
        } else if (Markdowns.LUTE_AVAILABLE && !LUTE_URL.equals(Markdowns.LUTE_ENGINE_URL)) {
            needTest = true;
            testErrorMsg = "The connection to the private welfare Lute service failed. Lute rendering will be disabled and the built-in renderer will be used.";
        } else {
            Markdowns.LUTE_AVAILABLE = status;
            LOGGER.log(Level.INFO, "Welfare Lute Service has disabled.");
        }
        if (needTest) {
            try {
                Markdowns.toHtmlByLute("#test");
            } catch (Exception exception) {
                LOGGER.log(Level.INFO, testErrorMsg);
                Markdowns.LUTE_AVAILABLE = false;
            }
        }
    }

    /**
     * Constructs a successful result.
     *
     * @return result
     */
    public static JSONObject newSucc() {
        return new JSONObject().put(Keys.CODE, 0).put(Keys.MSG, "");
    }

    /**
     * Constructs a failed result.
     *
     * @return result
     */
    public static JSONObject newFail() {
        return new JSONObject().put(Keys.CODE, -1).put(Keys.MSG, "System is abnormal, please try again later");
    }

    /**
     * Gets upload token.
     *
     * @param context the specified context
     * @return upload token and URL, returns {@code null} if not found
     */
    public static JSONObject getUploadToken(final RequestContext context) {
        try {
            final JSONObject currentUser = getCurrentUser(context.getRequest(), context.getResponse());
            if (null == currentUser) {
                return null;
            }

            final BeanManager beanManager = BeanManager.getInstance();
            UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);
            String userName = userQueryService.getB3username();
            String userB3Key = userQueryService.getB3password();

            final long now = System.currentTimeMillis();
            if (3600000 >= now - uploadTokenTime) {
                return new JSONObject().put(Common.UPLOAD_TOKEN, uploadToken).put(Common.UPLOAD_URL, uploadURL)
                        .put(Common.UPLOAD_MSG, uploadMsg);
            }

            if (15000 >= now - uploadTokenCheckTime) {
                return new JSONObject().put(Common.UPLOAD_TOKEN, uploadToken).put(Common.UPLOAD_URL, uploadURL)
                        .put(Common.UPLOAD_MSG, uploadMsg);
            }

            final JSONObject requestJSON = new JSONObject().put(User.USER_NAME, userName).put(UserExt.USER_B3_KEY,
                    userB3Key);
            final HttpResponse res = HttpRequest.post("https://" + Global.HACPAI_DOMAIN + "/apis/upload/token")
                    .body(requestJSON.toString()).connectionTimeout(3000).timeout(7000)
                    .header("User-Agent", Solos.USER_AGENT).send();
            uploadTokenCheckTime = now;
            if (HttpServletResponse.SC_OK != res.statusCode()) {
                return null;
            }
            res.charset("UTF-8");
            final JSONObject result = new JSONObject(res.bodyText());
            if (0 != result.optInt(Keys.CODE)) {
                uploadMsg = result.optString(Keys.MSG);
                // 去除后台 B3 Key 提示
                // LOGGER.log(Level.ERROR, uploadMsg);

                // return null;
            }

            final JSONObject data = result.optJSONObject(Common.DATA);
            uploadTokenTime = now;
            // 自定义图床字段
            final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);
            String config = "hacpai";
            try {
                config = optionRepository.get(Option.ID_C_TUCHUANG_CONFIG).optString(Option.OPTION_VALUE);
            } catch (Exception e) {
            }
            if (config.equals("hacpai") || config.isEmpty()) {
                try {
                    uploadURL = data.optString("uploadURL");
                } catch (NullPointerException NPE) {
                }
            } else {
                uploadURL = Latkes.getStaticServePath() + "/pic/upload";
            }
            try {
                uploadToken = data.optString("uploadToken");
            } catch (NullPointerException NPE) {
                uploadToken = "";
            }
            uploadMsg = "";

            return new JSONObject().put(Common.UPLOAD_TOKEN, uploadToken).put(Common.UPLOAD_URL, uploadURL)
                    .put(Common.UPLOAD_MSG, uploadMsg);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            LOGGER.log(Level.ERROR, "Gets Hacpai upload token failed, Wrong Hacpai Username / B3log key.");

            return null;
        }
    }

    /**
     * Sanitizes the specified file name.
     *
     * @param unsanitized the specified file name
     * @return sanitized file name
     */
    public static String sanitizeFilename(final String unsanitized) {
        return unsanitized.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5\\.)]", "")
                .replaceAll("[\\?\\\\/:|<>\\*\\[\\]\\(\\)\\$%\\{\\}@~]", "").replaceAll("\\s", "");
    }

    /**
     * Adds noindex header for Google. https://github.com/b3log/solo/issues/12631
     * <p>
     * 使用“noindex”阻止搜索引擎将您的网页编入索引
     * https://support.google.com/webmasters/answer/93710?hl=zh-Hans
     * </p>
     *
     * @param context the specified context
     */
    public static void addGoogleNoIndex(final RequestContext context) {
        context.setHeader("X-Robots-Tag", "noindex");
    }

    /**
     * Gets the current logged-in user.
     *
     * @param request  the specified request
     * @param response the specified response
     * @return the current logged-in user, returns {@code null} if not found
     */
    public static JSONObject getCurrentUser(final HttpServletRequest request, final HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        if (null == cookies || 0 == cookies.length) {
            return null;
        }

        final BeanManager beanManager = BeanManager.getInstance();
        final UserRepository userRepository = beanManager.getReference(UserRepository.class);
        try {
            for (int i = 0; i < cookies.length; i++) {
                final Cookie cookie = cookies[i];
                if (!COOKIE_NAME.equals(cookie.getName())) {
                    continue;
                }

                final String value = Crypts.decryptByAES(cookie.getValue(), COOKIE_SECRET);
                final JSONObject cookieJSONObject = new JSONObject(value);

                final String userId = cookieJSONObject.optString(Keys.OBJECT_ID);
                if (StringUtils.isBlank(userId)) {
                    break;
                }

                JSONObject user = userRepository.get(userId);
                if (null == user) {
                    break;
                }

                final String b3Key = user.optString(UserExt.USER_B3_KEY);
                final String tokenVal = cookieJSONObject.optString(Keys.TOKEN);
                final String token = StringUtils.substringBeforeLast(tokenVal, ":");
                if (StringUtils.equals(b3Key, token)) {
                    login(user, response);

                    return user;
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.TRACE, "Parses cookie failed, clears the cookie [name=" + COOKIE_NAME + "]");

            final Cookie cookie = new Cookie(COOKIE_NAME, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return null;
    }

    /**
     * Logins the specified user from the specified request.
     *
     * @param response the specified response
     * @param user     the specified user
     */
    public static void login(final JSONObject user, final HttpServletResponse response) {
        try {
            final String userId = user.optString(Keys.OBJECT_ID);
            final JSONObject cookieJSONObject = new JSONObject();
            cookieJSONObject.put(Keys.OBJECT_ID, userId);
            final String b3Key = user.optString(UserExt.USER_B3_KEY);
            final String random = RandomStringUtils.randomAlphanumeric(8);
            cookieJSONObject.put(Keys.TOKEN, b3Key + ":" + random);
            final String cookieValue = Crypts.encryptByAES(cookieJSONObject.toString(), COOKIE_SECRET);
            final Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_EXPIRY);
            cookie.setHttpOnly(COOKIE_HTTP_ONLY);
            response.addCookie(cookie);
        } catch (final Exception e) {
            LOGGER.log(Level.WARN, "Can not write cookie", e);
        }
    }

    /**
     * Logouts the specified user.
     *
     * @param request  the specified request
     * @param response the specified response
     * @return {@code true} if succeed, otherwise returns {@code false}
     */
    public static void logout(final HttpServletRequest request, final HttpServletResponse response) {
        if (null != response) {
            final Cookie cookie = new Cookie(COOKIE_NAME, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    /**
     * Checks whether the current request is made by a logged in user
     * (including default user and administrator lists in <i>users</i>).
     *
     * @param context the specified request context
     * @return {@code true} if the current request is made by logged in user,
     *         returns {@code false} otherwise
     */
    public static boolean isLoggedIn(final RequestContext context) {
        return null != Solos.getCurrentUser(context.getRequest(), context.getResponse());
    }

    /**
     * Checks whether the current request is made by logged in administrator.
     *
     * @param context the specified request context
     * @return {@code true} if the current request is made by logged in
     *         administrator, returns {@code false} otherwise
     */
    public static boolean isAdminLoggedIn(final RequestContext context) {
        final JSONObject user = getCurrentUser(context.getRequest(), context.getResponse());
        if (null == user) {
            return false;
        }

        return Role.ADMIN_ROLE.equals(user.optString(User.USER_ROLE));
    }

    /**
     * Checks whether the current request is made by logged in administrator or an
     * author.
     *
     * @param context the specified request context
     * @return {@code true} if the current request is made by logged in
     *         administrator, author, returns {@code false} otherwise
     */
    public static boolean isAdminOrAuthorLoggedIn(final RequestContext context) {
        final JSONObject user = getCurrentUser(context.getRequest(), context.getResponse());
        if (null == user) {
            return false;
        }

        return (Role.ADMIN_ROLE.equals(user.optString(User.USER_ROLE)) ||
                Role.DEFAULT_ROLE.equals(user.optString(User.USER_ROLE)));
    }

    /**
     * Checks whether need password to view the specified article with the specified
     * request.
     * <p>
     * Checks session, if not represents, checks article property
     * {@link Article#ARTICLE_VIEW_PWD view password}.
     * </p>
     * <p>
     * The blogger itself dose not need view password never.
     * </p>
     *
     * @param context the specified request context
     * @param article the specified article
     * @return {@code true} if need, returns {@code false} otherwise
     */
    public static boolean needViewPwd(final RequestContext context, final JSONObject article) {
        final String articleViewPwd = article.optString(Article.ARTICLE_VIEW_PWD);

        if (StringUtils.isBlank(articleViewPwd)) {
            return false;
        }

        final HttpServletRequest request = context.getRequest();
        if (null == request) {
            return true;
        }

        final HttpSession session = request.getSession();
        if (null != session) {
            Map<String, String> viewPwds = (Map<String, String>) session.getAttribute(Common.ARTICLES_VIEW_PWD);
            if (null == viewPwds) {
                viewPwds = new HashMap<>();
            }

            if (articleViewPwd.equals(viewPwds.get(article.optString(Keys.OBJECT_ID)))) {
                return false;
            }
        }

        final HttpServletResponse response = context.getResponse();
        final JSONObject currentUser = getCurrentUser(request, response);

        return !(null != currentUser && !Role.VISITOR_ROLE.equals(currentUser.optString(User.USER_ROLE)));
    }

    /**
     * Checks the specified request is made from a mobile device.
     *
     * @param request the specified request
     * @return {@code true} if it is, returns {@code false} otherwise
     */
    public static boolean isMobile(final HttpServletRequest request) {
        final Object val = request.getAttribute(Keys.HttpRequest.IS_MOBILE_BOT);
        if (!(val instanceof Boolean)) {
            return false;
        }

        return (boolean) val;
    }

    /**
     * Checks the specified request is made from a bot.
     *
     * @param request the specified request
     * @return {@code true} if it is, returns {@code false} otherwise
     */
    public static boolean isBot(final HttpServletRequest request) {
        final Object val = request.getAttribute(Keys.HttpRequest.IS_SEARCH_ENGINE_BOT);
        if (!(val instanceof Boolean)) {
            return false;
        }

        return (boolean) val;
    }

    /**
     * Gets the default user avatar URL..
     *
     * @return default user avatar URL
     */
    public static String getDefaultAvatar() {
        return Latkes.getStaticServePath() + "/images/default-user-thumbnail.png";
    }

    /**
     * Clones a JSON object from the specified source object.
     *
     * @param src the specified source object
     * @return cloned object
     */
    public static JSONObject clone(final JSONObject src) {
        return new JSONObject(src, CollectionUtils.jsonArrayToArray(src.names(), String[].class));
    }

    /**
     * Builds pagination request with the specified path.
     *
     * @param path the specified path, "/{page}/{pageSize}/{windowSize}"
     * @return pagination request json object, for example,
     * 
     *         <pre>
     * {
     *     "paginationCurrentPageNum": int,
     *     "paginationPageSize": int,
     *     "paginationWindowSize": int
     * }
     *         </pre>
     */
    public static JSONObject buildPaginationRequest(final String path) {
        final Integer currentPageNum = getCurrentPageNum(path);
        final Integer pageSize = getPageSize(path);
        final Integer windowSize = getWindowSize(path);

        final JSONObject ret = new JSONObject();
        ret.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, currentPageNum);
        ret.put(Pagination.PAGINATION_PAGE_SIZE, pageSize);
        ret.put(Pagination.PAGINATION_WINDOW_SIZE, windowSize);

        return ret;
    }

    /**
     * Gets the request page number from the specified path.
     *
     * @param path the specified path
     * @return page number, returns {@code 1} if the specified request URI can not
     *         convert to an number
     */
    private static int getCurrentPageNum(final String path) {
        if (StringUtils.isBlank(path) || path.equals("/")) {
            return 1;
        }
        final String currentPageNumber = path.split("/")[0];
        if (!Strings.isNumeric(currentPageNumber)) {
            return 1;
        }

        return Integer.valueOf(currentPageNumber);
    }

    /**
     * Gets the request page size from the specified path.
     *
     * @param path the specified path
     * @return page number, returns {@value #DEFAULT_PAGE_SIZE} if the specified
     *         request URI can not convert to an number
     */
    private static int getPageSize(final String path) {
        if (StringUtils.isBlank(path)) {
            return DEFAULT_PAGE_SIZE;
        }
        final String[] parts = path.split("/");
        if (1 >= parts.length) {
            return DEFAULT_PAGE_SIZE;
        }
        final String pageSize = parts[1];
        if (!Strings.isNumeric(pageSize)) {
            return DEFAULT_PAGE_SIZE;
        }

        return Integer.valueOf(pageSize);
    }

    /**
     * Gets the request window size from the specified path.
     *
     * @param path the specified path
     * @return page number, returns {@value #DEFAULT_WINDOW_SIZE} if the specified
     *         request URI can not convert to an number
     */
    private static int getWindowSize(final String path) {
        if (StringUtils.isBlank(path)) {
            return DEFAULT_WINDOW_SIZE;
        }
        final String[] parts = path.split("/");
        if (2 >= parts.length) {
            return DEFAULT_WINDOW_SIZE;
        }
        final String windowSize = parts[2];
        if (!Strings.isNumeric(windowSize)) {
            return DEFAULT_WINDOW_SIZE;
        }

        return Integer.valueOf(windowSize);
    }
}
