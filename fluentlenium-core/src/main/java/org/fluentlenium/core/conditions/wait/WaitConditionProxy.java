package org.fluentlenium.core.conditions.wait;

import org.fluentlenium.core.conditions.AtLeastOneElementConditions;
import org.fluentlenium.core.conditions.Conditions;
import org.fluentlenium.core.conditions.EachElementConditions;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.wait.FluentWait;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.function.Supplier;

/**
 * Provides proxy implementations of conditions that performs wait from those conditions.
 */
public final class WaitConditionProxy {
    private WaitConditionProxy() {
        //Utility class
    }

    /**
     * Build a wait proxy.
     *
     * @param wait             Fluent wait
     * @param context          Message context
     * @param elementsSupplier Supplier for elements to wait.
     * @return a proxy generating message from annotations.
     */
    public static FluentListConditions each(FluentWait wait, String context,
            Supplier<? extends List<? extends FluentWebElement>> elementsSupplier) {
        return list(wait, context, () -> new EachElementConditions(elementsSupplier.get()));
    }

    /**
     * Build a wait proxy.
     *
     * @param wait             Fluent wait
     * @param context          Message context
     * @param elementsSupplier Supplier for elements to wait.
     * @return a proxy generating message from annotations.
     */
    public static FluentListConditions one(FluentWait wait, String context,
            Supplier<? extends List<? extends FluentWebElement>> elementsSupplier) {
        return list(wait, context, () -> new AtLeastOneElementConditions(elementsSupplier.get()));
    }

    /**
     * Build a wait proxy.
     *
     * @param wait               Fluent wait
     * @param context            Message context
     * @param conditionsSupplier Supplier for elements to wait.
     * @return a proxy generating message from annotations.
     */
    public static FluentListConditions list(FluentWait wait, String context,
            Supplier<? extends FluentListConditions> conditionsSupplier) {
        return (FluentListConditions) Proxy
                .newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {FluentListConditions.class},
                        new WaitConditionInvocationHandler(FluentListConditions.class, wait, context, conditionsSupplier));
    }

    /**
     * Build a wait proxy.
     *
     * @param wait            Fluent wait
     * @param context         Message context
     * @param elementSupplier Supplier for element to wait.
     * @return a proxy generating message from annotations.
     */
    public static FluentConditions element(FluentWait wait, String context,
            Supplier<? extends FluentWebElement> elementSupplier) {
        return (FluentConditions) Proxy
                .newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {FluentConditions.class},
                        new WaitConditionInvocationHandler(FluentConditions.class, wait, context,
                                () -> new WebElementConditions(elementSupplier.get())));
    }

    /**
     * Build a wait proxy.
     *
     * @param conditionClass     condition class
     * @param wait               Fluent wait
     * @param context            Message context
     * @param conditionsSupplier Supplier for elements to wait.
     * @param <C>                condition type
     * @return a proxy generating message from annotations.
     */
    public static <C extends Conditions<?>> C custom(Class<C> conditionClass, FluentWait wait, String context,
            Supplier<C> conditionsSupplier) {
        return (C) Proxy.newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {conditionClass},
                new WaitConditionInvocationHandler(conditionClass, wait, context, conditionsSupplier));
    }
}
