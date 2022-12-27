package io.fluentlenium.test.initialization;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AnnotationInitializationTest extends FluentTest {

    @Page
    private TestAboutBlankPage page2; // NOPMD UsunedPrivateField

    @Page
    private TestPrivatePage page; // NOPMD UsunedPrivateField

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    void testNoException() {
        page2.go();
    }

    @Test
    void testNoExceptionWhenInnerClass() {
        page2.go();
    }

    @Override
    public WebDriver newWebDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        return new ChromeDriver(chromeOptions);
    }

}

class TestPrivatePage extends FluentPage {

    @Override
    public String getUrl() {
        return "about:blank";
    }
}
