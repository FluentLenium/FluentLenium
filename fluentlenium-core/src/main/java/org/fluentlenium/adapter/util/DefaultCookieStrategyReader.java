package org.fluentlenium.adapter.util;

public class DefaultCookieStrategyReader implements CookieStrategyReader {
    @Override
    public boolean shouldDeleteCookies(Class<?> clazz, String testName) {
        DeleteCookies deleteCookies = clazz.getAnnotation(DeleteCookies.class);
        return deleteCookies != null && deleteCookies.value();
    }
}
