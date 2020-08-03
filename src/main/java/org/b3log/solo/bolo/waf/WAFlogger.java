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

    public static final String prefix = "<< WAF [ACL] >> ";

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

    public static void logError(String log) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(log);
        LOGGER.log(Level.ERROR, stringBuilder.toString());
    }

    public static void logWarn(String log) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(log);
        LOGGER.log(Level.WARN, stringBuilder.toString());
    }
}
