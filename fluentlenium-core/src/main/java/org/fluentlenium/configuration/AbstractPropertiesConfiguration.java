package org.fluentlenium.configuration;

import com.google.common.base.Strings;

public abstract class AbstractPropertiesConfiguration implements ConfigurationRead {
    private final String[] prefixes;

    protected AbstractPropertiesConfiguration() {
        this("fluentlenium.");
    }

    protected AbstractPropertiesConfiguration(String... prefixes) {
        this.prefixes = prefixes;
    }

    protected abstract String getPropertyImpl(String propertyName);

    private String getProperty(String propertyName) {
        if ((prefixes.length) > 0) {
            for (String prefix : prefixes) {
                String property = getPropertyImpl(prefix + propertyName);
                if (property != null) {
                    return property;
                }
            }
            return null;
        } else {
            return getPropertyImpl(propertyName);
        }
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

    private <T extends Enum<T>> T getEnumProperty(Class<T> enumClass, String propertyName) {
        String property = getProperty(propertyName);
        if (!isValidProperty(property)) return null;
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

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getClassProperty(ConfigurationFactory.class, "configurationFactory");
    }

    @Override
    public String getWebDriver() {
        return getStringProperty("webDriver");
    }

    @Override
    public String getBaseUrl() {
        return getStringProperty("baseUrl");
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
