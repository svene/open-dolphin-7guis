package org.svenehrke;

import org.opendolphin.core.server.ServerPresentationModel;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

import static org.svenehrke.ApplicationConstants.*;
import static org.svenehrke.SharedDolphinFunctions.stringValue;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

		DomainLogic domainLogic = DomainLogic.builder().dateTimeService(new DateTimeService()).startDate(null);

		actionRegistry.register(ApplicationConstants.COMMAND_CREATE_PMS, (command, response) -> {

			PMCreator.createPMs(getServerDolphin());
			PMBinder.initializePMBinding(getServerDolphin(), domainLogic);
		});

        actionRegistry.register(ApplicationConstants.COMMAND_INIT_DATA, (command, response) -> {
			PMInitializer.initializePMs(getServerDolphin().getAt(PM_APP));
		});

        actionRegistry.register(ApplicationConstants.COMMAND_BOOK, (command, response) -> {
			ServerPresentationModel pm = getServerDolphin().getAt(PM_APP);
			String message = domainLogic.getMessageFor(domainLogic.isReturnFlight(stringValue(pm.getAt(ATT_FLIGHT_TYPE))), stringValue(pm.getAt(ATT_START_DATE)), stringValue(pm.getAt(ATT_RETURN_DATE)));
			pm.getAt(ATT_MESSAGE).setValue(message);
		});

    }

}
