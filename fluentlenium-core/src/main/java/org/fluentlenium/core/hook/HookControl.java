package org.fluentlenium.core.hook;

public interface HookControl<T> {
    /**
     * Disable all hooks.
     *
     * @return
     */
    T noHook();

    /**
     * Enable a hook with default options.
     *
     * @param hook class to enable.
     * @return
     */
    <O, H extends FluentHook<O>> T withHook(Class<H> hook);

    /**
     * Enable a hook with given options.
     *
     * @param hook
     * @param options
     * @param <O>
     * @param <H>
     * @return
     */
    <O, H extends FluentHook<O>> T withHook(Class<H> hook, O options);
}
