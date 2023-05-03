package io.fluentlenium.core.performance;

import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceTiming;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Unit test for {@link PerformanceTimingMetricsFactory}.
 */
public class PerformanceTimingMetricsFactoryTest {

    private final PerformanceTimingMetricsFactory factory = new PerformanceTimingMetricsFactory();

    @Test
    public void shouldCreateDefaultMetricsFactory() {
        Map<String, Object> timingObject = new HashMap<>();
        timingObject.put(PerformanceTimingEvent.SECURE_CONNECTION_START.getEvent(), 150000L);
        timingObject.put(PerformanceTimingEvent.NAVIGATION_START.getEvent(), 27500L);

        assertThat(factory.createFor(timingObject)).isInstanceOf(DefaultPerformanceTimingMetrics.class);
    }

    @Test
    public void shouldCreateHtmlUnitMetricsFactory() {
        PerformanceTiming htmlUnitPerformanceTiming = new PerformanceTiming();

        assertThat(factory.createFor(htmlUnitPerformanceTiming)).isInstanceOf(HtmlUnitPerformanceTimingMetrics.class);
    }

    @Test
    public void shouldThrowExceptionForUnknownImplementation() {
        assertThatExceptionOfType(UnknownPerformanceTimingImplementationException.class)
                .isThrownBy(() -> factory.createFor(Arrays.asList("some", "value")))
                .withMessageContaining("The object was of type: class java.util.Arrays$ArrayList"
                        + " with value: [some, value]");
    }
}
