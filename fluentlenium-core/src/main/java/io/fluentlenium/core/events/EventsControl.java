package io.fluentlenium.core.events;

/**
 * Control interface for events.
 */
public interface EventsControl {
    /**
     * Retrieves an event registry to register event listeners.
     *
     * @return the event registry.
     */
    EventsRegistry events();
}
