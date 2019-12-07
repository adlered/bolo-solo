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