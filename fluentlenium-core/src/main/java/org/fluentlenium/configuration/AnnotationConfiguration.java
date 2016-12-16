package org.fluentlenium.configuration;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.JsonException;
import org.openqa.selenium.remote.JsonToBeanConverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link ConfigurationProperties} based on {@link FluentConfiguration} annotation.
 *
 * @see FluentConfiguration
 * @see ConfigurationProperties
 */
public class AnnotationConfiguration extends BaseConfiguration implements ConfigurationProperties {
    private final FluentConfiguration configuration;

    private final Map<String, String> customProperties = new HashMap<>();

    private final JsonToBeanConverter jsonConverter = new JsonToBeanConverter();

    /**
     * Creates a new annotation based configuration.
     *
     * @param containerClass container class on which to read annotation
     */
    public AnnotationConfiguration(final Class<?> containerClass) {
        this(containerClass == null ? null : containerClass.getAnnotation(FluentConfiguration.class));
    }

    /**
     * Creates a new annotation based configuration.
     *
     * @param configuration annotation to read values from
     */
    public AnnotationConfiguration(final FluentConfiguration configuration) {
        super();
        this.configuration = configuration;

        if (this.configuration != null) {
            final CustomProperty[] custom = this.configuration.custom();
            if (custom != null) {
                for (final CustomProperty customProperty : custom) {
                    customProperties.put(customProperty.name(), customProperty.value());
                }
            }
        }
    }

    private String getStringValue(final String property) {
        if (StringUtils.isEmpty(property)) {
            return null;
        }
        return property;
    }

    private <T extends ConfigurationFactory> Class<T> getConfigurationFactoryClassValue(
            final Class<T> configurationFactoryClass) {
        if (configurationFactoryClass == DefaultConfigurationFactory.class) {
            return null;
        }
        return configurationFactoryClass;
    }

    private Class<? extends ConfigurationProperties> getConfigurationDefaultsClassValue(
            final Class<? extends ConfigurationProperties> configurationDefaultsClass) {
        if (configurationDefaultsClass == ConfigurationDefaults.class) {
            return null;
        }
        return configurationDefaultsClass;
    }

    private Capabilities getCapabilitiesValue(String property) {
        if (StringUtils.isEmpty(property)) {
            return null;
        }
        try {
            final URL url = new URL(property);
            InputStream stream = null;
            try {
                stream = url.openStream();
                property = IOUtils.toString(stream, Charset.defaultCharset());
            } catch (final IOException e) {
                throw new ConfigurationException("Can't read Capabilities defined at " + url, e);
            } finally {
                IOUtils.closeQuietly(stream);
            }
        } catch (final MalformedURLException e) { // NOPMD EmptyCatchBlock
            // This is not an URL. Consider property as JSON.
        }
        final CapabilitiesFactory factory = (CapabilitiesFactory) CapabilitiesRegistry.INSTANCE.get(property);
        if (factory != null) {
            return factory.newCapabilities(getGlobalConfiguration());
        }

        try {
            return jsonConverter.convert(DesiredCapabilities.class, property);
        } catch (final JsonException e) {
            throw new ConfigurationException("Can't convert JSON Capabilities to Object.", e);
        }
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        if (configuration == null) {
            return null;
        }
        return getConfigurationDefaultsClassValue(configuration.configurationDefaults());
    }

    private Long getLongValue(final Long property) {
        if (property < 0) {
            return null;
        }
        return property;
    }

    private TriggerMode getTriggerModeValue(final TriggerMode triggerMode) {
        if (triggerMode == TriggerMode.DEFAULT) {
            return null;
        }
        return triggerMode;
    }

    private DriverLifecycle getDriverLifecycleValue(final DriverLifecycle driverLifecycle) {
        if (driverLifecycle == DriverLifecycle.DEFAULT) {
            return null;
        }
        return driverLifecycle;
    }

    @Override
    public String getWebDriver() {
        if (configuration == null) {
            return null;
        }
        return getStringValue(configuration.webDriver());
    }

    @Override
    public String getRemoteUrl() {
        if (configuration == null) {
            return null;
        }
        return getStringValue(configuration.remoteUrl());
    }

    @Override
    public Capabilities getCapabilities() {
        if (configuration == null) {
            return null;
        }
        return getCapabilitiesValue(configuration.capabilities());
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        if (configuration == null) {
            return null;
        }
        return getConfigurationFactoryClassValue(configuration.configurationFactory());
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        if (configuration == null) {
            return null;
        }
        return getDriverLifecycleValue(configuration.driverLifecycle());
    }

    @Override
    public Boolean getDeleteCookies() {
        if (configuration == null) {
            return null;
        }
        return configuration.deleteCookies().asBoolean();
    }

    @Override
    public String getBaseUrl() {
        if (configuration == null) {
            return null;
        }
        return getStringValue(configuration.baseUrl());
    }

    @Override
    public Long getPageLoadTimeout() {
        if (configuration == null) {
            return null;
        }
        return getLongValue(configuration.pageLoadTimeout());
    }

    @Override
    public Long getImplicitlyWait() {
        if (configuration == null) {
            return null;
        }
        return getLongValue(configuration.implicitlyWait());
    }

    @Override
    public Long getScriptTimeout() {
        if (configuration == null) {
            return null;
        }
        return getLongValue(configuration.scriptTimeout());
    }

    @Override
    public Long getAwaitAtMost() {
        if (configuration == null) {
            return null;
        }
        return getLongValue(configuration.awaitAtMost());
    }

    @Override
    public Long getAwaitPollingEvery() {
        if (configuration == null) {
            return null;
        }
        return getLongValue(configuration.awaitPollingEvery());
    }

    @Override
    public Boolean getEventsEnabled() {
        if (configuration == null) {
            return null;
        }
        return configuration.eventsEnabled().asBoolean();
    }

    @Override
    public String getScreenshotPath() {
        if (configuration == null) {
            return null;
        }
        return getStringValue(configuration.screenshotPath());
    }

    @Override
    public String getHtmlDumpPath() {
        if (configuration == null) {
            return null;
        }
        return getStringValue(configuration.htmlDumpPath());
    }

    @Override
    public TriggerMode getScreenshotMode() {
        if (configuration == null) {
            return null;
        }
        return getTriggerModeValue(configuration.screenshotMode());
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        if (configuration == null) {
            return null;
        }
        return getTriggerModeValue(configuration.htmlDumpMode());
    }

    @Override
    public String getCustomProperty(final String propertyName) {
        return customProperties.get(propertyName);
    }
}
