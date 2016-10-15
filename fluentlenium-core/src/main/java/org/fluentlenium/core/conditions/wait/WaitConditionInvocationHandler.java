package org.fluentlenium.core.conditions.wait;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.conditions.Conditions;
import org.fluentlenium.core.conditions.ConditionsObject;
import org.fluentlenium.core.conditions.Negation;
import org.fluentlenium.core.conditions.message.MessageContext;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.internal.WrapsElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Invocation handler used to wait for a particular conditions call.
 *
 * @param <C> type of conditions
 */
public class WaitConditionInvocationHandler<C extends Conditions<?>> implements InvocationHandler {

    private final Class<C> conditionClass;
    private final Supplier<C> conditionSupplier;
    private final FluentWait wait;
    private String context;
    private boolean negation;

    /**
     * Creates a new wait condition invocation handler.
     *
     * @param conditionClass    condition class
     * @param wait              fluent wait
     * @param context           base context of generated message if condition is not verified
     * @param conditionSupplier supplier of conditions
     */
    public WaitConditionInvocationHandler(final Class<C> conditionClass, final FluentWait wait, final String context,
            final Supplier<C> conditionSupplier) {
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
    protected C conditions(final boolean ignoreNot) {
        final C conditions = conditionSupplier.get();
        return applyNegation(conditions, ignoreNot);
    }

    /**
     * Apply the current negation to the given condition
     *
     * @param conditions     conditions.
     * @param ignoreNegation true if the negation should be ignored.
     * @return conditions with the negation applied.
     */
    protected C applyNegation(final C conditions, final boolean ignoreNegation) {
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
    protected C messageBuilder(final boolean ignoreNegation) {
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
    protected void until(final Predicate<FluentControl> present, String message) {
        if (wait.hasMessageDefined()) {
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
    protected void until(final Predicate<FluentControl> present, final Supplier<String> messageSupplier) {
        if (wait.hasMessageDefined()) {
            wait.untilPredicate(present);
        } else {
            final Supplier<String> customMessageSupplier = new Supplier<String>() {
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
        final Predicate<FluentControl> predicate = new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl input) {
                return conditionFunction.apply(condition);
            }
        };
        final Supplier<String> messageSupplier = new Supplier<String>() {
            @Override
            public String get() {
                conditionFunction.apply(messageBuilder);
                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(MessageProxy.message(messageBuilder));

                if (condition instanceof ConditionsObject) {
                    final Object actualObject = ((ConditionsObject) condition).getActualObject();

                    if (!(actualObject instanceof WrapsElement)) {
                        stringBuilder.append(" (actual: ");
                        stringBuilder.append(actualObject);
                        stringBuilder.append(')');
                    }
                }

                return stringBuilder.toString();
            }
        };

        until(predicate, messageSupplier);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Negation.class)) {
            return buildNegationProxy();
        }

        if (method.isAnnotationPresent(MessageContext.class)) {
            context = context + " " + method.getAnnotation(MessageContext.class).value();
        }

        final Class<?> returnType = method.getReturnType();
        if (boolean.class.equals(returnType) || Boolean.class.equals(returnType)) {
            return waitForCondition(method, args);
        } else if (Conditions.class.isAssignableFrom(returnType)) {
            return buildChildProxy(method, args);
        } else {
            throw new IllegalStateException("An internal error has occured.");
        }
    }

    private Object buildChildProxy(final Method method, final Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method conditionGetter = conditions().getClass().getMethod(method.getName(), method.getParameterTypes());
        final Conditions<?> childConditions = (Conditions<?>) conditionGetter.invoke(conditions(true), args);

        final Conditions<?> childProxy = WaitConditionProxy.custom((Class<Conditions<?>>) method.getReturnType(), wait, context,
                Suppliers.<Conditions<?>>ofInstance(childConditions));
        final WaitConditionInvocationHandler childHandler = (WaitConditionInvocationHandler) Proxy
                .getInvocationHandler(childProxy);
        childHandler.negation = negation;
        return childProxy;
    }

    private boolean waitForCondition(final Method method, final Object[] args) {
        final C messageBuilder = messageBuilder();
        until(conditions(), messageBuilder, new Function<C, Boolean>() {
            @Override
            public Boolean apply(final C input) {
                try {
                    return (Boolean) method.invoke(input, args);
                } catch (final IllegalAccessException e) {
                    throw new IllegalStateException("An internal error has occured while waiting", e);
                } catch (final InvocationTargetException e) {
                    final Throwable targetException = e.getTargetException();
                    if (targetException instanceof RuntimeException) {
                        throw (RuntimeException) targetException;
                    }
                    throw new IllegalStateException("An internal error has occured while waiting", e);
                }
            }
        });
        return true;
    }

    private Conditions<?> buildNegationProxy() {
        final Conditions<?> negationProxy = WaitConditionProxy.custom(conditionClass, wait, context, conditionSupplier);
        final WaitConditionInvocationHandler negationHandler = (WaitConditionInvocationHandler) Proxy
                .getInvocationHandler(negationProxy);
        negationHandler.negation = !negation;
        return negationProxy;
    }
}
