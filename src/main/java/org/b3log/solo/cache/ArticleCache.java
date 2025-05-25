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

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Singleton;
import org.b3log.solo.model.Article;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Article cache.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 2.3.0
 */
@Singleton
public class ArticleCache {

    /**
     * Article id cache.
     */
    private final Map<String, JSONObject> idCache = new ConcurrentHashMap<>();

    /**
     * Article permalink cache.
     */
    private final Map<String, JSONObject> permalinkCache = new ConcurrentHashMap<>();

    /**
     * Gets an article by the specified article id.
     *
     * @param id the specified article id
     * @return article, returns {@code null} if not found
     */
    public JSONObject getArticle(final String id) {
        final JSONObject article = idCache.get(id);
        if (null == article) {
            return null;
        }

        return Solos.clone(article);
    }

    /**
     * Gets an article by the specified article permalink.
     *
     * @param permalink the specified article permalink
     * @return article, returns {@code null} if not found
     */
    public JSONObject getArticleByPermalink(final String permalink) {
        final JSONObject article = permalinkCache.get(permalink);
        if (null == article) {
            return null;
        }

        return Solos.clone(article);
    }

    /**
     * Adds or updates the specified article.
     *
     * @param article the specified article
     */
    public void putArticle(final JSONObject article) {
        idCache.put(article.optString(Keys.OBJECT_ID), Solos.clone(article));
        permalinkCache.put(article.optString(Article.ARTICLE_PERMALINK), Solos.clone(article));
    }

    /**
     * Removes an article by the specified article id.
     *
     * @param id the specified article id
     */
    public void removeArticle(final String id) {
        final JSONObject article = idCache.get(id);
        if (null == article) {
            return;
        }
        final String permalink = article.optString(Article.ARTICLE_PERMALINK);
        idCache.remove(id);
        permalinkCache.remove(permalink);
    }

    /**
     * Clears all cached data.
     */
    public void clear() {
        idCache.clear();
        permalinkCache.clear();
    }
}