package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import bll.OperationVehicle;
import handler.CentralUpdateHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ControllerUpdateTabOperationVehicle implements Initializable {
	@FXML
	private ListView<OperationVehicle> lvVehicles;
	@FXML
	private Label lbOldVehiclename, lbStatusbar;
	@FXML
	private TextField tfNewVehiclename;
	@FXML
	private Button btnAddNewVehicle;
	
	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog;
	private AtomicBoolean isValidVehiclename = new AtomicBoolean(false);
	
	public ControllerUpdateTabOperationVehicle(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
		this.initListViewListeners();
		this.initTextFieldListeners();
	}
	
	private void initListViewListeners() {
		this.lvVehicles.setOnMouseClicked(event -> {
			OperationVehicle selectedOperationVehicle = this.lvVehicles.getSelectionModel().getSelectedItem();
			if(selectedOperationVehicle != null) {
				this.lbOldVehiclename.setText(selectedOperationVehicle.getDescription());
			} else {
				this.lbOldVehiclename.setText("No Operationvehicle selected");
			}
		});
	}
	
	private void initTextFieldListeners() {
		this.tfNewVehiclename.textProperty().addListener((obj, oldVal, newVal) -> {
			if(newVal.length() >= 3) {
				this.isValidVehiclename.set(true);
				this.tfNewVehiclename.setStyle("-fx-text-box-border: green;");
				this.tfNewVehiclename.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
				if(this.btnAddNewVehicle.getText().equals("Save Member")) {
					this.btnAddNewVehicle.setDisable(false);
				}
			} else {
				this.isValidVehiclename.set(false);
				this.tfNewVehiclename.setStyle("-fx-text-box-border: red;");
				this.tfNewVehiclename.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				if(this.btnAddNewVehicle.getText().equals("Save Member")) {
					this.btnAddNewVehicle.setDisable(true);
				}
			}
		});
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
	
	@FXML
	private void onClickBtnAddNewVehicle(ActionEvent event) {
		if(this.btnAddNewVehicle.getText().equals("Add Vehicle")) {
			this.btnAddNewVehicle.setText("Save Vehicle");
			this.lvVehicles.setDisable(true);
			this.btnAddNewVehicle.setDisable(true);
			this.lbOldVehiclename.setText("No Vehicle selected");
			
			this.lbStatusbar.setText("Note: Fill all Textfield to save Operationvehicle");
		} else if(this.btnAddNewVehicle.getText().equals("Save Vehicle")) {
			this.btnAddNewVehicle.setText("Add Vehicle");
			this.lvVehicles.setDisable(false);
			this.btnAddNewVehicle.setDisable(false);
			this.lbOldVehiclename.setText("No Vehicledata available");
			
			OperationVehicle vehicleToCreate = new OperationVehicle();
			vehicleToCreate.setBase(CentralUpdateHandler.getInstance().getCurrBaseToUpdate());
			
			vehicleToCreate.setDescription(this.tfNewVehiclename.getText().trim());
			
			this.lbStatusbar.setText("Successfully added Operationvehicle '" + vehicleToCreate.getDescription() + "'");
		}
	}
}