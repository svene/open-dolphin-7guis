package org.svenehrke;

import org.opendolphin.core.Tag;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;

import static org.svenehrke.ApplicationConstants.*;
import static org.svenehrke.SharedDolphinFunctions.*;

public class ClientFlightBooker {

	private final ClientDolphin clientDolphin;

	public ClientFlightBooker(ClientDolphin clientDolphin) {
		this.clientDolphin = clientDolphin;
	}

	public ClientPresentationModel getPM() {
		return clientDolphin.getAt(PM_APP);
	}

	public ClientAttribute getFlightType() {
		return getPM().getAt(ATT_FLIGHT_TYPE);
	}

	public ClientAttribute getStartDate(Tag... tags) {
		return getPM().getAt(ATT_START_DATE, extractTag(tags));
	}

	public ClientAttribute getReturnDate(Tag... tags) {
		return getPM().getAt(ATT_RETURN_DATE, extractTag(tags));
	}

	public ClientAttribute getBookCommandEnabled() {
		return getPM().getAt(ATT_BOOK_COMMAND_ENABLED);
	}
}
