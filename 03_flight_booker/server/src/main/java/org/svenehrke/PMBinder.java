package org.svenehrke;

import org.opendolphin.core.Tag;
import org.opendolphin.core.server.ServerDolphin;
import org.opendolphin.core.server.ServerPresentationModel;

import java.util.Arrays;

import static org.svenehrke.ApplicationConstants.*;
import static org.svenehrke.SharedDolphinFunctions.*;

public class PMBinder {

	public static void initializePMBinding(ServerDolphin serverDolphin, DomainLogic domainLogic) {
		ServerPresentationModel pm = serverDolphin.getAt(PM_APP);

		bindAttributeTo(pm.getAt(ATT_START_DATE), domainLogic::isDateStringValid, pm.getAt(ATT_START_DATE, VALID_TAG));

		bindAttributeTo(pm.getAt(ATT_RETURN_DATE), domainLogic::isDateStringValid, pm.getAt(ATT_RETURN_DATE, VALID_TAG));

		// Handle change: flightType -> isReturnFlight:
		pm.getAt(ATT_FLIGHT_TYPE).addPropertyChangeListener(PROP_VALUE, evt -> {
			pm.getAt(ATT_RETURN_DATE, Tag.ENABLED).setValue(domainLogic.isReturnFlight(stringValue(pm.getAt(ATT_FLIGHT_TYPE))));
		});

		// Handle change: flightType | startDate | returnDate => BookCommandEnabled
		Arrays.asList(
			pm.getAt(ATT_FLIGHT_TYPE), pm.getAt(ATT_START_DATE), pm.getAt(ATT_RETURN_DATE)
		).forEach(attr -> attr.addPropertyChangeListener(PROP_VALUE, evt -> evaluateBookCommandEnabled(pm, domainLogic)));

	}

	private static void evaluateBookCommandEnabled(ServerPresentationModel pm, DomainLogic domainLogic) {
		final boolean enabled;
		if (domainLogic.isReturnFlight(stringValue(pm.getAt(ATT_FLIGHT_TYPE)))) {
			enabled = booleanValue(pm.getAt(ATT_START_DATE, VALID_TAG)) && booleanValue(pm.getAt(ATT_RETURN_DATE, VALID_TAG))
				&& domainLogic.isValidDateSequence(stringValue(pm.getAt(ATT_START_DATE)), stringValue(pm.getAt(ATT_RETURN_DATE)));
		}
		else {
			enabled = booleanValue(pm.getAt(ATT_START_DATE, VALID_TAG));
		}

		pm.getAt(ATT_BOOK_COMMAND_ENABLED).setValue(enabled);
	}

}
