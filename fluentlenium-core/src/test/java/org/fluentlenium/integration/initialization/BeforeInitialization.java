package org.fluentlenium.integration.initialization;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class BeforeInitialization extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();
    public TestPrivatePageWithElement page2;
    public TestPrivatePage2 page;

    @Before
    public void beforeTest() {
        page2 = createPage(TestPrivatePageWithElement.class);
        page = createPage(TestPrivatePage2.class);
    }

    @Test
    public void test_no_exception() {
        page.go();
    }

    @Test
    public void test_internal_fluentwebelement_instantiate() {
        TestPrivatePageWithElement page = createPage(TestPrivatePageWithElement.class);
        assertThat(page.myElement).isNotNull();
    }

    @Test
    public void test_superclass_fluentwebelement_instantiate() {
        TestPrivatePageWithElementSubclass page = createPage(TestPrivatePageWithElementSubclass.class);
        assertThat(page.myElement).isNotNull();
    }

    @Override
    public WebDriver getDefaultDriver() {
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

    FluentWebElement myElement;

    @Override
    public String getUrl() {
        return "about:blank";
    }
}

class TestPrivatePageWithElementSubclass extends TestPrivatePageWithElement {

}

