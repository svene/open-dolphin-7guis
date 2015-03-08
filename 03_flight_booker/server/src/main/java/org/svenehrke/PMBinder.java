package org.svenehrke;

import org.opendolphin.core.server.ServerDolphin;

public class PMBinder {

	private final ServerAPI serverAPI;
	private DomainLogic domainLogic;
	private ValidatingAttribute<String> startDateValid;

	public PMBinder(ServerDolphin serverDolphin) {
		serverAPI = ServerAPI.initializedInstance(serverDolphin);
		domainLogic = DomainLogic.builder()
			.dateTimeService(new DateTimeService())
			.startDate(null);
		startDateValid = new ValidatingAttribute<>(serverAPI.getStartDate(), serverAPI.getStartDateValid(), domainLogic::isDateStringValid);
	}

	public void bind() {

		startDateValid.bind();

		// Handle change: flightType -> isReturnFlight:
		serverAPI.getFlightType().addPropertyChangeListener(evt -> {
			if (!(evt.getNewValue() instanceof String)) {
				return;
			}
			serverAPI.getReturnTypeEnabled().setValue(serverAPI.isReturnFlight());

		});

		// Handle change: returnDate -> isReturnDateValid:
		serverAPI.getReturnDate().addPropertyChangeListener(evt -> {
			System.out.println("return date changed");
			serverAPI.setReturnDateValidity(domainLogic.isDateStringValid((String) serverAPI.getReturnDate().getValue()));
		});


		// Handle return-date change:
//			attReturnDate.addPropertyChangeListener(evt -> {
//				String s = (String) attStartDate.getValue();
//				LocalDate localDate = new DateTimeService().parse(s);
//				attInvalidReturnDate.setValue(localDate == null);
//			});

		// Init:
		startDateValid.getAttribute().addPropertyChangeListener(evt -> {
			evaluateBookCommandEnabled(serverAPI);
		});

		evaluateBookCommandEnabled(serverAPI);


	}

	private void evaluateBookCommandEnabled(ServerAPI serverAPI) {
		final boolean enabled;
		if (serverAPI.isReturnFlight()) {
			enabled = startDateValid.isOK() && serverAPI.isReturnDateValid();
		}
		else {
			enabled = startDateValid.isOK();
		}


		serverAPI.setBookCommandEnabled(enabled);
	}

}
