package org.fluentlenium.configuration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.PropertiesBackendConfigurationTest.DummyConfigurationFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.function.Function;

import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComposedConfigurationTest {
    private ProgrammaticConfiguration configuration;

    private ConfigurationProperties configurationProperties1;

    private ConfigurationProperties configurationProperties2;

    private ConfigurationProperties configurationProperties3;

    private ComposedConfiguration composed;

    @Before
    public void before() {
        Answer configurationReadAnswer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (invocation.getMethod().getReturnType().isPrimitive()) {
                    return RETURNS_DEFAULTS.answer(invocation);
                }
                return null;
            }
        };

        configurationProperties1 = mock(ConfigurationProperties.class, configurationReadAnswer);
        configurationProperties2 = mock(ConfigurationProperties.class, configurationReadAnswer);
        configurationProperties3 = mock(ConfigurationProperties.class, configurationReadAnswer);
        configuration = new ProgrammaticConfiguration();
        composed = new ComposedConfiguration(configuration, configuration, configurationProperties1, configurationProperties2,
                configurationProperties3);
    }

    @Test
    public void configurationFactory() {
        Assertions.assertThat(composed.getConfigurationFactory()).isNull();

        composed.setConfigurationFactory(DefaultConfigurationFactory.class);

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DefaultConfigurationFactory.class);

        when(configurationProperties2.getConfigurationFactory()).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return DummyConfigurationFactory.class;
            }
        });

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DefaultConfigurationFactory.class);

        composed.setConfigurationFactory(null);

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DummyConfigurationFactory.class);

        when(configurationProperties3.getConfigurationFactory()).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return DefaultConfigurationFactory.class;
            }
        });

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DummyConfigurationFactory.class);
    }

    private <T> void testImpl(Function<ConfigurationProperties, T> getter, Function<T, Void> setter, T defaultValue, T value1,
            T value2) {
        if (defaultValue == null) {
            Assertions.assertThat(getter.apply(composed)).isNull();
        } else {
            Assertions.assertThat(getter.apply(composed)).isEqualTo(defaultValue);
        }

        setter.apply(value1);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(value1);

        when(getter.apply(configurationProperties2)).thenReturn(value2);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(value1);

        setter.apply(defaultValue);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(value2);

        when(getter.apply(configurationProperties3)).thenReturn(value1);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(value2);
    }

    @Test
    public void webDriver() {
        testImpl(new Function<ConfigurationProperties, String>() {
            @Override
            public String apply(ConfigurationProperties input) {
                return input.getWebDriver();
            }
        }, new Function<String, Void>() {
            @Override
            public Void apply(String input) {
                composed.setWebDriver(input);
                return null;
            }
        }, null, "firefox", "chrome");
    }

    @Test
    public void baseUrl() {
        testImpl(input -> input.getBaseUrl(), input -> {
            composed.setBaseUrl(input);
            return null;
        }, null, "firefox", "chrome");
    }

    @Test
    public void pageLoadTimeout() {
        testImpl(input -> input.getPageLoadTimeout(), input -> {
            composed.setPageLoadTimeout(input);
            return null;
        }, null, 1000L, 2000L);
    }

    @Test
    public void implicitlyWait() {
        testImpl(input -> input.getImplicitlyWait(), input -> {
            composed.setImplicitlyWait(input);
            return null;
        }, null, 1000L, 2000L);
    }

    @Test
    public void scriptTimeout() {
        testImpl(input -> input.getScriptTimeout(), input -> {
            composed.setScriptTimeout(input);
            return null;
        }, null, 1000L, 2000L);
    }

    @Test
    public void screenshotPath() {
        testImpl(input -> input.getScreenshotPath(), input -> {
            composed.setScreenshotPath(input);
            return null;
        }, null, "firefox", "chrome");
    }

    @Test
    public void htmlDumpPath() {
        testImpl(input -> input.getHtmlDumpPath(), input -> {
            composed.setHtmlDumpPath(input);
            return null;
        }, null, "firefox", "chrome");
    }

    @Test
    public void screenshotMode() {
        testImpl(input -> input.getScreenshotMode(), input -> {
            composed.setScreenshotMode(input);
            return null;
        }, null, ConfigurationProperties.TriggerMode.MANUAL, ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void htmlDumpMode() {
        testImpl(input -> input.getHtmlDumpMode(), input -> {
            composed.setHtmlDumpMode(input);
            return null;
        }, null, ConfigurationProperties.TriggerMode.MANUAL, ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void capabilities() {
        DesiredCapabilities cap1 = new DesiredCapabilities();
        cap1.setJavascriptEnabled(true);

        DesiredCapabilities cap2 = new DesiredCapabilities();
        cap2.setJavascriptEnabled(false);

        testImpl(input -> input.getCapabilities(), input -> {
            composed.setCapabilities(input);
            return null;
        }, null, cap1, cap2);
    }

    @Test
    public void eventsEnabled() {
        testImpl(input -> input.getEventsEnabled(), input -> {
            composed.setEventsEnabled(input);
            return null;
        }, null, true, false);
    }
}
