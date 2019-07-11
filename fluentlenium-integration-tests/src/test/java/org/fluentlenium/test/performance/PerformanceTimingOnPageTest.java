package org.fluentlenium.test.performance;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.performance.PerformanceTimingEvent;
import org.fluentlenium.core.performance.PerformanceTimingMetrics;
import org.fluentlenium.pages.Page2;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for {@link org.fluentlenium.core.performance.DefaultPerformanceTiming}.
 * <p>
 * NOTE: the HtmlUnit implementation of the PerformanceTiming API returns mock values,
 * so in some circumstances these tests (since they use htmlunit) may produce false-positive or false-negative results.
 */
class PerformanceTimingOnPageTest extends IntegrationFluentTest {

    @Page
    Page2 page2;

    @Test
    void shouldReturnEventValue() {
        long currentTime = System.currentTimeMillis();
        goTo(DEFAULT_URL);

        assertThat(performanceTiming().getEventValue(PerformanceTimingEvent.DOM_CONTENT_LOADED_EVENT_START))
                .isGreaterThanOrEqualTo(currentTime);
    }

    @Test
    void shouldReturnSpecificEventValue() {
        long currentTime = System.currentTimeMillis();
        goTo(DEFAULT_URL);

        assertThat(performanceTiming().connectEnd()).isGreaterThan(currentTime);
    }

    @Test
    void shouldHaveAccessToPerformanceTimingViaFluentPages() {
        long currentTime = System.currentTimeMillis();
        goTo(PAGE_2_URL);

        assertThat(page2.performanceTiming().connectEnd()).isGreaterThan(currentTime);
    }

    @Test
    void shouldReturnMetricsInBulk() {
        goTo("https://duckduckgo.com");

        PerformanceTimingMetrics metrics = performanceTiming().getMetrics();

        assertThat(metrics.getLoadEventStart()).isGreaterThanOrEqualTo(System.currentTimeMillis());
    }

    @Test
    void shouldReturnMetricsInBulkInTimeUnit() {
        goTo("https://duckduckgo.com");

        long currentTime = System.currentTimeMillis();
        PerformanceTimingMetrics metrics = performanceTiming().getMetrics();
        assertThat(metrics.getLoadEventStart()).isGreaterThanOrEqualTo(currentTime);

        PerformanceTimingMetrics metricsInSecs = metrics.in(TimeUnit.SECONDS);
        assertThat(metricsInSecs.getLoadEventStart())
                .isEqualTo(TimeUnit.MILLISECONDS.toSeconds(metrics.getLoadEventStart()));

        assertThat(metrics.in(TimeUnit.SECONDS).getLoadEventStart())
                .isEqualTo(TimeUnit.MILLISECONDS.toSeconds(metrics.getLoadEventStart()));
    }
}
