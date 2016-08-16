package org.fluentlenium.adapter;

import org.fluentlenium.configuration.ConfigurationReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

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
    public void should_configure_properly() {
        FluentAdapter adapter = new FluentAdapter();

        adapter.setScreenshotMode(ConfigurationReader.TriggerMode.ON_FAIL);
        assertThat(adapter.getScreenshotMode()).isSameAs(ConfigurationReader.TriggerMode.ON_FAIL);
        adapter.setScreenshotMode(null);

        adapter.setScreenshotPath("path");
        assertThat(adapter.getScreenshotPath()).isEqualTo("path");
        adapter.setScreenshotPath(null);

        adapter.setHtmlDumpMode(ConfigurationReader.TriggerMode.ON_FAIL);
        assertThat(adapter.getHtmlDumpMode()).isSameAs(ConfigurationReader.TriggerMode.ON_FAIL);

        adapter.setHtmlDumpPath("dumpPath");
        assertThat(adapter.getHtmlDumpPath()).isEqualTo("dumpPath");

        assertThat(adapter.getDefaultBaseUrl()).isNull();

        /*

    public String getDefaultBaseUrl() {
        return null;
    }

    @Override
    public void setScreenshotPath(String path) {
        this.screenshotPath = path;
    }

    @Override
    public void setHtmlDumpPath(String htmlDumpPath) {
        this.htmlDumpPath = htmlDumpPath;
    }

    @Override
    public void setScreenshotMode(FluentDriverConfigurationReader.TriggerMode mode) {
        this.screenshotMode = mode;
    }

    @Override
    public FluentDriverConfigurationReader.TriggerMode getScreenshotMode() {
        return screenshotMode;
    }

    @Override
    public String getScreenshotPath() {
        return screenshotPath;
    }

    @Override
    public String getHtmlDumpPath() {
        return htmlDumpPath;
    }

    @Override
    public void setHtmlDumpMode(FluentDriverConfigurationReader.TriggerMode htmlDumpMode) {
        this.htmlDumpMode = htmlDumpMode;
    }

    @Override
    public FluentDriverConfigurationReader.TriggerMode getHtmlDumpMode() {
        return htmlDumpMode;
    }
    */
    }


}
