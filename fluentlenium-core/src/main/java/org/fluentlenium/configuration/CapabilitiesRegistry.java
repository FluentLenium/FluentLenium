package org.fluentlenium.configuration;

import lombok.experimental.Delegate;

/**
 * A registry of {@link CapabilitiesFactory}.
 */
public enum CapabilitiesRegistry {
    /**
     * Singleton.
     */
    INSTANCE;

    @Delegate
    private final CapabilitiesRegistryImpl impl = new CapabilitiesRegistryImpl();
}
