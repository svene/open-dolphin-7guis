package org.svenehrke;

import org.opendolphin.core.Tag;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;

import static org.svenehrke.ApplicationConstants.*;

public class ClientAPI {

	private final ClientDolphin clientDolphin;

	public static final Tag VALID_TAG = Tag.tagFor.get(TAG_VALID);


	public ClientAPI(ClientDolphin clientDolphin) {

		this.clientDolphin = clientDolphin;
	}

	public ClientPresentationModel getPM() {
		return clientDolphin.getAt(PM_APP);

	}


	public String getFlightType() {
		return (String) getPM().getAt(ATT_FLIGHT_TYPE).getValue();
	}

	public ClientAttribute getStartDate() {
		return getPM().getAt(ATT_START_DATE);
	}
	public String getStartDateValue() {
		return (String) getStartDate().getValue();
	}


	public ClientAttribute getValidStartDate() {
		return getPM().getAt(ATT_START_DATE, VALID_TAG);
	}

	public ClientAttribute getValidReturnDate() {
		return getPM().getAt(ATT_RETURN_DATE, VALID_TAG);
	}


}
