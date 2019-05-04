package org.fluentlenium.configuration;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;

import java.util.Locale;

/**
 * Abstract properties configuration.
 */
public class PropertiesBackendConfiguration extends BaseConfiguration implements ConfigurationProperties {
    /**
     * Default properties prefix.
     */
    static final String PROPERTIES_PREFIX = "fluentlenium.";
    private final String[] prefixes;
    private CapabilitiesConfigurationPropertyRetriever capabilitiesRetriever;
    private PropertiesBackend propertiesBackend;

    /**
     * Creates a new abstract properties configuration, using default properties prefix.
     *
     * @param propertiesReader properties reader
     * @see #PROPERTIES_PREFIX
     */
    PropertiesBackendConfiguration(PropertiesBackend propertiesReader) {
        this(propertiesReader, PROPERTIES_PREFIX);
    }

    /**
     * Creates a new abstract properties configuration, using given properties prefixes.
     *
     * @param propertiesReader properties reader
     * @param prefixes         array of allowed prefixes
     */
    PropertiesBackendConfiguration(PropertiesBackend propertiesReader, String... prefixes) {
        if (prefixes.length == 0) {
            throw new IllegalArgumentException("At least one configuration property prefix should be defined."
                    + "Alternatively you can call the PropertiesBackendConfiguration(PropertiesBackend) constructor"
                    + "to have the default 'fluentlenium.' prefix configured.");
        }
        propertiesBackend = propertiesReader;
        this.prefixes = prefixes;
        capabilitiesRetriever = new CapabilitiesConfigurationPropertyRetriever();
    }

    /**
     * Get the underlying properties backend.
     *
     * @return properties backend
     */
    PropertiesBackend getPropertiesBackend() {
        return propertiesBackend;
    }

    /**
     * Set the underlying properties backend.
     *
     * @param propertiesBackend properties backend
     */
    void setPropertiesBackend(PropertiesBackend propertiesBackend) {
        this.propertiesBackend = propertiesBackend;
    }

    /**
     * Get the property value used by underlying property storage.
     *
     * @param propertyName property key
     * @return property value
     */
    private String getPropertyImpl(String propertyName) {
        return propertiesBackend.getProperty(propertyName);
    }

    private String getProperty(String propertyName) {
        for (String prefix : prefixes) {
            String property = getPropertyImpl(prefix + propertyName);
            if (property != null) {
                return property;
            }
        }
        return null;
    }

    private boolean isValidProperty(String property) {
        return !StringUtils.isEmpty(property) && !"null".equalsIgnoreCase(property);
    }

    private String getStringProperty(String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) {
            return null;
        }
        return property;
    }

    private Long getLongProperty(String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property) || property == null) {
            return null;
        }
        try {
            return Long.parseLong(property);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean getBooleanProperty(String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property) || property == null) {
            return null;
        }
        return Boolean.parseBoolean(property);
    }

    private <T extends Enum<T>> T getEnumProperty(Class<T> enumClass, String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property) || property == null) {
            return null;
        }
        if ("DEFAULT".equalsIgnoreCase(propertyName)) {
            return null;
        }
        return Enum.valueOf(enumClass, property.toUpperCase(Locale.ENGLISH));
    }

    private <T> Class<T> getClassProperty(Class<T> clazz, String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property) || property == null) {
            return null;
        }
        try {
            Class<?> propertyClass = Class.forName(property);
            if (clazz.isAssignableFrom(propertyClass)) {
                return (Class<T>) propertyClass;
            }
        } catch (ClassNotFoundException e) { // NOPMD EmptyCatchBlock
        }
        return null;
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getClassProperty(ConfigurationFactory.class, "configurationFactory");
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getClassProperty(ConfigurationDefaults.class, "configurationDefaults");
    }

    @Override
    public String getWebDriver() {
        return getStringProperty("webDriver");
    }

    @Override
    public String getRemoteUrl() {
        return getStringProperty("remoteUrl");
    }

    @Override
    public Capabilities getCapabilities() {
        String capabilities = getProperty("capabilities");
        if (isValidProperty(capabilities)) {
            return capabilitiesRetriever.getCapabilitiesProperty(capabilities, getGlobalConfiguration());
        }
        return null;
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return getEnumProperty(DriverLifecycle.class, "driverLifecycle");
    }

    @Override
    public Long getBrowserTimeout() {
        return getLongProperty("browserTimeout");
    }

    @Override
    public Integer getBrowserTimeoutRetries() {
        Long browserTimeout = getLongProperty("browserTimeout");
        return (browserTimeout == null) ? null : browserTimeout.intValue();
    }

    @Override
    public Boolean getDeleteCookies() {
        return getBooleanProperty("deleteCookies");
    }

    @Override
    public String getBaseUrl() {
        return getStringProperty("baseUrl");
    }

    @Override
    public Boolean getEventsEnabled() {
        return getBooleanProperty("eventsEnabled");
    }

    @Override
    public Long getPageLoadTimeout() {
        return getLongProperty("pageLoadTimeout");
    }

    @Override
    public Long getImplicitlyWait() {
        return getLongProperty("implicitlyWait");
    }

    @Override
    public Long getScriptTimeout() {
        return getLongProperty("scriptTimeout");
    }

    @Override
    public Long getAwaitAtMost() {
        return getLongProperty("awaitAtMost");
    }

    @Override
    public Long getAwaitPollingEvery() {
        return getLongProperty("awaitPollingEvery");
    }

    @Override
    public String getScreenshotPath() {
        return getStringProperty("screenshotPath");
    }

    @Override
    public String getHtmlDumpPath() {
        return getStringProperty("htmlDumpPath");
    }

    @Override
    public TriggerMode getScreenshotMode() {
        return getEnumProperty(TriggerMode.class, "screenshotMode");
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return getEnumProperty(TriggerMode.class, "htmlDumpMode");
    }

    @Override
    public String getCustomProperty(String propertyName) {
        return getStringProperty(propertyName);
    }

    @VisibleForTesting
    void setCapabilitiesRetriever(CapabilitiesConfigurationPropertyRetriever capabilitiesRetriever) {
        this.capabilitiesRetriever = capabilitiesRetriever;
    }
}
