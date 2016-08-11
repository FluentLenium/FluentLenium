package org.fluentlenium.adapter;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.junit.Test;

public class DefaultSharedMutatorTest {

    @Test
    public void testDefaultMutator() {
        DefaultSharedMutator sharedMutator = new DefaultSharedMutator();

        Class<?> testClass = Object.class;
        String testName = "test";
        SharedDriverStrategy strategy = SharedDriverStrategy.PER_METHOD;

        SharedMutator.EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName, strategy);

        Assertions.assertThat(parameters.getTestClass()).isSameAs(testClass);
        Assertions.assertThat(parameters.getTestName()).isSameAs(testName);
        Assertions.assertThat(parameters.getStrategy()).isSameAs(strategy);
    }
}
