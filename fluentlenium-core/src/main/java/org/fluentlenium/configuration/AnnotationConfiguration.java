package org.fluentlenium.configuration;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.json.JsonException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * {@link ConfigurationProperties} based on {@link FluentConfiguration} annotation.
 *
 * @see FluentConfiguration
 * @see ConfigurationProperties
 */
public class AnnotationConfiguration extends BaseConfiguration implements ConfigurationProperties {
    private final FluentConfiguration configuration;

    private final Map<String, String> customProperties = new HashMap<>();

    private final Json jsonConverter = new Json();

    /**
     * Creates a new annotation based configuration.
     *
     * @param containerClass container class on which to read annotation
     */
    public AnnotationConfiguration(Class<?> containerClass) {
        this(containerClass == null ? null : containerClass.getAnnotation(FluentConfiguration.class));
    }

    /**
     * Creates a new annotation based configuration.
     *
     * @param configuration annotation to read values from
     */
    public AnnotationConfiguration(FluentConfiguration configuration) {
        super();
        this.configuration = configuration;

        if (this.configuration != null) {
            CustomProperty[] custom = this.configuration.custom();
            if (custom != null) {
                for (CustomProperty customProperty : custom) {
                    customProperties.put(customProperty.name(), customProperty.value());
                }
            }
        }
    }

    private String getStringValue(String property) {
        return StringUtils.isEmpty(property) ? null : property;
    }

    private <T extends ConfigurationFactory> Class<T> getConfigurationFactoryClassValue(Class<T> configurationFactoryClass) {
        if (configurationFactoryClass == DefaultConfigurationFactory.class) {
            return null;
        }
        return configurationFactoryClass;
    }

    private Class<? extends ConfigurationProperties> getConfigurationDefaultsClassValue(
        Class<? extends ConfigurationProperties> configurationDefaultsClass) {
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
            URL url = new URL(property);
            try (InputStream stream = url.openStream()) {
                property = IOUtils.toString(stream, Charset.defaultCharset());
            } catch (IOException e) {
                throw new ConfigurationException("Can't read Capabilities defined at " + url, e);
            }
        } catch (MalformedURLException e) { // NOPMD EmptyCatchBlock
            // This is not a URL. Consider property as JSON.
        }
        CapabilitiesFactory factory = (CapabilitiesFactory) CapabilitiesRegistry.INSTANCE.get(property);
        if (factory != null) {
            return factory.newCapabilities(getGlobalConfiguration());
        }

        try {
            return jsonConverter.toType(property, DesiredCapabilities.class);
        } catch (JsonException e) {
            throw new ConfigurationException("Can't convert JSON Capabilities to Object.", e);
        }
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getConfig(() -> getConfigurationDefaultsClassValue(configuration.configurationDefaults()));
    }

    private Long getLongValue(Long property) {
        return property < 0 ? null : property;
    }

    private TriggerMode getTriggerModeValue(TriggerMode triggerMode) {
        return triggerMode == TriggerMode.DEFAULT ? null : triggerMode;
    }

    private DriverLifecycle getDriverLifecycleValue(DriverLifecycle driverLifecycle) {
        return driverLifecycle == DriverLifecycle.DEFAULT ? null : driverLifecycle;
    }

    @Override
    public String getWebDriver() {
        return getConfig(() -> getStringValue(configuration.webDriver()));
    }

    @Override
    public String getRemoteUrl() {
        return getConfig(() -> getStringValue(configuration.remoteUrl()));
    }

    @Override
    public Capabilities getCapabilities() {
        return getConfig(() -> getCapabilitiesValue(configuration.capabilities()));
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getConfig(() -> getConfigurationFactoryClassValue(configuration.configurationFactory()));
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return getConfig(() -> getDriverLifecycleValue(configuration.driverLifecycle()));
    }

    @Override
    public Long getBrowserTimeout() {
        //Don't change this to method reference because it evaluates configuration before passing the argument, thus throw NPE.
        return getConfig(() -> configuration.browserTimeout());
    }

    @Override
    public Integer getBrowserTimeoutRetries() {
        //Don't change this to method reference because it evaluates configuration before passing the argument, thus throw NPE.
        return getConfig(() -> configuration.browserTimeoutRetries());
    }

    @Override
    public Boolean getDeleteCookies() {
        return getConfig(() -> configuration.deleteCookies().asBoolean());
    }

    @Override
    public String getBaseUrl() {
        return getConfig(() -> getStringValue(configuration.baseUrl()));
    }

    @Override
    public Long getPageLoadTimeout() {
        return getConfig(() -> getLongValue(configuration.pageLoadTimeout()));
    }

    @Override
    public Long getImplicitlyWait() {
        return getConfig(() -> getLongValue(configuration.implicitlyWait()));
    }

    @Override
    public Long getScriptTimeout() {
        return getConfig(() -> getLongValue(configuration.scriptTimeout()));
    }

    @Override
    public Long getAwaitAtMost() {
        return getConfig(() -> getLongValue(configuration.awaitAtMost()));
    }

    @Override
    public Long getAwaitPollingEvery() {
        return getConfig(() -> getLongValue(configuration.awaitPollingEvery()));
    }

    @Override
    public Boolean getEventsEnabled() {
        return getConfig(() -> configuration.eventsEnabled().asBoolean());
    }

    @Override
    public String getScreenshotPath() {
        return getConfig(() -> getStringValue(configuration.screenshotPath()));
    }

    @Override
    public String getHtmlDumpPath() {
        return getConfig(() -> getStringValue(configuration.htmlDumpPath()));
    }

    @Override
    public TriggerMode getScreenshotMode() {
        return getConfig(() -> getTriggerModeValue(configuration.screenshotMode()));
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return getConfig(() -> getTriggerModeValue(configuration.htmlDumpMode()));
    }

    @Override
    public String getCustomProperty(String propertyName) {
        return customProperties.get(propertyName);
    }

    private <T> T getConfig(Supplier<T> configSupplier) {
        return configuration == null ? null : configSupplier.get();
    }
}
