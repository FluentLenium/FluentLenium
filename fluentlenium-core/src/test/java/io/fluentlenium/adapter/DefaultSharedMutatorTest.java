package io.fluentlenium.adapter;

import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DefaultSharedMutatorTest {

    @Test
    public void testDefaultMutator() {
        DefaultSharedMutator sharedMutator = new DefaultSharedMutator();

        Class<?> testClass = Object.class;
        String testName = "test";
        DriverLifecycle driverLifecycle = DriverLifecycle.METHOD;

        SharedMutator.EffectiveParameters<?> parameters = sharedMutator
                .getEffectiveParameters(testClass, testName, driverLifecycle);

        Assertions.assertThat(parameters.getTestClass()).isSameAs(testClass);
        Assertions.assertThat(parameters.getTestName()).isSameAs(testName);
        Assertions.assertThat(parameters.getDriverLifecycle()).isSameAs(driverLifecycle);
    }
}
