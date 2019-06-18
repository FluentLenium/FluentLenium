package org.fluentlenium.example.appium.device;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

class Android implements Device {

    @Override
    public Capabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, getTestAppPath());
        return capabilities;
    }

    // get it from https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium
    @Override
    public String getTestAppPath() {
        return "/Applications/appium/swiftnotes.apk";
    }

    @Override
    public String toString() {
        return "Android Emulator";
    }
}
