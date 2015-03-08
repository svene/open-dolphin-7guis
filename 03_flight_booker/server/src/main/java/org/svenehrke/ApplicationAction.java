package org.svenehrke;

import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

        actionRegistry.register(ApplicationConstants.COMMAND_INIT, (command, response) -> {

			new PMBinder(getServerDolphin()).bind();
		});

        actionRegistry.register(ApplicationConstants.COMMAND_BOOK, (command, response) -> {
			System.out.println("Server reached.");
		});

    }


}
