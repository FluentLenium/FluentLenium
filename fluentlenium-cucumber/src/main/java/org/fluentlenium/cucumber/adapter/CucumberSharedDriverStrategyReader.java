package org.fluentlenium.cucumber.adapter;

import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.fluentlenium.adapter.util.SharedDriverStrategyReader;
import org.fluentlenium.cucumber.adapter.util.SharedDriver;

public class CucumberSharedDriverStrategyReader implements SharedDriverStrategyReader {
    @Override
    public SharedDriverStrategy getSharedDriverStrategy(Class<?> clazz, String testName) {
        SharedDriver sharedDriver = clazz.getAnnotation(SharedDriver.class);
        if (sharedDriver == null) {
            return SharedDriverStrategy.PER_CLASS;
        }

        if (sharedDriver.type() == SharedDriver.SharedType.ONCE) {
            return SharedDriverStrategy.ONCE;
        } else {
            return SharedDriverStrategy.PER_CLASS;
        }
    }
}
