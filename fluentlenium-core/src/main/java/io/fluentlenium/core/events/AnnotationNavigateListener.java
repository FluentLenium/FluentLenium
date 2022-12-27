package io.fluentlenium.core.events;

import io.fluentlenium.utils.ReflectionUtils;
import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Navigate annotation listener
 */
class AnnotationNavigateListener extends AbstractAnnotationListener implements NavigateListener {
    private final Method method;
    private final String annotationName;

    /**
     * Creates a new navigate annotation listener
     *
     * @param listenerContext the listener context
     */
    AnnotationNavigateListener(ListenerContext listenerContext) {
        super(listenerContext.getContainer(), listenerContext.getPriority());
        this.method = listenerContext.getMethod();
        this.annotationName = listenerContext.getAnnotationName();
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
