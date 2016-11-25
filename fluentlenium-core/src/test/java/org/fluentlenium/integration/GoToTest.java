package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.verify;

public class GoToTest extends IntegrationFluentTest {
    private final WebDriver webDriver = Mockito.mock(WebDriver.class);

    @Test(expected = IllegalArgumentException.class)
    public void goToWithNullString() {
        final String url = null;
        goTo(url);
    }

    @Test
    public void checkGoToUrl() {
        goTo(DEFAULT_URL);
        verify(webDriver).get(DEFAULT_URL);
    }

    @Test
    public void goToWithNullPage() {
        final FluentPage page = null;
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                goTo(page);
            }
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Page is mandatory");
    }

    @Test
    public void goToWithNullUrlOnPage() {
        final FluentPage page = new FluentPage() {
        };
        page.initFluent(this);
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                goTo(page);
            }
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
    }

    @Test
    public void checkGoToPage() {
        final FluentPage page = newInstance(MyPage.class);
        goTo(page);
        verify(webDriver).get(DEFAULT_URL);
    }

    @Override
    public WebDriver newWebDriver() {
        return webDriver;
    }
}

class MyPage extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.DEFAULT_URL;
    }

}
