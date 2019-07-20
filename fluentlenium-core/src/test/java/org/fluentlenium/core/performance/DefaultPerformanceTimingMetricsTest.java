package org.fluentlenium.core.performance;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link DefaultPerformanceTimingMetrics}.
 */
public class DefaultPerformanceTimingMetricsTest {

    @Test
    public void shouldReturnTimingMetricsAsIntervalValues() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_END.getEvent(), 120000L);
        sourceMetrics.put(PerformanceTimingEvent.NAVIGATION_START.getEvent(), 27500L);

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);

        assertThat(metrics.getLoadEventEnd()).isEqualTo(92500L);
    }

    @Test
    public void shouldReturnNegativeValueIfEventHasNotBeenRegistered() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_END.getEvent(), 0L);
        sourceMetrics.put(PerformanceTimingEvent.NAVIGATION_START.getEvent(), 27500L);

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);

        assertThat(metrics.getLoadEventEnd()).isEqualTo(-27500L);
    }

    @Test
    public void shouldReturnNewMetricsObjectConvertedToNewTimeUnitWithUndefinedOptionalAttributes() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_START.getEvent(), 60000L);
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_END.getEvent(), 120000L);
        sourceMetrics.put(PerformanceTimingEvent.SECURE_CONNECTION_START.getEvent(), "undefined");
        sourceMetrics.put(PerformanceTimingEvent.NAVIGATION_START.getEvent(), 27500L);

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);
        DefaultPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(convertedMetrics.getLoadEventStart()).isEqualTo(32L);
        softly.assertThat(convertedMetrics.getLoadEventEnd()).isEqualTo(92L);
        softly.assertThat(convertedMetrics.getSecureConnectionStart()).isEqualTo("undefined");
        softly.assertAll();
    }

    @Test
    public void shouldReturnNewMetricsObjectConvertedToNewTimeUnitWithLongOptionalAttributes() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.SECURE_CONNECTION_START.getEvent(), 150000L);
        sourceMetrics.put(PerformanceTimingEvent.NAVIGATION_START.getEvent(), 27500L);

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);
        DefaultPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        assertThat(convertedMetrics.getSecureConnectionStart()).isEqualTo(122L);
    }

    @Test
    public void timeUnitConversionCreatesNewInstance() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_START.getEvent(), 60000L);
        sourceMetrics.put(PerformanceTimingEvent.SECURE_CONNECTION_START.getEvent(), 15000L);
        sourceMetrics.put(PerformanceTimingEvent.NAVIGATION_START.getEvent(), 13000L);

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);
        DefaultPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        assertThat(metrics.getLoadEventStart()).isEqualTo(47000L);
        assertThat(convertedMetrics.getLoadEventStart()).isEqualTo(47L);
    }
}
