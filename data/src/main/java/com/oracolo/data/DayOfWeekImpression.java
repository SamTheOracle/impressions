package com.oracolo.data;

import java.time.DayOfWeek;
import java.util.Objects;

public class DayOfWeekImpression {

    private final DayOfWeek dayOfWeek;
    private final long count;
    public DayOfWeekImpression(DayOfWeek dayOfWeek, long count) {
        this.dayOfWeek = dayOfWeek;
        this.count = count;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayOfWeekImpression)) return false;
        DayOfWeekImpression that = (DayOfWeekImpression) o;
        return dayOfWeek == that.dayOfWeek;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek);
    }

    @Override
    public String toString() {
        return "DayOfWeekImpression{" +
                "dayOfWeek=" + dayOfWeek +
                ", count=" + count +
                '}';
    }
}
