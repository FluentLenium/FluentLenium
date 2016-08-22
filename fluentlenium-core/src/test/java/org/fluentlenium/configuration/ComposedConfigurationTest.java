package org.fluentlenium.configuration;

import com.google.common.base.Function;
import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.AbstractPropertiesConfigurationTest.DummyConfigurationFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
        composed = new ComposedConfiguration(configuration, configuration, configurationProperties1, configurationProperties2, configurationProperties3);
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

    private void testString(Function<ConfigurationProperties, String> getter, Function<String, Void> setter) {
        Assertions.assertThat(getter.apply(composed)).isNull();

        setter.apply("firefox");
        Assertions.assertThat(getter.apply(composed)).isEqualTo("firefox");

        when(getter.apply(configurationProperties2)).thenReturn("chrome");
        Assertions.assertThat(getter.apply(composed)).isEqualTo("firefox");

        setter.apply(null);
        Assertions.assertThat(getter.apply(composed)).isEqualTo("chrome");

        when(getter.apply(configurationProperties3)).thenReturn("firefox");
        Assertions.assertThat(getter.apply(composed)).isEqualTo("chrome");
    }

    private void testLong(Function<ConfigurationProperties, Long> getter, Function<Long, Void> setter) {
        Assertions.assertThat(getter.apply(composed)).isNull();

        setter.apply(1000L);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(1000L);

        when(getter.apply(configurationProperties2)).thenReturn(2000L);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(1000L);

        setter.apply(null);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(2000L);

        when(getter.apply(configurationProperties3)).thenReturn(1000L);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(2000L);
    }


    private void testTriggerMode(Function<ConfigurationProperties, ConfigurationProperties.TriggerMode> getter, Function<ConfigurationProperties.TriggerMode, Void> setter) {
        Assertions.assertThat(getter.apply(composed)).isNull();

        setter.apply(ConfigurationProperties.TriggerMode.MANUAL);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationProperties.TriggerMode.MANUAL);

        when(getter.apply(configurationProperties2)).thenReturn(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationProperties.TriggerMode.MANUAL);

        setter.apply(null);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);

        when(getter.apply(configurationProperties3)).thenReturn(ConfigurationProperties.TriggerMode.MANUAL);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL);
    }

    @Test
    public void webDriver() {
        testString(new Function<ConfigurationProperties, String>() {
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
        });
    }

    @Test
    public void baseUrl() {
        testString(new Function<ConfigurationProperties, String>() {
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
        });
    }

    @Test
    public void pageLoadTimeout() {
        testLong(new Function<ConfigurationProperties, Long>() {
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
        });
    }


    @Test
    public void implicitlyWait() {
        testLong(new Function<ConfigurationProperties, Long>() {
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
        });
    }

    @Test
    public void scriptTimeout() {
        testLong(new Function<ConfigurationProperties, Long>() {
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
        });
    }

    @Test
    public void screenshotPath() {
        testString(new Function<ConfigurationProperties, String>() {
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
        });
    }

    @Test
    public void htmlDumpPath() {
        testString(new Function<ConfigurationProperties, String>() {
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
        });
    }

    @Test
    public void screenshotMode() {
        testTriggerMode(new Function<ConfigurationProperties, ConfigurationProperties.TriggerMode>() {
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
        });
    }

    @Test
    public void htmlDumpMode() {
        testTriggerMode(new Function<ConfigurationProperties, ConfigurationProperties.TriggerMode>() {
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
        });
    }
}
