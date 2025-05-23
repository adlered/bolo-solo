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
package org.b3log.solo.processor;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.AbstractFreeMarkerRenderer;
import org.b3log.latke.util.Locales;
import org.b3log.latke.util.Paginator;
import org.b3log.latke.util.URLs;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.bolo.tool.FixSizeLinkedList;
import org.b3log.solo.log4j.RamAppender;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.*;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Map;

/**
 * Index processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://ld246.com/member/DASHU">DASHU</a>
 * @author <a href="https://vanessa.b3log.org">Vanessa</a>
 * @since 0.3.1
 */
@RequestProcessor
public class IndexProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(IndexProcessor.class);

    /**
     * DataModelService.
     */
    @Inject
    private DataModelService dataModelService;

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Initialization service.
     */
    @Inject
    private InitService initService;

    /**
     * Shows index with the specified context.
     *
     * @param context the specified context
     * @throws Exception exception
     */
    @RequestProcessing(value = {"", "/"}, method = HttpMethod.GET)
    public void showIndex(final RequestContext context) {
        final HttpServletRequest request = context.getRequest();
        final HttpServletResponse response = context.getResponse();
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "index.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        try {
            final int currentPageNum = Paginator.getPage(request);
            final JSONObject preference = optionQueryService.getPreference();

            // 前台皮肤切换 https://github.com/b3log/solo/issues/12060
            String specifiedSkin = Skins.getSkinDirName(context);
            if (StringUtils.isBlank(specifiedSkin)) {
                final JSONObject skinOpt = optionQueryService.getSkin();
                specifiedSkin = Solos.isMobile(request) ?
                        skinOpt.optString(Option.ID_C_MOBILE_SKIN_DIR_NAME) :
                        skinOpt.optString(Option.ID_C_SKIN_DIR_NAME);
            }
            request.setAttribute(Keys.TEMAPLTE_DIR_NAME, specifiedSkin);

            Cookie cookie;
            if (!Solos.isMobile(request)) {
                cookie = new Cookie(Common.COOKIE_NAME_SKIN, specifiedSkin);
            } else {
                cookie = new Cookie(Common.COOKIE_NAME_MOBILE_SKIN, specifiedSkin);
            }
            cookie.setMaxAge(60 * 60); // 1 hour
            cookie.setPath("/");
            response.addCookie(cookie);

            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING), (String) context.attr(Keys.TEMAPLTE_DIR_NAME), dataModel);

            dataModelService.fillIndexArticles(context, dataModel, currentPageNum, preference);
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);

            dataModel.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, currentPageNum);
            final int previousPageNum = currentPageNum > 1 ? currentPageNum - 1 : 0;
            dataModel.put(Pagination.PAGINATION_PREVIOUS_PAGE_NUM, previousPageNum);

            final Integer pageCount = (Integer) dataModel.get(Pagination.PAGINATION_PAGE_COUNT);
            final int nextPageNum = currentPageNum + 1 > pageCount ? pageCount : currentPageNum + 1;
            dataModel.put(Pagination.PAGINATION_NEXT_PAGE_NUM, nextPageNum);
            dataModel.put(Common.PATH, "");

            statisticMgmtService.incBlogViewCount(context, response);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Shows start page.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/start", method = HttpMethod.GET)
    public void showStart(final RequestContext context) {
        if (initService.isInited() && null != Solos.getCurrentUser(context.getRequest(), context.getResponse())) {
            context.sendRedirect(Latkes.getServePath() + "/admin-index.do#main");

            return;
        }

        String referer = context.header("referer");
        if (StringUtils.isBlank(referer) || !isInternalLinks(referer)) {
            referer = Latkes.getServePath();
        }

        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "common-template/start.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final Map<String, String> langs = langPropsService.getAll(Latkes.getLocale());
        dataModel.putAll(langs);
        // 登录失败提示
        final BeanManager beanManager = BeanManager.getInstance();
        final InitService initService = beanManager.getReference(InitService.class);
        String status = context.getRequest().getParameter("status");
        if (status != null && status.equals("error")) {
            dataModel.put(Common.DATA, 0);
        } else if (!initService.isInited()) {
            dataModel.put(Common.DATA, -1);
        } else if (UpgradeService.boloFastMigration) {
            dataModel.put(Common.DATA, 2);
        } else {
            dataModel.put(Common.DATA, 1);
        }
        dataModel.put(Common.VERSION, SoloServletListener.VERSION);
        dataModel.put(Common.STATIC_RESOURCE_VERSION, Latkes.getStaticResourceVersion());
        dataModel.put(Common.YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        dataModel.put(Common.REFERER, URLs.encode(referer));
        dataModel.put(Common.BOLO_VERSION, SoloServletListener.BOLO_VERSION);
        Keys.fillRuntime(dataModel);
        dataModelService.fillMinified(dataModel);
        dataModelService.fillFaviconURL(dataModel, optionQueryService.getPreference());
        dataModelService.fillUsite(dataModel);
        Solos.addGoogleNoIndex(context);
    }

    /**
     * Auto login page.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/root", method = HttpMethod.GET)
    @Deprecated
    public void showRoot(final RequestContext context) {
        if (initService.isInited() && null != Solos.getCurrentUser(context.getRequest(), context.getResponse())) {
            context.sendRedirect(Latkes.getServePath() + "/admin-index.do#main");

            return;
        } else {
            context.sendRedirect(Latkes.getServePath() + "/start");

            return;
        }
    }

    /**
     * Logout.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/logout", method = HttpMethod.GET)
    public void logout(final RequestContext context) {
        final HttpServletRequest httpServletRequest = context.getRequest();

        Solos.logout(httpServletRequest, context.getResponse());

        Solos.addGoogleNoIndex(context);
        context.sendRedirect(Latkes.getServePath());
    }

    /**
     * Shows kill browser page with the specified context.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/kill-browser", method = HttpMethod.GET)
    public void showKillBrowser(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "common-template/kill-browser.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        try {
            final Map<String, String> langs = langPropsService.getAll(Latkes.getLocale());
            dataModel.putAll(langs);
            final JSONObject preference = optionQueryService.getPreference();
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);
            Keys.fillServer(dataModel);
            Keys.fillRuntime(dataModel);
            dataModelService.fillMinified(dataModel);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Preventing unvalidated redirects and forwards. See more at:
     * <a href="https://www.owasp.org/index.php/Unvalidated_Redirects_and_Forwards_Cheat_Sheet">https://www.owasp.org/index.php/
     * Unvalidated_Redirects_and_Forwards_Cheat_Sheet</a>.
     *
     * @return whether the destinationURL is an internal link
     */
    private boolean isInternalLinks(final String destinationURL) {
        return destinationURL.startsWith(Latkes.getServePath());
    }

    /**
     * Get logs.
     */
    @RequestProcessing(value = "/admin/logs", method = HttpMethod.GET)
    public void logs(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        FixSizeLinkedList<Map<String, Object>> list = RamAppender.getList();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", list);
        jsonObject.put("freeMemNow", Runtime.getRuntime().freeMemory());
        context.renderJSON(jsonObject);
    }
}
