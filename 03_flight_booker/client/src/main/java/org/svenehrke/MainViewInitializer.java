package org.svenehrke;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;
import org.opendolphin.core.Tag;
import org.opendolphin.core.client.ClientDolphin;

import static org.svenehrke.ApplicationConstants.*;

public class MainViewInitializer {

	private final MainView mainView;
	private final ClientFlightBooker flightBooker;

	public MainViewInitializer(MainView mainView, ClientDolphin clientDolphin) {

		this.mainView = mainView;
		flightBooker = new ClientFlightBooker(clientDolphin);
	}

	public MainViewInitializer initializeBinding() {

		// Flight Type:
		ODComboBoxes.bindSelectionTo(mainView.getFlightTypeComboBox(), flightBooker.getFlightType());

		// Start Date:
		ODTextFields.addChangeBindingFromTo(mainView.getStartDateTextField(), flightBooker.getStartDate());
		ODTextFields.addRedBackgroundHandling(flightBooker.getStartDate(VALID_TAG), mainView.getStartDateTextField());

		// Return Date:
		ODTextFields.addChangeBindingFromTo(mainView.getReturnDateTextField(), flightBooker.getReturnDate());
		ODTextFields.addRedBackgroundHandling(flightBooker.getReturnDate(VALID_TAG), mainView.getReturnDateTextField());
		ODNodes.addDisablingBinding(flightBooker.getReturnDate(Tag.ENABLED), mainView.getReturnDateTextField());

		// Book Button:
		ODNodes.addDisablingBinding(flightBooker.getBookCommandEnabled(), mainView.getBookButton());

		return this;
	}

	/**
	 * todo: note: two-way binding for widgets would be overkill. That's why the widget's values are initialized here
	 */
	public void handleDataInitializedEvent() {

		ComboBox<Pair<String, String>> cb = mainView.getFlightTypeComboBox();

		// todo (Sven 13.03.15): initialize the supported values on the serverside:
		cb.getItems().addAll(FXCollections.observableArrayList(new Pair<>(ApplicationConstants.VAL_FT_ONE_WAY, "one-way-flight"), new Pair<>(ApplicationConstants.VAL_FT_RETURN, "return flight")));
		ODComboBoxes.populateFromAttribute(cb, flightBooker.getFlightType());

		ODTextFields.populateFromAttribute(mainView.getStartDateTextField(), flightBooker.getStartDate());
		ODTextFields.populateFromAttribute(mainView.getReturnDateTextField(), flightBooker.getReturnDate());
	}
}
