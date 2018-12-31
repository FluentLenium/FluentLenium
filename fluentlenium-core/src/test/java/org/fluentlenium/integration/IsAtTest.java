package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IsAtTest extends IntegrationFluentTest {
    @Page
    private PageIsAt pageOk;

    @Page
    private PageIsAtParameter pageOkParameter;

    @Page
    private PageIsNotAt pageFail;

    @Test
    public void testIsNotAt() {
        pageFail.go();

        assertThatThrownBy(() -> pageFail.isAt()).isInstanceOf(AssertionError.class);
    }

    @Test
    public void testIsAt() {
        pageOk.go();
        pageOk.isAt();
    }

    @Test
    public void testIsAtParameters() {
        pageOkParameter.go("html");
        pageOkParameter.isAt("html");
    }

    @FindBy(css = "#oneline")
    public static class PageIsAt extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL;
        }
    }

    @FindBy(css = "#oneline")
    public static class PageIsAtParameter extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL.replace(".html", ".{extension}");
        }
    }

    @FindBy(css = "#invalid")
    public static class PageIsNotAt extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL;
        }
    }

}
