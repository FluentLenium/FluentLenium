package org.fluentlenium.core.hook;

import com.google.common.base.Function;

/**
 * Control interface for hooks.
 *
 * @param <T> self type
 */
public interface HookControl<T> {
    /**
     * Disable all hooks from actual element.
     *
     * @return this object reference to chain calls
     */
    T noHook();

    /**
     * Disable given hook from actual element.
     *
     * @param hooks hook classes to disable
     * @return this object reference to chain calls
     */
    T noHook(Class<? extends FluentHook>... hooks);

    /**
     * Retore hooks that were defined initially.
     *
     * @return this object reference to chain calls
     */
    T restoreHooks();

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

    /**
     * Invoke a function with no hook.
     *
     * @param function function to invoke
     * @param <R>      return type
     * @return return value of the given function
     */
    <R> R noHook(Function<T, R> function);

    /**
     * Invoke a function with no hook.
     *
     * @param hook     hook class to disable
     * @param function function to invoke
     * @param <R>      return type
     * @return return value of the given function
     */
    <R> R noHook(Class<? extends FluentHook> hook, Function<T, R> function);

    /**
     * Creates a new element locator instance with all hooks disabled.
     *
     * @return new element locator with hook disabled.
     */
    T noHookInstance();

    /**
     * Creates a new element locator instance with given hook disabled.
     *
     * @param hooks hook classes to disable
     * @return new element locator with hook disabled.
     */
    T noHookInstance(Class<? extends FluentHook>... hooks);
}
