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

@RunWith(Parameterized.class)
public class PerformanceTimingMetricsIndividualEventsTest {

    private static final Map<String, Function<PerformanceTimingMetrics, Long>> EVENT_CALLS = new ImmutableMap.Builder<String, Function<PerformanceTimingMetrics, Long>>()
            .put("navigationStart", PerformanceTimingMetrics::getNavigationStart)
            .put("unloadEventStart", PerformanceTimingMetrics::getUnloadEventStart)
            .put("unloadEventEnd", PerformanceTimingMetrics::getUnloadEventEnd)
            .put("redirectStart", PerformanceTimingMetrics::getRedirectStart)
            .put("redirectEnd", PerformanceTimingMetrics::getRedirectEnd)
            .put("fetchStart", PerformanceTimingMetrics::getFetchStart)
            .put("domainLookupStart", PerformanceTimingMetrics::getDomainLookupStart)
            .put("domainLookupEnd", PerformanceTimingMetrics::getDomainLookupEnd)
            .put("connectStart", PerformanceTimingMetrics::getConnectStart)
            .put("connectEnd", PerformanceTimingMetrics::getConnectEnd)
            .put("requestStart", PerformanceTimingMetrics::getRequestStart)
            .put("responseStart", PerformanceTimingMetrics::getResponseStart)
            .put("responseEnd", PerformanceTimingMetrics::getResponseEnd)
            .put("domLoading", PerformanceTimingMetrics::getDomLoading)
            .put("domInteractive", PerformanceTimingMetrics::getDomInteractive)
            .put("domContentLoadedEventStart", PerformanceTimingMetrics::getDomContentLoadedEventStart)
            .put("domContentLoadedEventEnd", PerformanceTimingMetrics::getDomContentLoadedEventEnd)
            .put("domComplete", PerformanceTimingMetrics::getDomComplete)
            .put("loadEventStart", PerformanceTimingMetrics::getLoadEventStart)
            .put("loadEventEnd", PerformanceTimingMetrics::getLoadEventEnd)
            .build();

    private static final Map<String, Object> METRICS = new ImmutableMap.Builder<String, Object>()
            .put("navigationStart", 100000L)
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
        PerformanceTimingMetrics metrics = new PerformanceTimingMetrics(METRICS);

        assertThat(EVENT_CALLS.get(eventType).apply(metrics)).isEqualTo((Long) METRICS.get(eventType));
    }
}
