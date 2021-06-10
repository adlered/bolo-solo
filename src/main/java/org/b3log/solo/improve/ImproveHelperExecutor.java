package org.b3log.solo.improve;

import org.b3log.latke.servlet.RequestContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImproveHelperExecutor {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void submit(final RequestContext context) {
        if (ImproveOptions.doJoinHelpImprovePlan().equals("true")) {
            executor.execute(new ImproveHelper(context));
        }
    }
}
