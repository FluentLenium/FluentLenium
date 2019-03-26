package org.fluentlenium.core.proxy;

import javax.annotation.Nullable;

/**
 * Stores a proxy and a result object for locator handling.
 *
 * @param <T> the actual type of element/component
 */
public final class ProxyResultHolder<T> {

    private T proxy;
    private T result;

    public T getProxy() {
        return proxy;
    }

    public void setProxy(T proxy) {
        this.proxy = proxy;
    }

    public T getResult() {
        return result;
    }

    public void setResult(@Nullable T result) {
        this.result = result;
    }

    /**
     * Returns whether the undelying result object is present, meaning has null value.
     *
     * @return true if result's value is not null, false otherwise
     */
    public boolean isResultLoaded() {
        return result != null;
    }
}
