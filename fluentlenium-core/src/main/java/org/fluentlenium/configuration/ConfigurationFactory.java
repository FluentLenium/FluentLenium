package org.fluentlenium.configuration;

public interface ConfigurationFactory {
    Configuration newConfiguration(Class<?> containerClass);
}
