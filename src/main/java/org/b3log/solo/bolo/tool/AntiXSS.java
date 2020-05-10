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

/**
 * <h3>bolo-solo</h3>
 * <p>XSS 过滤</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-10
 **/
public class AntiXSS {
    public static String getSafeStringXSS(String s) {
        if (s == null || "".equals(s)) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&prime;");// &acute;");
                    break;
                case '′':
                    sb.append("&prime;");// &acute;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '＂':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("＆");
                    break;
                case '#':
                    sb.append("＃");
                    break;
                case '\\':
                    sb.append('￥');
                    break;
                case '=':
                    sb.append("&#61;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }
}
