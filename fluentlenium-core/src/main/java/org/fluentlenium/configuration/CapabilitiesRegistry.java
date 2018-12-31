package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

/**
 * A registry of {@link CapabilitiesFactory}.
 */
public enum CapabilitiesRegistry {
    /**
     * Singleton.
     */
    INSTANCE;

    private final CapabilitiesRegistryImpl impl = new CapabilitiesRegistryImpl();

    public CapabilitiesRegistryImpl getImpl() {
        return impl;
    }

    public void register(CapabilitiesFactory factory) {
        getImpl().register(factory);
    }

    public Capabilities newCapabilities(String name, ConfigurationProperties configuration) {
        return getImpl().newCapabilities(name, configuration);
    }

    public CapabilitiesFactory getDefault() {
        return getImpl().getDefault();
    }

    public CapabilitiesFactory get(String name) {
        return getImpl().get(name);
    }
}
