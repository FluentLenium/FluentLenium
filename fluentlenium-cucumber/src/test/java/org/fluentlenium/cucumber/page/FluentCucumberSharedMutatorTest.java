package org.fluentlenium.cucumber.page;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.DefaultSharedMutator;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.util.SharedDriverStrategy;
import org.fluentlenium.cucumber.adapter.FluentCucumberSharedMutator;
import org.junit.Test;

public class FluentCucumberSharedMutatorTest {
    @Test
    public void testCucumberMutator() {
        FluentCucumberSharedMutator sharedMutator = new FluentCucumberSharedMutator();

        Class<?> testClass = Object.class;
        String testName = "test";
        SharedDriverStrategy strategy = SharedDriverStrategy.PER_METHOD;

        SharedMutator.EffectiveParameters<?> parameters = sharedMutator.getEffectiveParameters(testClass, testName, strategy);

        Assertions.assertThat(parameters.getTestClass()).isNull();
        Assertions.assertThat(parameters.getTestName()).isSameAs(testName);
        Assertions.assertThat(parameters.getStrategy()).isSameAs(strategy);
    }
}
