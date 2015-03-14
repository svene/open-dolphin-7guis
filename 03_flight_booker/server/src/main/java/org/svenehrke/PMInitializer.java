package org.svenehrke;

import org.opendolphin.core.Tag;

import java.time.LocalDate;
import java.util.Arrays;

import static org.svenehrke.ApplicationConstants.*;
import static org.svenehrke.SharedDolphinFunctions.*;

public class PMInitializer {

	private final ServerFlightBooker flightBooker;
	private DomainLogic domainLogic;

	public PMInitializer(ServerFlightBooker flightBooker) {
		this.flightBooker = flightBooker;
		domainLogic = DomainLogic.builder()
			.dateTimeService(new DateTimeService())
			.startDate(null);
	}

	public void createPMs() {

	}

	public void bind() {

		bindAttributeTo(flightBooker.getStartDate(), domainLogic::isDateStringValid, flightBooker.getStartDate(VALID_TAG));
		bindAttributeTo(flightBooker.getReturnDate(), domainLogic::isDateStringValid, flightBooker.getReturnDate(VALID_TAG));

		// Handle change: flightType -> isReturnFlight:
		flightBooker.getFlightType().addPropertyChangeListener(PROP_VALUE, evt -> flightBooker.getReturnDate(Tag.ENABLED).setValue(flightBooker.isReturnFlight()));

		// Handle change: flightType | startDate | returnDate => BookCommandEnabled
		Arrays.asList(
			flightBooker.getFlightType(), flightBooker.getStartDate(), flightBooker.getReturnDate()
		).forEach(attr -> attr.addPropertyChangeListener(PROP_VALUE, evt -> evaluateBookCommandEnabled()));
	}

	public void initData() {
		flightBooker.getFlightType().setValue(VAL_FT_ONE_WAY);

		flightBooker.getStartDate().setValue(new DateTimeService().format(LocalDate.now()) );
		flightBooker.getReturnDate().setValue(""); // initial (invalid) value

		evaluateBookCommandEnabled();
	}

	private void evaluateBookCommandEnabled() {
		final boolean enabled;
		if (flightBooker.isReturnFlight()) {
			enabled = booleanValue(flightBooker.getStartDate(VALID_TAG)) && booleanValue(flightBooker.getReturnDate(VALID_TAG))
				&& domainLogic.isValidDateSequence(stringValue(flightBooker.getStartDate()), stringValue(flightBooker.getReturnDate()));
		}
		else {
			enabled = booleanValue(flightBooker.getStartDate(VALID_TAG));
		}

		flightBooker.setBookCommandEnabled(enabled);
	}

}
