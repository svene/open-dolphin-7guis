package org.svenehrke;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.opendolphin.binding.JFXBinder;
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.client.comm.OnFinishedHandlerAdapter;

import java.util.List;

import static org.svenehrke.ApplicationConstants.*;

public class Application extends javafx.application.Application {
    static ClientDolphin clientDolphin;

    private TextField celsiusTextField;
    private TextField fahrenheitTextField;
    private Label celsiusLabel;
    private Label fahrenheitLabel;

    @Override
    public void start(Stage stage) throws Exception {

        Pane root = setupStage();

        initializePMs();

        Scene scene = new Scene(root, 600, 100);
        scene.setFill(Color.GREEN);
        stage.setScene(scene);
        stage.setTitle("7 GUIs: Temperature Converter");
        stage.show();
    }

    private Pane setupStage() {
        Pane pane = new Pane();
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
		celsiusTextField = new TextField();

        celsiusLabel = new Label("");
		celsiusLabel.setFont(Font.font("Verdana", 20));

        fahrenheitLabel = new Label("");
		fahrenheitLabel.setFont(Font.font("Verdana", 20));

		fahrenheitTextField = new TextField();

        pane.getChildren().addAll(hBox);
		hBox.getChildren().addAll(celsiusTextField, celsiusLabel, fahrenheitTextField, fahrenheitLabel);
        celsiusLabel.setText("Celsius");
		fahrenheitLabel.setText("Fahrenheit");
        return pane;
    }

    private void initializePMs() {
		clientDolphin.send(COMMAND_INIT, new OnFinishedHandlerAdapter() {
    		@Override
    		public void onFinished(List<ClientPresentationModel> presentationModels) {
    			setupBinding();
    		}
    	});
    }

    private void setupBinding() {

        PresentationModel pm = clientDolphin.getAt(PM_APP);

		JFXBinder.bind("text").of(celsiusTextField).to(ATT_CELSIUS).of(pm);
		JFXBinder.bind(ATT_CELSIUS).of(pm).to("text").of(celsiusTextField);

		JFXBinder.bind("text").of(fahrenheitTextField).to(ATT_FAHRENHEIT).of(pm);
		JFXBinder.bind(ATT_FAHRENHEIT).of(pm).to("text").of(fahrenheitTextField);


		celsiusTextField.focusedProperty().addListener((s,o,gained) -> {
			System.out.println("celsiusTextField.focusedProperty: gained: " + gained);
			if (gained) {
				pm.getAt(ATT_DIRECTION).setValue(VAL_DIRECTION_CELSIUS_TO_FAHRENHEIT);
			}
		});

		fahrenheitTextField.focusedProperty().addListener((s,o,gained) -> {
			System.out.println("fahrenheitTextField.focusedProperty: gained: " + gained);
			if (gained) {
				pm.getAt(ATT_DIRECTION).setValue(VAL_DIRECTION_FAHRENHEIT_TO_CELSIUS);
			}
		});


    }

}
