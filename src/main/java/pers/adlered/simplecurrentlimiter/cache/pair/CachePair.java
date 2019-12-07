package pers.adlered.simplecurrentlimiter.cache.pair;

/**
 * <h3>SimpleCurrentLimiter</h3>
 * <p>字符串和次数 Bean</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-10-10 22:52
 **/
public class CachePair {
    private long frequency;
    private long timeStamp;

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
