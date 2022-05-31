package com.oracolo.data;

import java.time.LocalTime;

public class HourOfDayImpression {

	private final LocalTime hourOfDay;
	private final Long count;

	public HourOfDayImpression(LocalTime hourOfDay, Long count) {
		this.hourOfDay = hourOfDay;
		this.count = count;
	}

	public LocalTime getHourOfDay() {
		return hourOfDay;
	}

	public Long getCount() {
		return count;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof HourOfDayImpression))
			return false;

		HourOfDayImpression that = (HourOfDayImpression) o;

		return hourOfDay.equals(that.hourOfDay);
	}

	@Override
	public int hashCode() {
		return hourOfDay.hashCode();
	}

	@Override
	public String toString() {
		return "HourOfDayImpression{" + "hourOfDay=" + hourOfDay + ", count=" + count + '}';
	}
}
