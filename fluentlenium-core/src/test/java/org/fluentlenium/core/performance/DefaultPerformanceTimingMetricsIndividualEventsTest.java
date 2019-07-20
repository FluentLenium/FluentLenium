package org.fluentlenium.core.performance;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link DefaultPerformanceTimingMetrics}.
 */
@RunWith(Parameterized.class)
public class DefaultPerformanceTimingMetricsIndividualEventsTest {

    private static final Map<String, Function<DefaultPerformanceTimingMetrics, Long>> EVENT_CALLS =
            new ImmutableMap.Builder<String, Function<DefaultPerformanceTimingMetrics, Long>>()
                    .put("navigationStart", DefaultPerformanceTimingMetrics::getNavigationStart)
                    .put("unloadEventStart", DefaultPerformanceTimingMetrics::getUnloadEventStart)
                    .put("unloadEventEnd", DefaultPerformanceTimingMetrics::getUnloadEventEnd)
                    .put("redirectStart", DefaultPerformanceTimingMetrics::getRedirectStart)
                    .put("redirectEnd", DefaultPerformanceTimingMetrics::getRedirectEnd)
                    .put("fetchStart", DefaultPerformanceTimingMetrics::getFetchStart)
                    .put("domainLookupStart", DefaultPerformanceTimingMetrics::getDomainLookupStart)
                    .put("domainLookupEnd", DefaultPerformanceTimingMetrics::getDomainLookupEnd)
                    .put("connectStart", DefaultPerformanceTimingMetrics::getConnectStart)
                    .put("connectEnd", DefaultPerformanceTimingMetrics::getConnectEnd)
                    .put("requestStart", DefaultPerformanceTimingMetrics::getRequestStart)
                    .put("responseStart", DefaultPerformanceTimingMetrics::getResponseStart)
                    .put("responseEnd", DefaultPerformanceTimingMetrics::getResponseEnd)
                    .put("domLoading", DefaultPerformanceTimingMetrics::getDomLoading)
                    .put("domInteractive", DefaultPerformanceTimingMetrics::getDomInteractive)
                    .put("domContentLoadedEventStart", DefaultPerformanceTimingMetrics::getDomContentLoadedEventStart)
                    .put("domContentLoadedEventEnd", DefaultPerformanceTimingMetrics::getDomContentLoadedEventEnd)
                    .put("domComplete", DefaultPerformanceTimingMetrics::getDomComplete)
                    .put("loadEventStart", DefaultPerformanceTimingMetrics::getLoadEventStart)
                    .put("loadEventEnd", DefaultPerformanceTimingMetrics::getLoadEventEnd)
                    .build();

    private static final long NAVIGATION_START = 100000L;

    private static final Map<String, Object> METRICS = new ImmutableMap.Builder<String, Object>()
            .put("navigationStart", NAVIGATION_START)
            .put("unloadEventStart", 200000L)
            .put("unloadEventEnd", 300000L)
            .put("redirectStart", 400000L)
            .put("redirectEnd", 500000L)
            .put("fetchStart", 600000L)
            .put("domainLookupStart", 700000L)
            .put("domainLookupEnd", 800000L)
            .put("connectStart", 900000L)
            .put("connectEnd", 1000000L)
            .put("requestStart", 1100000L)
            .put("responseStart", 1200000L)
            .put("responseEnd", 1300000L)
            .put("domLoading", 1400000L)
            .put("domInteractive", 1500000L)
            .put("domContentLoadedEventStart", 1600000L)
            .put("domContentLoadedEventEnd", 1700000L)
            .put("domComplete", 1800000L)
            .put("loadEventStart", 1900000L)
            .put("loadEventEnd", 2000000L)
            .build();

    @Parameter
    public String eventType;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"navigationStart"},
                {"unloadEventStart"},
                {"unloadEventEnd"},
                {"redirectStart"},
                {"redirectEnd"},
                {"fetchStart"},
                {"domainLookupStart"},
                {"domainLookupEnd"},
                {"connectStart"},
                {"connectEnd"},
                {"requestStart"},
                {"responseStart"},
                {"responseEnd"},
                {"domLoading"},
                {"domInteractive"},
                {"domContentLoadedEventStart"},
                {"domContentLoadedEventEnd"},
                {"domComplete"},
                {"loadEventStart"},
                {"loadEventEnd"}
        });
    }

    @Test
    public void shouldReturnSpecificPerformanceTimingMetrics() {
        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(METRICS);

        Long expectedMetricValue = (Long) METRICS.get(eventType) - NAVIGATION_START;
        assertThat(EVENT_CALLS.get(eventType).apply(metrics)).isEqualTo(expectedMetricValue);
    }
}
