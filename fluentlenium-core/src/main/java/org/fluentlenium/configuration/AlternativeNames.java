package org.fluentlenium.configuration;

/**
 * Add alternative names to an object.
 * <p>
 * {@link WebDriverFactory} implementations can also implement this interface to be registered in {@link WebDrivers}
 * registry with those alternative names.
 */
public interface AlternativeNames {
    /**
     * Get the alternative names.
     *
     * @return array of alternative names
     */
    String[] getAlternativeNames();
}
