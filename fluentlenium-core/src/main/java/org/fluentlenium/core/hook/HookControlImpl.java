package org.fluentlenium.core.hook;

import java.util.function.Function;
import java.util.function.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.proxy.LocatorProxies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Control implementation for hooks.
 *
 * @param <T> self type
 */
public class HookControlImpl<T> implements HookControl<T> {
    private final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();
    private Stack<List<HookDefinition<?>>> hookRestoreStack = new Stack<>();
    private final HookChainBuilder hookChainBuilder;

    private final T self;
    private final Object proxy;
    private final Supplier<T> noHookInstanceSupplier;

    /**
     * Creates a new control implementation for hooks.
     *
     * @param self                   reference to object returned by chainnable calls
     * @param proxy                  proxy object to apply hooks on
     * @param control                control interface
     * @param instantiator           components instantiator
     * @param noHookInstanceSupplier supplier of new instance without any hook.
     */
    public HookControlImpl(final T self, final Object proxy, final FluentControl control,
            final ComponentInstantiator instantiator, final Supplier<T> noHookInstanceSupplier) {
        this.self = self;
        this.proxy = proxy;
        this.noHookInstanceSupplier = noHookInstanceSupplier;
        hookChainBuilder = new DefaultHookChainBuilder(control, instantiator);
    }

    /**
     * Get hook definitions.
     *
     * @return hook definitions
     */
    public List<HookDefinition<?>> getHookDefinitions() {
        return hookDefinitions;
    }

    /**
     * Get hook restore stack.
     *
     * @return hook restore stack
     */
    public Stack<List<HookDefinition<?>>> getHookRestoreStack() {
        return hookRestoreStack;
    }

    /**
     * Set the hook restore stack.
     *
     * @param hookRestoreStack hook restore stack
     */
    public void setHookRestoreStack(final Stack<List<HookDefinition<?>>> hookRestoreStack) {
        this.hookRestoreStack = hookRestoreStack;
    }

    /**
     * Removes hooks from definitions.
     *
     * @param definitions   hook definitions
     * @param hooksToRemove hooks to remove
     */
    public static void removeHooksFromDefinitions(final Collection<HookDefinition<?>> definitions,
            final Class<? extends FluentHook>... hooksToRemove) {
        final Iterator<HookDefinition<?>> hookDefinitionsIterator = definitions.iterator();
        final List<Class<? extends FluentHook>> toRemoveHooks = Arrays.asList(hooksToRemove);
        while (hookDefinitionsIterator.hasNext()) {
            final HookDefinition<?> next = hookDefinitionsIterator.next();
            if (toRemoveHooks.contains(next.getHookClass())) {
                hookDefinitionsIterator.remove();
            }
        }
    }

    private void backupHookDefinitions() {
        hookRestoreStack.add(new ArrayList<>(hookDefinitions));
    }

    private void restoreHookDefinitions() {
        if (!hookRestoreStack.isEmpty()) {
            final List<HookDefinition<?>> pop = hookRestoreStack.pop();
            hookDefinitions.clear();
            hookDefinitions.addAll(pop);
        }
    }

    @Override
    public T restoreHooks() {
        restoreHookDefinitions();
        applyHooks(proxy, hookChainBuilder, hookDefinitions);
        return self;
    }

    @Override
    public <O, H extends FluentHook<O>> T withHook(final Class<H> hook) {
        hookDefinitions.add(new HookDefinition<>(hook));
        backupHookDefinitions();
        applyHooks(proxy, hookChainBuilder, hookDefinitions);
        return self;
    }

    @Override
    public <O, H extends FluentHook<O>> T withHook(final Class<H> hook, final O options) {
        hookDefinitions.add(new HookDefinition<>(hook, options));
        backupHookDefinitions();
        applyHooks(proxy, hookChainBuilder, hookDefinitions);
        return self;
    }

    @Override
    public T noHook() {
        backupHookDefinitions();
        hookDefinitions.clear();
        applyHooks(proxy, hookChainBuilder, hookDefinitions);
        return self;
    }

    @Override
    public T noHook(final Class<? extends FluentHook>... hooks) {
        backupHookDefinitions();
        removeHooksFromDefinitions(hookDefinitions, hooks);
        applyHooks(proxy, hookChainBuilder, hookDefinitions);
        return self;
    }

    /**
     * Apply defined hooks on the proxy.
     *
     * @param proxy            proxy
     * @param hookChainBuilder hook chain builder
     * @param hookDefinitions  hook definitions
     */
    protected void applyHooks(final Object proxy, final HookChainBuilder hookChainBuilder,
            final List<HookDefinition<?>> hookDefinitions) {
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
    }

    @Override
    public <R> R noHook(final Function<T, R> function) {
        noHook();
        final R functionReturn = function.apply(self);
        restoreHooks();
        return functionReturn;
    }

    @Override
    public <R> R noHook(final Class<? extends FluentHook> hook, final Function<T, R> function) {
        noHook(hook);
        final R functionReturn = function.apply(self);
        restoreHooks();
        return functionReturn;
    }

    @Override
    public T noHookInstance() {
        return ((HookControl<T>) noHookInstanceSupplier.get()).noHook();
    }

    @Override
    public T noHookInstance(final Class<? extends FluentHook>... hooks) {
        final HookControl<T> hookControl = (HookControl<T>) noHookInstanceSupplier.get();

        for (final HookDefinition definition : hookDefinitions) {
            hookControl.withHook(definition.getHookClass(), definition.getOptions());
        }

        return hookControl.noHook(hooks);
    }
}
