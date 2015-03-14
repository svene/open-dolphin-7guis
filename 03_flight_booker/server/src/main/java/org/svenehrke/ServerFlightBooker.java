package org.svenehrke;

import org.opendolphin.core.Tag;
import org.opendolphin.core.server.*;

import static org.svenehrke.ApplicationConstants.*;
import static org.svenehrke.SharedDolphinFunctions.*;

public class ServerFlightBooker {

	private final ServerDolphin serverDolphin;

	public ServerFlightBooker(ServerDolphin serverDolphin) {

		this.serverDolphin = serverDolphin;
	}

	public static ServerFlightBooker initializedInstance(ServerDolphin serverDolphin) {
		return new ServerFlightBooker(serverDolphin).initialize();
	}

	private ServerFlightBooker initialize() {
		// Create PM:
		DTO dto = new DTO(
			new Slot(ATT_FLIGHT_TYPE, null),
			new Slot(ATT_START_DATE, null ),
			new Slot(ATT_START_DATE, null, null, VALID_TAG),
			new Slot(ATT_RETURN_DATE, null),
			new Slot(ATT_RETURN_DATE, null, null, VALID_TAG),
			new Slot(ATT_RETURN_DATE, null, null, Tag.ENABLED),
			new Slot(ATT_BOOK_COMMAND_ENABLED, null)
		);
		serverDolphin.presentationModel(PM_APP, null, dto);

		return this;
	}

	public ServerPresentationModel getPM() {
		return serverDolphin.getAt(PM_APP);
	}

	public ServerAttribute getFlightType() {
		return getPM().getAt(ATT_FLIGHT_TYPE);
	}

	public ServerAttribute getStartDate(Tag... tags) {
		return getPM().getAt(ATT_START_DATE, extractTag(tags));
	}

	public ServerAttribute getReturnDate(Tag... tags) {
		return getPM().getAt(ATT_RETURN_DATE, extractTag(tags));
	}

	public boolean isReturnFlight() {
		ServerAttribute attFlightType = getFlightType();
		return VAL_FT_RETURN.equals(attFlightType.getValue());
	}

	public ServerAttribute getBookCommandEnabled() {
		return getPM().getAt(ATT_BOOK_COMMAND_ENABLED);
	}
	public void setBookCommandEnabled(boolean enabled) {
		getBookCommandEnabled().setValue(enabled);
	}

}
