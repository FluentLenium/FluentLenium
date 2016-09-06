package org.fluentlenium.core.hook;

public interface HookControl<T> {
    /**
     * Disable all hooks from actual context.
     *
     * @return this object reference to chain calls
     */
    T noHook();

    /**
     * Enable a hook with default options.
     *
     * @param hook hook class to enable
     * @param <O>  Type of the hook
     * @param <H>  Type of the hook options
     * @return this object reference to chain calls
     */
    <O, H extends FluentHook<O>> T withHook(Class<H> hook);

    /**
     * Enable a hook with given options.
     *
     * @param hook    hook class to enable
     * @param options hook options to apply
     * @param <O>     Type of the hook
     * @param <H>     Type of the hook options
     * @return this object reference to chain calls
     */
    <O, H extends FluentHook<O>> T withHook(Class<H> hook, O options);
}
