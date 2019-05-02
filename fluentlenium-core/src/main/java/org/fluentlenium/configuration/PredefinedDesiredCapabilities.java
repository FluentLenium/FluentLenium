package org.fluentlenium.configuration;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PredefinedDesiredCapabilities {

    private PredefinedDesiredCapabilities() {
        // intentionally blank
    }

    public static DesiredCapabilities android() {
        return new DesiredCapabilities(BrowserType.ANDROID, "", Platform.ANDROID);
    }

    public static DesiredCapabilities chrome() {
        return new DesiredCapabilities(BrowserType.CHROME, "", Platform.ANY);
    }

    public static DesiredCapabilities firefox() {
        DesiredCapabilities capabilities = new DesiredCapabilities(BrowserType.FIREFOX, "", Platform.ANY);
        capabilities.setCapability("acceptInsecureCerts", true);
        return capabilities;
    }

    public static DesiredCapabilities htmlUnit() {
        return new DesiredCapabilities(BrowserType.HTMLUNIT, "", Platform.ANY);
    }

    public static DesiredCapabilities edge() {
        return new DesiredCapabilities(BrowserType.EDGE, "", Platform.WINDOWS);
    }

    public static DesiredCapabilities internetExplorer() {
        DesiredCapabilities capabilities = new DesiredCapabilities(BrowserType.IE, "", Platform.WINDOWS);
        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        return capabilities;
    }

    public static DesiredCapabilities iphone() {
        return new DesiredCapabilities(BrowserType.IPHONE, "", Platform.MAC);
    }

    public static DesiredCapabilities ipad() {
        return new DesiredCapabilities(BrowserType.IPAD, "", Platform.MAC);
    }

    public static DesiredCapabilities opera() {
        return new DesiredCapabilities(BrowserType.OPERA, "", Platform.ANY);
    }

    public static DesiredCapabilities operaBlink() {
        return new DesiredCapabilities(BrowserType.OPERA_BLINK, "", Platform.ANY);
    }

    public static DesiredCapabilities safari() {
        return new DesiredCapabilities(BrowserType.SAFARI, "", Platform.MAC);
    }

    public static DesiredCapabilities phantomjs() {
        return new DesiredCapabilities(BrowserType.PHANTOMJS, "", Platform.ANY);
    }

}
