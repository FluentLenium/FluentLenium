package io.fluentlenium.configuration;

/**
 * Add names to a factory.
 * <p>
 * {@link Factory} implementations can implement this interface to be registered
 * in registry with those names.
 */
public interface FactoryNames {
    /**
     * Get the factory names.
     *
     * @return array of factory names
     */
    String[] getNames();
}
