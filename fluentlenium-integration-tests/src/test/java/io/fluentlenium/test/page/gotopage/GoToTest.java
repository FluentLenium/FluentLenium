package io.fluentlenium.test.page.gotopage;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.test.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class GoToTest extends IntegrationFluentTest {
    private final WebDriver webDriver = Mockito.mock(WebDriver.class);

    @Test
    void goToWithNullString() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    String url = null;
                    goTo(url);
                });
    }

    @Test
    void checkGoToUrl() {
        goTo(DEFAULT_URL);
        verify(webDriver).get(DEFAULT_URL);
    }

    @Test
    void goToWithNullPage() {
        FluentPage page = null;
        Assertions.assertThatThrownBy(() -> goTo(page)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("It is required to specify an instance of FluentPage for navigation.");
    }

    @Test
    void goToWithNullUrlOnPage() {
        FluentPage page = new FluentPage() {
        };
        page.initFluent(this);
        Assertions.assertThatThrownBy(() -> goTo(page)).isInstanceOf(IllegalStateException.class)
                .hasMessage("An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
    }

    @Test
    void checkGoToPage() {
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
