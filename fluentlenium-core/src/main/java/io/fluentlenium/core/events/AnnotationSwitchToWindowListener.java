package io.fluentlenium.core.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import io.fluentlenium.utils.ReflectionUtils;
import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

/**
 * SwitchToWindow annotation listener.
 */
class AnnotationSwitchToWindowListener implements SwitchToWindowListener, ListenerPriority {
    private final Method method;
    private final Object container;
    private final String annotationName;
    private final int priority;

    /**
     * Creates a new SwitchToWindow annotation listener.
     *
     * @param listenerContext the listener context
     */
    AnnotationSwitchToWindowListener(ListenerContext listenerContext) {
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
     * @param s      script
     * @param driver driver
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(String s, WebDriver driver) {
        return input -> {
            if (input.isAssignableFrom(String.class)) {
                return s;
            }
            if (input.isAssignableFrom(WebDriver.class)) {
                return driver;
            }
            return null;
        };
    }

    @Override
    public void on(String s, WebDriver driver) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(s, driver), parameterTypes);

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
