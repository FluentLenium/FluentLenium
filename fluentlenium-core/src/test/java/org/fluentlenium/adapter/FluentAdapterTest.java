package org.fluentlenium.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.fluentlenium.configuration.ConfigurationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

@RunWith(MockitoJUnitRunner.class)
public class FluentAdapterTest {
    @Mock
    private WebDriver webDriver;

    @Mock
    private WebDriver webDriver2;

    @Test
    public void isDriverAvailableBehavesAsExpected() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);
        assertThat(adapter.isFluentControlAvailable()).isTrue();

        adapter.initFluent(null);
        assertThat(adapter.isFluentControlAvailable()).isFalse();
    }

    @Test
    public void deletagesToWebDriverWhenInitialized() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);

        adapter.goTo("url");
        verify(webDriver).get("url");
    }

    @Test
    public void registeringSameDriverMultipleTimeDoesntThrowException() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);

        adapter.goTo("url");
        verify(webDriver).get("url");
    }

    @Test(expected = IllegalStateException.class)
    public void registeringAnotherDriverThrowException() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);
        adapter.initFluent(webDriver2);
    }

    @Test
    public void registeringSameDriverDoesntThrowException() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);
        adapter.initFluent(webDriver);
    }

    @Test
    public void shouldConfigureProperly() {
        FluentAdapter adapter = new FluentAdapter();

        adapter.getConfiguration().setScreenshotMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        assertThat(adapter.getScreenshotMode()).isSameAs(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        adapter.getConfiguration().setScreenshotMode(null);

        adapter.getConfiguration().setScreenshotPath("path");
        assertThat(adapter.getScreenshotPath()).isEqualTo("path");
        adapter.getConfiguration().setScreenshotPath(null);

        adapter.getConfiguration().setHtmlDumpMode(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        assertThat(adapter.getHtmlDumpMode()).isSameAs(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);

        adapter.getConfiguration().setHtmlDumpPath("dumpPath");
        assertThat(adapter.getHtmlDumpPath()).isEqualTo("dumpPath");

        assertThat(adapter.getBaseUrl()).isNull();
    }

    @Test
    public void shouldNewWebDriverCreateNewInstances() {
        FluentAdapter adapter = new FluentAdapter() {
            @Override
            public String getWebDriver() {
                return "htmlunit";
            }

            @Override
            public MutableCapabilities getCapabilities() {
                return DesiredCapabilities.htmlUnit();
            }
        };

        adapter.initFluent(webDriver);

        WebDriver newWebDriver = null;
        WebDriver newWebDriver2 = null;
        try {
            newWebDriver = adapter.newWebDriver();
            newWebDriver2 = adapter.newWebDriver();

            assertThat(newWebDriver).isNotSameAs(newWebDriver2);
            assertThat(newWebDriver).isInstanceOf(EventFiringWebDriver.class);
            assertThat(newWebDriver2).isInstanceOf(EventFiringWebDriver.class);

            assertThat(((WrapsDriver) newWebDriver).getWrappedDriver()).isInstanceOf(HtmlUnitDriver.class);
            assertThat(((WrapsDriver) newWebDriver).getWrappedDriver()).isInstanceOf(HtmlUnitDriver.class);
        } finally {
            if (newWebDriver != null) {
                newWebDriver.quit();
            }
            if (newWebDriver2 != null) {
                newWebDriver2.quit();
            }
        }

    }
}
