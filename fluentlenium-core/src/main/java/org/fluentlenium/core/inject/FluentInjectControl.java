package org.fluentlenium.core.inject;

public interface FluentInjectControl {
    /**
     * Inject object with FluentLenium resources.
     *
     * @param container container to inject with FluentLenium resources
     */
    ContainerContext inject(Object container);

    /**
     * Inject array of object with FluentLenium resources.
     *
     * @param containers container to inject with FluentLenium resources
     */
    ContainerContext[] inject(Object... containers);

    /**
     * Creates a new instance of a class inject it.
     *
     * @param cls    class of the object to create
     * @param <T>    type of the object
     * @return new instance
     * @see FluentInjectControl#inject(Object)
     */
    <T> T newInstance(Class<T> cls);
}
