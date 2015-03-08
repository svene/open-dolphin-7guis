package org.svenehrke;

import org.opendolphin.core.server.ServerDolphin;

public class PMBinder {

	private final ServerAPI serverAPI;
	private DomainLogic domainLogic;
	private AttributeValidator<String> startDateValidator;
	private AttributeValidator<String> returnDateValidator;

	public PMBinder(ServerDolphin serverDolphin) {
		serverAPI = ServerAPI.initializedInstance(serverDolphin);
		domainLogic = DomainLogic.builder()
			.dateTimeService(new DateTimeService())
			.startDate(null);
		startDateValidator = new AttributeValidator<>(serverAPI.getStartDate(), serverAPI.getStartDateValid(), domainLogic::isDateStringValid);
		returnDateValidator = new AttributeValidator<>(serverAPI.getReturnDate(), serverAPI.getReturnDateValid(), domainLogic::isDateStringValid);
	}

	public void bind() {

		startDateValidator.bind();
		returnDateValidator.bind();

		// Handle change: flightType -> isReturnFlight:
		serverAPI.getFlightType().addPropertyChangeListener(evt -> {
			if (!(evt.getNewValue() instanceof String)) {
				return;
			}
			serverAPI.getReturnTypeEnabled().setValue(serverAPI.isReturnFlight());

		});

		// Init:
		startDateValidator.getAttribute().addPropertyChangeListener(evt -> {
			evaluateBookCommandEnabled(serverAPI);
		});

		evaluateBookCommandEnabled(serverAPI);


	}

	private void evaluateBookCommandEnabled(ServerAPI serverAPI) {
		final boolean enabled;
		if (serverAPI.isReturnFlight()) {
			enabled = startDateValidator.isOK() && returnDateValidator.isOK();
		}
		else {
			enabled = startDateValidator.isOK();
		}


		serverAPI.setBookCommandEnabled(enabled);
	}

}
