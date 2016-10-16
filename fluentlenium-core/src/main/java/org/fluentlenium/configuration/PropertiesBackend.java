package org.fluentlenium.configuration;

/**
 * Reader interface for raw properties access.
 */
public interface PropertiesBackend {
    /**
     * Get the property value.
     *
     * @param propertyName property name
     * @return property value
     */
    String getProperty(String propertyName);
}
