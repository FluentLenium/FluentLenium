package io.fluentlenium.core.hook;

import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.utils.ReflectionUtils;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Builder of hook chains from element supplier, element locator supplier and hook definitions list.
 */
public class DefaultHookChainBuilder implements HookChainBuilder {
    private final FluentControl control;
    private final ComponentInstantiator instantiator;

    /**
     * Creates a new default hook chain builder
     *
     * @param control      control interface
     * @param instantiator component instantiator
     */
    public DefaultHookChainBuilder(FluentControl control, ComponentInstantiator instantiator) {
        this.control = control;
        this.instantiator = instantiator;
    }

    @Override
    public List<FluentHook> build(Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locator,
            Supplier<String> toStringSupplier, List<HookDefinition<?>> hooks) {
        List<FluentHook> chain = new ArrayList<>();

        Supplier<WebElement> currentSupplier = elementSupplier;

        for (HookDefinition<?> hook : hooks) {
            FluentHook<?> newObject;
            try {
                newObject = newInstance(hook.getHookClass(), control, instantiator, currentSupplier, locator, toStringSupplier,
                        hook.getOptions());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new HookException(e);
            }
            FluentHook<?> hookInstance = newObject;

            currentSupplier = () -> hookInstance;

            chain.add(hookInstance);
        }

        return chain;
    }

    /**
     * Creates a new hook instance.
     *
     * @param hookClass        hook class
     * @param fluentControl    control interface
     * @param instantiator     component instantiator
     * @param elementSupplier  element supplier
     * @param locatorSupplier  element locator supplier
     * @param toStringSupplier element toString supplier
     * @param options          hook options
     * @return new hook instance
     * @throws NoSuchMethodException     if a matching method is not found.
     * @throws IllegalAccessException    if this {@code Constructor} object
     *                                   is enforcing Java language access control and the underlying
     *                                   constructor is inaccessible.
     * @throws InstantiationException    if the class that declares the
     *                                   underlying constructor represents an abstract class.
     * @throws InvocationTargetException if the underlying constructor
     *                                   throws an exception.
     */
    protected FluentHook<?> newInstance(Class<? extends FluentHook<?>> hookClass, FluentControl fluentControl,
            ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier,
            Supplier<String> toStringSupplier, Object options)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ReflectionUtils
                .newInstance(hookClass, fluentControl, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
    }
}
