package io.fluentlenium.core.events;

import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * NavigateAll annotation listener
 */
class AnnotationNavigateAllListener extends AbstractAnnotationListener implements NavigateAllListener {
    private final Method method;
    private final String annotationName;

    /**
     * Creates a new navigate all annotation listener
     *
     * @param listenerContext the listener context
     */
    AnnotationNavigateAllListener(ListenerContext listenerContext) {
        super(listenerContext.getContainer(), listenerContext.getPriority());
        this.method = listenerContext.getMethod();
        this.annotationName = listenerContext.getAnnotationName();
    }

    /**
     * Get a function that retrieves argument value based on argument class.
     *
     * @param url       url
     * @param driver    driver
     * @param direction direction of the navigation
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(String url, WebDriver driver, Direction direction) {
        return input -> {
            if (input.isAssignableFrom(String.class)) {
                return url;
            }
            if (input.isAssignableFrom(WebDriver.class)) {
                return driver;
            }
            if (input.isAssignableFrom(Direction.class)) {
                return direction;
            }
            return null;
        };
    }

    @Override
    public void on(String url, WebDriver driver, Direction direction) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(url, driver, direction), parameterTypes);

        try {
            ReflectionUtils.invoke(method, getContainer(), args);
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
