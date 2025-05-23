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

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Strings;
import org.b3log.solo.bolo.Global;
import org.b3log.solo.bolo.tool.MD5Utils;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * User management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://ld246.com/member/DASHU">DASHU</a>
 * @author <a href="https://github.com/nanolikeyou">nanolikeyou</a>
 * @since 0.4.0
 */
@Service
public class UserMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserMgmtService.class);

    /**
     * Length of hashed password.
     */
    private static final int HASHED_PASSWORD_LENGTH = 32;

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

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
     * Option management service.
     */
    @Inject
    private OptionMgmtService optionMgmtService;

    /**
     * Init service.
     */
    @Inject
    private InitService initService;

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Refresh usite. 展示站点连接 https://github.com/b3log/solo/issues/12719
     */
    public void refreshUSite() {
        if (!initService.isInited()) {
            return;
        }

        final BeanManager beanManager = BeanManager.getInstance();
        UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);
        String userName = userQueryService.getB3username();
        String userB3Key = userQueryService.getB3password();
        if (Option.DefaultPreference.DEFAULT_B3LOG_USERNAME.equals(userName)) {
            LOGGER.log(Level.INFO, "Usite refresh skipped because using the default B3 account.");

            return;
        }

        JSONObject usite;
        try {
            final JSONObject requestJSON = new JSONObject().
                    put(User.USER_NAME, userName).
                    put(UserExt.USER_B3_KEY, userB3Key);
            final HttpResponse res = HttpRequest.post("https://" + Global.HACPAI_DOMAIN + "/user/usite").
                    connectionTimeout(3000).timeout(7000).header("User-Agent", Solos.USER_AGENT).
                    body(requestJSON.toString()).send();
            if (200 != res.statusCode()) {
                return;
            }
            res.charset("UTF-8");
            final JSONObject result = new JSONObject(res.bodyText());
            if (0 != result.optInt(Keys.STATUS_CODE)) {
                LOGGER.log(Level.DEBUG, "Updates usite option failed: Invalid Username or B3log Key.");

                return;
            }
            usite = result.optJSONObject(Common.DATA);
        } catch (final Exception e) {
            LOGGER.log(Level.DEBUG, "Gets usite failed", e);

            return;
        }

        JSONObject usiteOpt = optionQueryService.getOptionById(Option.ID_C_USITE);
        if (null == usiteOpt) {
            usiteOpt = new JSONObject();
            usiteOpt.put(Keys.OBJECT_ID, Option.ID_C_USITE);
            usiteOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_HACPAI);
        }
        usiteOpt.put(Option.OPTION_VALUE, usite.toString());
        try {
            optionMgmtService.addOrUpdateOption(usiteOpt);
            LOGGER.log(Level.INFO, "Usite refresh from HacPai successful: " + usite.toString());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Updates usite option failed", e);

            return;
        }
    }

    /**
     * Updates a user by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "oId": "",
     *                          "userName": "",
     *                          "userRole": "",
     *                          "userURL": "",
     *                          "userB3Key": "",
     *                          "userGitHubId": "" // optional
     * @throws ServiceException service exception
     */
    public void updateUser(final JSONObject requestJSONObject, boolean changePassword) throws ServiceException {
        final Transaction transaction = userRepository.beginTransaction();

        try {
            final String oldUserId = requestJSONObject.optString(Keys.OBJECT_ID);
            final JSONObject oldUser = userRepository.get(oldUserId);
            if (null == oldUser) {
                throw new ServiceException(langPropsService.get("updateFailLabel"));
            }

            final String userName = requestJSONObject.optString(User.USER_NAME);
            if (UserExt.invalidUserName(userName)) {
                throw new ServiceException(langPropsService.get("userNameInvalidLabel"));
            }

            JSONObject mayBeAnother = userRepository.getByUserName(userName);
            if (null != mayBeAnother && !mayBeAnother.optString(Keys.OBJECT_ID).equals(oldUserId)) {
                throw new ServiceException(langPropsService.get("duplicatedUserNameLabel"));
            }
            oldUser.put(User.USER_NAME, userName);

            final String userRole = requestJSONObject.optString(User.USER_ROLE);
            oldUser.put(User.USER_ROLE, userRole);

            final String userURL = requestJSONObject.optString(User.USER_URL);
            oldUser.put(User.USER_URL, userURL);

            final String userAvatar = requestJSONObject.optString(UserExt.USER_AVATAR);
            oldUser.put(UserExt.USER_AVATAR, userAvatar);

            if (changePassword) {
                final String userB3Key = requestJSONObject.optString(UserExt.USER_B3_KEY);
                if (userB3Key != null && !userB3Key.isEmpty()) {
                    oldUser.put(UserExt.USER_B3_KEY, userB3Key);
                }
            }

            final String userGitHubId = requestJSONObject.optString(UserExt.USER_GITHUB_ID);
            if (StringUtils.isNotBlank(userGitHubId)) {
                oldUser.put(UserExt.USER_GITHUB_ID, userGitHubId);
            }

            userRepository.update(oldUserId, oldUser);
            transaction.commit();
        } catch (final RepositoryException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Updates a user failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Switches the user role between "defaultRole" and "visitorRole" by the specified user id.
     *
     * @param userId the specified user id
     * @throws ServiceException exception
     * @see User
     */
    public void changeRole(final String userId) throws ServiceException {
        final Transaction transaction = userRepository.beginTransaction();

        try {
            final JSONObject oldUser = userRepository.get(userId);

            if (null == oldUser) {
                throw new ServiceException(langPropsService.get("updateFailLabel"));
            }

            final String role = oldUser.optString(User.USER_ROLE);

            if (Role.VISITOR_ROLE.equals(role)) {
                oldUser.put(User.USER_ROLE, Role.DEFAULT_ROLE);
            } else if (Role.DEFAULT_ROLE.equals(role)) {
                oldUser.put(User.USER_ROLE, Role.VISITOR_ROLE);
            }

            userRepository.update(userId, oldUser, User.USER_ROLE);

            transaction.commit();
        } catch (final RepositoryException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Updates a user failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Adds a user with the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "userName": "",
     *                          "userURL": "", // optional, uses 'servePath' instead if not specified
     *                          "userRole": "", // optional, uses {@value Role#DEFAULT_ROLE} instead if not specified
     *                          "userAvatar": "", // optional, users generated gravatar url instead if not specified
     *                          "userGitHubId": "",
     *                          "userB3Key": ""
     * @return generated user id
     * @throws ServiceException service exception
     */
    public synchronized String addUser(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = userRepository.beginTransaction();

        try {
            final String userName = requestJSONObject.optString(User.USER_NAME);
            if (UserExt.invalidUserName(userName)) {
                throw new ServiceException(langPropsService.get("userNameInvalidLabel"));
            }

            JSONObject duplicatedUser = userRepository.getByUserName(userName);
            if (null != duplicatedUser) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw new ServiceException(langPropsService.get("duplicatedUserNameLabel"));
            }
            final JSONObject user = new JSONObject();
            user.put(User.USER_NAME, userName);

            String userURL = requestJSONObject.optString(User.USER_URL);
            if (StringUtils.isBlank(userURL)) {
                userURL = Latkes.getServePath();
            }
            if (!Strings.isURL(userURL)) {
                throw new ServiceException(langPropsService.get("urlInvalidLabel"));
            }
            user.put(User.USER_URL, userURL);

            final String roleName = requestJSONObject.optString(User.USER_ROLE, Role.DEFAULT_ROLE);
            user.put(User.USER_ROLE, roleName);

            final String userAvatar = requestJSONObject.optString(UserExt.USER_AVATAR);
            user.put(UserExt.USER_AVATAR, userAvatar);

            final String userGitHubId = requestJSONObject.optString(UserExt.USER_GITHUB_ID);
            user.put(UserExt.USER_GITHUB_ID, userGitHubId);

            final String userB3Key = requestJSONObject.optString(UserExt.USER_B3_KEY);
            user.put(UserExt.USER_B3_KEY, MD5Utils.stringToMD5Twice(userB3Key));

            userRepository.add(user);
            transaction.commit();

            return user.optString(Keys.OBJECT_ID);
        } catch (final RepositoryException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Adds a user failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Removes a user specified by the given user id.
     *
     * @param userId the given user id
     * @throws ServiceException service exception
     */
    public void removeUser(final String userId) throws ServiceException {
        final Transaction transaction = userRepository.beginTransaction();
        try {
            userRepository.remove(userId);

            transaction.commit();
        } catch (final RepositoryException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes a user [id=" + userId + "] failed", e);
            throw new ServiceException(e);
        }
    }
}
