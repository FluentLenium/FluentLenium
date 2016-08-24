package org.fluentlenium.configuration;

import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.JsonException;
import org.openqa.selenium.remote.JsonToBeanConverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * {@link ConfigurationProperties} based on {@link FluentConfiguration} annotation.
 *
 * @see FluentConfiguration
 * @see ConfigurationProperties
 */
public class AnnotationConfiguration implements ConfigurationProperties {
    private final FluentConfiguration configuration;

    private final JsonToBeanConverter jsonConverter = new JsonToBeanConverter();

    public AnnotationConfiguration(Class<?> containerClass) {
        this(containerClass != null ? containerClass.getAnnotation(FluentConfiguration.class) : null);
    }

    public AnnotationConfiguration(FluentConfiguration configuration) {
        super();
        this.configuration = configuration;
    }

    public String getStringValue(String property) {
        if (Strings.isNullOrEmpty(property)) return null;
        return property;
    }

    private <T extends ConfigurationFactory> Class<T> getConfigurationFactoryClassValue(Class<T> configurationFactoryClass) {
        if (configurationFactoryClass == DefaultConfigurationFactory.class) return null;
        return configurationFactoryClass;
    }

    private Class<? extends ConfigurationProperties> getConfigurationDefaultsClassValue(Class<? extends ConfigurationProperties> configurationDefaultsClass) {
        if (configurationDefaultsClass == ConfigurationDefaults.class) return null;
        return configurationDefaultsClass;
    }

    private Capabilities getCapabilitiesValue(String property) {
        if (Strings.isNullOrEmpty(property)) return null;
        try {
            URL url = new URL(property);
            InputStream stream = null;
            try {
                stream = url.openStream();
                property = IOUtils.toString(stream);
            } catch (IOException e) {
                throw new ConfigurationException("Can't read Capabilities defined at " + url);
            } finally {
                IOUtils.closeQuietly(stream);
            }
        } catch (MalformedURLException e) {
            // This is not an URL. Consider property as JSON.
        }

        try {
            return jsonConverter.convert(DesiredCapabilities.class, property);
        } catch (JsonException e) {
            throw new ConfigurationException("Can't convert JSON Capabilities to Object.", e);
        }
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        if (configuration == null) return null;
        return getConfigurationDefaultsClassValue(configuration.configurationDefaults());
    }

    private Long getLongValue(Long property) {
        if (property < 0) return null;
        return property;
    }

    private TriggerMode getTriggerModeValue(TriggerMode triggerMode) {
        if (triggerMode == TriggerMode.DEFAULT) return null;
        return triggerMode;
    }

    private DriverLifecycle getDriverLifecycleValue(DriverLifecycle driverLifecycle) {
        if (driverLifecycle == DriverLifecycle.DEFAULT) return null;
        return driverLifecycle;
    }

    @Override
    public String getWebDriver() {
        if (configuration == null) return null;
        return getStringValue(configuration.webDriver());
    }

    @Override
    public Capabilities getCapabilities() {
        if (configuration == null) return null;
        return getCapabilitiesValue(configuration.capabilities());
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        if (configuration == null) return null;
        return getConfigurationFactoryClassValue(configuration.configurationFactory());
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        if (configuration == null) return null;
        return getDriverLifecycleValue(configuration.driverLifecycle());
    }

    @Override
    public Boolean getDeleteCookies() {
        if (configuration == null) return null;
        return configuration.deleteCookies().asBoolean();
    }

    @Override
    public String getBaseUrl() {
        if (configuration == null) return null;
        return getStringValue(configuration.baseUrl());
    }

    @Override
    public Long getPageLoadTimeout() {
        if (configuration == null) return null;
        return getLongValue(configuration.pageLoadTimeout());
    }

    @Override
    public Long getImplicitlyWait() {
        if (configuration == null) return null;
        return getLongValue(configuration.implicitlyWait());
    }

    @Override
    public Long getScriptTimeout() {
        if (configuration == null) return null;
        return getLongValue(configuration.scriptTimeout());
    }

    @Override
    public Boolean getEventsEnabled() {
        if (configuration == null) return null;
        return configuration.eventsEnabled().asBoolean();
    }

    @Override
    public String getScreenshotPath() {
        if (configuration == null) return null;
        return getStringValue(configuration.screenshotPath());
    }

    @Override
    public String getHtmlDumpPath() {
        if (configuration == null) return null;
        return getStringValue(configuration.htmlDumpPath());
    }

    @Override
    public TriggerMode getScreenshotMode() {
        if (configuration == null) return null;
        return getTriggerModeValue(configuration.screenshotMode());
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        if (configuration == null) return null;
        return getTriggerModeValue(configuration.htmlDumpMode());
    }
}
