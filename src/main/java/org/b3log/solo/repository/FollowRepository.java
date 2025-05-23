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
import org.b3log.latke.repository.AbstractRepository;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.solo.model.Follow;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Follow repository.
 *
 * @author <a href="https://github.com/adlered">gakkiyomi (Bolo Commiter)</a>
 * @since 0.0.1
 */
@Repository
public class FollowRepository extends AbstractRepository {

    /**
     * Public constructor.
     */
    public FollowRepository() {
        super(Follow.FOLLOW);
    }

    /**
     * Gets a Follow by the specified address.
     *
     * @param address the specified address
     * @return Follow, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getByAddress(final String address) throws RepositoryException {
        final Query query = new Query()
                .setFilter(new PropertyFilter(Follow.FOLLOW_ADDRESS, FilterOperator.EQUAL, address))
                .setPageCount(1);
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
        final Query query = new Query().addSort(Follow.FOLLOW_ORDER, SortDirection.DESCENDING);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return -1;
        }

        return array.optJSONObject(0).optInt(Follow.FOLLOW_ORDER);
    }

    /**
     * Gets the upper Follow of the Follow specified by the given id.
     *
     * @param id the given id
     * @return upper Follow, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getUpper(final String id) throws RepositoryException {
        final JSONObject follow = get(id);
        if (null == follow) {
            return null;
        }

        final Query query = new Query()
                .setFilter(new PropertyFilter(Follow.FOLLOW_ORDER, FilterOperator.LESS_THAN,
                        follow.optInt(Follow.FOLLOW_ORDER)))
                .addSort(Follow.FOLLOW_ORDER, SortDirection.DESCENDING).setPage(1, 1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets the under Follow of the Follow specified by the given id.
     *
     * @param id the given id
     * @return under Follow, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getUnder(final String id) throws RepositoryException {
        final JSONObject follow = get(id);
        if (null == follow) {
            return null;
        }

        final Query query = new Query()
                .setFilter(
                        new PropertyFilter(Follow.FOLLOW_ORDER, FilterOperator.GREATER_THAN,
                                follow.optInt(Follow.FOLLOW_ORDER)))
                .addSort(Follow.FOLLOW_ORDER, SortDirection.ASCENDING).setPage(1, 1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    /**
     * Gets a Follow by the specified order.
     *
     * @param order the specified order
     * @return Follow, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    public JSONObject getByOrder(final int order) throws RepositoryException {
        final Query query = new Query().setFilter(new PropertyFilter(Follow.FOLLOW_ORDER, FilterOperator.EQUAL, order));
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }
}
