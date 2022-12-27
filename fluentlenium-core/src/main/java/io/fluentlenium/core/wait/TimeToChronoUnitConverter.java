package io.fluentlenium.core.wait;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Provides functionality to convert {@link TimeUnit}s to {@link ChronoUnit}s.
 */
public final class TimeToChronoUnitConverter {

    private static final Map<TimeUnit, ChronoUnit> TIME_UNIT_TO_CHRONO_UNIT = new HashMap<>();

    static {
        TIME_UNIT_TO_CHRONO_UNIT.put(TimeUnit.DAYS, ChronoUnit.DAYS);
        TIME_UNIT_TO_CHRONO_UNIT.put(TimeUnit.HOURS, ChronoUnit.HOURS);
        TIME_UNIT_TO_CHRONO_UNIT.put(TimeUnit.MINUTES, ChronoUnit.MINUTES);
        TIME_UNIT_TO_CHRONO_UNIT.put(TimeUnit.SECONDS, ChronoUnit.SECONDS);
        TIME_UNIT_TO_CHRONO_UNIT.put(TimeUnit.MILLISECONDS, ChronoUnit.MILLIS);
        TIME_UNIT_TO_CHRONO_UNIT.put(TimeUnit.MICROSECONDS, ChronoUnit.MICROS);
        TIME_UNIT_TO_CHRONO_UNIT.put(TimeUnit.NANOSECONDS, ChronoUnit.NANOS);
    }

    private TimeToChronoUnitConverter() {
        // helper class
    }

    /**
     * Returns the {@link ChronoUnit} representing the argument {@link TimeUnit}.
     *
     * @param unit the time unit to convert
     * @return the chrono unit
     */
    public static ChronoUnit of(TimeUnit unit) {
        return Optional.ofNullable(TIME_UNIT_TO_CHRONO_UNIT.get(unit))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported TimeUnit."));
    }
}
