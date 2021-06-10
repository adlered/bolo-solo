package org.b3log.solo.improve;

import java.util.Map;

public class LogHelperExecutor {

    public static void submit(final Map<String, Object> map) {
        SharedExecutorPool.executor.execute(new LogHelper(map));
    }
}
