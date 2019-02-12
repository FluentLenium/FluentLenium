package org.fluentlenium.test.initialization;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class AnnotationInitialization extends FluentTest {
    private final WebDriver webDriver = new HtmlUnitDriver();

    @Page
    private TestAboutBlankPage page2; // NOPMD UsunedPrivateField

    @Page
    private TestPrivatePage page; // NOPMD UsunedPrivateField

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
        return webDriver;
    }

}

class TestPrivatePage extends FluentPage {

    @Override
    public String getUrl() {
        return "about:blank";
    }
}
