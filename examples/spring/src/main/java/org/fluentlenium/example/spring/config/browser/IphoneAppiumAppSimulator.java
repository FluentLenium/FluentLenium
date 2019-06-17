package org.fluentlenium.example.spring.config.browser;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Capabilities to run https://github.com/appium/ios-test-app automation
 */
class IphoneAppiumAppSimulator implements IBrowser {

    @Override
    public Capabilities getBrowserCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0");
        capabilities.setCapability(MobileCapabilityType.APP,
                getTestAppPath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability("useNewWDA", false);
        return capabilities;
    }

    // get it from https://github.com/appium/ios-test-app and provide full path
    private String getTestAppPath() {
        return "/Users/penelope/TestApp.app";
    }

    @Override
    public String toString() {
        return "Iphone Simulator used to test Appium iOS test app";
    }
}
