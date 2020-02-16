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
package pers.adlered.simplecurrentlimiter.cache;

import pers.adlered.simplecurrentlimiter.cache.pair.CachePair;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>SimpleCurrentLimiter</h3>
 * <p>存储字符串、次数信息</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-10-10 22:49
 **/
public class MainCache {
    // 缓存 存储CachePair
    public Map<String, CachePair> cachePairMap = new HashMap<>();
    // 过期时间（毫秒）
    public long expireTime = -1;
    // 时间单位次数
    public long frequencyTime = -1;
}
