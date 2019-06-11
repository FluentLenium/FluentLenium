package org.fluentlenium.example.spring.config.browser;

import org.fluentlenium.example.spring.config.ConfigException;
import org.openqa.selenium.Capabilities;

import java.util.Map;

public interface IBrowser {

    Chrome chrome = new Chrome();
    Firefox firefox = new Firefox();
    IE ie = new IE();
    Edge edge = new Edge();
    Opera opera = new Opera();
    Safari safari = new Safari();
    Iphone iphone = new Iphone();
    Android android = new Android();

    IphoneSimulator iphone_simulator = new IphoneSimulator();
    AndroidSimulator android_simulator = new AndroidSimulator();

    Map<String, IBrowser> browsers = Map.of(
            "chrome", chrome,
            "firefox", firefox,
            "ie", ie,
            "edge", edge,
            "opera", opera,
            "safari", safari,
            "iphone_simulator", iphone_simulator,
            "iphone", iphone,
            "android_simulator", android_simulator,
            "android", android
    );

    Capabilities getBrowserCapabilities();

    default String getDriverExecutableName() {
        throw new ConfigException("Not supported");
    }

    default String getDriverSystemPropertyName()  {
        throw new ConfigException("Not supported");
    }

    static IBrowser getBrowser(String browserName) {
        return browsers.getOrDefault(browserName, chrome);
    }

}
