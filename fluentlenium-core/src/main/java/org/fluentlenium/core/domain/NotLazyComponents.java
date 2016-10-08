package org.fluentlenium.core.domain;

import org.fluentlenium.core.components.LazyComponents;
import org.fluentlenium.core.components.LazyComponentsListener;

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
    public boolean addLazyComponentsListener(final LazyComponentsListener listener) {
        return false;
    }

    @Override
    public boolean removeLazyComponentsListener(final LazyComponentsListener listener) {
        return false;
    }
}
