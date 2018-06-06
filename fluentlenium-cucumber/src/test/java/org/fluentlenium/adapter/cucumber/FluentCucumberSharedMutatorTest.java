package org.fluentlenium.adapter.cucumber;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.configuration.ConfigurationException;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.Before;
import org.junit.Test;

public class FluentCucumberSharedMutatorTest {
    private FluentCucumberSharedMutator sharedMutator;

    @Before
    public void before() {
        sharedMutator = new FluentCucumberSharedMutator();
    }

    @Test
    public void testCucumberMutator() {
        Class<?> testClass = Object.class;
        String testName = "test";
        DriverLifecycle driverLifecycle = DriverLifecycle.JVM;

        SharedMutator.EffectiveParameters<?> parameters = sharedMutator
                .getEffectiveParameters(testClass, testName, driverLifecycle);

        Assertions.assertThat(parameters.getTestClass()).isNull();
        Assertions.assertThat(parameters.getTestName()).isEqualTo(testName);
        Assertions.assertThat(parameters.getDriverLifecycle()).isEqualTo(DriverLifecycle.JVM);
    }

    @Test
    public void testCucumberMutatorWithClassLifecycle() {
        Class<?> testClass = Object.class;
        String testName = "test";
        DriverLifecycle driverLifecycle = DriverLifecycle.CLASS;

        Assertions.assertThatThrownBy(() -> sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle))
                .isExactlyInstanceOf(ConfigurationException.class)
                .hasMessage("Cucumber doesn't support CLASS driverLifecycle.");
    }
}
