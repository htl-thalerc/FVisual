package controller;

import java.net.URL;
import java.util.ResourceBundle;

import bll.OperationVehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControllerCreateBaseTabOperationVehicle implements Initializable {
	@FXML private ComboBox<OperationVehicle> cbAutoCompleteVehicle;
	@FXML private Button btnAddSelectedVehicle;
	@FXML private Button btnAddEnteredVehicle;
	@FXML private TextField tfDescription;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML private void onClickBtnAddSelectedVehicle(ActionEvent aE) {
		
	}
	
	@FXML private void onClickAddEnteredVehicle(ActionEvent aE) {
		
	}
}