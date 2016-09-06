package org.fluentlenium.core.domain;

import org.fluentlenium.core.components.LazyComponents;
import org.fluentlenium.core.components.LazyComponentsListener;

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
