package org.fluentlenium.adapter.util;

public class DefaultCookieStrategyReader implements CookieStrategyReader {
    @Override
    public boolean shouldDeleteCookies(Class<?> clazz, String testName) {
        SharedDriver sharedBrowser = clazz.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.deleteCookies());
    }
}
