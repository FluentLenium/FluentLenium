package io.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

import java.util.Map;

public class TestCapabilities implements Capabilities {
    @Override
    public String getBrowserName() {
        return null;
    }

    @Override
    public Map<String, Object> asMap() {
        return null;
    }

    @Override
    public Object getCapability(String capabilityName) {
        return null;
    }

    @Override
    public boolean is(String capabilityName) {
        return false;
    }
}
