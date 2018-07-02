package org.fluentlenium.core.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.OutputType;

/**
 * GetScreenshotAs annotation listener.
 */
class AnnotationGetScreenshotAsListener implements GetScreenshotAsListener, ListenerPriority {
    private final Method method;
    private final Object container;
    private final String annotationName;
    private final int priority;

    /**
     * Creates a new GetScreenshotAs annotation listener.
     *
     * @param method         method
     * @param container      container
     * @param annotationName annotation name
     * @param priority       listener priority
     */
    AnnotationGetScreenshotAsListener(Method method, Object container, String annotationName, int priority) {
        this.method = method;
        this.container = container;
        this.annotationName = annotationName;
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    /**
     * Get a function that retrieves argument value based on argument class.
     *
     * @param outputType output type
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(OutputType outputType) {
        return input -> {
            if (input.isAssignableFrom(OutputType.class)) {
                return outputType;
            }
            return null;
        };
    }

    /**
     * Get a function that retrieves argument value based on argument class.
     *
     * @param outputType output type
     * @param o object
     * @return function returning argument value from argument class
     */
    protected Function<Class<?>, Object> getArgsFunction(OutputType outputType, Object o) {
        return input -> {
            if (input.isAssignableFrom(OutputType.class)) {
                return outputType;
            }
            if (input.isAssignableFrom(Object.class)) {
                return o;
            }
            return null;
        };
    }

    @Override
    public void on(OutputType outputType) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(outputType), parameterTypes);

        invokeMethodByReflection(args);
    }

    @Override
    public void on(OutputType outputType, Object o) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(getArgsFunction(outputType, o), parameterTypes);

        invokeMethodByReflection(args);
    }

    private void invokeMethodByReflection(Object[] args) {
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
