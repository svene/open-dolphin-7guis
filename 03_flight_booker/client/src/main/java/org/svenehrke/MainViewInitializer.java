package org.svenehrke;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;
import org.opendolphin.core.Tag;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;

import static org.svenehrke.ApplicationConstants.*;

public class MainViewInitializer {

	private final MainView mainView;
	private final ClientDolphin clientDolphin;

	public MainViewInitializer(MainView mainView, ClientDolphin clientDolphin) {

		this.mainView = mainView;
		this.clientDolphin = clientDolphin;
	}

	public MainViewInitializer initializeBinding() {

		ClientPresentationModel pm = clientDolphin.getAt(PM_APP);

		// Flight Type:
		ODComboBoxes.bindSelectionTo(mainView.flightTypeComboBox, pm.getAt(ATT_FLIGHT_TYPE));

		// Start Date:
		ODTextFields.addChangeBindingFromTo(mainView.startDateTextField, pm.getAt(ATT_START_DATE));
		ODTextFields.addRedBackgroundHandling(pm.getAt(ATT_START_DATE, VALID_TAG), mainView.startDateTextField);

		// Return Date:
		ODTextFields.addChangeBindingFromTo(mainView.returnDateTextField, pm.getAt(ATT_RETURN_DATE));
		ODTextFields.addRedBackgroundHandling(pm.getAt(ATT_RETURN_DATE, VALID_TAG), mainView.returnDateTextField);
		ODNodes.addDisablingBinding(pm.getAt(ATT_RETURN_DATE, Tag.ENABLED), mainView.returnDateTextField);

		// Book Button:
		ODNodes.addDisablingBinding(pm.getAt(ATT_BOOK_COMMAND_ENABLED), mainView.bookButton);
		mainView.bookButton.setOnAction(event -> clientDolphin.send(COMMAND_BOOK));

		// Message:
		pm.getAt(ATT_MESSAGE).addPropertyChangeListener(PROP_VALUE, evt -> {
			mainView.messageLabel.setText((String) evt.getNewValue());
		});

		return this;
	}

	/**
	 * todo: note: two-way binding for widgets would be overkill. That's why the widget's values are initialized here
	 */
	public void handleDataInitializedEvent() {

		ClientPresentationModel pm = clientDolphin.getAt(PM_APP);

		ComboBox<Pair<String, String>> cb = mainView.flightTypeComboBox;

		// todo (Sven 13.03.15): initialize the supported values on the serverside:
		cb.getItems().addAll(FXCollections.observableArrayList(new Pair<>(ApplicationConstants.VAL_FT_ONE_WAY, "one-way-flight"), new Pair<>(ApplicationConstants.VAL_FT_RETURN, "return flight")));
		ODComboBoxes.populateFromAttribute(cb, pm.getAt(ATT_FLIGHT_TYPE));

		ODTextFields.populateFromAttribute(mainView.startDateTextField, pm.getAt(ATT_START_DATE));
		ODTextFields.populateFromAttribute(mainView.returnDateTextField, pm.getAt(ATT_RETURN_DATE));
	}
}
