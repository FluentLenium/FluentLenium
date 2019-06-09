package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;

import java.util.Map;

public interface IBrowser {

    Chrome chrome = new Chrome();
    Firefox firefox = new Firefox();
    IE ie = new IE();
    Edge edge = new Edge();
    Opera opera = new Opera();
    Safari safari = new Safari();

    Map<String, IBrowser> browsers = Map.of(
            "chrome", chrome,
            "firefox", firefox,
            "ie", ie,
            "edge", edge,
            "opera", opera,
            "safari", safari
    );

    Capabilities getBrowserCapabilities();

    String getDriverExecutableName();

    String getDriverSystemPropertyName();

    static IBrowser getBrowser(String browserName) {
        return browsers.getOrDefault(browserName, chrome);
    }

}
