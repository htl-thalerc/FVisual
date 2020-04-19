package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import bll.OperationCode;
import bll.OperationType;
import handler.OperationCodeHandler;
import handler.OperationTypeHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerCreateOperationTabOperationManagement implements Initializable {
	@FXML
	private ComboBox<OperationCode> cbOperationCode;
	@FXML
	private ComboBox<OperationType> cbOperationType;
	@FXML
	private TextArea tfShortDescription;
	@FXML
	private TextField tfTitle, tfAddress;
	@FXML
	private DatePicker dpDate, dpTime;

	private ControllerCreateOperationManagement controllerCreateOperationManagement;

	public ControllerCreateOperationTabOperationManagement(
			ControllerCreateOperationManagement controllerCreateOperationManagement) {
		this.controllerCreateOperationManagement = controllerCreateOperationManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.fillComboboxes();
		this.initComponentsListeners();
	}
	
	private void fillComboboxes() {
		if(this.cbOperationCode.getItems().size() == 0) {
			ObservableList<OperationCode> obsListOfOperationCodes = FXCollections.observableArrayList();
			ObservableList<OperationType> obsListOfOperationTypes = FXCollections.observableArrayList();
			
			obsListOfOperationCodes.addAll(OperationCodeHandler.getInstance().getOperationCodeList());
			obsListOfOperationTypes.addAll(OperationTypeHandler.getInstance().getOperationTypeList());
			
			this.cbOperationCode.setItems(obsListOfOperationCodes);
			this.cbOperationType.setItems(obsListOfOperationTypes);
			
			this.cbOperationCode.getSelectionModel().select(0);
			this.cbOperationType.getSelectionModel().select(0);
		}
	}
	
	private void initComponentsListeners() {
		AtomicBoolean isValidTitle = new AtomicBoolean(false);
		AtomicBoolean isValidShortDesc = new AtomicBoolean(false);
		AtomicBoolean isValidAddress = new AtomicBoolean(false);
		AtomicBoolean isValidDate = new AtomicBoolean(false);
		AtomicBoolean isValidTime = new AtomicBoolean(true); //change to false
		
		this.tfTitle.textProperty().addListener((obj, oldValue, newValue) -> {
			if(this.tfTitle.getText().length() >= 10) {
				isValidTitle.set(true);
				this.tfTitle.setStyle("-fx-text-box-border: green;");
				this.tfTitle.setStyle("-fx-focus-color: green;");
				this.tfTitle.setStyle("-fx-border-color: green;");
			} else {
				isValidTitle.set(false);
				this.tfTitle.setStyle("-fx-text-box-border: red;");
				this.tfTitle.setStyle("-fx-focus-color: red;");
				this.tfTitle.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(),
					isValidAddress.get(), isValidDate.get(), isValidTime.get());
		});
		
		this.tfShortDescription.textProperty().addListener((obj, oldValue, newValue) -> {
			if(this.tfShortDescription.getText().length() >= 30) {
				isValidShortDesc.set(true);
				this.tfShortDescription.setStyle("-fx-text-box-border: green;");
				this.tfShortDescription.setStyle("-fx-focus-color: green;");
				this.tfShortDescription.setStyle("-fx-border-color: green;");
			} else {
				isValidShortDesc.set(false);
				this.tfShortDescription.setStyle("-fx-text-box-border: red;");
				this.tfShortDescription.setStyle("-fx-focus-color: red;");
				this.tfShortDescription.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(),
					isValidAddress.get(), isValidDate.get(), isValidTime.get());
		});
		
		this.tfAddress.textProperty().addListener((obj, oldValue, newValue) -> {
			if(this.tfAddress.getText().length() >= 1) {
				isValidAddress.set(true);
				this.tfAddress.setStyle("-fx-text-box-border: green;");
				this.tfAddress.setStyle("-fx-focus-color: green;");
				this.tfAddress.setStyle("-fx-border-color: green;");
			} else {
				isValidAddress.set(false);
				this.tfAddress.setStyle("-fx-text-box-border: red;");
				this.tfAddress.setStyle("-fx-focus-color: red;");
				this.tfAddress.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(),
					isValidAddress.get(), isValidDate.get(), isValidTime.get());
		});
		
		this.dpDate.valueProperty().addListener((obj, oldValue, newValue) -> {
			if(this.dpDate.getValue() != null) {
				isValidDate.set(true);
				this.dpDate.setStyle("");
			} else {
				isValidDate.set(false);
				this.dpDate.setStyle("-fx-text-box-border: red;");
				this.dpDate.setStyle("-fx-focus-color: red;");
				this.dpDate.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(),
					isValidAddress.get(), isValidDate.get(), isValidTime.get());
		});
	}
	
	private void setControlDisability(boolean isValidTitle, 
			boolean isValidShortDesc, boolean isValidAddress, boolean isValidDate, boolean isValidTime) {
		if (isValidTitle && isValidShortDesc && isValidAddress && isValidDate && isValidTime) {
			this.controllerCreateOperationManagement.setButtonResetDisability(false);
			this.controllerCreateOperationManagement.setButtonNextDisability(false);
			this.controllerCreateOperationManagement.setButtonFinishDisability(false);
			this.controllerCreateOperationManagement.setTabOperationVehicleManagementDisability(false);
			this.controllerCreateOperationManagement.setTabMemberManagementDisability(false);
			this.controllerCreateOperationManagement.setTabOtherOrganisationManagementDisability(false);
		} else {
			this.controllerCreateOperationManagement.setButtonResetDisability(true);
			this.controllerCreateOperationManagement.setButtonFinishDisability(true);
			this.controllerCreateOperationManagement.setButtonNextDisability(true);
			this.controllerCreateOperationManagement.setTabOperationVehicleManagementDisability(true);
			this.controllerCreateOperationManagement.setTabMemberManagementDisability(true);
			this.controllerCreateOperationManagement.setTabOtherOrganisationManagementDisability(true);
		}
	}
}