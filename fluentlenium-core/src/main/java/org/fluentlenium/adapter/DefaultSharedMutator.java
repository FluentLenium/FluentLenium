package org.fluentlenium.adapter;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;

/**
 * Default implementation of {@link SharedMutator}, returning unchanged parameters.
 */
public class DefaultSharedMutator implements SharedMutator {
    @Override
    public <T> EffectiveParameters<T> getEffectiveParameters(final Class<T> testClass, final String testName,
            final DriverLifecycle driverLifecycle) {
        return new EffectiveParameters<>(testClass, testName, driverLifecycle);
    }
}
