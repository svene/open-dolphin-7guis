package org.svenehrke;

import org.opendolphin.core.server.*;

import java.time.LocalDate;

import static org.svenehrke.ApplicationConstants.*;

public class ServerAPI {
	private final ServerDolphin serverDolphin;
	private String startDate;

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
			new Slot(ATT_RETURN_DATE, ""),
			new Slot(ATT_VALID_START_DATE, Boolean.FALSE),
			new Slot(ATT_INVALID_RETURN_DATE, Boolean.FALSE),
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
	public ServerAttribute getReturnTypeEnabled() {
		return getPM().getAt(ATT_RETURN_DATE_ENABLED);
	}

	public String getStartDateValue() {
		return (String) getStartDate().getValue();
	}

	public ServerAttribute getStartDate() {
		return getPM().getAt(ATT_START_DATE);
	}

	public boolean isReturnFlight() {
		ServerAttribute attFlightType = getFlightType();
		return VAL_FT_RETURN.equals(attFlightType.getValue());
	}

	public void setStartDateValidity(boolean valid) {
		getPM().getAt(ATT_VALID_START_DATE).setValue(valid);
	}

	public boolean isStartDateValid() {
		return (boolean) getPM().getAt(ATT_VALID_START_DATE).getValue();
	}

	public void setBookCommandEnabled(boolean enabled) {
		getPM().getAt(ATT_BOOK_COMMAND_ENABLED).setValue(enabled);
	}

	public boolean isBookCommandEnabled() {
		return (boolean) getPM().getAt(ATT_BOOK_COMMAND_ENABLED).getValue();
	}

}
