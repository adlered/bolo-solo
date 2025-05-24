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
package org.b3log.solo.cache;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.b3log.latke.ioc.Singleton;
import org.json.JSONObject;

/**
 * Follow Article cache.
 *
 * @author <a href="https://github.com/gakkiyomi">adlered (Bolo Commiter)</a>
 * @since 0.0.1
 */
@Singleton
public class FollowArticleCache {

    private final Map<String, Map<String, JSONObject>> followArticleCache = new ConcurrentHashMap<>();

    public Map<String, JSONObject> getFollowArticles(final String followName) {
        final Map<String, JSONObject> articles = followArticleCache.get(followName);
        if (null == articles) {
            return Collections.emptyMap();
        }
        return articles;
    }

    public void putArticles(final String followName, final Map<String, JSONObject> articles) {
        followArticleCache.put(followName, articles);
    }

    public void clear() {
        followArticleCache.clear();
    }
}