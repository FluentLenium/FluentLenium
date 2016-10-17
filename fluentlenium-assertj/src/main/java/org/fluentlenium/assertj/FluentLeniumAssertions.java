package org.fluentlenium.assertj;

import org.fluentlenium.assertj.custom.AlertAssert;
import org.fluentlenium.assertj.custom.FluentConditionsAssert;
import org.fluentlenium.assertj.custom.FluentListAssert;
import org.fluentlenium.assertj.custom.FluentWebElementAssert;
import org.fluentlenium.assertj.custom.OldFluentWebElementAssert;
import org.fluentlenium.assertj.custom.PageAssert;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.alert.AlertImpl;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * FluentLenium assertions entry point.
 */
public final class FluentLeniumAssertions {

    private FluentLeniumAssertions() {
        //only static
    }

    /**
     * Perform assertions on alert.
     *
     * @param actual actual alert
     * @return Alert assertion object
     */
    public static AlertAssert assertThat(final AlertImpl actual) {
        return new AlertAssert(actual);
    }

    /**
     * Perform assertions on page.
     *
     * @param actual actual page
     * @return Page assertion object
     */
    public static PageAssert assertThat(final FluentPage actual) {
        return new PageAssert(actual);
    }

    /**
     * Perform assertions on element.
     *
     * @param actual actual element
     * @return Element assertion object
     */
    public static OldFluentWebElementAssert assertThatOld(final FluentWebElement actual) {
        return new OldFluentWebElementAssert(actual);
    }

    /**
     * Perform assertions on element.
     *
     * @param actual actual element
     * @return Element assertion object
     */
    public static FluentWebElementAssert assertThat(final FluentWebElement actual) {
        return new FluentWebElementAssert(actual);
    }

    /**
     * Perform assertions on element list.
     *
     * @param actual actual element list
     * @return Element list assertion object
     */
    public static FluentListAssert assertThat(final FluentList<?> actual) {
        return new FluentListAssert(actual);
    }

    /**
     * Perform assertions on fluent conditions.
     *
     * @param actual actual conditions object
     * @return Conditions assertion object
     */
    public static FluentConditionsAssert assertThat(final FluentConditions actual) {
        return new FluentConditionsAssert(actual, FluentConditions.class);
    }

}
