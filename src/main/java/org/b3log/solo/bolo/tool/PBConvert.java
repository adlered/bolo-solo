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
package org.b3log.solo.bolo.tool;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.util.Solos;

import javax.servlet.http.HttpServletResponse;

/**
 * <h3>bolo-solo</h3>
 * <p>将链滴图床的图床上传至当前设定的图床并替换链接</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-12-19 22:14
 **/
@RequestProcessor
public class PBConvert {

    final private PBThread pbThread = new PBThread();

    @RequestProcessing(value = "/test", method = HttpMethod.GET)
    public void test(final RequestContext context) {
        Logger.getLogger(PBConvert.class).log(Level.ERROR, "wow");
        return ;
    }

    @RequestProcessing(value = "/PBC/status", method = {HttpMethod.GET})
    public void getPBStatus(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        context.renderJSON().renderMsg(pbThread.getStatus());
    }

    @RequestProcessing(value = "/PBC/run", method = {HttpMethod.GET})
    public void runPB(final RequestContext context) {
        synchronized (this) {
            if (!Solos.isAdminLoggedIn(context)) {
                context.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Thread thread = new Thread(pbThread);
            thread.start();

            context.renderJSON().renderMsg("OK");
        }
    }
}
