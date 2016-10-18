package org.fluentlenium.core.conditions.wait;

import com.google.common.base.Supplier;
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
    public static FluentListConditions each(final FluentWait wait, final String context,
            final Supplier<? extends List<? extends FluentWebElement>> elementsSupplier) {
        return list(wait, context, new Supplier<FluentListConditions>() {
            @Override
            public FluentListConditions get() {
                return new EachElementConditions(elementsSupplier.get());
            }
        });
    }

    /**
     * Build a wait proxy.
     *
     * @param wait             Fluent wait
     * @param context          Message context
     * @param elementsSupplier Supplier for elements to wait.
     * @return a proxy generating message from annotations.
     */
    public static FluentListConditions one(final FluentWait wait, final String context,
            final Supplier<? extends List<? extends FluentWebElement>> elementsSupplier) {
        return list(wait, context, new Supplier<FluentListConditions>() {
            @Override
            public FluentListConditions get() {
                return new AtLeastOneElementConditions(elementsSupplier.get());
            }
        });
    }

    /**
     * Build a wait proxy.
     *
     * @param wait               Fluent wait
     * @param context            Message context
     * @param conditionsSupplier Supplier for elements to wait.
     * @return a proxy generating message from annotations.
     */
    public static FluentListConditions list(final FluentWait wait, final String context,
            final Supplier<? extends FluentListConditions> conditionsSupplier) {
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
    public static FluentConditions element(final FluentWait wait, final String context,
            final Supplier<? extends FluentWebElement> elementSupplier) {
        return (FluentConditions) Proxy
                .newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {FluentConditions.class},
                    new WaitConditionInvocationHandler(FluentConditions.class, wait, context,
                            new Supplier<FluentConditions>() {
                                @Override
                                public FluentConditions get() {
                                    return new WebElementConditions(elementSupplier.get());
                                }
                            }));
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
    public static <C extends Conditions<?>> C custom(final Class<C> conditionClass, final FluentWait wait, final String context,
            final Supplier<C> conditionsSupplier) {
        return (C) Proxy.newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {conditionClass},
                new WaitConditionInvocationHandler(conditionClass, wait, context, conditionsSupplier));
    }
}
