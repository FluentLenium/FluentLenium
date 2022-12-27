package io.fluentlenium.core.inject;

/**
 * Object for storing component and proxy pairs during field injection.
 *
 * @param <T> the component type
 * @param <P> the proxy type
 * @see FluentInjector
 */
final class ComponentAndProxy<T, P> {
    private final T component;
    private final P proxy;

    ComponentAndProxy(T component, P proxy) {
        this.component = component;
        this.proxy = proxy;
    }

    public T getComponent() {
        return component;
    }

    public P getProxy() {
        return proxy;
    }
}
