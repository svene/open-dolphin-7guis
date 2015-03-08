package org.svenehrke;

import org.opendolphin.core.Tag;
import org.opendolphin.core.server.*;

import java.time.LocalDate;

import static org.svenehrke.ApplicationConstants.*;

public class ServerAPI {

	public static final Tag VALID_TAG = Tag.tagFor.get(TAG_VALID);

	private final ServerDolphin serverDolphin;

	private ServerAPI(ServerDolphin serverDolphin) {

		this.serverDolphin = serverDolphin;
	}

	public static ServerAPI initializedInstance(ServerDolphin serverDolphin) {
		ServerAPI result = new ServerAPI(serverDolphin);
		return result.initialize();
	}

	public ServerAPI initialize() {
		// Create PM:
		DTO dto = new DTO(
			new Slot(ATT_FLIGHT_TYPE, ApplicationConstants.VAL_FT_ONE_WAY),
			new Slot(ATT_RETURN_DATE_ENABLED, Boolean.FALSE),
			new Slot(ATT_START_DATE, new DateTimeService().format(LocalDate.now())),
			new Slot(ATT_START_DATE, Boolean.FALSE, null, VALID_TAG),
			new Slot(ATT_RETURN_DATE, ""),
			new Slot(ATT_RETURN_DATE, Boolean.FALSE, null, VALID_TAG),
			new Slot(ATT_BOOK_COMMAND_ENABLED, Boolean.TRUE) // todo: remove redundancy to binding
		);
		ServerPresentationModel pm = serverDolphin.presentationModel(PM_APP, null, dto);

		return this;
	}

	public ServerPresentationModel getPM() {
		return serverDolphin.getAt(PM_APP);

	}

	public String getFlightTypeValue() {
		return (String) getFlightType().getValue();
	}

	public ServerAttribute getFlightType() {
		return getPM().getAt(ATT_FLIGHT_TYPE);
	}
	public ServerAttribute getStartDate() {
		return getPM().getAt(ATT_START_DATE);
	}
	public ServerAttribute getStartDateValid() {
		return getPM().getAt(ATT_START_DATE, VALID_TAG);
	}
	public ServerAttribute getReturnTypeEnabled() {
		return getPM().getAt(ATT_RETURN_DATE_ENABLED);
	}

	public ServerAttribute getReturnDate() {
		return getPM().getAt(ATT_RETURN_DATE);
	}
	public ServerAttribute getReturnDateValid() {
		return getPM().getAt(ATT_RETURN_DATE, VALID_TAG);
	}

	public boolean isReturnFlight() {
		ServerAttribute attFlightType = getFlightType();
		return VAL_FT_RETURN.equals(attFlightType.getValue());
	}

	public void setBookCommandEnabled(boolean enabled) {
		getPM().getAt(ATT_BOOK_COMMAND_ENABLED).setValue(enabled);
	}

	public boolean isBookCommandEnabled() {
		return (boolean) getPM().getAt(ATT_BOOK_COMMAND_ENABLED).getValue();
	}

}
