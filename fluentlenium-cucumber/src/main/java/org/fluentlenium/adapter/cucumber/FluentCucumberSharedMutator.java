package org.fluentlenium.adapter.cucumber;

import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.configuration.ConfigurationException;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;

/**
 * Cucumber implementation of {@link SharedMutator}, replacing {@code testClass} with a null reference
 * as it doesn't make sense in case of Cucumber tests, and raising a
 * {@link org.fluentlenium.configuration.ConfigurationException} when {@link DriverLifecycle#CLASS} is given
 * to link {@link org.openqa.selenium.WebDriver} instances with classes defining step definitions.
 */
public class FluentCucumberSharedMutator implements SharedMutator {
    @Override
    public <T> EffectiveParameters<T> getEffectiveParameters(Class<T> testClass, String testName,
            DriverLifecycle driverLifecycle) {
        if (driverLifecycle == DriverLifecycle.CLASS) {
            throw new ConfigurationException("Cucumber doesn't support CLASS driverLifecycle.");
        }
        return new EffectiveParameters<>(null, testName, driverLifecycle);
    }
}
