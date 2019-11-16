package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.CentralHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
	private Label lblMessage;
	private CentralHandler ch;
	private BorderPane mainPane;
	private ArrayList<Node> middlePaneContent = new ArrayList<>();
	private static Stage currentStage;
	
	static void setStage(Stage stage) {
		currentStage = stage;
		
		currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              System.exit(0);
	          }
	      });   
	}

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
	private void onClickmItemProfileSettings(ActionEvent aE) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/gui/EditProfile.fxml"));
		fxmlLoader.setController(new ControllerEditProfile());
		Scene scene = new Scene(fxmlLoader.load(), 476, 657);
		Stage stage = new Stage();
		stage.setScene(scene);
		ControllerEditProfile.setStage(stage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
		if(ch.getMember() == null) {
			currentStage.close();
		}
	}
	
	

	@FXML
	private void onClickmItemLogout(ActionEvent aE) {
		currentStage.close();
	}
}