package org.svenehrke;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;
import org.opendolphin.binding.JFXBinder;
import org.opendolphin.core.Attribute;
import org.opendolphin.core.PresentationModel;
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

		PresentationModel pm = clientDolphin.getAt(PM_APP);
		Attribute attFlightType = pm.getAt(ATT_FLIGHT_TYPE);
		Attribute attStartDate = pm.getAt(ATT_START_DATE);
		Attribute attInvalidStartDate = pm.getAt(ATT_INVALID_START_DATE);

		ComboBox<Pair<String, String>> cb = mainView.getFlightTypeComboBox();
		cb.getItems().addAll(FXCollections.observableArrayList(new Pair<>(ApplicationConstants.VAL_FT_ONE_WAY, "one-way-flight"), new Pair<>(ApplicationConstants.VAL_FT_RETURN, "return flight")));

		// Initial DropDown value:
		String key = (String) attFlightType.getValue();
		for (Pair<String, String> pair : cb.getItems()) {
			if (pair.getKey().equals(key)) {
				cb.getSelectionModel().select(pair);
			}
		}

		cb.setOnAction(e -> {
			String k = cb.getSelectionModel().getSelectedItem().getKey();
			System.out.println("key = " + k);
			attFlightType.setValue(k);
		});

		JFXBinder.bind(ATT_RETURN_DATE_ENABLED).of(pm).using(v -> !"Y".equals(v)).to("disable").of(mainView.getReturnDateTextField());

		JFXBinder.bind("text").of(mainView.getStartDateTextField()).to(ATT_START_DATE).of(pm);

		// Red background on start-date when invallid:
		attInvalidStartDate.addPropertyChangeListener(evt -> {
			if (evt.getNewValue() instanceof Boolean) {
				Boolean newValue = (Boolean) evt.getNewValue();
				mainView.getStartDateTextField().pseudoClassStateChanged(ERROR_CLASS, newValue);
			}
		});


//		JFXBinder.bind(ATT_FLIGHT_TYPE).of(pm).to("text").of(mainView.getStartDateTextField());
//		JFXBinder.bind("text").of(mainView.getStartDateTextField()).to(ATT_FLIGHT_TYPE).of(pm);

//		clientDolphin.getAt(PM_APP).getAt(ATT_FLIGHT_TYPE).setValue("Duke");

	}
}
