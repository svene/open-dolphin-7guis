package org.svenehrke.sevenguis.counter;

import org.opendolphin.core.server.DTO;
import org.opendolphin.core.server.ServerAttribute;
import org.opendolphin.core.server.Slot;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

import java.util.Collections;

import static org.svenehrke.sevenguis.counter.ApplicationConstants.*;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

        actionRegistry.register(ApplicationConstants.COMMAND_INIT, (command, response) -> {
			// Create PM:
			DTO dto = new DTO( new Slot(ATT_COUNTER, null) );
			getServerDolphin().presentationModel(PM_APP, null, dto);

			// Init PM:
			getServerDolphin().getAt(PM_APP).getAt(ATT_COUNTER).setValue("0");
		});

        actionRegistry.register(ApplicationConstants.COMMAND_INC_COUNTER, (command, response) -> {
			ServerAttribute counterAttribute = getServerDolphin().getAt(PM_APP).getAt(ATT_COUNTER);
			Integer i = Integer.valueOf((String) counterAttribute.getValue()) + 1;
			String newValue = String.valueOf(i);
			counterAttribute.setValue(newValue);
		});

    }
}
