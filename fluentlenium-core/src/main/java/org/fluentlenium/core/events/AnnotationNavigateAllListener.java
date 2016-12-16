package org.fluentlenium.core.events;

import com.google.common.base.Function;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * NavigateAll annotation listener
 */
class AnnotationNavigateAllListener extends AbstractAnnotationListener implements NavigateAllListener {
    private final Method method;
    private final String annotationName;

    /**
     * Creates a new navigate all annotation listener
     *
     * @param method         method to call when the event occurs
     * @param container      container to call when the event occurs
     * @param annotationName name of the annotation
     * @param priority       priority of this listener
     */
    AnnotationNavigateAllListener(Method method, Object container, String annotationName, int priority) {
        super(container, priority);
        this.method = method;
        this.annotationName = annotationName;
    }

    /**
     * Get a function that retrieves argument value based on argument class.
     *
     * @param url       url
     * @param driver    driver
     * @param direction direction of the navigation
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(final String url, final WebDriver driver, final Direction direction) {
        return new Function<Class<?>, Object>() {
            @Override
            public Object apply(Class<?> input) {
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
            }
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
