package org.fluentlenium.core.performance;

import org.assertj.core.api.Assertions;
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
    public void shouldReturnNewMetricsObjectConvertedToNewTimeUnitWithUndefinedOptionalAttributes() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_START.getEvent(), 60000L);
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_END.getEvent(), 120000L);
        sourceMetrics.put(PerformanceTimingEvent.SECURE_CONNECTION_START.getEvent(), "undefined");

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);
        DefaultPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(convertedMetrics.getLoadEventStart()).isEqualTo(60L);
        softly.assertThat(convertedMetrics.getLoadEventEnd()).isEqualTo(120L);
        softly.assertThat(convertedMetrics.getSecureConnectionStart()).isEqualTo("undefined");
        softly.assertAll();
    }

    @Test
    public void shouldReturnNewMetricsObjectConvertedToNewTimeUnitWithLongOptionalAttributes() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_START.getEvent(), 60000L);
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_END.getEvent(), 120000L);
        sourceMetrics.put(PerformanceTimingEvent.SECURE_CONNECTION_START.getEvent(), 150000L);

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);
        DefaultPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(convertedMetrics.getLoadEventStart()).isEqualTo(60L);
        softly.assertThat(convertedMetrics.getLoadEventEnd()).isEqualTo(120L);
        softly.assertThat(convertedMetrics.getSecureConnectionStart()).isEqualTo(150L);
        softly.assertAll();
    }

    @Test
    public void timeUnitConversionCreatesNewInstance() {
        Map<String, Object> sourceMetrics = new HashMap<>();
        sourceMetrics.put(PerformanceTimingEvent.LOAD_EVENT_START.getEvent(), 60000L);

        DefaultPerformanceTimingMetrics metrics = new DefaultPerformanceTimingMetrics(sourceMetrics);
        DefaultPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        assertThat(metrics.getLoadEventStart()).isEqualTo(60000L);
        assertThat(convertedMetrics.getLoadEventStart()).isEqualTo(60L);
    }
}
