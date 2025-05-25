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
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Transactional;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Category;
import org.b3log.solo.model.Tag;
import org.b3log.solo.repository.CategoryRepository;
import org.b3log.solo.repository.CategoryTagRepository;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Category management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 2.0.0
 */
@Service
public class CategoryMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CategoryMgmtService.class);

    /**
     * Category repository.
     */
    @Inject
    private CategoryRepository categoryRepository;

    /**
     * Category tag repository.
     */
    @Inject
    private CategoryTagRepository categoryTagRepository;

    /**
     * Changes the order of a category specified by the given category id with the specified direction.
     *
     * @param categoryId the given category id
     * @param direction  the specified direction, "up"/"down"
     * @throws ServiceException service exception
     */
    public void changeOrder(final String categoryId, final String direction)
            throws ServiceException {
        final Transaction transaction = categoryRepository.beginTransaction();

        try {
            final JSONObject srcCategory = categoryRepository.get(categoryId);
            final int srcCategoryOrder = srcCategory.getInt(Category.CATEGORY_ORDER);

            JSONObject targetCategory;

            if ("up".equals(direction)) {
                targetCategory = categoryRepository.getUpper(categoryId);
            } else { // Down
                targetCategory = categoryRepository.getUnder(categoryId);
            }

            if (null == targetCategory) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                LOGGER.log(Level.WARN, "Cant not find the target category of source category [order={0}]", srcCategoryOrder);

                return;
            }

            // Swaps
            srcCategory.put(Category.CATEGORY_ORDER, targetCategory.getInt(Category.CATEGORY_ORDER));
            targetCategory.put(Category.CATEGORY_ORDER, srcCategoryOrder);

            categoryRepository.update(srcCategory.getString(Keys.OBJECT_ID), srcCategory);
            categoryRepository.update(targetCategory.getString(Keys.OBJECT_ID), targetCategory);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Changes category's order failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Removes a category-tag relation.
     *
     * @param categoryId the specified category id
     * @param tagId      the specified tag id
     * @throws ServiceException service exception
     */
    @Transactional
    public void removeCategoryTag(final String categoryId, final String tagId) throws ServiceException {
        try {
            final JSONObject category = categoryRepository.get(categoryId);
            category.put(Category.CATEGORY_TAG_CNT, category.optInt(Category.CATEGORY_TAG_CNT) - 1);

            categoryRepository.update(categoryId, category);

            final Query query = new Query().setFilter(
                    CompositeFilterOperator.and(
                            new PropertyFilter(Category.CATEGORY + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, categoryId),
                            new PropertyFilter(Tag.TAG + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, tagId)));

            final JSONArray relations = categoryTagRepository.get(query).optJSONArray(Keys.RESULTS);
            if (relations.length() < 1) {
                return;
            }

            final JSONObject relation = relations.optJSONObject(0);
            categoryTagRepository.remove(relation.optString(Keys.OBJECT_ID));
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Adds a category-tag relation failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Adds a category-tag relation.
     *
     * @param categoryTag the specified category-tag relation
     * @throws ServiceException service exception
     */
    @Transactional
    public void addCategoryTag(final JSONObject categoryTag) throws ServiceException {
        try {
            categoryTagRepository.add(categoryTag);

            final String categoryId = categoryTag.optString(Category.CATEGORY + "_" + Keys.OBJECT_ID);
            final JSONObject category = categoryRepository.get(categoryId);
            final int tagCount =
                    categoryTagRepository.getByCategoryId(categoryId, 1, Integer.MAX_VALUE).
                            optJSONArray(Keys.RESULTS).length();
            category.put(Category.CATEGORY_TAG_CNT, tagCount);

            categoryRepository.update(categoryId, category);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.INFO, "Adds a category-tag relation failed", e);

            throw new ServiceException(e);
        } catch (NullPointerException NPE) {
            LOGGER.log(Level.INFO, "Category not set.");
        }
    }

    /**
     * Adds a category relation.
     *
     * @param category the specified category relation
     * @return category id
     * @throws ServiceException service exception
     */
    @Transactional
    public String addCategory(final JSONObject category) throws ServiceException {
        try {
            final JSONObject record = new JSONObject();
            record.put(Category.CATEGORY_TAG_CNT, 0);
            record.put(Category.CATEGORY_URI, category.optString(Category.CATEGORY_URI));
            record.put(Category.CATEGORY_TITLE, category.optString(Category.CATEGORY_TITLE));
            record.put(Category.CATEGORY_DESCRIPTION, category.optString(Category.CATEGORY_DESCRIPTION));

            final int maxOrder = categoryRepository.getMaxOrder();
            final int order = maxOrder + 1;
            record.put(Category.CATEGORY_ORDER, order);
            category.put(Category.CATEGORY_ORDER, order);

            final String ret = categoryRepository.add(record);

            return ret;
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Adds a category failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Updates the specified category by the given category id.
     *
     * @param categoryId the given category id
     * @param category   the specified category
     * @throws ServiceException service exception
     */
    @Transactional
    public void updateCategory(final String categoryId, final JSONObject category) throws ServiceException {
        try {
            final JSONObject oldCategory = categoryRepository.get(categoryId);
            category.put(Category.CATEGORY_ORDER, oldCategory.optInt(Category.CATEGORY_ORDER));
            category.put(Category.CATEGORY_TAG_CNT, oldCategory.optInt(Category.CATEGORY_TAG_CNT));

            categoryRepository.update(categoryId, category);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Updates a category [id=" + categoryId + "] failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Removes the specified category by the given category id.
     *
     * @param categoryId the given category id
     * @throws ServiceException service exception
     */
    @Transactional
    public void removeCategory(final String categoryId) throws ServiceException {
        try {
            categoryTagRepository.removeByCategoryId(categoryId);
            categoryRepository.remove(categoryId);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Remove a category [id=" + categoryId + "] failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Removes category-tag relations by the given category id.
     *
     * @param categoryId the given category id
     * @throws ServiceException service exception
     */
    @Transactional
    public void removeCategoryTags(final String categoryId) throws ServiceException {
        try {
            categoryTagRepository.removeByCategoryId(categoryId);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Remove category-tag [categoryId=" + categoryId + "] failed", e);

            throw new ServiceException(e);
        }
    }
}
