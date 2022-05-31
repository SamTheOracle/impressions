package com.oracolo.data;

import java.util.Set;
import java.util.concurrent.CompletionStage;

/**
 * Extracts useful impression data in an async fashion
 */
public interface AsyncImpressionExtractor {

    /**
     *
     * @return A {@link CompletionStage} wrapping an immutable {@link Set} of {@link DeviceImpression}
     */
    CompletionStage<Set<DeviceImpression>> deviceImpressions();

    /**
     *
     * @return A {@link CompletionStage} wrapping an immutable {@link Set} of {@link DayOfMonthImpression}
     */
    CompletionStage<Set<DayOfMonthImpression>> dayOfMonthImpressions();

    /**
     *
     * @return A {@link CompletionStage} wrapping an immutable {@link Set} of {@link DayOfWeekImpression}
     */
    CompletionStage<Set<DayOfWeekImpression>> dayOfWeekImpressions();

    /**
     *
     * @return A {@link CompletionStage} wrapping an immutable {@link Set} of {@link HourOfDayImpression}
     */
    CompletionStage<Set<HourOfDayImpression>> hourOfDayImpressions();

    /**
     *
     * @return true, if the parsing of the dataset is done, false otherwise
     */
    boolean resourceReady();
}
