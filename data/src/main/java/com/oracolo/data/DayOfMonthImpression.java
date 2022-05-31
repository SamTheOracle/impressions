package com.oracolo.data;

import java.time.LocalDate;

public class DayOfMonthImpression {

	private final LocalDate dayOfMonth;
	private final Long count;

	public DayOfMonthImpression(LocalDate dayOfMonth, Long count) {
		this.dayOfMonth = dayOfMonth;
		this.count = count;
	}

	public LocalDate getDayOfMonth() {
		return dayOfMonth;
	}

	public Long getCount() {
		return count;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DayOfMonthImpression))
			return false;

		DayOfMonthImpression that = (DayOfMonthImpression) o;

		return dayOfMonth.equals(that.dayOfMonth);
	}

	@Override
	public int hashCode() {
		return dayOfMonth.hashCode();
	}

	@Override
	public String toString() {
		return "DayOfMonthImpression{" + "dayOfMonth=" + dayOfMonth + ", count=" + count + '}';
	}
}
