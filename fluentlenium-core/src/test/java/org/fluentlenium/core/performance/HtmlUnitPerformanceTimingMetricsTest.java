package org.fluentlenium.core.performance;

import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceTiming;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link HtmlUnitPerformanceTimingMetrics}.
 */
public class HtmlUnitPerformanceTimingMetricsTest {

    @Mock
    private PerformanceTiming htmlUnitPerformanceTiming;

    private HtmlUnitPerformanceTimingMetrics metrics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(htmlUnitPerformanceTiming.getNavigationStart()).thenReturn(16500L);
        when(htmlUnitPerformanceTiming.getUnloadEventStart()).thenReturn(60000L);
        when(htmlUnitPerformanceTiming.getUnloadEventEnd()).thenReturn(0L);
        metrics = new HtmlUnitPerformanceTimingMetrics(htmlUnitPerformanceTiming);
    }

    @Test
    public void shouldDelegateCallToHtmlUnitImplementation() {
        assertThat(metrics.getUnloadEventStart()).isEqualTo(43500L);
        verify(htmlUnitPerformanceTiming, times(1)).getUnloadEventStart();
    }

    @Test
    public void shouldReturnMetricsValueInTimeUnit() {
        assertThat(metrics.in(TimeUnit.SECONDS).getUnloadEventStart()).isEqualTo(44L);
        assertThat(metrics.in(TimeUnit.SECONDS).in(TimeUnit.MINUTES).getUnloadEventStart()).isEqualTo(1L);
        verify(htmlUnitPerformanceTiming, times(2)).getUnloadEventStart();
    }

    @Test
    public void timeUnitConversionCreatesNewInstance() {
        HtmlUnitPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        assertThat(metrics.getUnloadEventStart()).isEqualTo(43500L);
        assertThat(convertedMetrics.getUnloadEventStart()).isEqualTo(44L);
    }

    @Test
    public void shouldReturnRelativeValues() {
        assertThat(metrics.getUnloadEventStart()).isEqualTo(43500L);
    }

    @Test
    public void shouldReturnNegativeValueIfEventHasNotBeenRegistered() {
        assertThat(metrics.getUnloadEventEnd()).isEqualTo(-16500L);
    }

    @Test
    public void shouldThrowUnsupportedOperationExceptionForRequestStart() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> metrics.getRequestStart());
    }
}
