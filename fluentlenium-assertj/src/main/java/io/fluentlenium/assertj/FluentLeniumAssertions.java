package io.fluentlenium.assertj;

import io.fluentlenium.assertj.custom.AlertAssert;
import io.fluentlenium.assertj.custom.FluentListAssert;
import io.fluentlenium.assertj.custom.FluentWebElementAssert;
import io.fluentlenium.assertj.custom.PageAssert;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.alert.AlertImpl;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import org.assertj.core.api.Assertions;

/**
 * FluentLenium assertions entry point.
 */
public final class FluentLeniumAssertions extends Assertions {

    private FluentLeniumAssertions() {
        //only static
    }

    /**
     * Perform assertions on alert.
     *
     * @param actual actual alert
     * @return Alert assertion object
     */
    public static AlertAssert assertThat(AlertImpl actual) {
        return new AlertAssert(actual);
    }

    /**
     * Perform assertions on a {@link FluentPage}.
     *
     * @param actual actual page
     * @return Page assertion object
     */
    public static PageAssert assertThat(FluentPage actual) {
        return new PageAssert(actual);
    }

    /**
     * Perform assertions on a {@link FluentWebElement}.
     *
     * @param actual actual element
     * @return Element assertion object
     */
    public static FluentWebElementAssert assertThat(FluentWebElement actual) {
        return new FluentWebElementAssert(actual);
    }

    /**
     * Perform assertions on a {@link FluentList}.
     *
     * @param actual actual element list
     * @return Element list assertion object
     */
    public static FluentListAssert assertThat(FluentList<? extends FluentWebElement> actual) {
        return new FluentListAssert(actual);
    }

}
