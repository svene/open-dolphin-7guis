package org.svenehrke;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.opendolphin.core.client.ClientDolphin;
import org.svenehrke.javafxextensions.fxml.FXMLLoader2;
import org.svenehrke.javafxextensions.fxml.ViewAndRoot;

public class Application extends javafx.application.Application {
    static ClientDolphin clientDolphin;

    private Button button;
    private TextField nameTextField;
    private Label greetingLabel;

    @Override
    public void start(Stage stage) throws Exception {
        bootstrap(stage);
    }

    private Pane rootView() {
        final ViewAndRoot<MainView, BorderPane> cr = FXMLLoader2.loadFXML("/Main.fxml");
        MainView view = cr.getView();
        Pane rootLayout = cr.getRoot();
        return rootLayout;
    }

    private void bootstrap(final Stage stage) {

		Pane root = rootView();

		Scene scene = new Scene(root, 300, 300);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("7 GUIs: CRUD");
        stage.show();
	}


}
