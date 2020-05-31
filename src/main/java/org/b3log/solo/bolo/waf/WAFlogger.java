package org.b3log.solo.bolo.waf;

import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;

/**
 * <h3>bolo-solo</h3>
 * <p>Logger of WAF.</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-31
 **/
public class WAFlogger {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(WAFlogger.class);

    public static final String prefix = "${ Web Application Firewall } ";

    public static void log(String log) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(log);
        LOGGER.log(Level.INFO, stringBuilder.toString());
    }

    public static void logTrace(String requestIP, String requestURL) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(requestIP + " >>> " + requestURL);
        LOGGER.log(Level.INFO, stringBuilder.toString());
    }

}
