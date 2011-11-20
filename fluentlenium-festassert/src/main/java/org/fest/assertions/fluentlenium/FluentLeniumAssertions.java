package org.fest.assertions.fluentlenium;

import fr.javafreelance.fluentlenium.core.FluentPage;
import fr.javafreelance.fluentlenium.core.domain.FluentWebElement;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.fest.assertions.fluentlenium.custom.PageAssert;

public class FluentLeniumAssertions {

    public static PageAssert assertThat(FluentPage actual) {
           return new PageAssert(actual);
       }

    public static FluentWebElementAssert assertThat(FluentWebElement actual) {
           return new FluentWebElementAssert(actual);
       }


}
