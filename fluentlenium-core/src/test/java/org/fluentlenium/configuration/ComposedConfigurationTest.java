package org.fluentlenium.configuration;

import com.google.common.base.Function;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ComposedConfigurationTest {
    private ProgrammaticConfiguration configuration;

    public static class DummyConfigurationFactory implements ConfigurationFactory {
        @Override
        public Configuration newConfiguration(Class<?> containerClass) {
            return null;
        }
    }

    private ConfigurationRead configurationRead1;

    private ConfigurationRead configurationRead2;

    private ConfigurationRead configurationRead3;

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

        configurationRead1 = mock(ConfigurationRead.class, configurationReadAnswer);
        configurationRead2 = mock(ConfigurationRead.class, configurationReadAnswer);
        configurationRead3 = mock(ConfigurationRead.class, configurationReadAnswer);
        configuration = new ProgrammaticConfiguration();
        composed = new ComposedConfiguration(configuration, configuration, configurationRead1, configurationRead2, configurationRead3);
    }

    @Test
    public void configurationFactory() {
        Assertions.assertThat(composed.getConfigurationFactory()).isNull();

        composed.setConfigurationFactory(DefaultConfigurationFactory.class);

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DefaultConfigurationFactory.class);

        when(configurationRead2.getConfigurationFactory()).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return DummyConfigurationFactory.class;
            }
        });

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DefaultConfigurationFactory.class);

        composed.setConfigurationFactory(null);

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DummyConfigurationFactory.class);

        when(configurationRead3.getConfigurationFactory()).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return DefaultConfigurationFactory.class;
            }
        });

        Assertions.assertThat(composed.getConfigurationFactory()).isSameAs(DummyConfigurationFactory.class);
    }

    private void testString(Function<ConfigurationRead, String> getter, Function<String, Void> setter) {
        Assertions.assertThat(getter.apply(composed)).isNull();

        setter.apply("firefox");
        Assertions.assertThat(getter.apply(composed)).isEqualTo("firefox");

        when(getter.apply(configurationRead2)).thenReturn("chrome");
        Assertions.assertThat(getter.apply(composed)).isEqualTo("firefox");

        setter.apply(null);
        Assertions.assertThat(getter.apply(composed)).isEqualTo("chrome");

        when(getter.apply(configurationRead3)).thenReturn("firefox");
        Assertions.assertThat(getter.apply(composed)).isEqualTo("chrome");
    }

    private void testLong(Function<ConfigurationRead, Long> getter, Function<Long, Void> setter) {
        Assertions.assertThat(getter.apply(composed)).isNull();

        setter.apply(1000L);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(1000L);

        when(getter.apply(configurationRead2)).thenReturn(2000L);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(1000L);

        setter.apply(null);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(2000L);

        when(getter.apply(configurationRead3)).thenReturn(1000L);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(2000L);
    }


    private void testTriggerMode(Function<ConfigurationRead, ConfigurationRead.TriggerMode> getter, Function<ConfigurationRead.TriggerMode, Void> setter) {
        Assertions.assertThat(getter.apply(composed)).isNull();

        setter.apply(ConfigurationRead.TriggerMode.NEVER);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationRead.TriggerMode.NEVER);

        when(getter.apply(configurationRead2)).thenReturn(ConfigurationRead.TriggerMode.ON_FAIL);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationRead.TriggerMode.NEVER);

        setter.apply(null);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationRead.TriggerMode.ON_FAIL);

        when(getter.apply(configurationRead3)).thenReturn(ConfigurationRead.TriggerMode.NEVER);
        Assertions.assertThat(getter.apply(composed)).isEqualTo(ConfigurationRead.TriggerMode.ON_FAIL);
    }

    @Test
    public void webDriver() {
        testString(new Function<ConfigurationRead, String>() {
            @Override
            public String apply(ConfigurationRead input) {
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
        testString(new Function<ConfigurationRead, String>() {
            @Override
            public String apply(ConfigurationRead input) {
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
        testLong(new Function<ConfigurationRead, Long>() {
            @Override
            public Long apply(ConfigurationRead input) {
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
        testLong(new Function<ConfigurationRead, Long>() {
            @Override
            public Long apply(ConfigurationRead input) {
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
        testLong(new Function<ConfigurationRead, Long>() {
            @Override
            public Long apply(ConfigurationRead input) {
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
        testString(new Function<ConfigurationRead, String>() {
            @Override
            public String apply(ConfigurationRead input) {
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
        testString(new Function<ConfigurationRead, String>() {
            @Override
            public String apply(ConfigurationRead input) {
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
        testTriggerMode(new Function<ConfigurationRead, ConfigurationRead.TriggerMode>() {
            @Override
            public ConfigurationRead.TriggerMode apply(ConfigurationRead input) {
                return input.getScreenshotMode();
            }
        }, new Function<ConfigurationRead.TriggerMode, Void>() {
            @Override
            public Void apply(ConfigurationRead.TriggerMode input) {
                composed.setScreenshotMode(input);
                return null;
            }
        });
    }

    @Test
    public void htmlDumpMode() {
        testTriggerMode(new Function<ConfigurationRead, ConfigurationRead.TriggerMode>() {
            @Override
            public ConfigurationRead.TriggerMode apply(ConfigurationRead input) {
                return input.getHtmlDumpMode();
            }
        }, new Function<ConfigurationRead.TriggerMode, Void>() {
            @Override
            public Void apply(ConfigurationRead.TriggerMode input) {
                composed.setHtmlDumpMode(input);
                return null;
            }
        });
    }
}
