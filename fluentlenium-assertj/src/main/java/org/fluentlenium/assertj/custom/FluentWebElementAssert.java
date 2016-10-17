package org.fluentlenium.assertj.custom;

import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.domain.FluentWebElement;

public class FluentWebElementAssert extends FluentConditionsAssert<FluentWebElementAssert, FluentConditions> {

    public FluentWebElementAssert(final FluentWebElement actual) {
        super(actual.conditions(), FluentConditions.class);
    }
}
