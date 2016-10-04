package org.fluentlenium.adapter;

import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.Test;

public class DefaultSharedMutatorTest {

    @Test
    public void testDefaultMutator() {
        final DefaultSharedMutator sharedMutator = new DefaultSharedMutator();

        final Class<?> testClass = Object.class;
        final String testName = "test";
        final DriverLifecycle driverLifecycle = DriverLifecycle.METHOD;

        final SharedMutator.EffectiveParameters<?> parameters = sharedMutator
                .getEffectiveParameters(testClass, testName, driverLifecycle);

        Assertions.assertThat(parameters.getTestClass()).isSameAs(testClass);
        Assertions.assertThat(parameters.getTestName()).isSameAs(testName);
        Assertions.assertThat(parameters.getDriverLifecycle()).isSameAs(driverLifecycle);
    }
}
