package org.fest.assertions.fluentlenium;

import java.util.List;

import org.fest.assertions.GenericAssert;
import org.fest.assertions.fluentlenium.custom.FluentListAssert;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.fest.assertions.fluentlenium.custom.PageAssert;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

public final class FluentLeniumAssertions {

    private FluentLeniumAssertions() {
        //only static
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
