package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import bll.Base;
import bll.CRUDOption;
import bll.OperationVehicle;

public class ControllerBaseDialog implements Initializable {
	@FXML
	private Label lbBaseName;
	@FXML
	private Label lbPostcodePlace;
	@FXML
	private Label lbAddress;
	@FXML
	private Label lbStatusMessage;
	@FXML
	private ListView<OperationVehicle> lvVehicleData;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnOk;

	private ControllerCreateBase controllerCreateBase = null;
	private ControllerBaseManagementBaseLookup controllerBaseManagementBaseLookup = null;
	private boolean buttonState = false;
	private CRUDOption crudOption;

	public ControllerBaseDialog(ControllerBaseManagementBaseLookup controllerBaseManagementBaseLookup, CRUDOption crudOption) {
		this.controllerBaseManagementBaseLookup = controllerBaseManagementBaseLookup;
		this.crudOption = crudOption;
	}
	
	public ControllerBaseDialog(ControllerCreateBase controllerCreateBase, CRUDOption crudOption) {
		this.controllerCreateBase = controllerCreateBase;
		this.crudOption = crudOption;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.crudOption.equals(CRUDOption.POST)) {
			this.btnOk.setText("Save");
			this.lbStatusMessage.setText("Note: When creating Base all associated Vehicles are also going to be created");
		} else if(this.crudOption.equals(CRUDOption.DELETE)) {
			this.btnOk.setText("Remove");
			this.lbStatusMessage.setText("Note: When removing Base all associated Vehicles are also going to be removed");
		}
	}

	@FXML
	private void onClickBtnCancel(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.buttonState = false;
		stage.close();
	}

	@FXML
	private void onClickBtnOk(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.buttonState = true;
		stage.close();
	}

	public void setData(Base createdBase, List<OperationVehicle> collOfCreatedVehicles) {
		this.lbBaseName.setText(createdBase.getName());
		this.lbPostcodePlace.setText(createdBase.getPostCode() + ", " + createdBase.getPlace());
		this.lbAddress.setText(createdBase.getStreet() + ", " + createdBase.getHouseNr());

		ObservableList<OperationVehicle> obsListOfVehicles = FXCollections.observableArrayList(collOfCreatedVehicles);
		this.lvVehicleData.setItems(obsListOfVehicles);
	}

	public boolean getButtonState() {
		return this.buttonState;
	}
}