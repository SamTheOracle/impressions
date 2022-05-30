package com.oracolo.data;

import java.util.Set;

/**
 * Extracts useful impression data
 */
public interface ImpressionExtractor {

    /**
     *
     * @return An immutable {@link Set} of {@link DeviceImpression}
     */
    Set<DeviceImpression> deviceImpressions();

    Set<DayOfMonthImpression> dayOfMonthImpressions();

    Set<DayOfWeekImpression> dayOfWeekImpressions();

    Set<HourOfDayImpression> hourOfDayImpressions();
}
