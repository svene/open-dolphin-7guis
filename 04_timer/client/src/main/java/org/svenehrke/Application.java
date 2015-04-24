package org.svenehrke;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.client.comm.OnFinishedHandlerAdapter;

import java.util.List;

import static org.svenehrke.ApplicationConstants.*;

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
        slider.setMax(100);
        slider.setValue(60);
        hBox.getChildren().addAll(new Label("Duration"), slider);
        vBox.getChildren().addAll(hBox);

        resetButton = new Button();
        resetButton.setText("Reset");
        vBox.getChildren().addAll(resetButton);

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

		Scene scene = new Scene(root, 300, 150);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        stage.setScene(scene);
		stage.setTitle("7 GUIs: Timer");
	}

    private void doSomething() {
        System.out.println("timeSeconds = " + timeSeconds);
    }

    private void setupBinding() {

        PresentationModel pm = clientDolphin.getAt(PM_APP);

//        JFXBinder.bind(ATT_GREETING).of(pm).to("text").of(elapsedTimeLabel);

        clientDolphin.getAt(PM_APP).getAt(ATT_NAME).setValue("Duke");

        // Binding:
        elapsedTimeLabel.textProperty().bind(timeSeconds.divide(100).asString().concat("s"));
        progressBar.progressProperty().bind(timeSeconds.divide(DURATION_SEC * 100.0));

        resetButton.setOnAction(e -> {
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds.set(0);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(DURATION_SEC), new KeyValue(timeSeconds, DURATION_SEC * 100))
                );
                timeline.playFromStart();
            }
        );

    }

    private void addClientSideAction() {
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clientDolphin.send(COMMAND_GREET);
            }
        });
    }
}
