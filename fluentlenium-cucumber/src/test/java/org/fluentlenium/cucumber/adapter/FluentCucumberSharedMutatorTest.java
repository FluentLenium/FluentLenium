package org.fluentlenium.cucumber.adapter;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.configuration.ConfigurationException;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.cucumber.adapter.FluentCucumberSharedMutator;
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
        final Class<?> testClass = Object.class;
        final String testName = "test";
        DriverLifecycle driverLifecycle = DriverLifecycle.JVM;

        SharedMutator.EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle);

        Assertions.assertThat(parameters.getTestClass()).isNull();
        Assertions.assertThat(parameters.getTestName()).isEqualTo(testName);
        Assertions.assertThat(parameters.getDriverLifecycle()).isEqualTo(DriverLifecycle.JVM);
    }

    @Test
    public void testCucumberMutatorWithClassLifecycle() {
        final Class<?> testClass = Object.class;
        final String testName = "test";
        final DriverLifecycle driverLifecycle = DriverLifecycle.CLASS;

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle);
            }
        }).isExactlyInstanceOf(ConfigurationException.class).hasMessage("Cucumber doesn't support CLASS driverLifecycle.");
    }
}
