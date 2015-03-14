package org.svenehrke;

import java.time.LocalDate;
import java.util.function.Supplier;

public class DomainLogic {

	private final DateTimeService dateTimeService;

	private DomainLogic(DateTimeService dateTimeService) {

		this.dateTimeService = dateTimeService;
	}

	public boolean isDateStringValid(String dateString) {
		boolean validDate = dateTimeService.isValidDate(dateString);
		return validDate;
	}

	public boolean isValidDateSequence(String dateString1, String dateString2) {
		LocalDate d1 = dateTimeService.dateFromString(dateString1);
		LocalDate d2 = dateTimeService.dateFromString(dateString2);
		return d2.isAfter(d1);
	}

	// Builder:
	public static IDateTimeServiceBuilder builder() {
		return dateTimeService -> startDateSupplier -> new DomainLogic(dateTimeService);
	}
	public interface IDateTimeServiceBuilder { IStartDateBuilder dateTimeService(DateTimeService dateTimeService);}
	public interface IStartDateBuilder { DomainLogic startDate(Supplier<String> startDateSupplier);}


}
