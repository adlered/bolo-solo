package org.b3log.solo.bolo.waf;

/**
 * <h3>bolo-solo</h3>
 * <p>Web Application Firewall.</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-31
 **/
public class WAF {
    public static boolean POWER = false;

    public static void on() {
        POWER = true;
        WAFlogger.log("WAF is on.");
    }

    public static void off() {
        POWER = false;
        WAFlogger.log("WAF is off.");
    }

    public static void in(String requestIP, String requestURL) {
        WAFlogger.logTrace(requestIP, requestURL);
    }
}
