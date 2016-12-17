package org.fluentlenium.core.inject;

import org.openqa.selenium.SearchContext;

/**
 * Control interface for FluentLenium injection.
 */
public interface FluentInjectControl {
    /**
     * Inject object with FluentLenium resources.
     *
     * @param container container to inject with FluentLenium resources
     * @return The container context related to the injected container
     */
    ContainerContext inject(Object container);

    /**
     * Inject object with FluentLenium resources, using given search context and parent container.
     *
     * @param componentContainer container to inject with FluentLenium resources
     * @param parentContainer    parent container
     * @param context            search context to use for injection
     * @return The container context related to the injected container
     */
    ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context);

    /**
     * Creates a new instance of a class inject it.
     *
     * @param cls class of the object to create
     * @param <T> type of the object
     * @return new container instance
     * @see FluentInjectControl#inject(Object)
     */
    <T> T newInstance(Class<T> cls);
}
