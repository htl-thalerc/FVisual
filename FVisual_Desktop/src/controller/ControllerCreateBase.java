package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class ControllerCreateBase implements Initializable {
	@FXML private Button btnCancel;
	@FXML private Button btnBack;
	@FXML private Button btnNext;
	@FXML private Button btnFinish;
	
	@FXML private Tab tabBase;
	@FXML private Tab tabOperationVehicle;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initPaneBase();
		this.initPaneOperationVehicle();
	}
	
	private void initPaneBase() {
		try {
			this.tabBase.setContent(FXMLLoader.load(getClass().getResource("/gui/CreateBaseTabBase.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initPaneOperationVehicle() {
		try {
			this.tabOperationVehicle.setContent(FXMLLoader.load(getClass().getResource("/gui/CreateBaseTabOperationVehicle.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void onClickBtnCancel(ActionEvent aE) {
		
	}
	
	@FXML private void onClickBtnBack(ActionEvent aE) {
		
	}

	@FXML private void onClickBtnNext(ActionEvent aE) {
	
	}

	@FXML private void onClickBtnFinish(ActionEvent aE) {
	
	}
}