package io.fluentlenium.core.hook;

import io.fluentlenium.core.components.ComponentInstantiator;import io.fluentlenium.core.proxy.LocatorProxies;import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.proxy.LocatorProxies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;

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
    public HookControlImpl(T self, Object proxy, FluentControl control, ComponentInstantiator instantiator,
            Supplier<T> noHookInstanceSupplier) {
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
    public void setHookRestoreStack(Stack<List<HookDefinition<?>>> hookRestoreStack) {
        this.hookRestoreStack = hookRestoreStack;
    }

    /**
     * Removes hooks from definitions.
     *
     * @param definitions   hook definitions
     * @param hooksToRemove hooks to remove
     */
    public static void removeHooksFromDefinitions(Collection<HookDefinition<?>> definitions,
            Class<? extends FluentHook>... hooksToRemove) {
        Iterator<HookDefinition<?>> hookDefinitionsIterator = definitions.iterator();
        List<Class<? extends FluentHook>> toRemoveHooks = Arrays.asList(hooksToRemove);
        while (hookDefinitionsIterator.hasNext()) {
            HookDefinition<?> next = hookDefinitionsIterator.next();
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
            List<HookDefinition<?>> pop = hookRestoreStack.pop();
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
    public <O, H extends FluentHook<O>> T withHook(Class<H> hook) {
        hookDefinitions.add(new HookDefinition<>(hook));
        backupHookDefinitions();
        applyHooks(proxy, hookChainBuilder, hookDefinitions);
        return self;
    }

    @Override
    public <O, H extends FluentHook<O>> T withHook(Class<H> hook, O options) {
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
    public T noHook(Class<? extends FluentHook>... hooks) {
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
    protected void applyHooks(Object proxy, HookChainBuilder hookChainBuilder, List<HookDefinition<?>> hookDefinitions) {
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
    }

    @Override
    public <R> R noHook(Function<T, R> function) {
        noHook();
        R functionReturn = function.apply(self);
        restoreHooks();
        return functionReturn;
    }

    @Override
    public <R> R noHook(Class<? extends FluentHook> hook, Function<T, R> function) {
        noHook(hook);
        R functionReturn = function.apply(self);
        restoreHooks();
        return functionReturn;
    }

    @Override
    public T noHookInstance() {
        return ((HookControl<T>) noHookInstanceSupplier.get()).noHook();
    }

    @Override
    public T noHookInstance(Class<? extends FluentHook>... hooks) {
        HookControl<T> hookControl = (HookControl<T>) noHookInstanceSupplier.get();

        for (HookDefinition definition : hookDefinitions) {
            hookControl.withHook(definition.getHookClass(), definition.getOptions());
        }

        return hookControl.noHook(hooks);
    }
}
