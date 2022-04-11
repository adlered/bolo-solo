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
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.TimeZone;

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
     * Private constructor.
     */
    private GitHubs() {
    }

    /**
     * Gets GitHub repos.
     *
     * @param githubUserId the specified GitHub user id
     * @return GitHub repos, returns {@code null} if not found
     */
    public static JSONArray getGitHubRepos(final String githubUserId) {
        try {
            final HttpResponse res = HttpRequest.get("https://api.github.com/users/" + githubUserId + "/repos").
                    connectionTimeout(20000).timeout(60000).header("User-Agent", Solos.USER_AGENT).send();
            if (HttpServletResponse.SC_OK != res.statusCode()) {
                return null;
            }
            res.charset("UTF-8");
            final JSONArray result = new JSONArray(res.bodyText());

            String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            // 重组兼容 JSONArray
            JSONArray compatibleResult = new JSONArray();
            for (int i = 0; i < result.length(); i++) {
                JSONObject resultObject = result.optJSONObject(i);
                JSONObject compatibleObject = new JSONObject();

                if (resultObject.getBoolean("fork")) {
                    continue;
                }

                compatibleObject.put("githubrepoId", resultObject.optString("id"));
                compatibleObject.put("githubrepoStatus", 0);
                compatibleObject.put("oId", "" + System.currentTimeMillis());
                compatibleObject.put("githubrepoDescription", resultObject.optString("description"));
                compatibleObject.put("githubrepoHomepage", resultObject.optString("homepage"));
                compatibleObject.put("githubrepoForksCount", resultObject.optLong("forks_count"));
                compatibleObject.put("githubrepoOwnerId", resultObject.optJSONObject("owner").optString("id"));
                compatibleObject.put("githubrepoStargazersCount", resultObject.optLong("stargazers_count"));
                compatibleObject.put("githubrepoWatchersCount", resultObject.optLong("watchers_count"));
                compatibleObject.put("githubrepoOwnerLogin", resultObject.optJSONObject("owner").optString("login"));
                compatibleObject.put("githubrepoHTMLURL", resultObject.optString("html_url"));
                compatibleObject.put("githubrepoLanguage", resultObject.optString("language"));
                compatibleObject.put("githubrepoUpdated", simpleDateFormat.parse(resultObject.optString("updated_at")).getTime());
                compatibleObject.put("githubrepoName", resultObject.optString("name"));
                compatibleObject.put("githubrepoFullName", resultObject.optString("full_name"));

                compatibleResult.put(compatibleObject);
            }

            // 排序
            ArrayList<String> tempResultList = new ArrayList<>();
            for (int i = 0; i < compatibleResult.length(); i++) {
                JSONObject compatibleObject = compatibleResult.optJSONObject(i);
                tempResultList.add(compatibleObject.toString());
            }
            tempResultList.sort((o1, o2) -> {
                int o1star = new JSONObject(o1).optInt("githubrepoStargazersCount");
                int o2star = new JSONObject(o2).optInt("githubrepoStargazersCount");
                return o2star - o1star;
            });
            JSONArray sortedCompatibleResult = new JSONArray();
            for (String json : tempResultList) {
                sortedCompatibleResult.put(new JSONObject(json));
            }

            return sortedCompatibleResult;
        } catch (JSONException e) {
            LOGGER.log(Level.ERROR, "Gets GitHub repos failed because the request has been reached GitHub's limit, try again at later.");

            return null;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets GitHub repos failed, please check your network connection to github.com");

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
}
