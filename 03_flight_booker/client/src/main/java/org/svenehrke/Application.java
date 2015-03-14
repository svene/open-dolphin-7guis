package org.svenehrke;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.client.comm.OnFinishedHandlerAdapter;

import java.util.List;

import static org.svenehrke.ApplicationConstants.COMMAND_CREATE_PMS;
import static org.svenehrke.ApplicationConstants.COMMAND_INIT_DATA;

public class Application extends javafx.application.Application {
    static ClientDolphin clientDolphin;

	private MainView mainView;

    @Override
    public void start(Stage stage) throws Exception {
        bootstrap(stage);
    }

	private void bootstrap(final Stage stage) {

		mainView = new MainView();
		clientDolphin.send(COMMAND_CREATE_PMS, new OnFinishedHandlerAdapter() {
    		@Override
    		public void onFinished(List<ClientPresentationModel> presentationModels) {
				MainViewInitializer mainViewInitializer = new MainViewInitializer(mainView, clientDolphin).initializeBinding();
				clientDolphin.send(COMMAND_INIT_DATA, new OnFinishedHandlerAdapter() {
						@Override
						public void onFinished(List<ClientPresentationModel> presentationModels) {
							mainViewInitializer.handleDataInitializedEvent();
							stage.show();
						}
					}
				);
			}
    	});

		Scene scene = new Scene(mainView.getRoot(), 300, 300);
		scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("7 GUIs: Flight Booker");
	}

}
