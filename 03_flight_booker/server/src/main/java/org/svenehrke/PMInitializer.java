package org.svenehrke;

import org.opendolphin.core.server.ServerPresentationModel;

import java.time.LocalDate;

import static org.svenehrke.ApplicationConstants.*;

public class PMInitializer {

	public static void initializePMs(ServerPresentationModel pm) {
		pm.getAt(ATT_FLIGHT_TYPE).setValue(VAL_FT_ONE_WAY);

		pm.getAt(ATT_START_DATE).setValue(new DateTimeService().format(LocalDate.now()) );
		pm.getAt(ATT_RETURN_DATE).setValue(""); // initial (invalid) value
		pm.getAt(ATT_MESSAGE).setValue("");
	}

}
