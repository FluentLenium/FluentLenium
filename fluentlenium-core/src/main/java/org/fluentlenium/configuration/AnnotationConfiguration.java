package org.fluentlenium.configuration;

import com.google.common.base.Strings;

/**
 * {@link ConfigurationProperties} based on {@link FluentConfiguration} annotation.
 *
 * @see FluentConfiguration
 * @see ConfigurationProperties
 */
public class AnnotationConfiguration implements ConfigurationProperties {
    private final FluentConfiguration configuration;

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

    private Long getLongValue(Long property) {
        if (property < 0) return null;
        return property;
    }

    private TriggerMode getTriggerModeValue(TriggerMode triggerMode) {
        if (triggerMode == TriggerMode.UNDEFINED) return null;
        return triggerMode;
    }

    @Override
    public String getWebDriver() {
        if (configuration == null) return null;
        return getStringValue(configuration.webDriver());
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        if (configuration == null) return null;
        return getConfigurationFactoryClassValue(configuration.configurationFactory());
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
