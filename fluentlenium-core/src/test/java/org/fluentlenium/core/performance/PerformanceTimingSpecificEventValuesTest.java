package org.fluentlenium.core.performance;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link PerformanceTiming}.
 */
@RunWith(Parameterized.class)
public class PerformanceTimingSpecificEventValuesTest {

    private static final String EVENT_SCRIPT = "return window.performance.timing.%s;";
    private static final String NAVIGATION_START_SCRIPT = "return window.performance.timing.navigationStart;";
    private static final Map<String, Function<DefaultPerformanceTiming, Long>> EVENT_CALLS =
            new ImmutableMap.Builder<String, Function<DefaultPerformanceTiming, Long>>()
                    .put("unloadEventStart", PerformanceTiming::unloadEventStart)
                    .put("unloadEventEnd", PerformanceTiming::unloadEventEnd)
                    .put("redirectStart", PerformanceTiming::redirectStart)
                    .put("redirectEnd", PerformanceTiming::redirectEnd)
                    .put("fetchStart", PerformanceTiming::fetchStart)
                    .put("domainLookupStart", PerformanceTiming::domainLookupStart)
                    .put("domainLookupEnd", PerformanceTiming::domainLookupEnd)
                    .put("connectStart", PerformanceTiming::connectStart)
                    .put("connectEnd", PerformanceTiming::connectEnd)
                    .put("secureConnectionStart", timing -> (Long) timing.secureConnectionStart())
                    .put("requestStart", PerformanceTiming::requestStart)
                    .put("responseStart", PerformanceTiming::responseStart)
                    .put("responseEnd", PerformanceTiming::responseEnd)
                    .put("domLoading", PerformanceTiming::domLoading)
                    .put("domInteractive", PerformanceTiming::domInteractive)
                    .put("domContentLoadedEventStart", PerformanceTiming::domContentLoadedEventStart)
                    .put("domContentLoadedEventEnd", PerformanceTiming::domContentLoadedEventEnd)
                    .put("domComplete", PerformanceTiming::domComplete)
                    .put("loadEventStart", PerformanceTiming::loadEventStart)
                    .put("loadEventEnd", PerformanceTiming::loadEventEnd)
                    .build();

    @Parameter
    public String eventType;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"unloadEventStart"},
                {"unloadEventEnd"},
                {"redirectStart"},
                {"redirectEnd"},
                {"fetchStart"},
                {"domainLookupStart"},
                {"domainLookupEnd"},
                {"connectStart"},
                {"connectEnd"},
                {"secureConnectionStart"},
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

    @Mock(extraInterfaces = JavascriptExecutor.class)
    private WebDriver driver;

    private DefaultPerformanceTiming performanceTiming;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        performanceTiming = new DefaultPerformanceTiming(driver);
    }

    @Test
    public void shouldGetSpecificEventValue() {
        String script = String.format(EVENT_SCRIPT, eventType);
        when(((JavascriptExecutor) driver).executeScript(script)).thenReturn(15000L);
        when(((JavascriptExecutor) driver).executeScript(NAVIGATION_START_SCRIPT)).thenReturn(7800L);

        assertThat(EVENT_CALLS.get(eventType).apply(performanceTiming)).isEqualTo(7200L);

        verify(((JavascriptExecutor) driver)).executeScript(script);
        verify(((JavascriptExecutor) driver)).executeScript(NAVIGATION_START_SCRIPT);
        verifyNoMoreInteractions(driver);
    }
}
