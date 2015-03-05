package org.svenehrke;

import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

        actionRegistry.register(ApplicationConstants.COMMAND_INIT, (command, response) -> {

			ServerAPI serverAPI = ServerAPI.initializedInstance(getServerDolphin());
			DomainLogic domainLogic = DomainLogic.domainLogic()
				.dateTimeService(new DateTimeService())
				.startDate(serverAPI::getStartDateValue);


			// Handle flight type change:
			serverAPI.getFlightType().addPropertyChangeListener(evt -> {
				if (!(evt.getNewValue() instanceof String)) {
					return;
				}
				System.out.println("*** ATT_FLIGHT_TYPE: " + evt.getNewValue());
				serverAPI.getReturnTypeEnabled().setValue(serverAPI.isReturnFlight());

			});


			// Handle from-date change:
			serverAPI.getStartDate().addPropertyChangeListener(evt -> {
				String s = serverAPI.getStartDateValue();
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

		});

        actionRegistry.register(ApplicationConstants.COMMAND_BOOK, (command, response) -> {
			System.out.println("Server reached.");
		});

    }


	private void evaluateBookCommandEnabled(ServerAPI serverAPI) {
		serverAPI.setBookCommandEnabled(
			serverAPI.isStartDateValid()
		);
	}
}
