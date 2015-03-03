package org.svenehrke;

import org.opendolphin.core.server.DTO;
import org.opendolphin.core.server.ServerAttribute;
import org.opendolphin.core.server.ServerPresentationModel;
import org.opendolphin.core.server.Slot;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

import java.time.LocalDate;

import static org.svenehrke.ApplicationConstants.*;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

        actionRegistry.register(ApplicationConstants.COMMAND_INIT, (command, response) -> {

			DateTimeService dateTimeService = new DateTimeService();

			// Create PM:
			ServerAPI serverAPI = new ServerAPI(getServerDolphin());
			DTO dto = new DTO(
				new Slot(ATT_FLIGHT_TYPE, ApplicationConstants.VAL_FT_ONE_WAY),
				new Slot(ATT_RETURN_DATE_ENABLED, Boolean.FALSE),
				new Slot(ATT_START_DATE, ""),
				new Slot(ATT_RETURN_DATE, ""),
				new Slot(ATT_INVALID_START_DATE, Boolean.FALSE),
				new Slot(ATT_INVALID_RETURN_DATE, Boolean.FALSE)
			);
			ServerPresentationModel pm = getServerDolphin().presentationModel(PM_APP, null, dto);

			ServerAttribute attFlightType = pm.getAt(ATT_FLIGHT_TYPE);
			ServerAttribute attReturnDateEnabled = pm.getAt(ATT_RETURN_DATE_ENABLED);
			ServerAttribute attStartDate = pm.getAt(ATT_START_DATE);
			ServerAttribute attReturnDate = pm.getAt(ATT_RETURN_DATE);
			ServerAttribute attInvalidStartDate = pm.getAt(ATT_INVALID_START_DATE);
			ServerAttribute attInvalidReturnDate = pm.getAt(ATT_INVALID_RETURN_DATE);

			// Handle flight type change:
			attFlightType.addPropertyChangeListener(evt -> {
				if ( ! (evt.getNewValue() instanceof String)) {
					return;
				}
				System.out.println("*** ATT_FLIGHT_TYPE: " + evt.getNewValue());
				attReturnDateEnabled.setValue(serverAPI.isReturnFlight());

			});


			// Handle from-date change:
			attStartDate.addPropertyChangeListener(evt -> {
				String s = serverAPI.getStartDate();
				LocalDate localDate = dateTimeService.parse(s);
				attInvalidStartDate.setValue(localDate == null);
			});

			// Handle return-date change:
//			attReturnDate.addPropertyChangeListener(evt -> {
//				String s = (String) attStartDate.getValue();
//				LocalDate localDate = new DateTimeService().parse(s);
//				attInvalidReturnDate.setValue(localDate == null);
//			});


			// Init:
			attStartDate.setValue(dateTimeService.format(LocalDate.now()));

		});

        actionRegistry.register(ApplicationConstants.COMMAND_BOOK, (command, response) -> {
			System.out.println("Server reached.");
		});

    }
}
