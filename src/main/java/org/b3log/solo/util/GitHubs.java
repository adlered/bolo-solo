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
package org.b3log.solo.util;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.solo.bolo.Global;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.UserExt;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * GitHub utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 3.0.0
 */
public final class GitHubs {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(GitHubs.class);

    /**
     * Gets GitHub repos.
     *
     * @param githubUserId the specified GitHub user id
     * @return GitHub repos, returns {@code null} if not found
     */
    public static JSONArray getGitHubRepos(final String githubUserId) {
        try {
            final HttpResponse res = HttpRequest.get("https://" + Global.HACPAI_DOMAIN + "/github/repos?id=" + githubUserId).trustAllCerts(true).
                    connectionTimeout(3000).timeout(7000).header("User-Agent", Solos.USER_AGENT).send();
            if (HttpServletResponse.SC_OK != res.statusCode()) {
                return null;
            }
            res.charset("UTF-8");
            final JSONObject result = new JSONObject(res.bodyText());
            if (0 != result.optInt(Keys.STATUS_CODE)) {
                return null;
            }
            final JSONObject data = result.optJSONObject(Common.DATA);
            return data.optJSONArray("githubrepos");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets GitHub repos failed", e);

            return null;
        }
    }

    /**
     * Updates a file by the specified personal access token, GitHub login name, repo name, file path and file content.
     *
     * @param pat       the specified personal access token
     * @param loginName the specified GitHub login name
     * @param repoName  the specified repo name
     * @param filePath  the specfiied file name
     * @param content   the specified file content
     * @return {@code true} if ok, returns {@code false} if failed
     */
    public static boolean updateFile(final String pat, final String loginName, final String repoName, final String filePath, final byte[] content) {
        final String fullRepoName = loginName + "/" + repoName;
        try {
            HttpResponse response = HttpRequest.get("https://api.github.com/repos/" + fullRepoName + "/git/trees/master").header("Authorization", "token " + pat).
                    connectionTimeout(7000).timeout(60000).header("User-Agent", Solos.USER_AGENT).send();
            int statusCode = response.statusCode();
            response.charset("UTF-8");
            String responseBody = response.bodyText();
            if (200 != statusCode && 409 != statusCode) {
                LOGGER.log(Level.ERROR, "Get git tree of file [" + filePath + "] failed: " + responseBody);
                return false;
            }

            final JSONObject body = new JSONObject().
                    put("message", ":memo: 更新博客").
                    put("content", Base64.getEncoder().encodeToString(content));
            if (200 == statusCode) {
                final JSONObject responseData = new JSONObject(responseBody);
                final JSONArray tree = responseData.optJSONArray("tree");
                for (int i = 0; i < tree.length(); i++) {
                    final JSONObject file = tree.optJSONObject(i);
                    if (StringUtils.equals(filePath, file.optString("path"))) {
                        body.put("sha", file.optString("sha"));
                        break;
                    }
                }
            }

            response = HttpRequest.put("https://api.github.com/repos/" + fullRepoName + "/contents/" + filePath).header("Authorization", "token " + pat).
                    connectionTimeout(7000).timeout(60000 * 2).header("User-Agent", Solos.USER_AGENT).bodyText(body.toString()).send();
            statusCode = response.statusCode();
            response.charset("UTF-8");
            responseBody = response.bodyText();
            if (200 != statusCode && 201 != statusCode) {
                LOGGER.log(Level.ERROR, "Updates repo [" + repoName + "] file [" + filePath + "] failed: " + responseBody);
                return false;
            }
            return true;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Updates repo [" + repoName + "] file [" + filePath + "] failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Creates or updates a GitHub repository by the specified personal access token, GitHub login name, repo name repo desc and repo homepage.
     *
     * @param pat          the specified personal access token
     * @param loginName    the specified GitHub login name
     * @param repoName     the specified repo name
     * @param repoDesc     the specified repo desc
     * @param repoHomepage the specified repo homepage
     * @return {@code true} if ok, returns {@code false} if failed
     */
    public static boolean createOrUpdateGitHubRepo(final String pat, final String loginName, final String repoName, final String repoDesc, final String repoHomepage) {
        try {
            final JSONObject body = new JSONObject().
                    put("name", repoName).
                    put("description", repoDesc).
                    put("homepage", repoHomepage).
                    put("has_wiki", false).
                    put("has_projects", false);
            HttpResponse response = HttpRequest.post("https://api.github.com/user/repos").header("Authorization", "token " + pat).
                    connectionTimeout(7000).timeout(30000).header("User-Agent", Solos.USER_AGENT).bodyText(body.toString()).send();
            int statusCode = response.statusCode();
            response.charset("UTF-8");
            String responseBody = response.bodyText();
            if (201 != statusCode && 422 != statusCode) {
                LOGGER.log(Level.ERROR, "Creates GitHub repo [" + repoName + "] failed: " + responseBody);
                return false;
            }
            if (201 == statusCode) {
                return true;
            }

            response = HttpRequest.patch("https://api.github.com/repos/" + loginName + "/" + repoName).header("Authorization", "token " + pat).
                    connectionTimeout(7000).timeout(30000).header("User-Agent", Solos.USER_AGENT).bodyText(body.toString()).send();
            statusCode = response.statusCode();
            responseBody = response.bodyText();
            if (200 != statusCode) {
                LOGGER.log(Level.ERROR, "Updates GitHub repo [" + repoName + "] failed: " + responseBody);
                return false;
            }
            return true;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Creates or updates GitHub repo failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets GitHub user by the specified personal access token.
     *
     * @param pat the specified personal access token
     * @return GitHub user, returns {@code null} if failed
     */
    public static JSONObject getGitHubUser(final String pat) {
        try {
            final HttpResponse response = HttpRequest.get("https://api.github.com/user").header("Authorization", "token " + pat).
                    connectionTimeout(7000).timeout(30000).header("User-Agent", Solos.USER_AGENT).send();
            if (200 != response.statusCode()) {
                return null;
            }
            response.charset("UTF-8");
            return new JSONObject(response.bodyText());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets GitHub user info failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Private constructor.
     */
    private GitHubs() {
    }
}
