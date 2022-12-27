package io.fluentlenium.core.components;

/**
 * Supports lazy initialization.
 *
 * @param <T> type of component
 */
public interface LazyComponents<T> {
    /**
     * Get the lazyness of this object.
     *
     * @return true if it's lazy, false otherwise.
     */
    boolean isLazy();

    /**
     * Check if the underlying lazy components are initialized.
     *
     * @return true if lazy components are initialized, false otherwise.
     */
    boolean isLazyInitialized();

    /**
     * Add a lazy components initialization listener.
     *
     * @param listener lazy components listener
     * @return true if the listener was added, false otherwise
     */
    boolean addLazyComponentsListener(LazyComponentsListener<T> listener);

    /**
     * Remove an existing lazy components initialization listener.
     *
     * @param listener lazy components listener
     * @return true if the listener was added, false otherwise
     */
    boolean removeLazyComponentsListener(LazyComponentsListener<T> listener);
}
