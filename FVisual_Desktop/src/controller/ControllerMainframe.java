package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class ControllerMainframe implements Initializable {
	@FXML
	private MenuItem mItemOperationManagement;
	@FXML
	private MenuItem mItemBaseManagement;
	@FXML
	private MenuItem mItemProfileSettings;
	@FXML
	private MenuItem mItemLogout;
	@FXML
	private BorderPane mainPane;
	private ArrayList<Node> middlePaneContent = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	private void onClickmItemOperationManagement(ActionEvent aE) {
		this.loadContentIntoMainPane("/gui/OperationManagement.fxml");
	}

	@FXML
	private void onClickmItemBaseManagement(ActionEvent aE) {
		this.loadContentIntoMainPane("/gui/BaseManagement.fxml");
	}
	
	private void loadContentIntoMainPane(String fxmlRessourceURL) {
		try {
			this.middlePaneContent.clear();
			this.middlePaneContent.addAll(this.mainPane.getChildren());
			this.mainPane.getChildren().clear();
			this.mainPane.getChildren().add(FXMLLoader.load(getClass().getResource(fxmlRessourceURL)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onClickmItemProfileSettings(ActionEvent aE) {

	}

	@FXML
	private void onClickmItemLogout(ActionEvent aE) {

	}
}