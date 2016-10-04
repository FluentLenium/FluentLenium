package org.fluentlenium.integration;

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
        String url = null;
        goTo(url);
    }

    @Test
    public void checkGoToUrl() {
        goTo(DEFAULT_URL);
        verify(webDriver).get(DEFAULT_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void goToWithNullPage() {
        FluentPage page = null;
        goTo(page);
    }

    @Test(expected = IllegalArgumentException.class)
    public void goToWithNullUrlOnPage() {
        FluentPage page = new FluentPage() {
        };
        page.initFluent(this);
        goTo(page);
    }

    @Test
    public void checkGoToPage() {
        FluentPage page = newInstance(MyPage.class);
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
