package org.b3log.solo.improve;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ImproveHelper 和 LogHelper 共享一个单例线程池。
 */
public class SharedExecutorPool {

    public static final ExecutorService executor = Executors.newSingleThreadExecutor();
}
