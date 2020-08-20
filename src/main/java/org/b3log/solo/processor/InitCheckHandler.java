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

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.handler.Handler;
import org.b3log.solo.bolo.waf.WAF;
import org.b3log.solo.service.InitService;
import org.b3log.solo.service.UpgradeService;

import javax.servlet.http.HttpServletResponse;

/**
 * Checks initialization handler.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 3.2.0
 */
public class InitCheckHandler implements Handler {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(InitCheckHandler.class);

    /**
     * Whether initialization info reported.
     */
    private static boolean initReported;

    @Override
    public void handle(final RequestContext context) {
        final String requestURI = context.requestURI();
        final boolean isSpiderBot = (boolean) context.attr(Keys.HttpRequest.IS_SEARCH_ENGINE_BOT);
        LOGGER.log(Level.TRACE, "Request [URI={0}]", requestURI);

        /**
         * Bolo WAF
         */
        String requestIP = context.remoteAddr();
        if (!WAF.in(requestIP, requestURI)) {
            context.sendError(HttpServletResponse.SC_GONE);

            return;
        }

        // 禁止直接获取 robots.txt https://github.com/b3log/solo/issues/12543
        if (requestURI.startsWith("/robots.txt") && !isSpiderBot) {
            context.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        if (StringUtils.startsWith(requestURI, Latkes.getContextPath() + "/oauth/bolo")) {
            // Do initialization
            context.handle();

            return;
        } else if (UpgradeService.boloFastMigration) {
            context.attr(Keys.HttpRequest.REQUEST_URI, Latkes.getContextPath() + "/start");
            context.handle();
            LOGGER.log(Level.DEBUG, "Bolo Fast Migrating is enabled, so redirects to /start");

            return;
        }

        final BeanManager beanManager = BeanManager.getInstance();
        final InitService initService = beanManager.getReference(InitService.class);
        if (initService.isInited()) {
            context.handle();

            return;
        }

        if (!initReported) {
            LOGGER.log(Level.DEBUG, "Bolo has not been initialized, so redirects to /start");
            initReported = true;
        }

        context.attr(Keys.HttpRequest.REQUEST_URI, Latkes.getContextPath() + "/start");
        context.handle();
    }
}
