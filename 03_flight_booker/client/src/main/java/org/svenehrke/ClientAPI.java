package org.svenehrke;

import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;

import static org.svenehrke.ApplicationConstants.ATT_FLIGHT_TYPE;
import static org.svenehrke.ApplicationConstants.PM_APP;

public class ClientAPI {

	private final ClientDolphin clientDolphin;

	public ClientAPI(ClientDolphin clientDolphin) {

		this.clientDolphin = clientDolphin;
	}

	public ClientPresentationModel getPM() {
		return clientDolphin.getAt(PM_APP);

	}


	public String getFlightType() {
		return (String) getPM().getAt(ATT_FLIGHT_TYPE).getValue();
	}


}
