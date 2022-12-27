package io.fluentlenium.test.initialization;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstructorInitializationTest extends FluentTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    void doNotUseOverridableMethodsInAConstructor() {
        assertThat(driver).isEqualTo(getDriver());
    }

    @Override
    public WebDriver newWebDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        return driver;
    }
}
