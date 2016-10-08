package org.fluentlenium.core.events;

/**
 * Support for priority between listeners.
 */
public interface ListenerPriority {
    /**
     * Get the priority of this listener.
     *
     * @return listener priority
     */
    int getPriority();
}
