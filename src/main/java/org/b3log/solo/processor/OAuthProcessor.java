/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
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

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.URLs;
import org.b3log.solo.bolo.tool.MD5Utils;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.service.*;
import org.b3log.solo.util.GitHubs;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OAuth processor.
 * <ul>
 * <li>Redirects to auth page (/oauth/github/redirect), GET</li>
 * <li>OAuth callback (/oauth/github), GET</li>
 * </ul>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.1, Sep 12, 2019
 * @since 2.9.5
 */
@RequestProcessor
public class OAuthProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(OAuthProcessor.class);

    /**
     * OAuth parameters - state.
     */
    private static final Set<String> STATES = ConcurrentHashMap.newKeySet();

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
     * Initialization service.
     */
    @Inject
    private InitService initService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Bolo admin login.
     *
     * @param context
     */
    @RequestProcessing(value = "/oauth/bolo/login", method = HttpMethod.POST)
    public void adminLogin(final RequestContext context) {
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5 = MD5Utils.stringToMD5(password);
        try {
            if (!initService.isInited()) {
                LOGGER.log(Level.INFO, "Bolo initializing...");
                final JSONObject initReq = new JSONObject();
                initReq.put(User.USER_NAME, username);
                initReq.put(UserExt.USER_B3_KEY, password);
                initReq.put(UserExt.USER_AVATAR, "https://pic.stackoverflow.wiki/uploadImages/114/244/224/38/2019/11/30/01/04/1cb648c8-f9f8-430f-8616-43f7b0e2333a.png");
                initReq.put(UserExt.USER_GITHUB_ID, "000000");
                initService.init(initReq);
                context.sendRedirect(Latkes.getServePath() + "/");
            } else {
                try {
                    JSONObject user = userQueryService.getUserByName(username);
                    String cUser = user.optString(User.USER_NAME);
                    String cPass = user.optString(UserExt.USER_B3_KEY);
                    // 同时兼容明文和密文密码
                    if ((username.equals(cUser) && password.equals(cPass)) || (username.equals(cUser) && md5.equals(cPass))) {
                        Solos.login(user, context.getResponse());
                        LOGGER.log(Level.INFO, "Logged in [name={0}, remoteAddr={1}] with bolo auth", username, Requests.getRemoteAddr(request));
                        context.sendRedirect(Latkes.getServePath() + "/admin-index.do#main");
                    } else {
                        context.sendRedirect(Latkes.getServePath() + "/start?status=error");
                    }
                } catch (NullPointerException NPE) {
                    context.sendRedirect(Latkes.getServePath() + "/start?status=error");
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARN, "Can not write cookie", e);
        }
    }
}
