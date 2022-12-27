package io.fluentlenium.test.initialization;

import io.fluentlenium.adapter.junit.jupiter.FluentTest;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentWebElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BeforeInitializationTest extends FluentTest {
    private TestPrivatePageWithElement page2; // NOPMD UsunedPrivateField
    private TestPrivatePage2 page;

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void beforeTest() {
        page2 = newInstance(TestPrivatePageWithElement.class);
        page = newInstance(TestPrivatePage2.class);
    }

    @Test
    void testNoException() {
        page.go();
    }

    @Test
    void testInternalFluentWebElementInstantiate() {
        TestPrivatePageWithElement privatePage = newInstance(TestPrivatePageWithElement.class);
        Assertions.assertThat(privatePage.myElement).isNotNull();
    }

    @Test
    void testSuperclassFluentWebElementInstantiate() {
        TestPrivatePageWithElementSubclass privatePage = newInstance(TestPrivatePageWithElementSubclass.class);
        Assertions.assertThat(privatePage.myElement).isNotNull();
    }

    @Override
    public WebDriver newWebDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        return new ChromeDriver(chromeOptions);
    }

}

class TestPrivatePage2 extends FluentPage {

    @Override
    public String getUrl() {
        return "about:blank";
    }
}

class TestPrivatePageWithElement extends FluentPage {

    /* default */ FluentWebElement myElement;

    @Override
    public String getUrl() {
        return "about:blank";
    }
}

class TestPrivatePageWithElementSubclass extends TestPrivatePageWithElement {

}

