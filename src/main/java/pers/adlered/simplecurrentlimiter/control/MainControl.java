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
package pers.adlered.simplecurrentlimiter.control;

import pers.adlered.simplecurrentlimiter.cache.MainCache;
import pers.adlered.simplecurrentlimiter.cache.pair.CachePair;

/**
 * <h3>SimpleCurrentLimiter</h3>
 * <p>底层数据控制</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-10-10 22:55
 **/
public class MainControl extends MainCache {
    public boolean write(String str) {
        boolean isOK = true;
        // 先读取查看是否已经存在
        long frequency = -1l;
        long timeStamp = -1l;
        long currentTimeStamp = System.currentTimeMillis();
        CachePair verifyPair = this.cachePairMap.get(str);
        if (verifyPair == null) {
            frequency = 1l;
            timeStamp = currentTimeStamp;
        } else {
            frequency = verifyPair.getFrequency() + 1l;
            timeStamp = verifyPair.getTimeStamp();
            // 超时刷新
            if (this.expireTime != -1) {
                if ((currentTimeStamp - timeStamp) > this.expireTime) {
                    frequency = 1l;
                    timeStamp = currentTimeStamp;
                }
            }
        }
        if (frequency > this.frequencyTime) {
            isOK = false;
        }
        CachePair newPair = new CachePair();
        newPair.setFrequency(frequency);
        newPair.setTimeStamp(timeStamp);
        this.cachePairMap.put(str, newPair);
        return isOK;
    }

    public void setFrequencyTime(long frequencyTime) {
        this.frequencyTime = frequencyTime;
    }

    public void setExpireTimeMilli(long timeMilli) {
        this.expireTime = timeMilli;
    }

    public void setExpireTimeSecond(long timeSecond) {
        this.expireTime = timeSecond * 1000;
    }

    public void setExpireTimeMin(long timeMin) {
        this.expireTime = timeMin * 60000;
    }

    public void setExpireTimeHour(long timeHour) {
        this.expireTime = timeHour * 3600000;
    }
}
