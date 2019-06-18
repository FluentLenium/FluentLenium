package org.fluentlenium.example.appium.device;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

class Iphone implements Device {

    @Override
    public Capabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0");
        capabilities.setCapability(MobileCapabilityType.APP, getTestAppPath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability("useNewWDA", false);
        capabilities.setCapability("showXcodeLog", true);

        return capabilities;
    }

    // get it from https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium
    @Override
    public String getTestAppPath() {
        return "/Applications/appium/UITestDemo.iOS.app";
    }

    @Override
    public String toString() {
        return "Iphone Simulator";
    }
}
