package org.svenehrke;

import java.util.function.Supplier;

public class DomainLogic {

	private final DateTimeService dateTimeService;
	private final Supplier<String> startDateSupplier;

	public DomainLogic(DateTimeService dateTimeService, Supplier<String> startDateSupplier) {

		this.dateTimeService = dateTimeService;
		this.startDateSupplier = startDateSupplier;
	}


	public boolean isStartDateValid() {
		return dateTimeService.isValidDate(startDateSupplier.get());
	}

	public boolean isBookingPosssible() {
		return false;
	}
}
