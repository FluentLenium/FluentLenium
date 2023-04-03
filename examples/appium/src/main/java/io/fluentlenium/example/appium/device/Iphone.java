package io.fluentlenium.example.appium.device;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("iphone")
public class Iphone implements Device {

    @Value("${iphone.app.path}")
    private String appPath;

    @Override
    public Capabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "16.2");
        capabilities.setCapability(MobileCapabilityType.APP, getTestAppPath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability("useNewWDA", false);
        capabilities.setCapability("showXcodeLog", true);

        return capabilities;
    }

    @Override
    public String getTestAppPath() {
        return appPath;
    }

    @Override
    public String toString() {
        return "Iphone Simulator";
    }
}
