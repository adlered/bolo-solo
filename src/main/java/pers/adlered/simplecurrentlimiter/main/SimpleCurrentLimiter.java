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
package pers.adlered.simplecurrentlimiter.main;

import pers.adlered.simplecurrentlimiter.control.MainControl;

/**
 * <h3>SimpleCurrentLimiter</h3>
 * <p>简单限流器，用于IP地址访问次数验证，其它字符串访问次数验证</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-10-10 22:45
 **/
public class SimpleCurrentLimiter {
    MainControl mainControl = null;

    private MainControl getMainControl() {
        if (mainControl == null) {
            mainControl = new MainControl();
        }
        return mainControl;
    }

    public SimpleCurrentLimiter(long expireTimeSecond, long frequencyTime) {
        MainControl mainControl = getMainControl();
        mainControl.setExpireTimeSecond(expireTimeSecond);
        mainControl.setFrequencyTime(frequencyTime);
    }

    /**
     * 当用户访问时，特征将传入此方法
     * @return 用户令牌
     */
    public boolean access(String str) {
        MainControl mainControl = getMainControl();
        return mainControl.write(str);
    }

    public void setExpireTimeMilli(long timeMilli) {
        MainControl mainControl = getMainControl();
        mainControl.setExpireTimeMilli(timeMilli);
    }

    public void setExpireTimeSecond(long timeSecond) {
        MainControl mainControl = getMainControl();
        mainControl.setExpireTimeSecond(timeSecond);
    }

    public void setExpireTimeMin(long timeMin) {
        MainControl mainControl = getMainControl();
        mainControl.setExpireTimeMin(timeMin);
    }

    public void setExpireTimeHour(long timeHour) {
        MainControl mainControl = getMainControl();
        mainControl.setExpireTimeHour(timeHour);
    }

    public void setFrequencyTime(long frequencyTime) {
        MainControl mainControl = getMainControl();
        mainControl.setFrequencyTime(frequencyTime);
    }
}