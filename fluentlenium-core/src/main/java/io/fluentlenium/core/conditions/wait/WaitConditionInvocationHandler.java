package io.fluentlenium.core.conditions.wait;

import io.fluentlenium.core.conditions.message.MessageContext;import io.fluentlenium.core.conditions.message.MessageProxy;import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.conditions.Conditions;
import io.fluentlenium.core.conditions.ConditionsObject;
import io.fluentlenium.core.conditions.Negation;
import io.fluentlenium.core.conditions.message.MessageContext;
import io.fluentlenium.core.conditions.message.MessageProxy;
import io.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.WrapsElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        return Function.identity();
    }

    /**
     * Perform the wait.
     *
     * @param present predicate to wait for.
     * @param message message to use.
     */
    protected void until(Predicate<FluentControl> present, String message) {
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
    protected void until(Predicate<FluentControl> present, Supplier<String> messageSupplier) {
        if (wait.hasMessageDefined()) {
            wait.untilPredicate(present);
        } else {
            Supplier<String> customMessageSupplier = () -> messageCustomizer().apply(messageSupplier.get());
            wait.withMessage(customMessageSupplier).untilPredicate(present);
        }
    }

    /**
     * Perform the wait.
     *
     * @param condition         condition object to wait for
     * @param messageBuilder    message builder matching the condition object
     * @param conditionFunction condition function
     */
    protected void until(C condition, C messageBuilder, Function<C, Boolean> conditionFunction) {
        Predicate<FluentControl> predicate = input -> conditionFunction.apply(condition);
        Supplier<String> messageSupplier = () -> {
            conditionFunction.apply(messageBuilder);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(MessageProxy.message(messageBuilder));

            if (condition instanceof ConditionsObject) {
                Object actualObject = ((ConditionsObject) condition).getActualObject();

                if (!(actualObject instanceof WrapsElement)) {
                    stringBuilder.append(" (actual: ").append(actualObject).append(')');
                }
            }

            return stringBuilder.toString();
        };

        until(predicate, messageSupplier);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Negation.class)) {
            return buildNegationProxy();
        }

        if (method.isAnnotationPresent(MessageContext.class)) {
            context = context + " " + method.getAnnotation(MessageContext.class).value();
        }

        Class<?> returnType = method.getReturnType();
        if (boolean.class.equals(returnType) || Boolean.class.equals(returnType)) {
            return waitForCondition(method, args);
        } else if (Conditions.class.isAssignableFrom(returnType)) {
            return buildChildProxy(method, args);
        } else {
            throw new IllegalStateException("An internal error has occurred.");
        }
    }

    private Object buildChildProxy(Method method, Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method conditionGetter = conditions().getClass().getMethod(method.getName(), method.getParameterTypes());
        Conditions<?> childConditions = (Conditions<?>) conditionGetter.invoke(conditions(true), args);

        Conditions<?> childProxy = WaitConditionProxy
                .custom((Class<Conditions<?>>) method.getReturnType(), wait, context, () -> childConditions);
        WaitConditionInvocationHandler childHandler = (WaitConditionInvocationHandler) Proxy.getInvocationHandler(childProxy);
        childHandler.negation = negation;
        return childProxy;
    }

    private boolean waitForCondition(Method method, Object[] args) {
        C messageBuilder = messageBuilder();
        until(conditions(), messageBuilder, input -> {
            try {
                return (Boolean) method.invoke(input, args);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("An internal error has occured while waiting", e);
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getTargetException();
                if (targetException instanceof RuntimeException) {
                    throw (RuntimeException) targetException;
                }
                throw new IllegalStateException("An internal error has occured while waiting", e);
            }
        });
        return true;
    }

    private Conditions<?> buildNegationProxy() {
        Conditions<?> negationProxy = WaitConditionProxy.custom(conditionClass, wait, context, conditionSupplier);
        WaitConditionInvocationHandler negationHandler = (WaitConditionInvocationHandler) Proxy
                .getInvocationHandler(negationProxy);
        negationHandler.negation = !negation;
        return negationProxy;
    }
}
