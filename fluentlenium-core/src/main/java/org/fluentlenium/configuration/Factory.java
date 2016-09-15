package org.fluentlenium.configuration;

public interface Factory {
    /**
     * Primary name of this factory.
     * <p>
     * To register it with alternative name, use {@link AlternativeNames}.
     *
     * @return Primary name
     */
    String getName();

    /**
     * Priority of the factory to be grabbed as default Factory.
     *
     * @return a priority index
     */
    int getPriority();
}
