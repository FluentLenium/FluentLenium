package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;

import java.util.Map;

public class TestCapabilities implements Capabilities {
    @Override
    public String getBrowserName() {
        return null;
    }

    @Override
    public Platform getPlatform() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public boolean isJavascriptEnabled() {
        return false;
    }

    @Override
    public Map<String, ?> asMap() {
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
