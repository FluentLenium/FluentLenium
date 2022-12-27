package io.fluentlenium.core.domain;

import io.fluentlenium.core.components.LazyComponents;
import io.fluentlenium.core.components.LazyComponentsListener;

/**
 * Implementation of {@link LazyComponents} for a non lazy components wrapper.
 */
public class NotLazyComponents implements LazyComponents {
    @Override
    public boolean isLazy() {
        return false;
    }

    @Override
    public boolean isLazyInitialized() {
        return true;
    }

    @Override
    public boolean addLazyComponentsListener(LazyComponentsListener listener) {
        return false;
    }

    @Override
    public boolean removeLazyComponentsListener(LazyComponentsListener listener) {
        return false;
    }
}
