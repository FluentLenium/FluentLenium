package org.fluentlenium.configuration;

import io.appium.java_client.gecko.options.GeckoOptions;
import io.appium.java_client.safari.options.SafariOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.opera.OperaOptions;

public class GenericBrowserCapabilities<T extends Capabilities> {
    public T getBrowserOptions(Class<T> browserCapabilitiesTypeToReturn, Capabilities capabilitiesToMerge) {
        if (browserCapabilitiesTypeToReturn == ChromeOptions.class || browserCapabilitiesTypeToReturn == ChromiumOptions.class) {
            return (T) new ChromeOptions().merge(capabilitiesToMerge);
        } else if (browserCapabilitiesTypeToReturn == EdgeOptions.class) {
            return (T) new EdgeOptions().merge(capabilitiesToMerge);
        } else if (browserCapabilitiesTypeToReturn == SafariOptions.class) {
            return (T) new SafariOptions().merge(capabilitiesToMerge);
        } else if (browserCapabilitiesTypeToReturn == GeckoOptions.class) {
            return (T) new GeckoOptions().merge(capabilitiesToMerge);
        } else if (browserCapabilitiesTypeToReturn == OperaOptions.class) {
            return (T) new OperaOptions().merge(capabilitiesToMerge);
        } else {
            return (T) new MutableCapabilities().merge(capabilitiesToMerge);
        }
    }
}
