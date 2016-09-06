package org.fluentlenium.core.components;

public interface LazyComponents<T> {
    boolean isLazy();

    boolean isLazyInitialized();

    boolean addLazyComponentsListener(LazyComponentsListener<T> listener);

    boolean removeLazyComponentsListener(LazyComponentsListener<T> listener);
}
