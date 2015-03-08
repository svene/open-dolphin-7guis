package org.svenehrke;

import java.util.function.Supplier;

public class DomainLogic {

	private final DateTimeService dateTimeService;
	private final Supplier<String> startDateSupplier;

	private DomainLogic(DateTimeService dateTimeService, Supplier<String> startDateSupplier) {

		this.dateTimeService = dateTimeService;
		this.startDateSupplier = startDateSupplier;
	}

	public boolean isStartDateValid() {
		return dateTimeService.isValidDate(startDateSupplier.get());
	}

	public boolean isBookingPosssible() {
		return false;
	}

	// Builder:
	public static IDateTimeServiceBuilder builder() {
		return dateTimeService -> startDateSupplier -> new DomainLogic(dateTimeService, startDateSupplier);
	}
	public interface IDateTimeServiceBuilder { IStartDateBuilder dateTimeService(DateTimeService dateTimeService);}
	public interface IStartDateBuilder { DomainLogic startDate(Supplier<String> startDateSupplier);}


}
