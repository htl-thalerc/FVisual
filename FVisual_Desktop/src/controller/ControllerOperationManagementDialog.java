package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bll.EnumCRUDOption;
import bll.Member;
import bll.Operation;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ControllerOperationManagementDialog implements Initializable{
    @FXML
    private Button btnCancel, btnOk;
    @FXML
    private ListView<OperationVehicle> lvOperationVehicles;
    @FXML
    private ListView<OtherOrganisation> lvOtherOrganisations;
    @FXML
    private ListView<Member> lvMembers;
    @FXML
    private Label lbAddress, lbOperationTitle, lbBaseInformation, lbShortDescription, lbOperationType, lbOperationCode,
    	lbStatusMessage;

    private ControllerCreateOperationManagement controllerCreateOperationManagement = null;
	private boolean btnSaveState = false;
	private EnumCRUDOption crudOption;

	public ControllerOperationManagementDialog(ControllerCreateOperationManagement controllerCreateOperationManagement,
			EnumCRUDOption option) {
		this.controllerCreateOperationManagement = controllerCreateOperationManagement;
		this.crudOption = option;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.crudOption.equals(EnumCRUDOption.POST)) {
			this.btnOk.setText("Save");
			this.lbStatusMessage.setText("Note: When creating Operation all associated Vehicles, Members and Organisations will be created");
		} else if(this.crudOption.equals(EnumCRUDOption.DELETE)) {
			this.btnOk.setText("Remove");
			this.lbStatusMessage.setText("Note: When removing Operation all associated Vehicles, Members and Organisations will be removed");
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
	
	public void setOperationData(Operation createdOperation) {
		this.lbAddress.setText(createdOperation.getAddress().trim());
		this.lbBaseInformation.setText(createdOperation.getBase().getName());
		this.lbOperationCode.setText(createdOperation.getOperationCode().getCode());
		this.lbOperationTitle.setText(createdOperation.getTitle());
		this.lbOperationType.setText(createdOperation.getOperationType().getDescription());
		this.lbShortDescription.setText(createdOperation.getShortDescription());
	}

	public void setListViewOperationVehicleData(List<OperationVehicle> collOfoperationVehicles) {
		ObservableList<OperationVehicle> obsListOfVehicles = FXCollections.observableArrayList(collOfoperationVehicles);
		this.lvOperationVehicles.setItems(obsListOfVehicles);
	}
	
	public void setListViewMemberData(List<Member> collOfMembers) {
		ObservableList<Member> obsListOfMembers = FXCollections.observableArrayList(collOfMembers);
		this.lvMembers.setItems(obsListOfMembers);
	}
	
	public void setListViewOtherOrgData(List<OtherOrganisation> collOfOtherOrgs) {
		ObservableList<OtherOrganisation> obsListOfOtherOrgs = FXCollections.observableArrayList(collOfOtherOrgs);
		this.lvOtherOrganisations.setItems(obsListOfOtherOrgs);
	}

	public boolean getButtonState() {
		return this.btnSaveState;
	}
}