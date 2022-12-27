package io.fluentlenium.core.events;

import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Alert annotation listener.
 */
class AnnotationAlertListener implements AlertListener, ListenerPriority {
    private final Method method;
    private final Object container;
    private final String annotationName;
    private final int priority;

    /**
     * Creates a new alert annotation listener.
     *
     * @param listenerContext the listener context
     */
    AnnotationAlertListener(ListenerContext listenerContext) {
        this.method = listenerContext.getMethod();
        this.container = listenerContext.getContainer();
        this.annotationName = listenerContext.getAnnotationName();
        this.priority = listenerContext.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    /**
     * Get a function that retrieves argument value based on argument class.
     *
     * @param driver driver
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(WebDriver driver) {
        return input -> {
            if (input.isAssignableFrom(WebDriver.class)) {
                return driver;
            }
            return null;
        };
    }

    @Override
    public void on(WebDriver driver) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(driver), parameterTypes);

        try {
            ReflectionUtils.invoke(method, container, args);
        } catch (IllegalAccessException e) {
            throw new EventAnnotationsException("An error has occured in " + annotationName + " " + method, e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) e.getTargetException();
            } else if (e.getTargetException() instanceof Error) {
                throw (Error) e.getTargetException();
            }
            throw new EventAnnotationsException("An error has occured in " + annotationName + " " + method, e);
        }
    }
}
