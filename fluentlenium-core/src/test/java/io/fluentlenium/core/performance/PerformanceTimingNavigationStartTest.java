package io.fluentlenium.core.performance;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link DefaultPerformanceTiming}.
 */
public class PerformanceTimingNavigationStartTest {

    private static final String NAVIGATION_START_SCRIPT = "return window.performance.timing.navigationStart;";

    @Mock(extraInterfaces = JavascriptExecutor.class)
    private WebDriver driver;

    private DefaultPerformanceTiming performanceTiming;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        performanceTiming = new DefaultPerformanceTiming(driver);
    }

    @Test
    public void shouldReturnZeroForNavigationStart() {
        when(((JavascriptExecutor) driver).executeScript(NAVIGATION_START_SCRIPT)).thenReturn(15600L);

        assertThat(performanceTiming.navigationStart()).isEqualTo(0L);
        assertThat(performanceTiming.navigationStart(TimeUnit.MILLISECONDS)).isEqualTo(0L);
        assertThat(performanceTiming.navigationStart(TimeUnit.SECONDS)).isEqualTo(0L);

        verify((JavascriptExecutor) driver, times(6)).executeScript(NAVIGATION_START_SCRIPT);
        verifyNoMoreInteractions(driver);
    }
}
