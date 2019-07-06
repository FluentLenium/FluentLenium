package org.fluentlenium.example.spring.config.browser;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Represents Android simulator running locally via Android Studio
 */
class AndroidEmulator implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        return capabilities;
    }

    @Override
    public String toString() {
        return "Android Emulator";
    }
}
