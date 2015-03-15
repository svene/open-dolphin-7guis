package org.svenehrke;

import org.opendolphin.core.Tag;
import org.opendolphin.core.server.DTO;
import org.opendolphin.core.server.ServerDolphin;
import org.opendolphin.core.server.Slot;

import static org.svenehrke.ApplicationConstants.*;

public class PMCreator {

	public static void createPMs(ServerDolphin serverDolphin) {
		// Create PM:
		DTO dto = new DTO(
			new Slot(ATT_FLIGHT_TYPE, null),
			new Slot(ATT_START_DATE, null ),
			new Slot(ATT_START_DATE, null, null, VALID_TAG),
			new Slot(ATT_RETURN_DATE, null),
			new Slot(ATT_RETURN_DATE, null, null, VALID_TAG),
			new Slot(ATT_RETURN_DATE, null, null, Tag.ENABLED),
			new Slot(ATT_MESSAGE, null),
			new Slot(ATT_BOOK_COMMAND_ENABLED, null)
		);
		serverDolphin.presentationModel(PM_APP, null, dto);
	}
}
