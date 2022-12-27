package io.fluentlenium.core.events;

import io.fluentlenium.utils.ReflectionUtils;
import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * NavigateTo annotation listener
 */
class AnnotationNavigateToListener extends AbstractAnnotationListener implements NavigateToListener {
    private final Method method;
    private final String annotationName;

    /**
     * Creates a new navigate to annotation listener
     *
     * @param listenerContext the listener context
     */
    AnnotationNavigateToListener(ListenerContext listenerContext) {
        super(listenerContext.getContainer(), listenerContext.getPriority());
        this.method = listenerContext.getMethod();
        this.annotationName = listenerContext.getAnnotationName();
    }

    /**
     * Get a function that retrieves argument value based on argument class.
     *
     * @param url    url
     * @param driver driver
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(String url, WebDriver driver) {
        return input -> {
            if (input.isAssignableFrom(String.class)) {
                return url;
            }
            if (input.isAssignableFrom(WebDriver.class)) {
                return driver;
            }
            return null;
        };
    }

    @Override
    public void on(String url, WebDriver driver) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(url, driver), parameterTypes);

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
