package org.b3log.solo.bolo_support;

import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.SoloServletListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h3>bolo-solo</h3>
 * <p>属性 API.</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-20 20:02
 **/
@RequestProcessor
public class PropProcessor {
    @RequestProcessing(value = "/prop/set", method = {HttpMethod.GET})
    public void setProperty(final RequestContext context) {
        HttpServletRequest request = context.getRequest();
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        SoloServletListener.prop.setProperty(key, value);
    }

    @RequestProcessing(value = "/prop/get", method = {HttpMethod.GET})
    public void getProperty(final RequestContext context) {
        HttpServletRequest request = context.getRequest();
        String key = request.getParameter("key");
        String value = SoloServletListener.prop.getProperty(key);
        context.renderJSON().renderMsg(value);

        return ;
    }
}
