package org.b3log.solo.bolo.waf;

import pers.adlered.simplecurrentlimiter.main.SimpleCurrentLimiter;

/**
 * <h3>bolo-solo</h3>
 * <p>rules of WAF.</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-31
 **/
public class WAFrule {
    public boolean access(String str) {
        return WAFstorage.currentLimiter.access(str);
    }

}
