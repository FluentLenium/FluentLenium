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
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PerformanceTimingSpecificEventValueInTimeUnitTest {

    private static final String EVENT_SCRIPT = "return window.performance.timing.%s;";
    private static final Map<String, BiFunction<DefaultPerformanceTiming, TimeUnit, Long>> EVENT_CALLS =
            new ImmutableMap.Builder<String, BiFunction<DefaultPerformanceTiming, TimeUnit, Long>>()
                    .put("navigationStart", PerformanceTiming::navigationStart)
                    .put("unloadEventStart", PerformanceTiming::unloadEventStart)
                    .put("unloadEventEnd", PerformanceTiming::unloadEventEnd)
                    .put("redirectStart", PerformanceTiming::redirectStart)
                    .put("redirectEnd", PerformanceTiming::redirectEnd)
                    .put("fetchStart", PerformanceTiming::fetchStart)
                    .put("domainLookupStart", PerformanceTiming::domainLookupStart)
                    .put("domainLookupEnd", PerformanceTiming::domainLookupEnd)
                    .put("connectStart", PerformanceTiming::connectStart)
                    .put("connectEnd", PerformanceTiming::connectEnd)
                    .put("secureConnectionStart", PerformanceTiming::secureConnectionStart)
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
    public void shouldGetSpecificEventValueInTimeUnit() {
        String script = String.format(EVENT_SCRIPT, eventType);
        when(((JavascriptExecutor) driver).executeScript(script)).thenReturn(60000L);

        assertThat(EVENT_CALLS.get(eventType).apply(performanceTiming, TimeUnit.MILLISECONDS)).isEqualTo(60000L);
        assertThat(EVENT_CALLS.get(eventType).apply(performanceTiming, TimeUnit.SECONDS)).isEqualTo(60L);

        verify((JavascriptExecutor) driver, times(2)).executeScript(script);
        verifyNoMoreInteractions(driver);
    }
}
