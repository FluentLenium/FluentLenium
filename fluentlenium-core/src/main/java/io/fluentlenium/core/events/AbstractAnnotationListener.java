package io.fluentlenium.core.events;

/**
 * Abstract class for annotation listeners.
 */
public abstract class AbstractAnnotationListener implements ListenerPriority, ListenerContainer {
    private final Object container;
    private final int priority;

    /**
     * Creates a new annotation listener.
     *
     * @param container container where annotation has been found
     * @param priority  priority of this listener
     */
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
