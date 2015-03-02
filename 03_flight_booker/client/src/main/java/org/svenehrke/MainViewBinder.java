package org.svenehrke;

import javafx.collections.FXCollections;
import javafx.util.Pair;
import org.opendolphin.binding.JFXBinder;
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.client.ClientDolphin;

import static org.svenehrke.ApplicationConstants.ATT_NAME;
import static org.svenehrke.ApplicationConstants.PM_APP;

public class MainViewBinder {

	private final MainView mainView;
	private final ClientDolphin clientDolphin;

	public MainViewBinder(MainView mainView, ClientDolphin clientDolphin) {

		this.mainView = mainView;
		this.clientDolphin = clientDolphin;
	}

	public void bind() {

		mainView.getFlightTypeComboBox().getItems().addAll(FXCollections.observableArrayList(new Pair<>("o", "one-way-flight"), new Pair<>("r", "return flight")));

		PresentationModel pm = clientDolphin.getAt(PM_APP);

		JFXBinder.bind(ATT_NAME).of(pm).to("text").of(mainView.getStartDateTextField());
		JFXBinder.bind("text").of(mainView.getStartDateTextField()).to(ATT_NAME).of(pm);

		clientDolphin.getAt(PM_APP).getAt(ATT_NAME).setValue("Duke");

	}
}
