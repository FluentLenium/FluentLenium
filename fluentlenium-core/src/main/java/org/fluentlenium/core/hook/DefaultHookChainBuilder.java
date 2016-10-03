package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DefaultHookChainBuilder implements HookChainBuilder {
    private final FluentControl fluentControl;
    private final ComponentInstantiator instantiator;

    public DefaultHookChainBuilder(FluentControl fluentControl, ComponentInstantiator instantiator) {
        this.fluentControl = fluentControl;
        this.instantiator = instantiator;
    }

    @Override
    public List<FluentHook> build(Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locator, Supplier<String> toStringSupplier, List<HookDefinition<?>> hooks) {
        List<FluentHook> chain = new ArrayList<>();

        Supplier<WebElement> currentSupplier = elementSupplier;

        for (HookDefinition<?> hook : hooks) {
            FluentHook<?> newObject;
            try {
                newObject = newInstance(hook.getHookClass(), fluentControl, instantiator, currentSupplier, locator, toStringSupplier, hook.getOptions());
            } catch (NoSuchMethodException e) {
                throw new HookException(e);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new HookException(e);
            }
            final FluentHook<?> hookInstance = newObject;

            currentSupplier = new Supplier<WebElement>() {
                @Override
                public WebElement get() {
                    return hookInstance;
                }
            };

            chain.add(hookInstance);
        }

        return chain;
    }

    protected FluentHook<?> newInstance(Class<? extends FluentHook<?>> hookClass, FluentControl fluentControl, ComponentInstantiator instantiator, Supplier<WebElement> currentSupplier, Supplier<ElementLocator> locator, Supplier<String> toStringSupplier, Object options) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ReflectionUtils.newInstance(hookClass, fluentControl, instantiator, currentSupplier, locator, toStringSupplier, options);
    }
}
