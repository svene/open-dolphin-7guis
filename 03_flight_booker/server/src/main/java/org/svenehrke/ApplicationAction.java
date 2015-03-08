package org.svenehrke;

import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

        actionRegistry.register(ApplicationConstants.COMMAND_INIT, (command, response) -> {

			ServerAPI serverAPI = ServerAPI.initializedInstance(getServerDolphin());
			DomainLogic domainLogic = DomainLogic.builder()
				.dateTimeService(new DateTimeService())
				.startDate(serverAPI::getStartDateValue);

			new PMBinder().bind(serverAPI, domainLogic);




		});

        actionRegistry.register(ApplicationConstants.COMMAND_BOOK, (command, response) -> {
			System.out.println("Server reached.");
		});

    }


}
