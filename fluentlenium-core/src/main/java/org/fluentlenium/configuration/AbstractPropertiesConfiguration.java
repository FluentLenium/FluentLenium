package org.fluentlenium.configuration;

import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.JsonException;
import org.openqa.selenium.remote.JsonToBeanConverter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Abstract properties configuration.
 */
public abstract class AbstractPropertiesConfiguration extends BaseConfiguration implements ConfigurationProperties {
    /**
     * Default properties prefix.
     */
    public static final String PROPERTIES_PREFIX = "fluentlenium.";

    private final String[] prefixes;

    private final JsonToBeanConverter jsonConverter = new JsonToBeanConverter();

    /**
     * Creates a new abstract properties configuration, using default properties prefix.
     */
    protected AbstractPropertiesConfiguration() {
        this(PROPERTIES_PREFIX);
    }

    /**
     * Creates a new abstract properties configuration, using given properties prefixes.
     *
     * @param prefixes array of allowed prefixes
     */
    protected AbstractPropertiesConfiguration(final String... prefixes) {
        if (prefixes.length == 0) {
            throw new IllegalArgumentException("Prefixes should be defined");
        }
        this.prefixes = prefixes;
    }

    /**
     * Get the property value used by underlying property storage.
     *
     * @param propertyName property key
     * @return property value
     */
    protected abstract String getPropertyImpl(String propertyName);

    private String getProperty(final String propertyName) {
        for (final String prefix : prefixes) {
            final String property = getPropertyImpl(prefix + propertyName);
            if (property != null) {
                return property;
            }
        }
        return null;
    }

    private boolean isValidProperty(final String property) {
        return !Strings.isNullOrEmpty(property) && !"null".equalsIgnoreCase(property);
    }

    private String getStringProperty(final String propertyName) {
        final String property = getProperty(propertyName);
        if (!isValidProperty(property)) {
            return null;
        }
        return property;
    }

    private Long getLongProperty(final String propertyName) {
        final String property = getProperty(propertyName);
        if (!isValidProperty(property)) {
            return null;
        }
        try {
            return Long.parseLong(property);
        } catch (final NumberFormatException e) {
            return null;
        }
    }

    private Boolean getBooleanProperty(final String propertyName) {
        final String property = getProperty(propertyName);
        if (!isValidProperty(property)) {
            return null;
        }
        return Boolean.parseBoolean(property);
    }

    private <T extends Enum<T>> T getEnumProperty(final Class<T> enumClass, final String propertyName) {
        final String property = getProperty(propertyName);
        if (!isValidProperty(property)) {
            return null;
        }
        if ("DEFAULT".equalsIgnoreCase(propertyName)) {
            return null;
        }
        return (T) Enum.valueOf(enumClass, property);
    }

    private <T> Class<T> getClassProperty(final Class<T> clazz, final String propertyName) {
        final String property = getProperty(propertyName);
        if (!isValidProperty(property)) {
            return null;
        }
        try {
            final Class<?> propertyClass = Class.forName(property);
            if (clazz.isAssignableFrom(propertyClass)) {
                return (Class<T>) propertyClass;
            }
        } catch (final ClassNotFoundException e) { // NOPMD EmptyCatchBlock
        }
        return null;
    }

    /**
     * Creates a new URL from it's representation
     *
     * @param url url
     * @return URL object
     * @throws MalformedURLException if given url is not valid
     */
    protected URL newURL(final String url) throws MalformedURLException {
        return new URL(url);
    }

    private Capabilities getCapabilitiesProperty(final String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) {
            return null;
        }
        try {
            final URL url = newURL(property);
            try {
                property = IOUtils.toString(url, Charset.defaultCharset());
            } catch (final IOException e) {
                throw new ConfigurationException("Can't read Capabilities defined at " + url, e);
            }
        } catch (final MalformedURLException e) { // NOPMD EmptyCatchBlock PreserveStackTrace
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
        return getCapabilitiesProperty("capabilities");
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return getEnumProperty(DriverLifecycle.class, getStringProperty("driverLifecycle"));
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
    public String getCustomProperty(final String propertyName) {
        return getStringProperty(propertyName);
    }
}
