package org.svenehrke;

import org.opendolphin.core.server.ServerAttribute;
import org.opendolphin.core.server.ServerDolphin;
import org.opendolphin.core.server.ServerPresentationModel;

import static org.svenehrke.ApplicationConstants.*;

public class ServerAPI {
	private final ServerDolphin serverDolphin;
	private String startDate;

	public ServerAPI(ServerDolphin serverDolphin) {

		this.serverDolphin = serverDolphin;
	}

	public ServerPresentationModel getPM() {
		return serverDolphin.getAt(PM_APP);

	}

	public String getFlightType() {
		return (String) getPM().getAt(ATT_FLIGHT_TYPE).getValue();
	}
	public String getStartDate() {
		return (String) getPM().getAt(ATT_START_DATE).getValue();
	}

	public boolean isReturnFlight() {
		ServerAttribute attFlightType = getPM().getAt(ATT_FLIGHT_TYPE);
		return VAL_FT_RETURN.equals(attFlightType.getValue());
	}


}
