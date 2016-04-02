package org.fluentlenium.adapter.util;


public interface CookieStrategyReader {
    boolean shouldDeleteCookies(Class<?> clazz, String testName);
}
