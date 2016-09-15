package org.fluentlenium.configuration;

import org.atteo.classindex.IndexSubclasses;

/**
 * Factory of {@link org.openqa.selenium.Capabilities}
 */
@IndexSubclasses
public interface CapabilitiesFactory extends Factory {
    /**
     * Creates a new instance of {@link CapabilitiesRegistry}.
     *
     * @return new instance of capabilities
     */
    org.openqa.selenium.Capabilities newCapabilities();
}
