package org.fluentlenium.integration;

import org.assertj.core.api.ThrowableAssert;
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
    private PageIsNotAt pageFail;

    @Test
    public void testIsNotAt() {
        pageFail.go();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                pageFail.isAt();
            }
        }).isInstanceOf(AssertionError.class);

    }

    @Test
    public void testIsAt() {
        pageOk.go();
        pageOk.isAt();
    }

    @FindBy(css = "#oneline")
    public static class PageIsAt extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL;
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
