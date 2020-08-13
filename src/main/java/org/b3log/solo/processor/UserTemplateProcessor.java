/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
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

import freemarker.template.Template;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.AbstractFreeMarkerRenderer;
import org.b3log.latke.util.Locales;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.*;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User template processor.
 *
 * <p>
 * User can add a template (for example "links.ftl") then visits the page ("links.html").
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.4.5
 */
@RequestProcessor
public class UserTemplateProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleProcessor.class);

    /**
     * DataModelService.
     */
    @Inject
    private DataModelService dataModelService;

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
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    /**
     * Option management service.
     */
    @Inject
    private OptionMgmtService optionMgmtService;

    /**
     * Shows the user template page.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/{name}.html", method = HttpMethod.GET)
    public void showPage(final RequestContext context) {
        final String requestURI = context.requestURI();
        final String templateName = context.pathVar("name") + ".ftl";
        LOGGER.log(Level.DEBUG, "Shows page [requestURI={0}, templateName={1}]", requestURI, templateName);

        final HttpServletRequest request = context.getRequest();
        final HttpServletResponse response = context.getResponse();
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, templateName);

        final Map<String, Object> dataModel = renderer.getDataModel();
        final Template template = Skins.getSkinTemplate(context, templateName);
        if (null == template) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        try {
            final Map<String, String> langs = langPropsService.getAll(Locales.getLocale(request));
            dataModel.putAll(langs);
            final JSONObject preference = optionQueryService.getPreference();
            dataModelService.fillCommon(context, dataModel, preference);
            dataModelService.fillFaviconURL(dataModel, preference);
            dataModelService.fillUsite(dataModel);
            dataModelService.fillUserTemplate(context, template, dataModel, preference);
            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING), (String) context.attr(Keys.TEMAPLTE_DIR_NAME), dataModel);
            statisticMgmtService.incBlogViewCount(context, response);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            context.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Refresh usite from hacpai.
     * @param context
     */
    @RequestProcessing(value = "/admin/usite/refresh", method = HttpMethod.GET)
    public void refreshUsite(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        userMgmtService.refreshUSite();

        context.renderJSON().renderCode(200);
        context.renderJSON().renderMsg("OK");
    }

    @RequestProcessing(value = "/admin/usite/set", method = HttpMethod.POST)
    public void setUsite(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        JSONObject usiteOpt = optionQueryService.getOptionById(Option.ID_C_USITE);
        if (null == usiteOpt) {
            usiteOpt = new JSONObject();
            usiteOpt.put(Keys.OBJECT_ID, Option.ID_C_USITE);
            usiteOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_HACPAI);
        }
        String usite = context.requestJSON().toString();

        // Usite 合法性检测
        try {
            JSONObject usiteObject = new JSONObject(usite);
            List<String> usiteList = new ArrayList<>();
            Collections.addAll(usiteList, "usiteUserId", "usiteWeiBo", "usiteQQMusic", "usiteStackOverflow", "usiteDribbble", "usiteGitHub", "usiteMedium", "usiteTwitter", "usiteQQ", "usiteLinkedIn", "usiteSteam", "oId", "usiteInstagram", "usiteCodePen", "usiteWYMusic", "usiteWeChat", "usiteZhiHu", "usiteBehance", "usiteTelegram", "usiteFacebook");
            for (String i : usiteList) {
                if (!usiteObject.has(i)) {
                    LOGGER.log(Level.ERROR, "Updates usite option failed: Invalid JSON Object.");
                    context.renderJSON().renderCode(500);

                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Updates usite option failed", e);
            context.renderJSON().renderCode(500);

            return;
        }

        usiteOpt.put(Option.OPTION_VALUE, usite);
        try {
            optionMgmtService.addOrUpdateOption(usiteOpt);
            LOGGER.log(Level.INFO, "Usite refresh from Local successful: " + usite);
            context.renderJSON().renderCode(200);

            return;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Updates usite option failed", e);
            context.renderJSON().renderCode(500);

            return;
        }
    }

    @RequestProcessing(value = "/admin/usite/get", method = HttpMethod.GET)
    public void getUsite(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        try {
            JSONObject usiteOpt = optionQueryService.getOptionById(Option.ID_C_USITE);
            String usite = usiteOpt.optString(Option.OPTION_VALUE);
            context.renderJSON().renderCode(200);
            context.renderJSON().renderMsg(usite);
        } catch (Exception e) {
            context.renderJSON().renderCode(500);
            context.renderJSON().renderMsg("");
        }
    }
}
