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

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.repository.*;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.latke.util.Paginator;
import org.b3log.solo.model.Category;
import org.b3log.solo.model.Tag;
import org.b3log.solo.repository.CategoryRepository;
import org.b3log.solo.repository.CategoryTagRepository;
import org.b3log.solo.repository.TagRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Category query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://ld246.com/member/lzh984294471">lzh984294471</a>
 * @since 2.0.0
 */
@Service
public class CategoryQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CategoryQueryService.class);

    /**
     * Category repository.
     */
    @Inject
    private CategoryRepository categoryRepository;

    /**
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

    /**
     * Category tag repository.
     */
    @Inject
    private CategoryTagRepository categoryTagRepository;

    /**
     * Gets most tag category.
     *
     * @param fetchSize the specified fetch size
     * @return categories, returns an empty list if not found
     */
    public List<JSONObject> getMostTagCategory(final int fetchSize) {
        final Query query = new Query().addSort(Category.CATEGORY_ORDER, SortDirection.ASCENDING).
                addSort(Category.CATEGORY_TAG_CNT, SortDirection.DESCENDING).
                addSort(Keys.OBJECT_ID, SortDirection.DESCENDING).
                setPageSize(fetchSize).setPageCount(1);
        try {
            final List<JSONObject> ret = categoryRepository.getList(query);
            for (final JSONObject category : ret) {
                final List<JSONObject> tags = getTags(category.optString(Keys.OBJECT_ID));

                category.put(Category.CATEGORY_T_TAGS, (Object) tags);
            }

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets most tag category error", e);

            return Collections.emptyList();
        }
    }

    /**
     * Gets a category's tags.
     *
     * @param categoryId the specified category id
     * @return tags, returns an empty list if not found
     */
    public List<JSONObject> getTags(final String categoryId) {
        final List<JSONObject> ret = new ArrayList<>();

        final Query query = new Query().
                setFilter(new PropertyFilter(Category.CATEGORY + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, categoryId));
        try {
            final List<JSONObject> relations = categoryTagRepository.getList(query);
            for (final JSONObject relation : relations) {
                final String tagId = relation.optString(Tag.TAG + "_" + Keys.OBJECT_ID);
                final JSONObject tag = tagRepository.get(tagId);
                if (null == tag) { // 修复修改分类时空指针错误 https://github.com/b3log/solo/pull/12876
                    continue;
                }
                ret.add(tag);
            }
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets category [id=" + categoryId + "] tags error", e);
        }

        return ret;
    }

    /**
     * Gets a category by the specified category URI.
     *
     * @param categoryURI the specified category URI
     * @return category, returns {@code null} if not null
     * @throws ServiceException service exception
     */
    public JSONObject getByURI(final String categoryURI) throws ServiceException {
        try {
            final JSONObject ret = categoryRepository.getByURI(categoryURI);
            if (null == ret) {
                return null;
            }

            return ret;
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets category [URI=" + categoryURI + "] failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Gets a category by the specified category title.
     *
     * @param categoryTitle the specified category title
     * @return category, returns {@code null} if not null
     * @throws ServiceException service exception
     */
    public JSONObject getByTitle(final String categoryTitle) throws ServiceException {
        try {
            final JSONObject ret = categoryRepository.getByTitle(categoryTitle);

            return ret;
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets category [title=" + categoryTitle + "] failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Gets categories by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "categoryTitle": "", // optional
     *                          "paginationCurrentPageNum": 1,
     *                          "paginationPageSize": 20,
     *                          "paginationWindowSize": 10
     *                          see {@link Pagination} for more details
     * @return for example,
     * <pre>
     * {
     *     "pagination": {
     *         "paginationPageCount": 100,
     *         "paginationPageNums": [1, 2, 3, 4, 5]
     *     },
     *     "categories": [{
     *         "oId": "",
     *         "categoryTitle": "",
     *         "categoryDescription": "",
     *         ....
     *      }, ....]
     * }
     * </pre>
     * @throws ServiceException service exception
     * @see Pagination
     */
    public JSONObject getCategoris(final JSONObject requestJSONObject) throws ServiceException {
        final JSONObject ret = new JSONObject();

        final int currentPageNum = requestJSONObject.optInt(Pagination.PAGINATION_CURRENT_PAGE_NUM);
        final int pageSize = requestJSONObject.optInt(Pagination.PAGINATION_PAGE_SIZE);
        final int windowSize = requestJSONObject.optInt(Pagination.PAGINATION_WINDOW_SIZE);
        final Query query = new Query().setPage(currentPageNum, pageSize).
                addSort(Category.CATEGORY_ORDER, SortDirection.ASCENDING).
                addSort(Category.CATEGORY_TAG_CNT, SortDirection.DESCENDING).
                addSort(Keys.OBJECT_ID, SortDirection.DESCENDING);

        if (requestJSONObject.has(Category.CATEGORY_TITLE)) {
            query.setFilter(new PropertyFilter(Category.CATEGORY_TITLE, FilterOperator.EQUAL,
                    requestJSONObject.optString(Category.CATEGORY_TITLE)));
        }

        JSONObject result;
        try {
            result = categoryRepository.get(query);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets categories failed", e);

            throw new ServiceException(e);
        }

        final int pageCount = result.optJSONObject(Pagination.PAGINATION).optInt(Pagination.PAGINATION_PAGE_COUNT);

        final JSONObject pagination = new JSONObject();
        ret.put(Pagination.PAGINATION, pagination);
        final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);
        pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
        pagination.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

        final JSONArray data = result.optJSONArray(Keys.RESULTS);
        final List<JSONObject> categories = CollectionUtils.jsonArrayToList(data);

        ret.put(Category.CATEGORIES, categories);

        return ret;
    }

    /**
     * Gets a category by the specified id.
     *
     * @param categoryId the specified id
     * @return a category, return {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getCategory(final String categoryId) throws ServiceException {
        try {
            final JSONObject ret = categoryRepository.get(categoryId);
            if (null == ret) {
                return null;
            }

            final List<JSONObject> tags = getTags(categoryId);
            ret.put(Category.CATEGORY_T_TAGS, (Object) tags);

            return ret;
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets a category [categoryId=" + categoryId + "] failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Whether a tag specified by the given tag title in a category specified by the given category id.
     *
     * @param tagTitle   the given tag title
     * @param categoryId the given category id
     * @return {@code true} if the tag in the category, returns {@code false} otherwise
     */
    public boolean containTag(final String tagTitle, final String categoryId) {
        try {
            final JSONObject category = categoryRepository.get(categoryId);
            if (null == category) {
                return true;
            }

            final JSONObject tag = tagRepository.getByTitle(tagTitle);
            if (null == tag) {
                return true;
            }

            final Query query = new Query().setFilter(
                    CompositeFilterOperator.and(
                            new PropertyFilter(Category.CATEGORY + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, categoryId),
                            new PropertyFilter(Tag.TAG + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, tag.optString(Keys.OBJECT_ID))));

            return categoryTagRepository.count(query) > 0;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Check category tag [tagTitle=" + tagTitle + ", categoryId=" + categoryId + "] failed", e);

            return true;
        }
    }
}
