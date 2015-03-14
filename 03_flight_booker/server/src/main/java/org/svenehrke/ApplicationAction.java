package org.svenehrke;

import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

        actionRegistry.register(ApplicationConstants.COMMAND_CREATE_PMS, (command, response) -> {

			ServerFlightBooker flightBooker = ServerFlightBooker.initializedInstance(getServerDolphin());
			PMInitializer pmInitializer = new PMInitializer(flightBooker);
			pmInitializer.createPMs();
			pmInitializer.bind();
		});

        actionRegistry.register(ApplicationConstants.COMMAND_INIT_DATA, (command, response) -> {
			new PMInitializer(new ServerFlightBooker(getServerDolphin())).initData();
		});

        actionRegistry.register(ApplicationConstants.COMMAND_BOOK, (command, response) -> {
			System.out.println("Server reached.");
		});

    }


}
