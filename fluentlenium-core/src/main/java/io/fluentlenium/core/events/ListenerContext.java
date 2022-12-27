package io.fluentlenium.core.events;

import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

import static java.util.Objects.requireNonNull;

/**
 * Context object for storing data related to registering listeners to {@link EventsRegistry}.
 */
class ListenerContext {

    private final Method method;
    private final Object container;
    private final WebElement targetElement;
    private String annotationName;
    private int priority;

    /**
     * Creates a new ListenerContext object.
     *
     * @param method        method to call when the event occurs, never null
     * @param container     container to call when the event occurs, never null
     * @param targetElement the WebElement that is needed for certain listeners, may be null.
     */
    ListenerContext(Method method, Object container, WebElement targetElement) {
        this.method = requireNonNull(method);
        this.container = requireNonNull(container);
        this.targetElement = targetElement;
    }

    Method getMethod() {
        return method;
    }

    Object getContainer() {
        return container;
    }

    WebElement getTargetElement() {
        return targetElement;
    }

    String getAnnotationName() {
        return annotationName;
    }

    void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    int getPriority() {
        return priority;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }
}
