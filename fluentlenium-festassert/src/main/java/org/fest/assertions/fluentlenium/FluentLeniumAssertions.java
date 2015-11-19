package org.fest.assertions.fluentlenium;

import org.fest.assertions.fluentlenium.custom.FluentListAssert;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.fest.assertions.fluentlenium.custom.PageAssert;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.domain.FluentList;

public final class FluentLeniumAssertions {

    private FluentLeniumAssertions() {
        //only static
    }

    @Deprecated
    /**
     * Use AssertJ plugin instead - FestAssert is not active anymore
     *   <dependency>
            <groupId>org.fluentlenium</groupId>
            <artifactId>fluentlenium-assertj</artifactId>
         </dependency>
     */
    public static PageAssert assertThat(FluentPage actual) {
        return new PageAssert(actual);
    }


    @Deprecated
    /**
     * Use AssertJ plugin instead - FestAssert is not active anymore
     *   <dependency>
            <groupId>org.fluentlenium</groupId>
            <artifactId>fluentlenium-assertj</artifactId>
         </dependency>
     */    public static FluentWebElementAssert assertThat(FluentWebElement actual) {
        return new FluentWebElementAssert(actual);
    }


    @Deprecated
    /**
     * Use AssertJ plugin instead - FestAssert is not active anymore
     *   <dependency>
            <groupId>org.fluentlenium</groupId>
            <artifactId>fluentlenium-assertj</artifactId>
         </dependency>
     */
    public static FluentListAssert assertThat(FluentList<?> actual) {
        return new FluentListAssert(actual);
    }

}
