package org.fluentlenium.test.initialization;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class BeforeInitialization extends FluentTest {
    private final WebDriver webDriver = new HtmlUnitDriver();
    private TestPrivatePageWithElement page2; // NOPMD UsunedPrivateField
    private TestPrivatePage2 page;

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
        assertThat(privatePage.myElement).isNotNull();
    }

    @Test
    void testSuperclassFluentWebElementInstantiate() {
        TestPrivatePageWithElementSubclass privatePage = newInstance(TestPrivatePageWithElementSubclass.class);
        assertThat(privatePage.myElement).isNotNull();
    }

    @Override
    public WebDriver newWebDriver() {
        return webDriver;
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

