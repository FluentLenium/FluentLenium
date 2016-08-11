package org.fluentlenium.core.inject;

public interface FluentInjectControl {
    /**
     * @deprecated use {@link #newInstance(Class, Object...)} instead.
     */
    @Deprecated
    <T> T createPage(Class<T> cls, Object... params);

    /**
     * Creates a new instance of a class inject it.
     *
     * @param cls    class of the object to create
     * @param params parameters of the constructor to use
     * @param <T>    type of the object
     * @return new instance
     * @see #inject(Object)
     */
    <T> T newInstance(Class<T> cls, Object... params);

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
