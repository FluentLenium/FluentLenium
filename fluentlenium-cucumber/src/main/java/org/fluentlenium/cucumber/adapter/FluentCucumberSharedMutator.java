package org.fluentlenium.cucumber.adapter;

import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.util.SharedDriverStrategy;

/**
 * Cucumber implementation of {@link SharedMutator}, replacing testClass with a null reference as it doesn't make sense
 * to link {@link org.openqa.selenium.WebDriver} instances with classes defining Step.
 */
public class FluentCucumberSharedMutator implements SharedMutator {
    @Override
    public <T> EffectiveParameters<T> getEffectiveParameters(Class<T> testClass, String testName, SharedDriverStrategy strategy) {
        return new EffectiveParameters<>(null, testName, strategy);
    }
}
