package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

public class TestCapabilitiesFactory implements CapabilitiesFactory {
    @Override
    public String getName() {
        return "test-capabilities-factory";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public Capabilities newCapabilities() {
        return new TestCapabilities();
    }
}
