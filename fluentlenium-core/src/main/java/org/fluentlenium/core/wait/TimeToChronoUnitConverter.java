package org.fluentlenium.core.wait;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class TimeToChronoUnitConverter {

    private TimeToChronoUnitConverter() {
        // helper class
    }

    public static ChronoUnit of(TimeUnit unit) {
        switch (unit) {
            case DAYS:
                return ChronoUnit.DAYS;
            case HOURS:
                return ChronoUnit.HOURS;
            case MINUTES:
                return ChronoUnit.MINUTES;
            case SECONDS:
                return ChronoUnit.SECONDS;
            case MILLISECONDS:
                return ChronoUnit.MILLIS;
            case NANOSECONDS:
                return ChronoUnit.NANOS;
            case MICROSECONDS:
                return ChronoUnit.MICROS;
            default:
                throw new RuntimeException("Unsupported TimeUnit.");
        }
    }
}
