package org.fluentlenium.core.performance;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link DefaultPerformanceTiming}.
 */
public class DefaultPerformanceTimingTest {

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
        when(((JavascriptExecutor) driver).executeScript("return window.performance.timing.loadEventEnd;")).thenReturn(2L);

        assertThat(performanceTiming.getEventValue(PerformanceTimingEvent.LOAD_EVENT_END)).isEqualTo(2L);

        verify(((JavascriptExecutor) driver)).executeScript("return window.performance.timing.loadEventEnd;");
        verifyNoMoreInteractions(driver);
    }

    @Test
    public void shouldGetSpecificEventValue() {
        when(((JavascriptExecutor) driver).executeScript("return window.performance.timing.loadEventEnd;")).thenReturn(5L);

        assertThat(performanceTiming.loadEventEnd()).isEqualTo(5L);

        verify(((JavascriptExecutor) driver)).executeScript("return window.performance.timing.loadEventEnd;");
        verifyNoMoreInteractions(driver);
    }

    @Test
    public void shouldThrowExceptionForNullEvent() {
        assertThatIllegalArgumentException().isThrownBy(() -> performanceTiming.getEventValue(null))
                .withMessage("The event should not be null.");
    }

    @Test
    public void shouldGetMetricsModelObject() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("domComplete", 1234L);
        metrics.put("unloadEventStart", 5678L);
        when(((JavascriptExecutor) driver).executeScript("return window.performance.timing;"))
                .thenReturn(metrics);

        assertThat(performanceTiming.getMetrics().getDomComplete()).isEqualTo(1234L);
        assertThat(performanceTiming.getMetrics().getUnloadEventStart()).isEqualTo(5678L);
    }
}
