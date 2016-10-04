package org.fluentlenium.integration.initialization;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.util.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class AnnotationInitialization extends FluentTest {
    private final WebDriver webDriver = new HtmlUnitDriver();

    @Page
    private TestAboutBlankPage page2; // NOPMD UsunedPrivateField

    @Page
    private TestPrivatePage page; // NOPMD UsunedPrivateField

    @Test
    public void testNoException() {
        page2.go();
    }

    @Test
    public void testNoExceptionWhenInnerClass() {
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
