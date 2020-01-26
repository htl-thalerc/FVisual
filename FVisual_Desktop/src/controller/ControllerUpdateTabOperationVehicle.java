package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import bll.Member;
import bll.OperationVehicle;
import bll.Rank;
import handler.CentralUpdateHandler;
import handler.EditingListCellOperationVehicle;
import handler.OperationVehicleHandler;
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
	private Button btnAddNewVehicle, btnSaveUpdatedVehicleChanges;
	
	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog;
	private AtomicBoolean isValidVehiclename = new AtomicBoolean(false);
	private ObservableList<OperationVehicle> obsListOfVehicleData;
	
	private final String CONST_NAME_FOR_NEW_VEHICLE = "Name for new Vehicle";
	
	public ControllerUpdateTabOperationVehicle(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
		this.btnSaveUpdatedVehicleChanges.setDisable(true);
		this.initListViewListeners();
		this.initTextFieldListeners();
	}
	
	private void initListViewListeners() {
		this.lvVehicles.setOnMouseClicked(event -> {
			OperationVehicle selectedOperationVehicle = this.lvVehicles.getSelectionModel().getSelectedItem();
			if(selectedOperationVehicle != null) {
				this.lbOldVehiclename.setText(selectedOperationVehicle.getDescription());
			} else {
				if(this.lbOldVehiclename.getText().equals("No Vehicle selected") && selectedOperationVehicle == null) {
					this.btnSaveUpdatedVehicleChanges.setDisable(true);
				} else {
					this.lbOldVehiclename.setText("No Operationvehicle selected");	
				}
			}
		});
	}
	
	private void initTextFieldListeners() {
		this.tfNewVehiclename.textProperty().addListener((obj, oldVal, newVal) -> {
			if(newVal.length() >= 3) {
				if(this.tfNewVehiclename.getText().equals(CONST_NAME_FOR_NEW_VEHICLE)) {
					this.isValidVehiclename.set(false);
					this.btnSaveUpdatedVehicleChanges.setDisable(true);
					this.tfNewVehiclename.setStyle("-fx-text-box-border: red;");
					this.tfNewVehiclename.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				} else {
					this.isValidVehiclename.set(true);
					this.btnSaveUpdatedVehicleChanges.setDisable(false);
					this.tfNewVehiclename.setStyle("-fx-text-box-border: green;");
					this.tfNewVehiclename.setStyle("-fx-focus-color: green;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
					
					if(this.btnAddNewVehicle.getText().equals("Add Vehicle") && 
							this.lbOldVehiclename.getText().equals("Change Vehiclename")) {
						this.btnAddNewVehicle.setDisable(false);
						this.btnAddNewVehicle.setText("Save Vehicle");
						this.btnSaveUpdatedVehicleChanges.setDisable(true);
					} else if(this.btnAddNewVehicle.getText().equals("Save Vehicle") && 
							this.lbOldVehiclename.getText().equals("Change Vehiclename")) {
						this.btnSaveUpdatedVehicleChanges.setDisable(true);
						this.btnAddNewVehicle.setDisable(false);
					}
				} 
			} else {
				this.isValidVehiclename.set(false);
				this.btnSaveUpdatedVehicleChanges.setDisable(true);
				this.tfNewVehiclename.setStyle("-fx-text-box-border: red;");
				this.tfNewVehiclename.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				if(this.btnAddNewVehicle.getText().equals("Save Vehicle")) {
					this.btnAddNewVehicle.setDisable(true);
				} else {
					this.btnAddNewVehicle.setDisable(false);
				}
			}
		});
	}
	
	public void setListOfOperationVehicles(ArrayList<OperationVehicle> listOfOperationVehicles) {
		this.obsListOfVehicleData = FXCollections.observableArrayList();
		if(listOfOperationVehicles.size() == 1) {
			OperationVehicle vehicleToUpdate = listOfOperationVehicles.get(0);
			this.obsListOfVehicleData.add(vehicleToUpdate);
		} else {
			ArrayList<OperationVehicle> list = OperationVehicleHandler.getInstance().getVehicleListByBaseId();
			if(list.size() != 0) {
				for(int i=0;i<list.size();i++) {
					this.obsListOfVehicleData.add(list.get(i));
				}	
			}
		}
		this.lvVehicles.setItems(this.obsListOfVehicleData);
		if(this.obsListOfVehicleData.size() != 0) {
			this.lvVehicles.getSelectionModel().select(this.obsListOfVehicleData.get(0));
			this.lbOldVehiclename.setText(this.obsListOfVehicleData.get(0).getDescription());
		}
	}

	public ArrayList<OperationVehicle> getListOfNewOperationVehicles() {
		ArrayList<OperationVehicle> retVal = new ArrayList<OperationVehicle>();
		
		if(this.lvVehicles.getItems().size() == 1) {
			OperationVehicle vehicle = new OperationVehicle();
			
			if(this.isValidVehiclename.get()) {
				vehicle.setDescription(this.tfNewVehiclename.getText());	
			}
			retVal.add(vehicle);
		} else {
			for(int i=0;i< this.lvVehicles.getItems().size();i++) {
				retVal.add(this.lvVehicles.getItems().get(i));
			}
		}
		return retVal;
	}
	
	@FXML
	private void onClickBtnAddNewVehicle(ActionEvent event) {
		if(this.btnAddNewVehicle.getText().equals("Add Vehicle")) {
			this.lvVehicles.getSelectionModel().clearSelection();
			
			this.lvVehicles.setDisable(true);
			
			this.btnAddNewVehicle.setDisable(true);
			this.btnSaveUpdatedVehicleChanges.setDisable(true);
			
			this.lbOldVehiclename.setText("Change Vehiclename");
			this.tfNewVehiclename.setText(CONST_NAME_FOR_NEW_VEHICLE);
		} else if(this.btnAddNewVehicle.getText().equals("Save Vehicle")) {
			this.btnAddNewVehicle.setText("Add Vehicle");
			this.lvVehicles.setDisable(false);
			this.btnAddNewVehicle.setDisable(false);
			this.lbOldVehiclename.setText("No Vehicledata available");
			
			OperationVehicle vehicleToCreate = new OperationVehicle();
			vehicleToCreate.setBase(CentralUpdateHandler.getInstance().getCurrBaseToUpdate());
			vehicleToCreate.setBaseId(CentralUpdateHandler.getInstance().getCurrBaseToUpdate().getBaseId());
			
			vehicleToCreate.setDescription(this.tfNewVehiclename.getText().trim());
			vehicleToCreate.setOperationVehicleId(-1);
			
			this.obsListOfVehicleData.add(vehicleToCreate);
			this.lvVehicles.refresh();
			
			this.lvVehicles.getSelectionModel().clearSelection();
			this.tfNewVehiclename.setText("");
			this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
		}
	}
	
	@FXML
	private void onClickBtnSaveUpdatedVehicleChanges(ActionEvent event) {
		OperationVehicle currSelectedVehicle = this.lvVehicles.getSelectionModel().getSelectedItem();
		
		if(currSelectedVehicle != null) {
			if(this.isValidVehiclename.get()) {
				currSelectedVehicle.setDescription(this.tfNewVehiclename.getText().trim());
			} else {
				currSelectedVehicle.setDescription(this.lbOldVehiclename.getText());
			}
			
			for(int i=0;i<this.obsListOfVehicleData.size();i++) {
				if(this.obsListOfVehicleData.get(i).getOperationVehicleId() == currSelectedVehicle.getOperationVehicleId()) {
					this.obsListOfVehicleData.remove(this.obsListOfVehicleData.get(i));
					this.obsListOfVehicleData.add(currSelectedVehicle);
				}
			}
			this.lvVehicles.refresh();
			this.lvVehicles.getSelectionModel().clearSelection();
			this.tfNewVehiclename.setText("");
			this.lbOldVehiclename.setText("No Vehicle selected");
			this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			this.btnSaveUpdatedVehicleChanges.setDisable(false);
		}
	}
}