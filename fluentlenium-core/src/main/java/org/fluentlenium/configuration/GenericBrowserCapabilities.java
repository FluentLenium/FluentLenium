package org.fluentlenium.configuration;

import io.appium.java_client.gecko.options.GeckoOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

public class GenericBrowserCapabilities<T extends Capabilities> {
    public T getBrowserOptions(Class<T> browserCapabilitiesTypeToReturn, Capabilities capabilitiesToMerge) {
        if (browserCapabilitiesTypeToReturn == ChromeOptions.class) {
            return (T) new ChromeOptions().merge(capabilitiesToMerge);
        } else if (browserCapabilitiesTypeToReturn == GeckoOptions.class) {
            return (T) new GeckoOptions().merge(capabilitiesToMerge);
        }
        return null;
    }
}
