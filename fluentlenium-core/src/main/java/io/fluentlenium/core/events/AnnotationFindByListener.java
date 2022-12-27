package io.fluentlenium.core.events;

import io.fluentlenium.utils.ReflectionUtils;import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * FindBy annotation listener
 */
class AnnotationFindByListener extends AbstractAnnotationListener implements FindByListener {
    private final Method method;
    private final String annotationName;
    private final WebElement targetElement;

    /**
     * Creates a new find by annotation listener
     *
     * @param listenerContext the listener context
     */
    AnnotationFindByListener(ListenerContext listenerContext) {
        super(listenerContext.getContainer(), listenerContext.getPriority());
        this.method = listenerContext.getMethod();
        this.annotationName = listenerContext.getAnnotationName();
        this.targetElement = listenerContext.getTargetElement();
    }

    /**
     * Get a function that retrieves argument value based on argument class.
     *
     * @param by      locator
     * @param element element
     * @param driver  driver
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(By by, FluentWebElement element, WebDriver driver) {
        return input -> {
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
        };
    }

    @Override
    public void on(By by, FluentWebElement element, WebDriver driver) {
        if (targetElement != null && (element == null || !targetElement.equals(element.getElement()))) {
            return;
        }

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
