package org.svenehrke;

import org.opendolphin.binding.Binder;
import org.opendolphin.core.server.DTO;
import org.opendolphin.core.server.ServerPresentationModel;
import org.opendolphin.core.server.Slot;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

import static org.svenehrke.ApplicationConstants.ATT_CELSIUS;
import static org.svenehrke.ApplicationConstants.ATT_FAHRENHEIT;
import static org.svenehrke.ApplicationConstants.PM_APP;

public class ApplicationAction extends DolphinServerAction{

    public void registerIn(ActionRegistry actionRegistry) {

        actionRegistry.register(ApplicationConstants.COMMAND_INIT, (command, response) -> {
			// Create PM:
			DTO dto = new DTO( new Slot(ATT_CELSIUS, null), new Slot(ATT_FAHRENHEIT, null) );
			getServerDolphin().presentationModel(PM_APP, null, dto);

			// Init PM:
//			getServerDolphin().getAt(PM_APP).getAt(ATT_CELSIUS).setValue("0");

			ServerPresentationModel pm = getServerDolphin().getAt(PM_APP);
			pm.getAt(ATT_CELSIUS).addPropertyChangeListener(evt -> {
				if ( ! (evt.getNewValue() instanceof String)) {
					return;
				}
				String newValue = (String) evt.getNewValue();
				// C = (F - 32) * (5/9)
				// F = C * (9/5) + 32
				Double c = null;
				try {
					c = Double.valueOf(newValue);
					Double f = c * (9 / 5) + 32;
					String sf = String.valueOf(f);
					pm.getAt(ATT_FAHRENHEIT).setValue(sf);
				} catch (NumberFormatException e) {
					// invalid celsius string
				}
			});
//			Binder.bind(ATT_CELSIUS).of(pm).using(value -> "xx").to(ATT_FAHRENHEIT).of(pm);
		});


    }
}
