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
    AndroidEmulator android_emulator = new AndroidEmulator();

    Map<String, IBrowser> browsers = Map.ofEntries(
            Map.entry("chrome", chrome),
            Map.entry("firefox", firefox),
            Map.entry("ie", ie),
            Map.entry("edge", edge),
            Map.entry("opera", opera),
            Map.entry("safari", safari),

            Map.entry("iphone", iphone),
            Map.entry("android", android),

            Map.entry("iphone_simulator", iphone_simulator),
            Map.entry("android_emulator", android_emulator)
    );

    Capabilities getCapabilities();

    default String getDriverExecutableName() {
        throw new ConfigException("Not supported");
    }

    default String getDriverSystemPropertyName() {
        throw new ConfigException("Not supported");
    }

    static IBrowser getBrowser(String browserName) {
        return browsers.getOrDefault(browserName, chrome);
    }

}
