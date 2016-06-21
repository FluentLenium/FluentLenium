package org.fluentlenium.adapter.util;

public class DefaultSharedDriverStrategyReader implements SharedDriverStrategyReader {
    @Override
    public SharedDriverStrategy getSharedDriverStrategy(Class<?> clazz, String testName) {
        SharedDriver sharedDriver = clazz.getAnnotation(SharedDriver.class);

        if (sharedDriver == null) {
            return SharedDriverStrategy.PER_METHOD;
        }

        if (sharedDriver.type() == SharedDriver.SharedType.ONCE) {
            return SharedDriverStrategy.ONCE;
        }

        if (sharedDriver.type() == SharedDriver.SharedType.PER_CLASS) {
            return SharedDriverStrategy.PER_CLASS;
        }

        return SharedDriverStrategy.PER_METHOD;
    }
}
