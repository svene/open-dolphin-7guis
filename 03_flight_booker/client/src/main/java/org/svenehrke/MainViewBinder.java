package org.svenehrke;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.opendolphin.binding.JFXBinder;
import org.opendolphin.core.Attribute;
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;

import static org.svenehrke.ApplicationConstants.*;

public class MainViewBinder {

	private final MainView mainView;
	private final ClientDolphin clientDolphin;
	public static final PseudoClass ERROR_CLASS = PseudoClass.getPseudoClass("error");

	public MainViewBinder(MainView mainView, ClientDolphin clientDolphin) {

		this.mainView = mainView;
		this.clientDolphin = clientDolphin;
	}

	public void bind() {

		ClientAPI clientAPI = new ClientAPI(clientDolphin);

		PresentationModel pm = clientDolphin.getAt(PM_APP);
		Attribute attFlightType = pm.getAt(ATT_FLIGHT_TYPE);

		ComboBox<Pair<String, String>> cb = mainView.getFlightTypeComboBox();
		cb.getItems().addAll(FXCollections.observableArrayList(new Pair<>(ApplicationConstants.VAL_FT_ONE_WAY, "one-way-flight"), new Pair<>(ApplicationConstants.VAL_FT_RETURN, "return flight")));

		// Initial DropDown value:
		cb.getSelectionModel().select(ComboBoxes.comboBoxItemForKey(cb, clientAPI.getFlightType())); // todo: note: two-way binding for initially selected item would be overkill

		cb.setOnAction(e -> {
			String k = cb.getSelectionModel().getSelectedItem().getKey();
			System.out.println("key = " + k);
			attFlightType.setValue(k);
		});

		JFXBinder.bind(ATT_RETURN_DATE_ENABLED).of(pm).using(value -> !(Boolean) value).to("disable").of(mainView.getReturnDateTextField());

		mainView.getStartDateTextField().setText(clientAPI.getStartDateValue()); // todo: note: only TF -> ATT binding is necessary, except for initilization from PM. Therefore this line is used instead of 2-way-binding which would be overkill
		JFXBinder.bind("text").of(mainView.getStartDateTextField()).to(ATT_START_DATE).of(pm);

		addReadBackgroundHandling(clientAPI.getValidStartDate(), mainView.getStartDateTextField());


		// Return Date:
		JFXBinder.bind("text").of(mainView.getReturnDateTextField()).to(ATT_RETURN_DATE).of(pm);
		addReadBackgroundHandling(clientAPI.getValidReturnDate(), mainView.getReturnDateTextField());

		JFXBinder.bind(ATT_BOOK_COMMAND_ENABLED).of(pm).using(v -> !(Boolean)v).to("disable").of(mainView.getBookButton());

	}

	// Red background on 'textField' when boolean 'attribute' is invalid
	public void addReadBackgroundHandling(ClientAttribute attribute, TextField textField) {
		attribute.addPropertyChangeListener(evt -> {
			if (evt.getNewValue() instanceof Boolean) {
				Boolean newValue = (Boolean) evt.getNewValue();
				textField.pseudoClassStateChanged(ERROR_CLASS, !newValue);
			}
		});
	}

}
