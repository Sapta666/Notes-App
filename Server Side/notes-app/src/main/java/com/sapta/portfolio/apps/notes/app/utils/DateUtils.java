package com.sapta.portfolio.apps.notes.app.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	public DateUtils() {
		// TODO Auto-generated constructor stub
	}

	public static int getCurrentNumDate() {
		int numDate = 0;
		LocalDate currentDate = LocalDate.now();

		numDate = Integer.parseInt(("" + currentDate.getYear()) + String.format("%02d", currentDate.getMonthValue())
				+ String.format("%02d", +currentDate.getDayOfMonth()));

		return numDate;
	}

	public static int getCurrentDecTime() {
		int decTime = 0;
		LocalTime currentTime = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");

		decTime = Integer.parseInt(currentTime.format(formatter));

		return decTime;
	}

}
