package controller;

import java.awt.MenuItem;
import java.net.URL;
import java.util.ResourceBundle;

import bll.Member;
import bll.Operation;
import bll.OperationVehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;

public class ControllerOperationManagementOperationLookup implements Initializable {
	@FXML
	private Accordion dropMenuFilter;
	@FXML
	private MenuItem mItemRemoveBase, mItemUpdateBase;
	@FXML
	private Label lbShowNameData, lbShowAddressData, lbShowPostcodeAndPlaceData;
	@FXML
	private TableView<OperationVehicle> tvVehicleData;
	@FXML
	private TableView<Member> tvMemberData;
	@FXML
	private TableView<Operation> tvOperationData;
	@FXML
	private TitledPane tpOtherOrg, tpMember, tpOperationVehcile;
	@FXML
	private Button btnLoadAllOtherOrg, btnLoadAllMembers, btnLoadAllVehicles, btnLoadOperationOtherOrg,
			btnLoadOperationMembers, btnLoadOperationVehicles;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	private void onClickBtnLoadAllMembers(ActionEvent event) {

	}

	@FXML
	private void onClickBtnLoadAllOtherOrg(ActionEvent event) {

	}

	@FXML
	private void onClickBtnLoadAllVehicles(ActionEvent event) {

	}

	@FXML
	private void onClickBtnLoadOperationMembers(ActionEvent event) {

	}

	@FXML
	private void onClickBtnLoadOperationOtherOrg(ActionEvent event) {

	}

	@FXML
	private void onClickBtnLoadOperationVehicles(ActionEvent event) {

	}

	@FXML
	private void onClickMItemRemoveBase(ActionEvent event) {

	}

	@FXML
	private void onClickMItemRemoveMember(ActionEvent event) {

	}

	@FXML
	private void onClickMItemRemoveVehicle(ActionEvent event) {

	}

	@FXML
	private void onClickMItemUpdateBase(ActionEvent event) {

	}

	@FXML
	private void onClickMItemUpdateMember(ActionEvent event) {

	}

	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent event) {

	}
}