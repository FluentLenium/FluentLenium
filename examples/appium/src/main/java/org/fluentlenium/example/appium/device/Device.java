package org.fluentlenium.example.appium.device;

import org.openqa.selenium.Capabilities;

import java.util.Map;

public interface Device {

    Iphone iphone = new Iphone();
    Android android = new Android();

    Map<String, Device> browsers = Map.ofEntries(
            Map.entry("android", android),
            Map.entry("iphone", iphone)
    );

    Capabilities getCapabilities();

    String getTestAppPath();

    static Device getBrowser(String browserName) {
        return browsers.getOrDefault(browserName, iphone);
    }

}
