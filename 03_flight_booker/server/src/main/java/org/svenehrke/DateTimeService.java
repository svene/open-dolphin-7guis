package org.svenehrke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeService {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public LocalDate parse(String s) {
		try {
			LocalDate result = LocalDate.parse(s, formatter);
			System.out.println("*** valid date:" + s);
			return result;
		} catch (DateTimeParseException e) {
			System.out.println("*** invalid date:" + s);
			return null;
		}
	}

	public String format(LocalDate localDate) {
		return formatter.format(localDate);
	}
}
