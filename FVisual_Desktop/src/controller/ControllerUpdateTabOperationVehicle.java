package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import bll.OperationVehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ControllerUpdateTabOperationVehicle implements Initializable {
	@FXML
	private ListView<OperationVehicle> lvVehicles;
	@FXML
	private Label lbOldVehiclename;
	@FXML
	private TextField tfNewVehiclename;
	@FXML
	private Label lbStatusbar;
	
	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog;
	private AtomicBoolean isValidVehiclename = new AtomicBoolean(false);
	
	public ControllerUpdateTabOperationVehicle(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
		this.initListeners();
	}

	public void setOperationVehicleData(OperationVehicle oldOperationVehicleData) {
		ObservableList<OperationVehicle> obsListOfVehicleData = FXCollections.observableArrayList();
		obsListOfVehicleData.add(oldOperationVehicleData);
		this.lvVehicles.setItems(obsListOfVehicleData);
		this.lvVehicles.getSelectionModel().select(oldOperationVehicleData);
		this.lbOldVehiclename.setText(oldOperationVehicleData.getDescription());
	}

	public OperationVehicle getNewOperationVehicleData() {
		OperationVehicle vehicle = new OperationVehicle();
		
		if(this.isValidVehiclename.get()) {
				vehicle.setDescription(this.tfNewVehiclename.getText());	
		} 
		return vehicle;
	}
	
	private void initListeners() {
		this.lvVehicles.setOnMouseClicked(event -> {
			OperationVehicle selectedOperationVehicle = this.lvVehicles.getSelectionModel().getSelectedItem();
			if(selectedOperationVehicle != null) {
				this.lbOldVehiclename.setText(selectedOperationVehicle.getDescription());
			} else {
				this.lbOldVehiclename.setText("No Operationvehicle selected");
			}
		});
		
		this.tfNewVehiclename.textProperty().addListener((obj, oldVal, newVal) -> {
			if(newVal.length() >= 3) {
				this.isValidVehiclename.set(true);
				this.lbStatusbar.setText("Valid Inputvalue for Vehiclename");
				this.tfNewVehiclename.setStyle("-fx-text-box-border: green;");
				this.tfNewVehiclename.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				this.isValidVehiclename.set(false);
				this.lbStatusbar.setText("InValid Inputvalue for Vehiclename is too short (length >= 3)");
				this.tfNewVehiclename.setStyle("-fx-text-box-border: red;");
				this.tfNewVehiclename.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
			}
		});
	}
}