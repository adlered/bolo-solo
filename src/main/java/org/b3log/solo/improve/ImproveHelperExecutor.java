package org.b3log.solo.improve;

import org.b3log.latke.servlet.RequestContext;

public class ImproveHelperExecutor {

    public static void submit(final RequestContext context) {
        SharedExecutorPool.executor.execute(new ImproveHelper(context));
    }
}
