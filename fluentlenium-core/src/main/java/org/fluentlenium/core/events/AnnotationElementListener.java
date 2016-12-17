package org.fluentlenium.core.events;

import com.google.common.base.Function;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Element annotation listener
 */
class AnnotationElementListener extends AbstractAnnotationListener implements ElementListener {
    private final Method method;
    private final String annotationName;
    private final WebElement targetElement;

    /**
     * Creates a new element annotation listener
     *
     * @param method         method to call when the event occurs
     * @param container      container to call when the event occurs
     * @param annotationName name of the annotation
     * @param priority       priority of this listener
     * @param targetElement  target element
     */
    AnnotationElementListener(Method method, Object container, String annotationName, int priority, WebElement targetElement) {
        super(container, priority);
        this.method = method;
        this.annotationName = annotationName;
        this.targetElement = targetElement;
    }

    /**
     * Get a function that retrieves argument value based on argument type.
     *
     * @param element element
     * @param driver  driver
     * @return function
     */
    protected Function<Class<?>, Object> getArgsFunction(final FluentWebElement element, final WebDriver driver) {
        return new Function<Class<?>, Object>() {
            @Override
            public Object apply(Class<?> input) {
                if (input.isAssignableFrom(FluentWebElement.class)) {
                    return element;
                }
                if (input.isAssignableFrom(WebDriver.class)) {
                    return driver;
                }
                return null;
            }
        };
    }

    @Override
    public void on(FluentWebElement element, WebDriver driver) {
        if (targetElement != null && (element == null || !targetElement.equals(element.getElement()))) {
            return;
        }

        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(element, driver), parameterTypes);

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
