package org.fluentlenium.core.hook;

import javax.annotation.Nonnull;

/**
 * Definition of a hook, containing class of the hook and defined options
 *
 * @param <T> type of the option class
 */
public class HookDefinition<T> {
    @Nonnull
    private Class<? extends FluentHook<T>> hookClass;
    private T options;

    public HookDefinition(@Nonnull Class<? extends FluentHook<T>> hookClass) {
        this.hookClass = hookClass;
    }

    public HookDefinition(@Nonnull Class<? extends FluentHook<T>> hookClass, T options) {
        this.hookClass = hookClass;
        this.options = options;
    }

    @Nonnull
    public Class<? extends FluentHook<T>> getHookClass() {
        return hookClass;
    }

    public T getOptions() {
        return options;
    }
}
