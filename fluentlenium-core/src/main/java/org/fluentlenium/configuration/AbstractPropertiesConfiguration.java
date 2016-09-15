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
public abstract class AbstractPropertiesConfiguration implements ConfigurationProperties {
    public static final String PROPERTIES_PREFIX = "fluentlenium.";

    private final String[] prefixes;

    private final JsonToBeanConverter jsonConverter = new JsonToBeanConverter();

    protected AbstractPropertiesConfiguration() {
        this(PROPERTIES_PREFIX);
    }

    protected AbstractPropertiesConfiguration(String... prefixes) {
        if (prefixes.length == 0) throw new IllegalArgumentException("Prefixes should be defined");
        this.prefixes = prefixes;
    }

    protected abstract String getPropertyImpl(String propertyName);

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
        if (Strings.isNullOrEmpty(property) || "null".equalsIgnoreCase(property)) return false;
        return true;
    }

    private String getStringProperty(String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) return null;
        return property;
    }

    private Long getLongProperty(String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) return null;
        try {
            return Long.parseLong(property);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean getBooleanProperty(String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) return null;
        return Boolean.parseBoolean(property);
    }

    private <T extends Enum<T>> T getEnumProperty(Class<T> enumClass, String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) return null;
        if (propertyName.equalsIgnoreCase("DEFAULT")) return null;
        T enumValue = (T) Enum.valueOf(enumClass, property);
        return enumValue;
    }

    private <T> Class<T> getClassProperty(Class<T> clazz, String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) return null;
        try {
            Class<?> propertyClass = Class.forName(property);
            if (clazz.isAssignableFrom(propertyClass)) {
                return (Class<T>) propertyClass;
            }
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    protected URL newURL(String url) throws MalformedURLException {
        return new URL(url);
    }

    private Capabilities getCapabilitiesProperty(String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) return null;
        try {
            URL url = newURL(property);
            try {
                property = IOUtils.toString(url, Charset.defaultCharset());
            } catch (IOException e) {
                throw new ConfigurationException("Can't read Capabilities defined at " + url);
            }
        } catch (MalformedURLException e) {
            // This is not an URL. Consider property as JSON.
        }
        CapabilitiesFactory factory = (CapabilitiesFactory) CapabilitiesRegistry.INSTANCE.get(property);
        if (factory != null) {
            return factory.newCapabilities();
        }
        try {
            return jsonConverter.convert(DesiredCapabilities.class, property);
        } catch (JsonException e) {
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
}
