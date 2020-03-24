package controller;

import java.net.URL;
import java.util.ResourceBundle;

import bll.Member;
import bll.Operation;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerOperationManagementOperationLookup implements Initializable {
	@FXML
    private Accordion dropMenuFilter;
    @FXML
    private TableView<Operation> tvOperationData;
    @FXML
    private MenuItem mItemRemoveOperation, mItemUpdateOperation;
    @FXML
    private Label lbShowCodeAndTypeAndTitleData, lbShowShortdescriptionData, lbShowAddressAndPlzData;
    @FXML
    private TableView<OperationVehicle> tvVehicleData;
    @FXML
    private TableView<OtherOrganisation> tvOtherOrgData;
    @FXML
    private TitledPane tpMember, tpOtherOrg, tpOperationVehcile;
    @FXML
    private TableView<Member> tvMemberData;
    @FXML
    private Button btnLoadAllOtherOrg, btnLoadAllMembers, btnLoadAllVehicles, btnLoadOperationOtherOrg, btnLoadOperationMembers,
    	btnLoadOperationVehicles;

	private ObservableList<Operation> obsListTVOperations = null;
	private ObservableList<OperationVehicle> obsListTVOperationVehicles = null;
	private ObservableList<Member> obsListTVMembers = null;
	private ObservableList<OtherOrganisation> obsListTVOtherOrgs = null;
	
	private ControllerOperationManagement controllerOperationManagement = null;
	
	public ControllerOperationManagementOperationLookup(ControllerOperationManagement controllerOperationManagement) {
		this.controllerOperationManagement = controllerOperationManagement;
	}
	
	public ControllerOperationManagementOperationLookup() {
		super();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTVOperation();
		this.initTVOperationVehicle();
		this.initTVOtherOrg();
		this.initTVMember();
	}
	
	@SuppressWarnings("unchecked")
	private void initTVOperation() {
		TableColumn<Operation, String> columnOperationCode = new TableColumn<Operation, String>("Code");
		TableColumn<Operation, String> columnOperationType = new TableColumn<Operation, String>("Type");
		TableColumn<Operation, String> columnAddress = new TableColumn<Operation, String>("Address");
		TableColumn<Operation, String> columnTitle = new TableColumn<Operation, String>("Title");
		TableColumn<Operation, String> columnDescription = new TableColumn<Operation, String>("Description");

		columnOperationCode.setCellValueFactory(new PropertyValueFactory<Operation, String>("operationCode"));
		columnOperationType.setCellValueFactory(new PropertyValueFactory<Operation, String>("operationType"));
		columnAddress.setCellValueFactory(new PropertyValueFactory<Operation, String>("address"));
		columnTitle.setCellValueFactory(new PropertyValueFactory<Operation, String>("title"));
		columnDescription.setCellValueFactory(new PropertyValueFactory<Operation, String>("shortDescription"));

		this.tvOperationData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnOperationCode.setMaxWidth(1f * Integer.MAX_VALUE * 10);
		columnOperationType.setMaxWidth(1f * Integer.MAX_VALUE * 10);
		columnAddress.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnTitle.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnDescription.setMaxWidth(1f * Integer.MAX_VALUE * 40);

		this.tvOperationData.getColumns().addAll(columnOperationCode, columnOperationType, columnTitle, columnDescription, columnAddress);
	}

	@SuppressWarnings("unchecked")
	private void initTVOperationVehicle() {
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>(
				"Description");
		TableColumn<OperationVehicle, String> columnBaseName = new TableColumn<OperationVehicle, String>("Operation");

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));
		columnBaseName
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		columnBaseName.setMaxWidth(1f * Integer.MAX_VALUE * 60);

		this.tvVehicleData.getColumns().addAll(columnVehicleDescription, columnBaseName);
	}
	
	@SuppressWarnings("unchecked")
	private void initTVOtherOrg() {
		TableColumn<OtherOrganisation, String> columnName = new TableColumn<OtherOrganisation, String>(
				"Name");
		TableColumn<OtherOrganisation, String> columnOpeartion = new TableColumn<OtherOrganisation, String>("Operation");

		columnName.setCellValueFactory(new PropertyValueFactory<OtherOrganisation, String>("name"));
		columnOpeartion
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOperation().getTitle()));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnName.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		columnOpeartion.setMaxWidth(1f * Integer.MAX_VALUE * 60);

		this.tvOtherOrgData.getColumns().addAll(columnName, columnOpeartion);
	}
	
	@SuppressWarnings("unchecked")
	private void initTVMember() {
		TableColumn<Member, String> colNameBlock = new TableColumn<Member, String>("Name");
		TableColumn<Member, String> colFirstname = new TableColumn<Member, String>("Firstname");
		TableColumn<Member, String> colLastname = new TableColumn<Member, String>("Lastname");
		TableColumn<Member, String> colUsername = new TableColumn<Member, String>("Username");
		TableColumn<Member, String> colContraction = new TableColumn<Member, String>("Rank");

		colFirstname.setCellValueFactory(new PropertyValueFactory<Member, String>("firstname"));
		colLastname.setCellValueFactory(new PropertyValueFactory<Member, String>("lastname"));
		colUsername.setCellValueFactory(new PropertyValueFactory<Member, String>("username"));

		colContraction.setCellValueFactory(new PropertyValueFactory<Member, String>("rank"));

		colNameBlock.getColumns().addAll(colFirstname, colLastname, colUsername);

		this.tvMemberData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		colFirstname.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		colContraction.setMaxWidth(1f * Integer.MAX_VALUE * 60);

		colFirstname.setMaxWidth(1f * Integer.MAX_VALUE * 25);
		colLastname.setMaxWidth(1f * Integer.MAX_VALUE * 25);
		colUsername.setMaxWidth(1f * Integer.MAX_VALUE * 50);

		this.tvMemberData.getColumns().addAll(colNameBlock, colContraction);
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
    private void onClickMItemRemoveMember(ActionEvent event) {

    }

    @FXML
    private void onClickMItemRemoveOperation(ActionEvent event) {

    }

    @FXML
    private void onClickMItemRemoveOrg(ActionEvent event) {

    }

    @FXML
    private void onClickMItemRemoveVehicle(ActionEvent event) {

    }

    @FXML
    private void onClickMItemUpdateMember(ActionEvent event) {

    }

    @FXML
    private void onClickMItemUpdateOperation(ActionEvent event) {

    }

    @FXML
    private void onClickMItemUpdateOrg(ActionEvent event) {

    }

    @FXML
    private void onClickMItemUpdateVehicle(ActionEvent event) {

    }
}