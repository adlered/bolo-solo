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
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Paginator;
import org.b3log.solo.model.Follow;
import org.b3log.solo.repository.FollowRepository;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Follow service.
 *
 * @author <a href="https://github.com/gakkiyomi">gakkiyomi (Bolo Commiter)</a>
 * @since 0.0.1
 */
@Service
public class FollowService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FollowService.class);

    /**
     * Follow repository.
     */
    @Inject
    private FollowRepository followRepository;

    /**
     * Gets follows by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "paginationCurrentPageNum": 1,
     *                          "paginationPageSize": 20,
     *                          "paginationWindowSize": 10
     *                          see {@follow Pagination} for more details
     * @return for example,
     * 
     *         <pre>
     * {
     *     "pagination": {
     *         "paginationPageCount": 100,
     *         "paginationPageNums": [1, 2, 3, 4, 5]
     *     },
     *     "follows": [{
     *         "oId": "",
     *         "followTitle": "",
     *         "followAddress": "",
     *         ""followDescription": ""
     *      }, ....]
     * }
     *         </pre>
     * 
     * @throws ServiceException service exception
     * @see Pagination
     */
    public JSONObject getFollows(final JSONObject requestJSONObject) throws ServiceException {
        final JSONObject ret = new JSONObject();

        try {
            final int currentPageNum = requestJSONObject.getInt(Pagination.PAGINATION_CURRENT_PAGE_NUM);
            final int pageSize = requestJSONObject.getInt(Pagination.PAGINATION_PAGE_SIZE);
            final int windowSize = requestJSONObject.getInt(Pagination.PAGINATION_WINDOW_SIZE);

            final Query query = new Query().setPage(currentPageNum, pageSize).addSort(Follow.FOLLOW_ORDER,
                    SortDirection.ASCENDING);
            final JSONObject result = followRepository.get(query);
            final int pageCount = result.getJSONObject(Pagination.PAGINATION).getInt(Pagination.PAGINATION_PAGE_COUNT);

            final JSONObject pagination = new JSONObject();
            final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

            pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            pagination.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

            final JSONArray follows = result.getJSONArray(Keys.RESULTS);

            ret.put(Pagination.PAGINATION, pagination);
            ret.put(Follow.FOLLOWS, follows);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets follows failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets a follow by the specified follow id.
     *
     * @param followId the specified follow id
     * @return for example,
     * 
     *         <pre>
     * {
     *     "follow": {
     *         "oId": "",
     *         "followTitle": "",
     *         "followAddress": "",
     *         "followDescription": ""
     *     }
     * }
     *         </pre>
     * 
     *         , returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getFollow(final String followId) throws ServiceException {
        final JSONObject ret = new JSONObject();

        try {
            final JSONObject follow = followRepository.get(followId);

            if (null == follow) {
                return null;
            }

            ret.put(Follow.FOLLOW, follow);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets a follow failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Removes a follow specified by the given follow id.
     *
     * @param followId the given follow id
     * @throws ServiceException service exception
     */
    public void removeFollow(final String followId) throws ServiceException {
        final Transaction transaction = followRepository.beginTransaction();

        try {
            followRepository.remove(followId);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes a follow[id=" + followId + "] failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates a follow by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "follow": {
     *                          "oId": "",
     *                          "followTitle": "",
     *                          "followAddress": "",
     *                          "followDescription": "",
     *                          "followIcon": ""
     *                          }
     *                          see {@link follow} for more details
     * @throws ServiceException service exception
     */
    public void updateFollow(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = followRepository.beginTransaction();

        try {
            final JSONObject follow = requestJSONObject.getJSONObject(Follow.FOLLOW);
            final String followId = follow.getString(Keys.OBJECT_ID);
            final JSONObject oldfollow = followRepository.get(followId);

            follow.put(Follow.FOLLOW_ORDER, oldfollow.getInt(Follow.FOLLOW_ORDER));

            followRepository.update(followId, follow);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, e.getMessage(), e);

            throw new ServiceException(e);
        }
    }

    /**
     * Changes the order of a follow specified by the given follow id with the
     * specified direction.
     *
     * @param followId  the given follow id
     * @param direction the specified direction, "up"/"down"
     * @throws ServiceException service exception
     */
    public void changeOrder(final String followId, final String direction) throws ServiceException {
        final Transaction transaction = followRepository.beginTransaction();

        try {
            final JSONObject srcFollow = followRepository.get(followId);
            final int srcFollowOrder = srcFollow.getInt(Follow.FOLLOW_ORDER);

            JSONObject targetFollow = null;

            if ("up".equals(direction)) {
                targetFollow = followRepository.getUpper(followId);
            } else { // Down
                targetFollow = followRepository.getUnder(followId);
            }

            if (null == targetFollow) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                LOGGER.log(Level.WARN, "Cant not find the target follow of source follow[order={0}]", srcFollowOrder);
                return;
            }

            // Swaps
            srcFollow.put(Follow.FOLLOW_ORDER, targetFollow.getInt(Follow.FOLLOW_ORDER));
            targetFollow.put(Follow.FOLLOW_ORDER, srcFollowOrder);

            followRepository.update(srcFollow.getString(Keys.OBJECT_ID), srcFollow);
            followRepository.update(targetFollow.getString(Keys.OBJECT_ID), targetFollow);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Changes follow's order failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Adds a follow with the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "follow": {
     *                          "followTitle": "",
     *                          "followAddress": "",
     *                          "followDescription": "",
     *                          "followIcon": ""
     *                          }
     *                          }, see {@follow Follow} for more details
     * @return generated follow id
     * @throws ServiceException service exception
     */
    public String addFollow(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = followRepository.beginTransaction();

        try {
            final JSONObject follow = requestJSONObject.getJSONObject(Follow.FOLLOW);
            final int maxOrder = followRepository.getMaxOrder();

            follow.put(Follow.FOLLOW_ORDER, maxOrder + 1);
            final String ret = followRepository.add(follow);

            transaction.commit();

            return ret;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Adds a follow failed", e);
            throw new ServiceException(e);
        }
    }
}
