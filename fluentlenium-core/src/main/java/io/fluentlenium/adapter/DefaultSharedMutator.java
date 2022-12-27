package io.fluentlenium.adapter;

import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;

/**
 * Default implementation of {@link SharedMutator}, returning unchanged parameters.
 */
public class DefaultSharedMutator implements SharedMutator {
    @Override
    public <T> EffectiveParameters<T> getEffectiveParameters(Class<T> testClass, String testName,
                                                             DriverLifecycle driverLifecycle) {
        return new EffectiveParameters<>(testClass, testName, driverLifecycle);
    }
}
