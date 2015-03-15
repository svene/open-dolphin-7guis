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
	private final ClientDolphin clientDolphin;

	public MainViewInitializer(MainView mainView, ClientDolphin clientDolphin) {

		this.mainView = mainView;
		this.clientDolphin = clientDolphin;
		flightBooker = new ClientFlightBooker(this.clientDolphin);
	}

	public MainViewInitializer initializeBinding() {

		// Flight Type:
		ODComboBoxes.bindSelectionTo(mainView.flightTypeComboBox, flightBooker.getFlightType());

		// Start Date:
		ODTextFields.addChangeBindingFromTo(mainView.startDateTextField, flightBooker.getStartDate());
		ODTextFields.addRedBackgroundHandling(flightBooker.getStartDate(VALID_TAG), mainView.startDateTextField);

		// Return Date:
		ODTextFields.addChangeBindingFromTo(mainView.returnDateTextField, flightBooker.getReturnDate());
		ODTextFields.addRedBackgroundHandling(flightBooker.getReturnDate(VALID_TAG), mainView.returnDateTextField);
		ODNodes.addDisablingBinding(flightBooker.getReturnDate(Tag.ENABLED), mainView.returnDateTextField);

		// Book Button:
		ODNodes.addDisablingBinding(flightBooker.getBookCommandEnabled(), mainView.bookButton);
		mainView.bookButton.setOnAction(event -> clientDolphin.send(COMMAND_BOOK));

		// Message:
		flightBooker.getPM().getAt(ATT_MESSAGE).addPropertyChangeListener(PROP_VALUE, evt -> {
			mainView.messageLabel.setText((String) evt.getNewValue());
		});

		return this;
	}

	/**
	 * todo: note: two-way binding for widgets would be overkill. That's why the widget's values are initialized here
	 */
	public void handleDataInitializedEvent() {

		ComboBox<Pair<String, String>> cb = mainView.flightTypeComboBox;

		// todo (Sven 13.03.15): initialize the supported values on the serverside:
		cb.getItems().addAll(FXCollections.observableArrayList(new Pair<>(ApplicationConstants.VAL_FT_ONE_WAY, "one-way-flight"), new Pair<>(ApplicationConstants.VAL_FT_RETURN, "return flight")));
		ODComboBoxes.populateFromAttribute(cb, flightBooker.getFlightType());

		ODTextFields.populateFromAttribute(mainView.startDateTextField, flightBooker.getStartDate());
		ODTextFields.populateFromAttribute(mainView.returnDateTextField, flightBooker.getReturnDate());
	}
}
