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
package org.b3log.solo.bolo.waf;

import org.b3log.latke.Latkes;
import org.b3log.solo.bolo.prop.Options;
import org.b3log.solo.model.Option;
import pers.adlered.simplecurrentlimiter.main.SimpleCurrentLimiter;

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
        if (!POWER) {
            POWER = true;

            int second = 0;
            int times = 0;

            try {
                second = Integer.parseInt(Options.get(Option.ID_C_WAF_CURRENT_LIMIT_SECOND));
                times = Integer.parseInt(Options.get(Option.ID_C_WAF_CURRENT_LIMIT_TIMES));
            } catch (Exception e) {
                second = 180;
                times = 180;
            }

            WAFstorage.currentLimiter = new SimpleCurrentLimiter(second, times);
            WAFlogger.log("Power on [second=" + second + ", times=" + times + "]");
        }
    }

    public static void off() {
        if (POWER) {
            POWER = false;

            WAFstorage.currentLimiter = null;
            WAFlogger.log("Power off");
        }
    }

    public static void restart() {
        off();
        on();
    }

    public static void set() {
        try {
            String wafPower = Options.get(Option.ID_C_WAF_POWER);
            if (wafPower.equals("off")) {
                WAF.off();
            } else {
                WAF.restart();
            }
        } catch (Exception e) {
            WAF.restart();
        }
    }

    public static boolean in(String requestIP, String requestURL) {
        // 防火墙未开启直接放行
        if (!WAF.POWER) {
            return true;
        }

        String contextPath = Latkes.getContextPath();
        requestURL = requestURL.replaceFirst("^" + contextPath, "");
        if (requestURL.isEmpty()) {
            requestURL = "/";
        }

        if (!(
                requestURL.startsWith("/articles/random") ||
                        requestURL.startsWith("/manifest.json") ||
                        requestURL.endsWith("/relevant/articles") ||
                        requestURL.startsWith("/opensearch.xml") ||
                        requestURL.startsWith("/waf/denied") ||
                        requestIP.equals("0:0:0:0:0:0:0:1") ||
                        requestIP.equals("127.0.0.1") ||
                        requestURL.startsWith("/admin") ||
                        requestURL.startsWith("/console") ||
                        requestURL.startsWith("/plugins") ||
                        requestURL.startsWith("/bolo") ||
                        requestURL.startsWith("/logout") ||
                        requestURL.startsWith("/start") ||
                        requestURL.startsWith("/favicon.ico") ||
                        requestURL.startsWith("/error") ||
                        requestURL.startsWith("/images") ||
                        requestURL.startsWith("/article/commentSync/getList") ||
                        requestURL.startsWith("/PBC/status") ||
                        requestURL.startsWith("/favicon") ||
                        requestURL.startsWith("/oauth/bolo/login")
        )) {

            WAFrule rule = new WAFrule();
            String str = requestIP;

            if (rule.access(str)) {
                WAFlogger.logTrace(requestIP, requestURL);

                return true;
            } else {
                WAFlogger.logWarn("Illegal access from " + requestIP + " was denied to " + requestURL);

                return false;
            }
        }

        return true;
    }

}
