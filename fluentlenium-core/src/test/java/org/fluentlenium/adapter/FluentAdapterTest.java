package org.fluentlenium.adapter;

import org.fluentlenium.configuration.ConfigurationProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class FluentAdapterTest {



    @Mock
    WebDriver webDriver;

    @Mock
    WebDriver webDriver2;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void is_driver_available_behaves_as_expected() {
        FluentAdapter adapter = new FluentAdapter(webDriver);
        assertThat(adapter.isFluentDriverAvailable()).isTrue();

        adapter.initFluent(null);
        assertThat(adapter.isFluentDriverAvailable()).isFalse();
    }

    @Test
    public void deletages_to_webdriver_when_initialized() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);

        adapter.goTo("url");
        verify(webDriver).get("url");
    }

    @Test
    public void registering_same_driver_multiple_time_doesnt_throw_exception() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);

        adapter.goTo("url");
        verify(webDriver).get("url");
    }

    @Test(expected = IllegalStateException.class)
    public void registering_another_driver_throw_exception() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);
        adapter.initFluent(webDriver2);
    }

    @Test
    public void registering_same_driver_doesnt_throw_exception() {
        FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(webDriver);
        adapter.initFluent(webDriver);
    }

    @Test
    public void should_configure_properly() {
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
    public void should_new_web_driver_create_new_instances() {
        FluentAdapter adapter = new FluentAdapter() {
            @Override
            public String getWebDriver() {
                return "htmlunit";
            }
        };

        adapter.initFluent(this.webDriver);

        WebDriver webDriver = null;
        WebDriver webDriver2 = null;
        try {
            webDriver = adapter.newWebDriver();
            webDriver2 = adapter.newWebDriver();

            assertThat(webDriver).isNotSameAs(webDriver2);
            assertThat(webDriver).isInstanceOf(EventFiringWebDriver.class);
            assertThat(webDriver2).isInstanceOf(EventFiringWebDriver.class);

            assertThat(((WrapsDriver)webDriver).getWrappedDriver()).isInstanceOf(HtmlUnitDriver.class);
            assertThat(((WrapsDriver)webDriver).getWrappedDriver()).isInstanceOf(HtmlUnitDriver.class);
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
            if (webDriver2 != null) {
                webDriver2.quit();
            }
        }



    }


}
