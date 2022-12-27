package io.fluentlenium.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import io.fluentlenium.configuration.Configuration;import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

/**
 * Unit test for {@link FluentDriver}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentDriverTest {

    @Mock
    private WebDriver webDriver;
    @Mock
    private Configuration configuration;
    @Mock
    private FluentControl adapter;
    private FluentDriver fluentDriver;

    //events()

    @Test
    public void shouldThrowExceptionForEventsIfEventsRegistryIsNotSet() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));

        assertThatIllegalStateException().isThrownBy(() -> fluentDriver.events())
                                         .withMessageStartingWith("An EventFiringWebDriver instance is required to use events.");
    }

    //url()

    @Test
    public void shouldReturnNullUrlIfNoCurrentUrlFromDriverIsPresent() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        when(webDriver.getCurrentUrl()).thenReturn(null);

        assertThat(fluentDriver.url()).isNull();
    }

    @Test
    public void shouldReturnCurrentUrlFromDriverUrlIfNoBaseUrlIsPresent() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        when(webDriver.getCurrentUrl()).thenReturn("https://duckduckgo.com/?q=fluentlenium&ia=web");
        when(configuration.getBaseUrl()).thenReturn(null);

        assertThat(fluentDriver.url()).isEqualTo("https://duckduckgo.com/?q=fluentlenium&ia=web");
    }

    @Test
    public void shouldReturnCurrentUrlFromDriverIfCurrentUrlDoesntStartWithBaseUrl() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        when(webDriver.getCurrentUrl()).thenReturn("https://duckduckgo.com/?q=fluentlenium&ia=web");
        when(configuration.getBaseUrl()).thenReturn("https://fluentlenium.io/");

        assertThat(fluentDriver.url()).isEqualTo("https://duckduckgo.com/?q=fluentlenium&ia=web");
    }

    @Test
    public void shouldReturnUrl() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        when(webDriver.getCurrentUrl()).thenReturn("https://duckduckgo.com/?q=fluentlenium&ia=web");
        when(configuration.getBaseUrl()).thenReturn("https://duckduckgo.com/");

        assertThat(fluentDriver.url()).isEqualTo("?q=fluentlenium&ia=web");
    }

    //goTo(page)

    @Test
    public void shouldGoToPage() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        FluentPage fluentPage = mock(FluentPage.class);
        when(fluentPage.go()).thenReturn(mock(FluentPage.class));

        fluentDriver.goTo(fluentPage);
        verify(fluentPage).go();
    }

    @Test
    public void shouldFailGoToPageWhenNoPageIsPresent() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));

        assertThatIllegalArgumentException().isThrownBy(() -> fluentDriver.goTo((FluentPage) null))
                                            .withMessage("It is required to specify an instance of FluentPage for navigation.");
    }

    //goTo(url)

    @Test
    public void shouldFailGoToUrlWhenNoUrlIsPresent() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));

        assertThatIllegalArgumentException().isThrownBy(() -> fluentDriver.goTo((String) null))
                                            .withMessage("It is required to specify a URL to navigate to.");
    }

    //goToInNewTab(url)

    @Test
    public void shouldFailGoToInNewTabWhenNoUrlIsPresent() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));

        assertThatIllegalArgumentException().isThrownBy(() -> fluentDriver.goToInNewTab(null))
                                            .withMessage("It is required to specify a URL to navigate to (in a new tab).");
    }

    //switchTo(element)

    @Test
    public void shouldSwitchToDefaultContentForNullElement() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
        WebDriver defaultContent = mock(WebDriver.class);
        when(fluentDriver.getDriver()).thenReturn(webDriver);
        when(webDriver.switchTo()).thenReturn(targetLocator);
        when(targetLocator.defaultContent()).thenReturn(defaultContent);

        fluentDriver.switchTo((FluentWebElement) null);

        verify(webDriver).switchTo();
        verify(targetLocator).defaultContent();
    }

    @Test
    public void shouldSwitchToDefaultContentForIframeElement() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        FluentWebElement element = mock(FluentWebElement.class);
        WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
        WebDriver defaultContent = mock(WebDriver.class);
        when(fluentDriver.getDriver()).thenReturn(webDriver);
        when(webDriver.switchTo()).thenReturn(targetLocator);
        when(targetLocator.defaultContent()).thenReturn(defaultContent);
        when(element.tagName()).thenReturn("div");

        fluentDriver.switchTo(element);

        verify(webDriver).switchTo();
        verify(targetLocator).defaultContent();
    }

    @Test
    public void shouldSwitchToFrameOfInnermostWrappedElement() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        FluentWebElement element = mock(FluentWebElement.class);
        WebElement target = mock(WebElement.class, withSettings().extraInterfaces(WrapsElement.class));
        WebElement wrappedElement = mock(WebElement.class);
        when(((WrapsElement) target).getWrappedElement()).thenReturn(wrappedElement);
        WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
        when(fluentDriver.getDriver()).thenReturn(webDriver);
        when(webDriver.switchTo()).thenReturn(targetLocator);
        when(element.tagName()).thenReturn("iframe");
        when(element.getElement()).thenReturn(target);

        fluentDriver.switchTo(element);

        verify(webDriver).switchTo();
        verify(targetLocator).frame(wrappedElement);
    }

    //quit

    @Test
    public void shouldQuitDriverIfItsPresent() {
        fluentDriver = spy(new FluentDriver(webDriver, configuration, adapter));
        doNothing().when(fluentDriver).releaseFluent();

        fluentDriver.quit();

        verify(webDriver).quit();
        verify(fluentDriver).releaseFluent();
    }

    @Test
    public void shouldOnlyReleaseFluentIfDriverIsNotPresent() {
        fluentDriver = spy(new FluentDriver(null, configuration, adapter));
        doNothing().when(fluentDriver).releaseFluent();

        fluentDriver.quit();

        verify(fluentDriver).getDriver();
        verify(fluentDriver).releaseFluent();
    }
}
