package org.fluentlenium.cucumber.page;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.cucumber.adapter.FluentCucumberSharedMutator;
import org.junit.Test;

public class FluentCucumberSharedMutatorTest {
    @Test
    public void testCucumberMutator() {
        FluentCucumberSharedMutator sharedMutator = new FluentCucumberSharedMutator();

        Class<?> testClass = Object.class;
        String testName = "test";
        DriverLifecycle driverLifecycle = DriverLifecycle.METHOD;

        SharedMutator.EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle);

        Assertions.assertThat(parameters.getTestClass()).isNull();
        Assertions.assertThat(parameters.getTestName()).isEqualTo(testName);
        Assertions.assertThat(parameters.getDriverLifecycle()).isEqualTo(DriverLifecycle.CLASS);
    }
}
