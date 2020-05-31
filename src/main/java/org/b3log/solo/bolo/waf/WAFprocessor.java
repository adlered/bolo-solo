package org.b3log.solo.bolo.waf;

import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;

/**
 * <h3>bolo-solo</h3>
 * <p>Processor of WAF.</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-31
 **/
@RequestProcessor
public class WAFprocessor {
    @RequestProcessing(value = "/waf/denied", method = {HttpMethod.GET})
    public void accessDenied(final RequestContext context) {
        context.renderJSON().renderMsg("访问频率过快，请稍候再试！");
    }
}
