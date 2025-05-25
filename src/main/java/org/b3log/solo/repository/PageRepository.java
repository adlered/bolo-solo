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
package org.b3log.solo.repository;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.solo.cache.PageCache;
import org.b3log.solo.model.Page;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Page repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.1
 */
@Repository
public class PageRepository extends AbstractRepository {

    /**
     * Page cache.
     */
    @Inject
    private PageCache pageCache;

    /**
     * Public constructor.
     */
    public PageRepository() {
        super(Page.PAGE);
    }

    @Override
    public void remove(final String id) throws RepositoryException {
        super.remove(id);

        pageCache.removePage(id);
    }

    @Override
    public JSONObject get(final String id) throws RepositoryException {
        JSONObject ret = pageCache.getPage(id);
        if (null != ret) {
            return ret;
        }

        ret = super.get(id);
        if (null == ret) {
            return null;
        }

        pageCache.putPage(ret);

        return ret;
    }

    @Override
    public void update(final String id, final JSONObject page, final String... propertyNames) throws RepositoryException {
        super.update(id, page, propertyNames);

        page.put(Keys.OBJECT_ID, id);
        pageCache.putPage(page);
    }

    /**
     * Gets a page by the specified permalink.
     *
     * @param permalink the specified permalink
     * @return page, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getByPermalink(final String permalink) throws RepositoryException {
        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_PERMALINK, FilterOperator.EQUAL, permalink)).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets the maximum order.
     *
     * @return order number, returns {@code -1} if not found
     * @throws RepositoryException repository exception
     */
    public int getMaxOrder() throws RepositoryException {
        final Query query = new Query().addSort(Page.PAGE_ORDER, SortDirection.DESCENDING).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return -1;
        }

        return array.optJSONObject(0).optInt(Page.PAGE_ORDER);
    }

    /**
     * Gets the upper page of the page specified by the given id.
     *
     * @param id the given id
     * @return upper page, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getUpper(final String id) throws RepositoryException {
        final JSONObject page = get(id);
        if (null == page) {
            return null;
        }

        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_ORDER, FilterOperator.LESS_THAN, page.optInt(Page.PAGE_ORDER))).
                addSort(Page.PAGE_ORDER, SortDirection.DESCENDING).setPage(1, 1).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets the under page of the page specified by the given id.
     *
     * @param id the given id
     * @return under page, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getUnder(final String id) throws RepositoryException {
        final JSONObject page = get(id);
        if (null == page) {
            return null;
        }

        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_ORDER, FilterOperator.GREATER_THAN, page.optInt(Page.PAGE_ORDER))).
                addSort(Page.PAGE_ORDER, SortDirection.ASCENDING).setPage(1, 1).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets a page by the specified order.
     *
     * @param order the specified order
     * @return page, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getByOrder(final int order) throws RepositoryException {
        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_ORDER, FilterOperator.EQUAL, order)).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets pages.
     *
     * @return a list of pages, returns an empty list if  not found
     * @throws RepositoryException repository exception
     */
    public List<JSONObject> getPages() throws RepositoryException {
        final Query query = new Query().addSort(Page.PAGE_ORDER, SortDirection.ASCENDING).setPageCount(1);

        return getList(query);
    }
}
