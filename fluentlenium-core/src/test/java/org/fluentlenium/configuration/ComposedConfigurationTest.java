package org.fluentlenium.configuration;

import com.google.common.base.Function;
import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.AbstractPropertiesConfigurationTest.DummyConfigurationFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

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
        testImpl(new Function<ConfigurationProperties, String>() {
            @Override
            public String apply(ConfigurationProperties input) {
                return input.getBaseUrl();
            }
        }, new Function<String, Void>() {
            @Override
            public Void apply(String input) {
                composed.setBaseUrl(input);
                return null;
            }
        }, null, "firefox", "chrome");
    }

    @Test
    public void pageLoadTimeout() {
        testImpl(new Function<ConfigurationProperties, Long>() {
            @Override
            public Long apply(ConfigurationProperties input) {
                return input.getPageLoadTimeout();
            }
        }, new Function<Long, Void>() {
            @Override
            public Void apply(Long input) {
                composed.setPageLoadTimeout(input);
                return null;
            }
        }, null, 1000L, 2000L);
    }

    @Test
    public void implicitlyWait() {
        testImpl(new Function<ConfigurationProperties, Long>() {
            @Override
            public Long apply(ConfigurationProperties input) {
                return input.getImplicitlyWait();
            }
        }, new Function<Long, Void>() {
            @Override
            public Void apply(Long input) {
                composed.setImplicitlyWait(input);
                return null;
            }
        }, null, 1000L, 2000L);
    }

    @Test
    public void scriptTimeout() {
        testImpl(new Function<ConfigurationProperties, Long>() {
            @Override
            public Long apply(ConfigurationProperties input) {
                return input.getScriptTimeout();
            }
        }, new Function<Long, Void>() {
            @Override
            public Void apply(Long input) {
                composed.setScriptTimeout(input);
                return null;
            }
        }, null, 1000L, 2000L);
    }

    @Test
    public void screenshotPath() {
        testImpl(new Function<ConfigurationProperties, String>() {
            @Override
            public String apply(ConfigurationProperties input) {
                return input.getScreenshotPath();
            }
        }, new Function<String, Void>() {
            @Override
            public Void apply(String input) {
                composed.setScreenshotPath(input);
                return null;
            }
        }, null, "firefox", "chrome");
    }

    @Test
    public void htmlDumpPath() {
        testImpl(new Function<ConfigurationProperties, String>() {
            @Override
            public String apply(ConfigurationProperties input) {
                return input.getHtmlDumpPath();
            }
        }, new Function<String, Void>() {
            @Override
            public Void apply(String input) {
                composed.setHtmlDumpPath(input);
                return null;
            }
        }, null, "firefox", "chrome");
    }

    @Test
    public void screenshotMode() {
        testImpl(new Function<ConfigurationProperties, ConfigurationProperties.TriggerMode>() {
            @Override
            public ConfigurationProperties.TriggerMode apply(ConfigurationProperties input) {
                return input.getScreenshotMode();
            }
        }, new Function<ConfigurationProperties.TriggerMode, Void>() {
            @Override
            public Void apply(ConfigurationProperties.TriggerMode input) {
                composed.setScreenshotMode(input);
                return null;
            }
        }, null, ConfigurationProperties.TriggerMode.MANUAL, ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void htmlDumpMode() {
        testImpl(new Function<ConfigurationProperties, ConfigurationProperties.TriggerMode>() {
            @Override
            public ConfigurationProperties.TriggerMode apply(ConfigurationProperties input) {
                return input.getHtmlDumpMode();
            }
        }, new Function<ConfigurationProperties.TriggerMode, Void>() {
            @Override
            public Void apply(ConfigurationProperties.TriggerMode input) {
                composed.setHtmlDumpMode(input);
                return null;
            }
        }, null, ConfigurationProperties.TriggerMode.MANUAL, ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void capabilities() {
        DesiredCapabilities cap1 = new DesiredCapabilities();
        cap1.setJavascriptEnabled(true);

        DesiredCapabilities cap2 = new DesiredCapabilities();
        cap2.setJavascriptEnabled(false);

        testImpl(new Function<ConfigurationProperties, Capabilities>() {
            @Override
            public Capabilities apply(ConfigurationProperties input) {
                return input.getCapabilities();
            }
        }, new Function<Capabilities, Void>() {
            @Override
            public Void apply(Capabilities input) {
                composed.setCapabilities(input);
                return null;
            }
        }, null, cap1, cap2);
    }

    @Test
    public void eventsEnabled() {
        testImpl(new Function<ConfigurationProperties, Boolean>() {
            @Override
            public Boolean apply(ConfigurationProperties input) {
                return input.getEventsEnabled();
            }
        }, new Function<Boolean, Void>() {
            @Override
            public Void apply(Boolean input) {
                composed.setEventsEnabled(input);
                return null;
            }
        }, null, true, false);
    }
}
