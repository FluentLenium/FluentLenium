package org.fluentlenium.core.events;

public class AbstractAnnotationListener implements ListenerPriority, ListenerContainer {
    private Object container;
    private int priority;

    public AbstractAnnotationListener(Object container, int priority) {
        this.container = container;
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public Object getContainer() {
        return container;
    }
}
