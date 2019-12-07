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
