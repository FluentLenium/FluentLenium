package org.fluentlenium.core.performance;

import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceTiming;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
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
        metrics = new HtmlUnitPerformanceTimingMetrics(htmlUnitPerformanceTiming);
    }

    @Test
    public void shouldDelegateCallToHtmlUnitImplementation() {
        when(htmlUnitPerformanceTiming.getUnloadEventStart()).thenReturn(60000L);

        assertThat(metrics.getUnloadEventStart()).isEqualTo(60000L);
        verify(htmlUnitPerformanceTiming, times(1)).getUnloadEventStart();
    }

    @Test
    public void shouldReturnMetricsValueInTimeUnit() {
        when(htmlUnitPerformanceTiming.getUnloadEventStart()).thenReturn(60000L);

        assertThat(metrics.in(TimeUnit.SECONDS).getUnloadEventStart()).isEqualTo(60L);
        assertThat(metrics.in(TimeUnit.SECONDS).in(TimeUnit.MINUTES).getUnloadEventStart()).isEqualTo(1L);
        verify(htmlUnitPerformanceTiming, times(2)).getUnloadEventStart();
    }

    @Test
    public void timeUnitConversionCreatesNewInstance() {
        when(htmlUnitPerformanceTiming.getUnloadEventStart()).thenReturn(60000L);
        HtmlUnitPerformanceTimingMetrics convertedMetrics = metrics.in(TimeUnit.SECONDS);

        assertThat(metrics.getUnloadEventStart()).isEqualTo(60000L);
        assertThat(convertedMetrics.getUnloadEventStart()).isEqualTo(60L);
    }
}
