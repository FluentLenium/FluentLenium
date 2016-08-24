package org.fluentlenium.cucumber.adapter;

import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;

/**
 * Cucumber implementation of {@link SharedMutator}, replacing testClass with a null reference as it doesn't make sense
 * to link {@link org.openqa.selenium.WebDriver} instances with classes defining Step.
 */
public class FluentCucumberSharedMutator implements SharedMutator {
    @Override
    public <T> EffectiveParameters<T> getEffectiveParameters(Class<T> testClass, String testName, DriverLifecycle driverLifecycle) {
        if (driverLifecycle == DriverLifecycle.METHOD) {
            // It has no sense to use METHOD lifecycle in Cucumber.
            driverLifecycle = DriverLifecycle.CLASS;
        }
        return new EffectiveParameters<>(null, testName, driverLifecycle);
    }
}
