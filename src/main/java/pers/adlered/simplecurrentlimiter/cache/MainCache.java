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
