package org.svenehrke;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.opendolphin.binding.JFXBinder;
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.client.comm.OnFinishedHandlerAdapter;

import static org.svenehrke.ApplicationConstants.*;

import java.util.List;

public class Application extends javafx.application.Application {
    static ClientDolphin clientDolphin;

    private ComboBox<Pair<String, String>> flightTypeComboBox;
    private TextField startDateTextField;
	private TextField endDateTextField;
	private Button bookButton;

    @Override
    public void start(Stage stage) throws Exception {
        bootstrap(stage);
    }

    private Pane rootView() {
        Pane pane = new Pane();
		pane.setStyle("-fx-background-color: #008000;");
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

		ObservableList<Pair<String, String>> flightTypes = FXCollections.observableArrayList(new Pair<>("o", "one-way-flight"), new Pair<>("r", "return flight"));

		flightTypeComboBox = WidgetFactory.flightComboBox(flightTypes);

        startDateTextField = new TextField();
        endDateTextField = new TextField();
		bookButton = new Button();

        pane.getChildren().addAll(vBox);
        vBox.getChildren().addAll(flightTypeComboBox);
        vBox.getChildren().addAll(startDateTextField);
        vBox.getChildren().addAll(endDateTextField);
        vBox.getChildren().addAll(bookButton);
        bookButton.setText("Book");
        return pane;
    }

	private void bootstrap(final Stage stage) {

		Pane root = rootView();
		addClientSideAction();

		clientDolphin.send(COMMAND_INIT, new OnFinishedHandlerAdapter() {
    		@Override
    		public void onFinished(List<ClientPresentationModel> presentationModels) {
    			setupBinding();
				stage.show();
    		}
    	});

		Scene scene = new Scene(root, 300, 300);
		stage.setScene(scene);
		stage.setTitle("7 GUIs: Flight Booker");
	}

    private void setupBinding() {

        PresentationModel pm = clientDolphin.getAt(PM_APP);

        JFXBinder.bind(ATT_NAME).of(pm).to("text").of(startDateTextField);
        JFXBinder.bind("text").of(startDateTextField).to(ATT_NAME).of(pm);

        clientDolphin.getAt(PM_APP).getAt(ATT_NAME).setValue("Duke");
    }

    private void addClientSideAction() {
        bookButton.setOnAction(actionEvent -> clientDolphin.send(COMMAND_BOOK));
    }
}
