package org.fluentlenium.adapter;

import org.fluentlenium.adapter.util.SharedDriverStrategy;

/**
 * Default implementation of {@link SharedMutator}, returning unchanged parameters.
 */
public class DefaultSharedMutator implements SharedMutator {
    @Override
    public <T> EffectiveParameters<T> getEffectiveParameters(Class<T> testClass, String testName, SharedDriverStrategy strategy) {
        return new EffectiveParameters<>(testClass, testName, strategy);
    }
}
