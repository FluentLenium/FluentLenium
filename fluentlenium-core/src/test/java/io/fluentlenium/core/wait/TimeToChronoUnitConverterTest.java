package io.fluentlenium.core.wait;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for {@link TimeToChronoUnitConverter}.
 */
public class TimeToChronoUnitConverterTest {

    @Test
    public void shouldReturnCorrectChronoUnit() {

        SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(TimeToChronoUnitConverter.of(TimeUnit.MICROSECONDS)).isEqualTo(ChronoUnit.MICROS);
        assertions.assertThat(TimeToChronoUnitConverter.of(TimeUnit.NANOSECONDS)).isEqualTo(ChronoUnit.NANOS);
        assertions.assertThat(TimeToChronoUnitConverter.of(TimeUnit.MILLISECONDS)).isEqualTo(ChronoUnit.MILLIS);
        assertions.assertThat(TimeToChronoUnitConverter.of(TimeUnit.SECONDS)).isEqualTo(ChronoUnit.SECONDS);
        assertions.assertThat(TimeToChronoUnitConverter.of(TimeUnit.MINUTES)).isEqualTo(ChronoUnit.MINUTES);
        assertions.assertThat(TimeToChronoUnitConverter.of(TimeUnit.HOURS)).isEqualTo(ChronoUnit.HOURS);
        assertions.assertThat(TimeToChronoUnitConverter.of(TimeUnit.DAYS)).isEqualTo(ChronoUnit.DAYS);

        assertions.assertAll();
    }
}
