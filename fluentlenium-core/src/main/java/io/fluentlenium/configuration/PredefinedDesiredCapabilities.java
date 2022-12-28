package io.fluentlenium.configuration;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PredefinedDesiredCapabilities {

    private PredefinedDesiredCapabilities() {
        // intentionally blank
    }

    public static DesiredCapabilities android() {
        return new DesiredCapabilities(Browser.CHROME.browserName(), "", Platform.ANDROID);
    }

    public static DesiredCapabilities chrome() {
        return new DesiredCapabilities(Browser.CHROME.browserName(), "", Platform.ANY);
    }

    public static DesiredCapabilities firefox() {
        DesiredCapabilities capabilities = new DesiredCapabilities(Browser.FIREFOX.browserName(), "", Platform.ANY);
        capabilities.setCapability("acceptInsecureCerts", true);
        return capabilities;
    }

    public static DesiredCapabilities htmlUnit() {
        return new DesiredCapabilities(Browser.HTMLUNIT.browserName(), "", Platform.ANY);
    }

    public static DesiredCapabilities edge() {
        return new DesiredCapabilities(Browser.EDGE.browserName(), "", Platform.WINDOWS);
    }

    public static DesiredCapabilities internetExplorer() {
        DesiredCapabilities capabilities = new DesiredCapabilities(Browser.IE.browserName(), "", Platform.WINDOWS);
        return capabilities;
    }

    public static DesiredCapabilities iphone() {
        return new DesiredCapabilities(Browser.SAFARI.browserName(), "", Platform.IOS);
    }

    public static DesiredCapabilities ipad() {
        return new DesiredCapabilities(Browser.SAFARI.browserName(), "", Platform.IOS);
    }

    public static DesiredCapabilities opera() {
        return new DesiredCapabilities(Browser.OPERA.browserName(), "", Platform.ANY);
    }

    public static DesiredCapabilities safari() {
        return new DesiredCapabilities(Browser.SAFARI.browserName(), "", Platform.MAC);
    }

}
