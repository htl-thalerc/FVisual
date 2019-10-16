package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
	private AnchorPane mainPane;
	private ArrayList<Node> middlePaneContent = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	private void onClickmItemOperationManagement(ActionEvent aE) {
		AnchorPane operationManagementPane = null;
		try {
			this.middlePaneContent.clear();
			this.middlePaneContent.addAll(this.mainPane.getChildren());
			this.mainPane.getChildren().clear();
			operationManagementPane = FXMLLoader.load(getClass().getResource("/gui/OperationManagement.fxml"));
			this.mainPane.getChildren().add(operationManagementPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onClickmItemBaseManagement(ActionEvent aE) {
		AnchorPane baseManagementPane = null;
		try {
			this.middlePaneContent.clear();
			this.middlePaneContent.addAll(this.mainPane.getChildren());
			this.mainPane.getChildren().clear();
			baseManagementPane = FXMLLoader.load(getClass().getResource("/gui/BaseManagement.fxml"));
			this.mainPane.getChildren().add(baseManagementPane);
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