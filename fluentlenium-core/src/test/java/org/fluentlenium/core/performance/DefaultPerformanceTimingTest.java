package org.fluentlenium.core.performance;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link DefaultPerformanceTiming}.
 */
public class DefaultPerformanceTimingTest {

    private static final String LOAD_EVENT_END_SCRIPT = "return window.performance.timing.loadEventEnd;";
    private static final String TIMING_OBJECT_SCRIPT = "return window.performance.timing;";

    @Mock(extraInterfaces = JavascriptExecutor.class)
    private WebDriver driver;

    private DefaultPerformanceTiming performanceTiming;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        performanceTiming = new DefaultPerformanceTiming(driver);
    }

    @Test
    public void shouldGetEventValue() {
        when(((JavascriptExecutor) driver).executeScript(LOAD_EVENT_END_SCRIPT)).thenReturn(2L);

        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END)).isEqualTo(2L);

        verify(((JavascriptExecutor) driver)).executeScript(LOAD_EVENT_END_SCRIPT);
        verifyNoMoreInteractions(driver);
    }

    @Test
    public void shouldGetEventValueInTimeUnit() {
        when(((JavascriptExecutor) driver).executeScript(LOAD_EVENT_END_SCRIPT)).thenReturn(60000L);

        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END, TimeUnit.MILLISECONDS))
                .isEqualTo(60000L);
        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END, TimeUnit.SECONDS))
                .isEqualTo(60L);

        verify((JavascriptExecutor) driver, times(2)).executeScript(LOAD_EVENT_END_SCRIPT);
        verifyNoMoreInteractions(driver);
    }

    @Test
    public void shouldThrowExceptionForNullEvent() {
        assertThatIllegalArgumentException().isThrownBy(() -> performanceTiming.getEventValue(null))
                .withMessage("The event should not be null.");
    }

    @Test
    public void shouldGetMetricsObject() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("domComplete", 1234L);
        metrics.put("unloadEventStart", 5678L);
        when(((JavascriptExecutor) driver).executeScript(TIMING_OBJECT_SCRIPT))
                .thenReturn(metrics);

        assertThat(performanceTiming.getMetrics().getDomComplete()).isEqualTo(1234L);
        assertThat(performanceTiming.getMetrics().getUnloadEventStart()).isEqualTo(5678L);
    }
}
