package org.fluentlenium.examples.test;

import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.BeforeClass;
import org.openqa.selenium.os.ExecutableFinder;

public abstract class AbstractChromeTest extends FluentTest {

    private static final String PATH_TO_CHROME_DRIVER = "C:\\drivers\\chromedriver.exe";
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

    @BeforeClass
    public static void setup() {
        if (systemPropertyNotSet() && executableNotPresentInPath()) {
            setSystemProperty();
        }
    }

    private static boolean executableNotPresentInPath() {
        return new ExecutableFinder().find("chromedriver") == null;
    }

    private static boolean systemPropertyNotSet() {
        return System.getProperty(CHROME_DRIVER_PROPERTY) == null;
    }

    private static void setSystemProperty() {
        System.setProperty(CHROME_DRIVER_PROPERTY, PATH_TO_CHROME_DRIVER);
    }

}
