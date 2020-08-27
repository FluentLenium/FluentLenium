package org.fluentlenium.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class ExecutorServiceUtil {

    private ExecutorServiceUtil() {
    }

    public static void shutDownExecutor(ExecutorService executorService, Long browserTimeout) throws InterruptedException {
        executorService.shutdown();
        if (didNotExitGracefully(executorService, browserTimeout)) {
            executorService.shutdownNow();
        }
    }

    public static ExecutorService getExecutor(ExecutorService webDriverExecutor) {
        if (webDriverExecutor == null) {
            return Executors.newSingleThreadExecutor();
        }
        return webDriverExecutor;
    }

    private static boolean didNotExitGracefully(ExecutorService executorService, Long browserTimeout) throws InterruptedException {
        return !executorService.awaitTermination(browserTimeout, TimeUnit.MILLISECONDS);
    }
}
