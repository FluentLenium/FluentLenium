package org.fluentlenium.adapter.cucumber.unit;

import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.cucumber.FluentCucumberSharedMutator;
import org.fluentlenium.configuration.ConfigurationException;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        assertThat(parameters.getTestClass()).isNull();
        assertThat(parameters.getTestName()).isEqualTo(testName);
        assertThat(parameters.getDriverLifecycle()).isEqualTo(DriverLifecycle.JVM);
    }

    @Test
    public void testCucumberMutatorWithClassLifecycle() {
        Class<?> testClass = Object.class;
        String testName = "test";
        DriverLifecycle driverLifecycle = DriverLifecycle.CLASS;

        assertThatThrownBy(() -> sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle))
                .isExactlyInstanceOf(ConfigurationException.class)
                .hasMessage("Cucumber doesn't support CLASS driverLifecycle.");
    }
}
