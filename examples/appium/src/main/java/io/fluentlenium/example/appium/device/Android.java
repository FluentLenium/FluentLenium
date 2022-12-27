package io.fluentlenium.example.appium.device;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("android")
@Component
public class Android implements Device {

    @Value("${android.app.path}")
    private String androidAppPath;

    @Override
    public Capabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, getTestAppPath());
        return capabilities;
    }

    @Override
    public String getTestAppPath() {
        return androidAppPath;
    }

    @Override
    public String toString() {
        return "Android Emulator";
    }
}
