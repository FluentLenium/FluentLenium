package io.fluentlenium.core.performance;

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
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link DefaultPerformanceTiming}.
 */
public class DefaultPerformanceTimingTest {

    private static final String LOAD_EVENT_END_SCRIPT = "return window.performance.timing.loadEventEnd;";
    private static final String NAVIGATION_START_SCRIPT = "return window.performance.timing.navigationStart;";
    private static final String SECURE_CONNECTION_START_SCRIPT =
            "return window.performance.timing.secureConnectionStart;";
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
        when(((JavascriptExecutor) driver).executeScript(LOAD_EVENT_END_SCRIPT)).thenReturn(45L);
        when(((JavascriptExecutor) driver).executeScript(NAVIGATION_START_SCRIPT)).thenReturn(28L);

        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END)).isEqualTo(17L);

        verify(((JavascriptExecutor) driver)).executeScript(LOAD_EVENT_END_SCRIPT);
        verify(((JavascriptExecutor) driver)).executeScript(NAVIGATION_START_SCRIPT);
        verifyNoMoreInteractions(driver);
    }

    @Test
    public void shouldReturnNegativeValueIfEventHasNotBeenRegistered() {
        when(((JavascriptExecutor) driver).executeScript(LOAD_EVENT_END_SCRIPT)).thenReturn(0L);
        when(((JavascriptExecutor) driver).executeScript(NAVIGATION_START_SCRIPT)).thenReturn(28L);

        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END)).isEqualTo(-28L);

        verify(((JavascriptExecutor) driver)).executeScript(LOAD_EVENT_END_SCRIPT);
        verify(((JavascriptExecutor) driver)).executeScript(NAVIGATION_START_SCRIPT);
        verifyNoMoreInteractions(driver);
    }

    @Test
    public void shouldGetEventValueInTimeUnit() {
        when(((JavascriptExecutor) driver).executeScript(LOAD_EVENT_END_SCRIPT)).thenReturn(60000L);
        when(((JavascriptExecutor) driver).executeScript(NAVIGATION_START_SCRIPT)).thenReturn(45900L);

        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END, TimeUnit.MILLISECONDS))
                .isEqualTo(14100L);
        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END, TimeUnit.SECONDS))
                .isEqualTo(14L);

        verify((JavascriptExecutor) driver, times(2)).executeScript(LOAD_EVENT_END_SCRIPT);
        verify((JavascriptExecutor) driver, times(2)).executeScript(NAVIGATION_START_SCRIPT);
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
        metrics.put("navigationStart", 550L);
        when(((JavascriptExecutor) driver).executeScript(TIMING_OBJECT_SCRIPT)).thenReturn(metrics);

        assertThat(performanceTiming.getMetrics().getDomComplete()).isEqualTo(684L);
        assertThat(performanceTiming.getMetrics().getUnloadEventStart()).isEqualTo(5128L);
    }

    @Test
    public void shouldReturnSecureConnectionStartAsLong() {
        when(((JavascriptExecutor) driver).executeScript(SECURE_CONNECTION_START_SCRIPT)).thenReturn(45L);
        when(((JavascriptExecutor) driver).executeScript(NAVIGATION_START_SCRIPT)).thenReturn(28L);

        assertThat(performanceTiming.secureConnectionStart()).isEqualTo(17L);

        verify(((JavascriptExecutor) driver)).executeScript(SECURE_CONNECTION_START_SCRIPT);
        verify(((JavascriptExecutor) driver)).executeScript(NAVIGATION_START_SCRIPT);
        verifyNoMoreInteractions(driver);
    }

    @Test
    public void shouldReturnSecureConnectionStartAsLongAsObject() {
        when(((JavascriptExecutor) driver).executeScript(SECURE_CONNECTION_START_SCRIPT)).thenReturn("undefined");

        assertThat(performanceTiming.secureConnectionStart()).isEqualTo("undefined");

        verify(((JavascriptExecutor) driver)).executeScript(SECURE_CONNECTION_START_SCRIPT);
        verifyNoMoreInteractions(driver);
    }
}
