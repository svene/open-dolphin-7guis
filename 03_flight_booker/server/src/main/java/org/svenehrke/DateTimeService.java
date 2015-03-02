package org.svenehrke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeService {

	public LocalDate parse(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		try {
			LocalDate result = LocalDate.parse(s, formatter);
			System.out.println("*** valid date:" + s);
			return result;
		} catch (DateTimeParseException e) {
			System.out.println("*** invalid date:" + s);
			return null;
		}
	}
}
