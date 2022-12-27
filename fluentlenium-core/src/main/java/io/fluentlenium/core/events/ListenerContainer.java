package io.fluentlenium.core.events;

/**
 * Listener attached to a container.
 */
public interface ListenerContainer {
    /**
     * Get the underlying container of this listener.
     *
     * @return container object
     */
    Object getContainer();
}
