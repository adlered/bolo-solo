package org.b3log.solo.bolo.tool;

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
