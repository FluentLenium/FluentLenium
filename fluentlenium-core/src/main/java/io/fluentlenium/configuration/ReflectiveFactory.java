package io.fluentlenium.configuration;

/**
 * Reflective factory.
 */
@IndexIgnore
public interface ReflectiveFactory extends Factory {
    /**
     * Check if the class instantiated by this factory is available.
     *
     * @return true if the class is available.
     */
    boolean isAvailable();
}
