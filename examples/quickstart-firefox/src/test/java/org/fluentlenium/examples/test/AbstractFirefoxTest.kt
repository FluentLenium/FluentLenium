package org.fluentlenium.examples.test;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.os.ExecutableFinder;

public abstract class AbstractFirefoxTest extends FluentTest {

    private static final String PATH_TO_GECKO_DRIVER = "C:\\drivers\\geckodriver.exe";
    private static final String GECKO_DRIVER_PROPERTY = "webdriver.gecko.driver";

    @BeforeAll
    static void setup() {
        if (systemPropertyNotSet() && executableNotPresentInPath()) {
            setSystemProperty();
        }
    }

    private static boolean executableNotPresentInPath() {
        return new ExecutableFinder().find("geckodriver") == null;
    }

    private static boolean systemPropertyNotSet() {
        return System.getProperty(GECKO_DRIVER_PROPERTY) == null;
    }

    private static void setSystemProperty() {
        System.setProperty(GECKO_DRIVER_PROPERTY, PATH_TO_GECKO_DRIVER);
    }

}
