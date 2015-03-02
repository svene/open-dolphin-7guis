package org.svenehrke;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class MainView {

	private Pane root;

	private ComboBox<Pair<String, String>> flightTypeComboBox;
	private TextField startDateTextField;
	private TextField returnDateTextField;
	private Button bookButton;

	public MainView() {
		root = new Pane();
		root.setStyle("-fx-background-color: #008000;");
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);

		flightTypeComboBox = WidgetFactory.flightComboBox();

		startDateTextField = new TextField();
		returnDateTextField = new TextField();
		bookButton = new Button();

		root.getChildren().addAll(vBox);
		vBox.getChildren().addAll(flightTypeComboBox);
		vBox.getChildren().addAll(startDateTextField);
		vBox.getChildren().addAll(returnDateTextField);
		vBox.getChildren().addAll(bookButton);
		bookButton.setText("Book");
	}

	public Pane getRoot() {
		return root;
	}

	public ComboBox<Pair<String, String>> getFlightTypeComboBox() {
		return flightTypeComboBox;
	}

	public TextField getStartDateTextField() {
		return startDateTextField;
	}

	public TextField getReturnDateTextField() {
		return returnDateTextField;
	}

	public Button getBookButton() {
		return bookButton;
	}
}
