package org.fluentlenium.assertj;

import org.fluentlenium.assertj.custom.AlertAssert;
import org.fluentlenium.assertj.custom.FluentListAssert;
import org.fluentlenium.assertj.custom.FluentWebElementAssert;
import org.fluentlenium.assertj.custom.PageAssert;
import org.fluentlenium.core.alert.Alert;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

public final class FluentLeniumAssertions {

    private FluentLeniumAssertions() {
        //only static
    }

    public static AlertAssert assertThat(Alert actual) {
        return new AlertAssert(actual);
    }

    public static PageAssert assertThat(FluentPage actual) {
        return new PageAssert(actual);
    }

    public static FluentWebElementAssert assertThat(FluentWebElement actual) {
        return new FluentWebElementAssert(actual);
    }

    public static FluentListAssert assertThat(FluentList<?> actual) {
        return new FluentListAssert(actual);
    }

}
