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

import java.util.List;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Paginator;
import org.b3log.latke.util.URLs;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * User query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.4.0
 */
@Service
public class UserQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserQueryService.class);

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Gets a user by the specified GitHub id.
     *
     * @param githubId the specified GitHub id
     * @return user, returns {@code null} if not found
     */
    public JSONObject getUserByGitHubId(final String githubId) {
        try {
            return userRepository.getFirst(
                    new Query().setFilter(new PropertyFilter(UserExt.USER_GITHUB_ID, FilterOperator.EQUAL, githubId)));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets a user by GitHub id [" + githubId + "] failed", e);

            return null;
        }
    }

    /**
     * Gets the administrator.
     *
     * @return administrator, returns {@code null} if not found
     */
    public JSONObject getAdmin() {
        try {
            return userRepository.getAdmin();
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets admin failed", e);
            return null;
        }
    }

    /**
     * Gets a user by the specified user name.
     *
     * @param userName the specified user name
     * @return user, returns {@code null} if not found
     */
    public JSONObject getUserByName(final String userName) {
        try {
            return userRepository.getByUserName(userName);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets a user by username [" + userName + "] failed", e);

            return null;
        }
    }

    /**
     * Gets users by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "paginationCurrentPageNum": 1,
     *                          "paginationPageSize": 20,
     *                          "paginationWindowSize": 10
     * @return for example,
     * 
     *         <pre>
     * {
     *     "pagination": {
     *         "paginationPageCount": 100,
     *         "paginationPageNums": [1, 2, 3, 4, 5]
     *     },
     *     "users": [{
     *         "oId": "",
     *         "userName": "",
     *         "roleName": ""
     *      }, ....]
     * }
     *         </pre>
     * 
     * @throws ServiceException service exception
     * @see Pagination
     */
    public JSONObject getUsers(final JSONObject requestJSONObject) throws ServiceException {
        final JSONObject ret = new JSONObject();

        final int currentPageNum = requestJSONObject.optInt(Pagination.PAGINATION_CURRENT_PAGE_NUM);
        final int pageSize = requestJSONObject.optInt(Pagination.PAGINATION_PAGE_SIZE);
        final int windowSize = requestJSONObject.optInt(Pagination.PAGINATION_WINDOW_SIZE);
        final Query query = new Query().setPage(currentPageNum, pageSize);

        JSONObject result;
        try {
            result = userRepository.get(query);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets users failed", e);

            throw new ServiceException(e);
        }

        final int pageCount = result.optJSONObject(Pagination.PAGINATION).optInt(Pagination.PAGINATION_PAGE_COUNT);
        final JSONObject pagination = new JSONObject();
        ret.put(Pagination.PAGINATION, pagination);
        final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);
        pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
        pagination.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);
        final JSONArray users = result.optJSONArray(Keys.RESULTS);
        ret.put(User.USERS, users);

        return ret;
    }

    /**
     * Gets a user by the specified user id.
     *
     * @param userId the specified user id
     * @return for example,
     * 
     *         <pre>
     * {
     *     "user": {
     *         "oId": "",
     *         "userName": ""
     *     }
     * }
     *         </pre>
     * 
     *         , returns {@code null} if not found
     */
    public JSONObject getUser(final String userId) {
        final JSONObject ret = new JSONObject();

        JSONObject user;
        try {
            user = userRepository.get(userId);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets a user failed", e);

            return null;
        }

        if (null == user) {
            return null;
        }

        ret.put(User.USER, user);

        return ret;
    }

    /**
     * Gets the URL of user logout.
     *
     * @return logout URL, returns {@code null} if the user is not logged in
     */
    public String getLogoutURL() {
        String to = Latkes.getServePath();
        to = URLs.encode(to);

        return Latkes.getContextPath() + "/logout?referer=" + to;
    }

    /**
     * Gets the URL of user login.
     *
     * @param redirectURL redirect URL after logged in
     * @return login URL
     */
    public String getLoginURL(final String redirectURL) {
        String to = Latkes.getServePath();
        to = URLs.encode(to + redirectURL);

        return Latkes.getContextPath() + "/start?referer=" + to;
    }

    /**
     * Get B3log Username. If it's not exists, will returns default account config.
     *
     * @return B3log Username
     */
    public String getB3username() {
        String b3name = "";
        try {
            b3name = optionRepository.get(Option.ID_C_HACPAI_USER).optString(Option.OPTION_VALUE);
        } catch (Exception e) {
            b3name = Option.DefaultPreference.DEFAULT_B3LOG_USERNAME;
        }
        if (b3name.isEmpty()) {
            b3name = Option.DefaultPreference.DEFAULT_B3LOG_USERNAME;
        }

        return b3name;
    }

    /**
     * Get B3log Passwrd. If it's not exists, will returns default account config.
     *
     * @return B3log Password
     */
    public String getB3password() {
        String b3pass = "";
        try {
            b3pass = optionRepository.get(Option.ID_C_B3LOG_KEY).optString(Option.OPTION_VALUE);
        } catch (Exception e) {
            b3pass = Option.DefaultPreference.DEFAULT_B3LOG_PASSWORD;
        }
        if (b3pass.isEmpty()) {
            b3pass = Option.DefaultPreference.DEFAULT_B3LOG_PASSWORD;
        }

        return b3pass;
    }

    public String getFishKey() {
        String fishKey = "";
        try {
            fishKey = optionRepository.get(Option.ID_C_FISH_KEY).optString(Option.OPTION_VALUE);
        } catch (Exception e) {
        }
        return fishKey;
    }
}
