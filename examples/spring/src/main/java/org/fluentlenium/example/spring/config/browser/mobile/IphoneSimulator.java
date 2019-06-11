package org.fluentlenium.example.spring.config.browser.mobile;

import io.appium.java_client.remote.MobileCapabilityType;
import org.fluentlenium.example.spring.config.ConfigException;
import org.fluentlenium.example.spring.config.browser.IBrowser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IphoneSimulator implements IBrowser {

    @Override
    public Capabilities getBrowserCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
        return capabilities;
    }

    @Override
    public String getDriverExecutableName() {
        throw new ConfigException("Not supported on iOS");
    }

    @Override
    public String getDriverSystemPropertyName() {
        throw new ConfigException("Not supported on iOS");
    }
}
