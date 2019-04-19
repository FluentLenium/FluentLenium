package org.fluentlenium.core.proxy;

import static org.fluentlenium.utils.ReflectionUtils.getMethod;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Abstract proxy handler supporting lazy loading and hooks on {@link WebElement}.
 * <p>
 * This class handles the actual method invocation on the proxy.
 * <p>
 * If you want to create your own custom component handler, this is the class that must be extended.
 *
 * @param <T> type of underlying element or component
 */
public abstract class AbstractLocatorAndInvocationHandler<T> extends AbstractLocatorHandler<T> implements InvocationHandler {
    private static final Method TO_STRING = getMethod(Object.class, "toString");
    private static final Method EQUALS = getMethod(Object.class, "equals", Object.class);
    private static final Method HASH_CODE = getMethod(Object.class, "hashCode");

    private static final int MAX_RETRY = 5;
    private static final int HASH_CODE_SEED = 2048;

    /**
     * Creates a new locator handler.
     *
     * @param locator selenium element locator
     */
    public AbstractLocatorAndInvocationHandler(ElementLocator locator) {
        super(locator);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invocationResult = null;
        if (TO_STRING.equals(method)) {
            invocationResult = proxyToString(loaded() ? (String) invoke(method, args) : null);
        } else if (loaded()) {
            invocationResult = invokeEqualsOnLoadedProxy(method, args, invocationResult);
        } else {
            invocationResult = invokeMethodOnUnloadedProxy(proxy, method, args, invocationResult);
        }

        if (invocationResult == null) {
            getLocatorResult();
            invocationResult = invokeWithRetry(method, args);
        }

        return invocationResult;
    }

    private Object invokeEqualsOnLoadedProxy(Method method, Object[] args, Object invocationResult) {
        Object result = invocationResult;
        if (EQUALS.equals(method)) {
            result = invokeEqualsWhenResultIsPresent(args[0]);
        }
        return result;
    }

    private Object invokeEqualsWhenResultIsPresent(Object arg) {
        Object invocationResult = null;
        LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(arg);
        if (otherLocatorHandler != null && !otherLocatorHandler.loaded()) {
            otherLocatorHandler.now();
            invocationResult = otherLocatorHandler.equals(this);
        }
        return invocationResult;
    }

    private Object invokeMethodOnUnloadedProxy(Object proxy, Method method, Object[] args, Object invocationResult) {
        Object result = invocationResult;
        if (EQUALS.equals(method)) {
            result = invokeEqualsWhenResultIsAbsent(proxy, args);
        } else if (HASH_CODE.equals(method)) {
            result = HASH_CODE_SEED + locator.hashCode();
        }
        return result;
    }

    private Object invokeEqualsWhenResultIsAbsent(Object proxy, Object[] args) {
        Object invocationResult = null;
        LocatorHandler otherLocatorHandler = LocatorProxies.getLocatorHandler(args[0]);
        if (otherLocatorHandler != null) {
            if (!otherLocatorHandler.loaded() || args[0] == null) {
                invocationResult = equals(otherLocatorHandler);
            } else {
                invocationResult = args[0].equals(proxy);
            }
        }
        return invocationResult;
    }

    //CHECKSTYLE.OFF: IllegalThrows
    private Object invokeWithRetry(Method method, Object[] args) throws Throwable {
        Throwable lastThrowable = null;
        for (int i = 0; i < MAX_RETRY; i++) {
            try {
                return invoke(method, args);
            } catch (StaleElementReferenceException e) {
                lastThrowable = e;
                reset();
                getLocatorResult(); // Reload the stale element
            }
        }

        throw lastThrowable;
    }
    //CHECKSTYLE.ON: IllegalThrows

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(getInvocationTarget(method), args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractLocatorHandler<?> that = (AbstractLocatorHandler<?>) obj;
        return Objects.equals(locator, that.locator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locator);
    }

    @Override
    public String toString() {
        return proxyToString(null);
    }
}
