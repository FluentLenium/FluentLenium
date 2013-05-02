package org.fluentlenium.cucumber.adapter.driver;

public enum SupportedWebDriver {
    HTMLUNIT("htmlunit"),
    FIREFOX("firefox"),
    CHROME("chrome"),
    PHANTOMJS("phantomjs"),
    REMOTE("remote");

    private String name;

    SupportedWebDriver(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static SupportedWebDriver getBrowser(String name) {
        for (SupportedWebDriver supportedWebDriver : values()) {
            if (supportedWebDriver.getName().equalsIgnoreCase(name)) {
                return supportedWebDriver;
            }
        }
        return HTMLUNIT;
    }
}
