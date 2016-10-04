package org.fluentlenium.core.events;

import com.google.common.base.Function;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class AnnotationFindByListener extends AbstractAnnotationListener implements FindByListener {
    private final Method method;
    private final String annotationName;

    AnnotationFindByListener(Method method, Object container, String annotationName, int priority) {
        super(container, priority);
        this.method = method;
        this.annotationName = annotationName;
    }

    protected Function<Class<?>, Object> getArgsFunction(final By by, final FluentWebElement element, final WebDriver driver) {
        return new Function<Class<?>, Object>() {
            @Override
            public Object apply(Class<?> input) {
                if (input.isAssignableFrom(FluentWebElement.class)) {
                    return element;
                }
                if (input.isAssignableFrom(By.class)) {
                    return by;
                }
                if (input.isAssignableFrom(WebDriver.class)) {
                    return driver;
                }
                return null;
            }
        };
    }

    @Override
    public void on(By by, FluentWebElement element, WebDriver driver) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(by, element, driver), parameterTypes);

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
