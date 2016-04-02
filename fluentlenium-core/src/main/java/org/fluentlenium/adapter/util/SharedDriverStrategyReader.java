package org.fluentlenium.adapter.util;


/**
 * Extract SharedDriver mode from a class
 */
public interface SharedDriverStrategyReader {
    SharedDriverStrategy getSharedDriverStrategy(Class<?> clazz, String testName);
}
