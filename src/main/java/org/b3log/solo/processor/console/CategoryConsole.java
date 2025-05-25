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
package org.b3log.solo.processor.console;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.Before;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JsonRenderer;
import org.b3log.latke.util.URLs;
import org.b3log.solo.model.Category;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Tag;
import org.b3log.solo.service.CategoryMgmtService;
import org.b3log.solo.service.CategoryQueryService;
import org.b3log.solo.service.TagQueryService;
import org.b3log.solo.util.Solos;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Category console request processing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://ld246.com/member/lzh984294471">lzh984294471</a>
 * @since 2.0.0
 */
@RequestProcessor
@Before(ConsoleAdminAuthAdvice.class)
public class CategoryConsole {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CategoryConsole.class);

    /**
     * Category management service.
     */
    @Inject
    private CategoryMgmtService categoryMgmtService;

    /**
     * Category query service.
     */
    @Inject
    private CategoryQueryService categoryQueryService;

    /**
     * Tag query service.
     */
    @Inject
    private TagQueryService tagQueryService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Changes a category order by the specified category id and direction.
     * <p>
     * Request json:
     * 
     * <pre>
     * {
     *     "oId": "",
     *     "direction": "" // "up"/"down"
     * }
     * </pre>
     * </p>
     * <p>
     * Renders the response with a json object, for example,
     * 
     * <pre>
     * {
     *     "sc": boolean,
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     * @throws Exception exception
     */
    public void changeOrder(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();
        try {
            final JSONObject requestJSON = context.requestJSON();
            final String categoryId = requestJSON.getString(Keys.OBJECT_ID);
            final String direction = requestJSON.getString(Common.DIRECTION);

            categoryMgmtService.changeOrder(categoryId, direction);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));
            renderer.setJSONObject(ret);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("updateFailLabel"));
        }
    }

    /**
     * Gets a category by the specified request.
     * <p>
     * Renders the response with a json object, for example,
     * 
     * <pre>
     * {
     *     "sc": boolean,
     *     "category": {
     *         "oId": "",
     *         "categoryTitle": "",
     *         "categoryURI": "",
     *         ....
     *     }
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     * @throws Exception exception
     */
    @SuppressWarnings("unchecked")
    public void getCategory(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        try {
            final String categoryId = context.pathVar("id");
            final JSONObject result = categoryQueryService.getCategory(categoryId);
            if (null == result) {
                renderer.setJSONObject(new JSONObject().put(Keys.STATUS_CODE, false));

                return;
            }

            final StringBuilder tagBuilder = new StringBuilder();
            final List<JSONObject> tags = (List<JSONObject>) result.opt(Category.CATEGORY_T_TAGS);
            for (final JSONObject tag : tags) {
                if (null == tag || !tag.has(Tag.TAG_TITLE)) { // 修复修改分类时空指针错误 https://github.com/b3log/solo/pull/12876
                    continue;
                }
                tagBuilder.append(tag.optString(Tag.TAG_TITLE)).append(",");
            }
            if (tagBuilder.length() != 0) {
                tagBuilder.deleteCharAt(tagBuilder.length() - 1);
                result.put(Category.CATEGORY_T_TAGS, tagBuilder.toString());
            }

            renderer.setJSONObject(result);
            result.put(Keys.STATUS_CODE, true);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Removes a category by the specified request.
     * <p>
     * Renders the response with a json object, for example,
     * 
     * <pre>
     * {
     *     "sc": boolean,
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     * @throws Exception exception
     */
    public void removeCategory(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        final JSONObject jsonObject = new JSONObject();
        renderer.setJSONObject(jsonObject);
        try {
            final String categoryId = context.pathVar("id");
            categoryMgmtService.removeCategory(categoryId);

            jsonObject.put(Keys.STATUS_CODE, true);
            jsonObject.put(Keys.MSG, langPropsService.get("removeSuccLabel"));
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("removeFailLabel"));
        }
    }

    /**
     * Updates a category by the specified request.
     * <p>
     * Request json:
     * 
     * <pre>
     * {
     *     "oId": "",
     *     "categoryTitle": "",
     *     "categoryURI": "", // optional
     *     "categoryDescription": "", // optional
     *     "categoryTags": "tag1, tag2" // optional
     * }
     * </pre>
     * </p>
     * <p>
     * Renders the response with a json object, for example,
     * 
     * <pre>
     * {
     *     "sc": boolean,
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     */
    public void updateCategory(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();
        renderer.setJSONObject(ret);

        try {
            final JSONObject requestJSON = context.requestJSON();

            final String categoryId = requestJSON.optString(Keys.OBJECT_ID);
            final String title = requestJSON.optString(Category.CATEGORY_TITLE, "Category");
            String uri = requestJSON.optString(Category.CATEGORY_URI);
            final JSONObject targetCategory = categoryQueryService.getCategory(categoryId);
            JSONObject mayExist = categoryQueryService.getByTitle(title);
            if (null != mayExist && !mayExist.optString(Keys.OBJECT_ID).equals(categoryId)) {
                final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
                renderer.setJSONObject(jsonObject);
                jsonObject.put(Keys.MSG, langPropsService.get("duplicatedCategoryLabel"));

                return;
            }
            boolean urlUpdateFlag = false;
            if (StringUtils.isBlank(uri)) {
                uri = title;
                urlUpdateFlag = true;
            } else {
                if (!targetCategory.optString(Category.CATEGORY_URI).equals(uri)) {
                    urlUpdateFlag = true;
                }
            }
            if (urlUpdateFlag) {
                uri = URLs.encode(uri);
                mayExist = categoryQueryService.getByURI(uri);
                if (null != mayExist && !mayExist.optString(Keys.OBJECT_ID).equals(categoryId)) {
                    final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
                    renderer.setJSONObject(jsonObject);
                    jsonObject.put(Keys.MSG, langPropsService.get("duplicatedCategoryURILabel"));
                    return;
                }
                if (255 <= StringUtils.length(uri)) {
                    final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
                    renderer.setJSONObject(jsonObject);
                    jsonObject.put(Keys.MSG, langPropsService.get("categoryURITooLongLabel"));
                    return;
                }
            }
            final String desc = requestJSON.optString(Category.CATEGORY_DESCRIPTION);

            final JSONObject category = new JSONObject();
            category.put(Category.CATEGORY_TITLE, title);
            category.put(Category.CATEGORY_URI, uri);
            category.put(Category.CATEGORY_DESCRIPTION, desc);
            categoryMgmtService.updateCategory(categoryId, category);
            ret.put(Keys.OBJECT_ID, categoryId);
            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));
            ret.put(Keys.STATUS_CODE, true);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("updateFailLabel"));
        }
    }

    /**
     * Adds a category with the specified request.
     * <p>
     * Request json:
     * 
     * <pre>
     * {
     *     "categoryTitle": "",
     *     "categoryURI": "", // optional
     *     "categoryDescription": "", // optional
     *     "categoryTags": "tag1, tag2" // optional
     * }
     * </pre>
     * </p>
     * <p>
     * Renders the response with a json object, for example,
     * 
     * <pre>
     * {
     *     "sc": boolean,
     *     "oId": "", // Generated category id
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     */
    public void addCategory(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();
        renderer.setJSONObject(ret);

        try {
            final JSONObject requestJSONObject = context.requestJSON();

            final String title = requestJSONObject.optString(Category.CATEGORY_TITLE, "Category");
            JSONObject mayExist = categoryQueryService.getByTitle(title);
            if (null != mayExist) {
                final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
                renderer.setJSONObject(jsonObject);
                jsonObject.put(Keys.MSG, langPropsService.get("duplicatedCategoryLabel"));

                return;
            }

            String uri = requestJSONObject.optString(Category.CATEGORY_URI, title);
            if (StringUtils.isBlank(uri)) {
                uri = title;
            }
            uri = URLs.encode(uri);
            mayExist = categoryQueryService.getByURI(uri);
            if (null != mayExist) {
                final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
                renderer.setJSONObject(jsonObject);
                jsonObject.put(Keys.MSG, langPropsService.get("duplicatedCategoryURILabel"));

                return;
            }
            if (255 <= StringUtils.length(uri)) {
                final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
                renderer.setJSONObject(jsonObject);
                jsonObject.put(Keys.MSG, langPropsService.get("categoryURITooLongLabel"));

                return;
            }

            final String desc = requestJSONObject.optString(Category.CATEGORY_DESCRIPTION);

            final JSONObject category = new JSONObject();
            category.put(Category.CATEGORY_TITLE, title);
            category.put(Category.CATEGORY_URI, uri);
            category.put(Category.CATEGORY_DESCRIPTION, desc);

            final String categoryId = categoryMgmtService.addCategory(category);

            ret.put(Keys.OBJECT_ID, categoryId);
            ret.put(Keys.MSG, langPropsService.get("addSuccLabel"));
            ret.put(Keys.STATUS_CODE, true);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("updateFailLabel"));
        }
    }

    /**
     * Gets categories by the specified request json object.
     * <p>
     * The request URI contains the pagination arguments. For example, the request
     * URI is /console/categories/1/10/20, means
     * the current page is 1, the page size is 10, and the window size is 20.
     * </p>
     * <p>
     * Renders the response with a json object, for example,
     * 
     * <pre>
     * {
     *     "pagination": {
     *         "paginationPageCount": 100,
     *         "paginationPageNums": [1, 2, 3, 4, 5]
     *     },
     *     "categories": [{
     *         "oId": "",
     *         "categoryTitle": "",
     *         "categoryURI": "",
     *         ....
     *      }, ....]
     *     "sc": true
     * }
     * </pre>
     * </p>
     *
     * @param context the specified request context
     */
    public void getCategories(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);

        try {
            final HttpServletRequest request = context.getRequest();
            final String requestURI = context.requestURI();
            final String path = requestURI.substring((Latkes.getContextPath() + "/console/categories/").length());
            final JSONObject requestJSONObject = Solos.buildPaginationRequest(path);
            final JSONObject result = categoryQueryService.getCategoris(requestJSONObject);
            result.put(Keys.STATUS_CODE, true);
            renderer.setJSONObject(result);

            final JSONArray categories = result.optJSONArray(Category.CATEGORIES);
            for (int i = 0; i < categories.length(); i++) {
                final JSONObject category = categories.optJSONObject(i);
                String title = category.optString(Category.CATEGORY_TITLE);
                title = StringEscapeUtils.escapeXml(title);
                category.put(Category.CATEGORY_TITLE, title);
            }
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject().put(Keys.STATUS_CODE, false);
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }
}
