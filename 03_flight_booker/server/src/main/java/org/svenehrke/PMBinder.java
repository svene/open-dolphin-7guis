package org.svenehrke;

public class PMBinder {

	public void bind(ServerAPI serverAPI, DomainLogic domainLogic) {

		// Handle change: flightType -> isReturnFlight:
		serverAPI.getFlightType().addPropertyChangeListener(evt -> {
			if (!(evt.getNewValue() instanceof String)) {
				return;
			}
			serverAPI.getReturnTypeEnabled().setValue(serverAPI.isReturnFlight());

		});

		// Handle change: startDate -> isStartDateValid:
		serverAPI.getStartDate().addPropertyChangeListener(evt -> {
			serverAPI.setStartDateValidity(domainLogic.isStartDateValid());
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
			enabled = serverAPI.isStartDateValid() && serverAPI.isReturnDateValid();

		}
		else {
			enabled = serverAPI.isStartDateValid();
		}


		serverAPI.setBookCommandEnabled(enabled);
	}

}
