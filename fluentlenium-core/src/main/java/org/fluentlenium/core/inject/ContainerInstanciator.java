package org.fluentlenium.core.inject;

/**
 * Creates container instances.
 */
public interface ContainerInstanciator {

    /**
     * Creates a new instance of a container class and inject it.
     *
     * @param cls     class of the object to create
     * @param context parent content for the newly created container
     * @param <T>     type of the container
     * @return new instance of the container class
     * @see FluentInjectControl#inject(Object)
     */
    <T> T newInstance(Class<T> cls, ContainerContext context);
}
