package org.fluentlenium.test.performance;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.performance.PerformanceTimingEvent;
import org.fluentlenium.pages.Page2;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for {@link org.fluentlenium.core.performance.DefaultPerformanceTiming}.
 */
class PerformanceTimingOnPageTest extends IntegrationFluentTest {

    @Page
    Page2 page2;

    @Test
    void shouldReturnEventValue() {
        goTo(DEFAULT_URL);

        assertThat(performanceTiming().getEventValue(PerformanceTimingEvent.DOM_COMPLETE)).isGreaterThan(0);
    }

    @Test
    void shouldReturnSpecificEventValue() {
        goTo(DEFAULT_URL);

        assertThat(performanceTiming().connectEnd()).isGreaterThan(0);
    }

    @Test
    void shouldHaveAccessToPerformanceTimingViaFluentPages() {
        goTo(PAGE_2_URL);

        assertThat(page2.performanceTiming().connectEnd()).isGreaterThan(0);
    }

    @Test
    @Disabled("HtmlUnitDriver returns constantly increasing values for the same calls."
            + "It doesn't happen e.g with ChromeDriver.")
    void shouldReturnTheSameValuesViaFluentPageAndFluentTest() {
        goTo(PAGE_2_URL);

        assertThat(page2.performanceTiming()).isSameAs(performanceTiming());
        assertThat(page2.performanceTiming().connectEnd()).isEqualTo(performanceTiming().connectEnd());

        long fromFluentPage = page2.performanceTiming().connectEnd();
        long fromFluentPage2 = page2.performanceTiming().connectEnd();

        assertThat(fromFluentPage).isEqualTo(fromFluentPage2);
    }
}
