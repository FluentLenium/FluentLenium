package org.fluentlenium.core.inject;

public interface FluentInjectControl extends ContainerInstanciator {
    /**
     * @deprecated use {@link #newInstance(Class)} instead.
     */
    @Deprecated
    <T> T createPage(Class<T> cls, Object... params);

    /**
     * Inject object with FluentLenium resources.
     *
     * @param container container to inject with FluentLenium resources
     */
    void inject(Object container);

    /**
     * Inject array of object with FluentLenium resources.
     *
     * @param containers container to inject with FluentLenium resources
     */
    void inject(Object... containers);
}
