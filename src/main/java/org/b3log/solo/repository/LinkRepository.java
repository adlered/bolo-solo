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
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.solo.model.Link;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Link repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.1
 */
@Repository
public class LinkRepository extends AbstractRepository {

    /**
     * Public constructor.
     */
    public LinkRepository() {
        super(Link.LINK);
    }

    /**
     * Gets a link by the specified address.
     *
     * @param address the specified address
     * @return link, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getByAddress(final String address) throws RepositoryException {
        final Query query = new Query().setFilter(new PropertyFilter(Link.LINK_ADDRESS, FilterOperator.EQUAL, address)).setPageCount(1);
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
        final Query query = new Query().addSort(Link.LINK_ORDER, SortDirection.DESCENDING);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return -1;
        }

        return array.optJSONObject(0).optInt(Link.LINK_ORDER);
    }

    /**
     * Gets the upper link of the link specified by the given id.
     *
     * @param id the given id
     * @return upper link, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getUpper(final String id) throws RepositoryException {
        final JSONObject link = get(id);
        if (null == link) {
            return null;
        }

        final Query query = new Query().setFilter(new PropertyFilter(Link.LINK_ORDER, FilterOperator.LESS_THAN, link.optInt(Link.LINK_ORDER))).
                addSort(Link.LINK_ORDER, SortDirection.DESCENDING).setPage(1, 1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets the under link of the link specified by the given id.
     *
     * @param id the given id
     * @return under link, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getUnder(final String id) throws RepositoryException {
        final JSONObject link = get(id);
        if (null == link) {
            return null;
        }

        final Query query = new Query().setFilter(new PropertyFilter(Link.LINK_ORDER, FilterOperator.GREATER_THAN, link.optInt(Link.LINK_ORDER))).
                addSort(Link.LINK_ORDER, SortDirection.ASCENDING).setPage(1, 1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets a link by the specified order.
     *
     * @param order the specified order
     * @return link, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getByOrder(final int order) throws RepositoryException {
        final Query query = new Query().setFilter(new PropertyFilter(Link.LINK_ORDER, FilterOperator.EQUAL, order));
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }
}
