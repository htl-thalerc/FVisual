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
import bll.EnumCRUDOption;
import bll.Member;
import bll.OperationVehicle;

public class ControllerBaseManagementDialog implements Initializable {
	@FXML
	private Label lbBasename, lbPostcodeAndPlace, lbStreetAndHouseNr, lbStatusMessage;
	@FXML
	private ListView<OperationVehicle> lvOperationVehicleData;
	@FXML
	private ListView<Member> lvMemberData;
	@FXML
	private Button btnCancel, btnOk;

	private ControllerCreateBaseManagement controllerCreateBase = null;
	private ControllerBaseManagementBaseLookup controllerBaseManagementBaseLookup = null;
	private boolean btnSaveState = false;
	private EnumCRUDOption crudOption;

	public ControllerBaseManagementDialog(ControllerBaseManagementBaseLookup controllerBaseManagementBaseLookup, EnumCRUDOption crudOption) {
		this.controllerBaseManagementBaseLookup = controllerBaseManagementBaseLookup;
		this.crudOption = crudOption;
	}
	
	public ControllerBaseManagementDialog(ControllerCreateBaseManagement controllerCreateBase, EnumCRUDOption crudOption) {
		this.controllerCreateBase = controllerCreateBase;
		this.crudOption = crudOption;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.crudOption.equals(EnumCRUDOption.POST)) {
			this.btnOk.setText("Save");
			this.lbStatusMessage.setText("Note: When creating Base all associated Vehicles and Members will be created");
		} else if(this.crudOption.equals(EnumCRUDOption.DELETE)) {
			this.btnOk.setText("Remove");
			this.lbStatusMessage.setText("Note: When removing Base all associated Vehicles and Members will be removed");
		}
	}

	@FXML
	private void onClickBtnCancel(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.btnSaveState = false;
		stage.close();
	}

	@FXML
	private void onClickBtnOk(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.btnSaveState = true;
		stage.close();
	}
	
	public void setBaseData(Base createdBase) {
		this.lbBasename.setText(createdBase.getName());
		this.lbPostcodeAndPlace.setText(createdBase.getPostCode() + ", " + createdBase.getPlace());
		this.lbStreetAndHouseNr.setText(createdBase.getStreet() + ", " + createdBase.getHouseNr());
	}

	public void setListViewOperationVehicleData(List<OperationVehicle> collOfoperationVehicles) {
		ObservableList<OperationVehicle> obsListOfVehicles = FXCollections.observableArrayList(collOfoperationVehicles);
		this.lvOperationVehicleData.setItems(obsListOfVehicles);
	}
	
	public void setListViewMemberData(List<Member> collOfMembers) {
		ObservableList<Member> obsListOfVehicles = FXCollections.observableArrayList(collOfMembers);
		this.lvMemberData.setItems(obsListOfVehicles);
	}

	public boolean getButtonState() {
		return this.btnSaveState;
	}
}