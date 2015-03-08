package org.svenehrke;

import org.opendolphin.core.server.ServerDolphin;

public class PMBinder {

	public void bind(ServerDolphin serverDolphin) {

		ServerAPI serverAPI = ServerAPI.initializedInstance(serverDolphin);
		DomainLogic domainLogic = DomainLogic.builder()
			.dateTimeService(new DateTimeService())
			.startDate(serverAPI::getStartDateValue);

		// Handle change: flightType -> isReturnFlight:
		serverAPI.getFlightType().addPropertyChangeListener(evt -> {
			if (!(evt.getNewValue() instanceof String)) {
				return;
			}
			serverAPI.getReturnTypeEnabled().setValue(serverAPI.isReturnFlight());

		});

		// Handle change: startDate -> isStartDateValid:
		serverAPI.getStartDate().addPropertyChangeListener(evt -> {
			serverAPI.startDateValid.setOK(domainLogic.isDateStringValid(serverAPI.getStartDateValue()));
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
		serverAPI.getStartDate().addPropertyChangeListener(evt -> {
			evaluateBookCommandEnabled(serverAPI);
		});

		evaluateBookCommandEnabled(serverAPI);


	}

	private void evaluateBookCommandEnabled(ServerAPI serverAPI) {
		final boolean enabled;
		if (serverAPI.isReturnFlight()) {
			enabled = serverAPI.startDateValid.isOK() && serverAPI.isReturnDateValid();

		}
		else {
			enabled = serverAPI.startDateValid.isOK();
		}


		serverAPI.setBookCommandEnabled(enabled);
	}

}
