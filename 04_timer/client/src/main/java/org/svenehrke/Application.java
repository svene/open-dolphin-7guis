package org.svenehrke;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.client.comm.OnFinishedHandlerAdapter;

import java.util.List;

import static org.svenehrke.ApplicationConstants.COMMAND_INIT;

public class Application extends javafx.application.Application {
    static ClientDolphin clientDolphin;

    private Button resetButton;
    private Label elapsedTimeLabel;
    private ProgressBar progressBar;
    private Slider slider;

    public static final Integer DURATION_SEC = 5;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(0);
    private Timeline timeline;

    @Override
    public void start(Stage stage) throws Exception {
        bootstrap(stage);
    }

    private Pane rootView() {
        Pane pane = new Pane();
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.setFillWidth(true);
        pane.getChildren().addAll(vBox);

        HBox hBox = new HBox();
        progressBar = new ProgressBar();
        progressBar.setProgress(0);
        hBox.getChildren().addAll(new Label("Elapsed time:"), progressBar);
        vBox.getChildren().addAll(hBox);

        elapsedTimeLabel = new Label("");
        vBox.getChildren().addAll(elapsedTimeLabel);

        hBox = new HBox();
        slider = new Slider();
        slider.setMin(0);
        slider.setMax(20);
        slider.setValue(DURATION_SEC);
        hBox.getChildren().addAll(new Label("Duration"), slider);
        vBox.getChildren().addAll(hBox);

        resetButton = new Button();
        resetButton.setText("Reset");
        vBox.getChildren().addAll(resetButton);

        return pane;
    }

    private void bootstrap(final Stage stage) {

		Pane root = rootView();

        clientDolphin.send(COMMAND_INIT, new OnFinishedHandlerAdapter() {
            @Override
            public void onFinished(List<ClientPresentationModel> presentationModels) {
                setupBinding();
                stage.show();
            }
        });

		Scene scene = new Scene(root, 300, 150);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        stage.setScene(scene);
		stage.setTitle("7 GUIs: Timer");
	}

    private void setupBinding() {


        // Binding:
        elapsedTimeLabel.textProperty().bind(timeSeconds.divide(100).asString().concat("s"));
        progressBar.progressProperty().bind(timeSeconds.divide(slider.getValue() * 100.0));


        slider.valueProperty().addListener((s, o, n) -> {
            System.out.println("n = " + n);
            startTimeLine();
        });

        resetButton.setOnAction(e -> {
                startTimeLine();
            }
        );

    }

    private void startTimeLine() {
        if (timeline != null) {
			timeline.stop();
		}
        timeSeconds.set(0);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
			new KeyFrame(Duration.seconds(slider.getValue()), new KeyValue(timeSeconds, slider.getValue() * 100))
		);
        timeline.playFromStart();
    }

}
