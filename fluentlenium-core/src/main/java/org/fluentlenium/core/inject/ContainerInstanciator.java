package org.fluentlenium.core.inject;

public interface ContainerInstanciator {

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
