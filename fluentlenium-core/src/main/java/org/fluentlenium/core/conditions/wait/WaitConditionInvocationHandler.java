package org.fluentlenium.core.conditions.wait;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.conditions.Conditions;
import org.fluentlenium.core.conditions.Negation;
import org.fluentlenium.core.conditions.NoSuchElementValue;
import org.fluentlenium.core.conditions.message.MessageContext;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WaitConditionInvocationHandler<C extends Conditions<?>> implements InvocationHandler {

    private final Class<C> conditionClass;
    private final Supplier<C> conditionSupplier;
    private final FluentWait wait;
    private String context;
    private boolean negation;

    public WaitConditionInvocationHandler(Class<C> conditionClass, FluentWait wait, String context,
            Supplier<C> conditionSupplier) {
        this.conditionClass = conditionClass;
        this.wait = wait;
        this.context = context;
        this.conditionSupplier = conditionSupplier;
    }

    /**
     * Get the underlying conditions of wait matcher.
     *
     * @return underlying conditions.
     */
    protected C conditions() {
        return conditions(false);
    }

    /**
     * Get the underlying conditions of wait matcher.
     *
     * @param ignoreNot true if the negation should be ignored.
     * @return underlying conditions.
     */
    protected C conditions(boolean ignoreNot) {
        C conditions = conditionSupplier.get();
        return applyNegation(conditions, ignoreNot);
    }

    /**
     * Apply the current negation to the given condition
     *
     * @param conditions     conditions.
     * @param ignoreNegation true if the negation should be ignored.
     * @return conditions with the negation applied.
     */
    protected C applyNegation(C conditions, boolean ignoreNegation) {
        if (!ignoreNegation && negation) {
            return (C) conditions.not();
        }
        return conditions;
    }

    /**
     * Builds a message builder proxy.
     *
     * @return message builder proxy
     */
    protected C messageBuilder() {
        return messageBuilder(false);
    }

    /**
     * Builds a message builder proxy.
     *
     * @param ignoreNegation true if the negation should be ignored.
     * @return message builder proxy
     */
    protected C messageBuilder(boolean ignoreNegation) {
        C conditions = MessageProxy.builder(conditionClass, context);
        conditions = applyNegation(conditions, ignoreNegation);
        return conditions;
    }

    /**
     * Build the final message from default message.
     *
     * @return final message
     */
    protected Function<String, String> messageCustomizer() {
        return Functions.identity();
    }

    /**
     * Perform the wait.
     *
     * @param present predicate to wait for.
     * @param message message to use.
     */
    protected void until(Predicate<FluentControl> present, String message) {
        if (wait.useCustomMessage()) {
            wait.untilPredicate(present);
        } else {
            message = messageCustomizer().apply(message);
            wait.withMessage(message).untilPredicate(present);
        }
    }

    /**
     * Perform the wait.
     *
     * @param present         predicate to wait for.
     * @param messageSupplier default message to use.
     */
    protected void until(Predicate<FluentControl> present, final Supplier<String> messageSupplier) {
        if (wait.useCustomMessage()) {
            wait.untilPredicate(present);
        } else {
            Supplier<String> customMessageSupplier = new Supplier<String>() {
                @Override
                public String get() {
                    return messageCustomizer().apply(messageSupplier.get());
                }
            };
            wait.withMessage(customMessageSupplier).untilPredicate(present);
        }
    }

    /**
     * Perform the wait.
     *
     * @param condition         condition object to wait for
     * @param messageBuilder    message builder matching the condition object
     * @param conditionFunction condition fonction
     */
    protected void until(final C condition, final C messageBuilder, final Function<C, Boolean> conditionFunction) {
        Predicate<FluentControl> predicate = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditionFunction.apply(condition);
            }
        };
        Supplier<String> messageSupplier = new Supplier<String>() {
            @Override
            public String get() {
                conditionFunction.apply(messageBuilder);
                return MessageProxy.message(messageBuilder);
            }
        };

        until(predicate, messageSupplier);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();

        if (method.isAnnotationPresent(Negation.class)) {
            Conditions<?> negationProxy = WaitConditionProxy.custom(conditionClass, wait, context, conditionSupplier);
            WaitConditionInvocationHandler negationHandler = (WaitConditionInvocationHandler) Proxy
                    .getInvocationHandler(negationProxy);
            negationHandler.negation = !negation;
            return negationProxy;
        }

        if (method.isAnnotationPresent(MessageContext.class)) {
            context = context + " " + method.getAnnotation(MessageContext.class).value();
        }

        if (boolean.class.equals(returnType) || Boolean.class.equals(returnType)) {
            until(conditions(), messageBuilder(), new Function<C, Boolean>() {
                @Override
                public Boolean apply(C input) {
                    try {
                        return (Boolean) method.invoke(input, args);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("An internal error has occured while waiting", e);
                    } catch (InvocationTargetException e) {
                        Throwable targetException = e.getTargetException();
                        if (targetException instanceof TimeoutException || targetException instanceof NoSuchElementException
                                || targetException instanceof StaleElementReferenceException) {
                            NoSuchElementValue annotation = method.getAnnotation(NoSuchElementValue.class);
                            return annotation == null ? false : annotation.value();
                        }
                        throw new IllegalStateException("An internal error has occured while waiting", e);
                    }
                }
            });
            return true;
        } else if (Conditions.class.isAssignableFrom(returnType)) {
            Method conditionGetter = conditions().getClass().getMethod(method.getName(), method.getParameterTypes());
            Conditions<?> childConditions = (Conditions<?>) conditionGetter.invoke(conditions(true), args);

            Conditions<?> childProxy = WaitConditionProxy.custom((Class<Conditions<?>>) method.getReturnType(), wait, context,
                    Suppliers.<Conditions<?>>ofInstance(childConditions));
            WaitConditionInvocationHandler childHandler = (WaitConditionInvocationHandler) Proxy.getInvocationHandler(childProxy);
            childHandler.negation = negation;
            return childProxy;
        } else {
            throw new IllegalStateException("An internal error has occured.");
        }
    }
}
