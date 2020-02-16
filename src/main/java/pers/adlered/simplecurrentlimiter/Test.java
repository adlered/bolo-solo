/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
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
package pers.adlered.simplecurrentlimiter;

import pers.adlered.simplecurrentlimiter.main.SimpleCurrentLimiter;

/**
 * <h3>SimpleCurrentLimiter</h3>
 * <p></p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-06 14:53
 **/
public class Test {
    public static void main(String[] args) {
        try {
            SimpleCurrentLimiter simpleCurrentLimiter = new SimpleCurrentLimiter(2, 1);
            boolean result;
            result = simpleCurrentLimiter.access("127.0.0.1");
            System.out.println("IP Access: 127.0.0.1, passed: " + result);
            Thread.sleep(500);
            result = simpleCurrentLimiter.access("1.1.1.1");
            System.out.println("IP Access: 1.1.1.1, passed: " + result);
            Thread.sleep(500);
            result = simpleCurrentLimiter.access("127.0.0.1");
            System.out.println("IP Access: 127.0.0.1, passed: " + result);
            Thread.sleep(500);
        } catch (InterruptedException IE) {}
    }
}
