package org.fluentlenium.utils;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public final class ScreenshotUtil {

    private ScreenshotUtil(){
    }

    private static final Set<String> IGNORED_EXCEPTIONS = ImmutableSet.of(
            "org.junit.AssumptionViolatedException",
            "org.junit.internal.AssumptionViolatedException",
            "org.testng.SkipException");

    /**
     * Checks if the exception should be ignored and not reported as a test case fail
     *
     * @param e - the exception to check is it defined in ignored exceptions set
     * @return boolean
     */
    public static boolean isIgnoredException(Throwable e) {
        boolean isIgnored = false;
        if (e != null) {
            Class<?> clazz = e.getClass();
            do {
                if (IGNORED_EXCEPTIONS.contains(clazz.getName())) {
                    isIgnored = true;
                    break;
                }
                clazz = clazz.getSuperclass();
            } while (clazz != Object.class);
        }

        return isIgnored;
    }

}
