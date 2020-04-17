package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import bll.Base;
import bll.Member;
import bll.Member_Is_In_Operation;
import bll.Operation;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import handler.BaseHandler;
import handler.CentralHandler;
import handler.CentralUpdateHandler;
import handler.MemberHandler;
import handler.OperationHandler;
import handler.OperationVehicleHandler;
import handler.OtherOrganisationHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import loader.OperationMemberLoader;

public class ControllerOperationManagementOperationLookup implements Initializable {
	@FXML
    private Accordion dropMenuFilter, accordionSubTables;
    @FXML
    private TableView<Operation> tvOperationData;
    @FXML
    private MenuItem mItemRemoveOperation, mItemUpdateOperation;
    @FXML
    private Label lbShowCodeAndTypeAndTitleData, lbShowShortdescriptionData, lbShowAddressAndPlzData, lbShowBase;
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
		
		this.fillTableViews(false);
		this.initTVOperationListeners();
	}

	@SuppressWarnings("unchecked")
	private void initTVOperation() {
		TableColumn<Operation, String> columnOperationCode = new TableColumn<Operation, String>("Code");
		TableColumn<Operation, String> columnOperationType = new TableColumn<Operation, String>("Type");
		TableColumn<Operation, String> columnBase = new TableColumn<Operation, String>("Base");
		TableColumn<Operation, String> columnTitle = new TableColumn<Operation, String>("Title");
		TableColumn<Operation, String> columnDescription = new TableColumn<Operation, String>("Description");
		
		columnOperationCode
			.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOperationCode().getCode()));
		columnOperationType
			.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOperationType().getDescription()));
		columnBase
			.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));
		columnTitle.setCellValueFactory(new PropertyValueFactory<Operation, String>("title"));
		columnDescription.setCellValueFactory(new PropertyValueFactory<Operation, String>("shortDescription"));

		this.tvOperationData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnOperationCode.setMaxWidth(1f * Integer.MAX_VALUE * 10);
		columnOperationType.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnBase.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnTitle.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnDescription.setMaxWidth(1f * Integer.MAX_VALUE * 30);

		this.tvOperationData.getColumns().addAll(columnOperationCode, columnOperationType, columnTitle, columnDescription, columnBase);
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
		//columnOpeartion
			//	.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOperation().getTitle()));

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
	
	public void fillTableViews(boolean isCalledInReloadingData) {
		if(isCalledInReloadingData) {			
			
		}
		this.fillTableViewOperationsFromThread();
		this.fillTableViewOperationVehiclesFromThread();
		this.fillTableViewOtherOrgFromThread();
		this.fillTableViewMembersFromThread();
	}
	
	private void fillTableViewOperationsFromThread() {
		this.obsListTVOperations = FXCollections.observableArrayList();
		this.obsListTVOperations.addAll(OperationHandler.getInstance().getOperationList());

		this.tvOperationData.setItems(this.obsListTVOperations.sorted());
	}
	
	private void fillTableViewOperationVehiclesFromThread() {
		this.obsListTVOperationVehicles = FXCollections.observableArrayList();
		this.obsListTVOperationVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleList());

		this.tvVehicleData.setItems(this.obsListTVOperationVehicles.sorted());
	}
	
	private void fillTableViewOtherOrgFromThread() {
		this.obsListTVOtherOrgs = FXCollections.observableArrayList();
		this.obsListTVOtherOrgs.addAll(OtherOrganisationHandler.getInstance().getOtherOrganisationList());

		this.tvOtherOrgData.setItems(this.obsListTVOtherOrgs.sorted());
	}
	
	private void fillTableViewMembersFromThread() {
		this.obsListTVMembers = FXCollections.observableArrayList();
		this.obsListTVMembers.addAll(MemberHandler.getInstance().getMemberList());

		this.tvMemberData.setItems(this.obsListTVMembers.sorted());
	}
	
	private void initTVOperationListeners() {
		 this.tvOperationData.setOnMouseClicked(event -> {
			Operation selectedOperation = this.tvOperationData.getSelectionModel().getSelectedItem();
			if(selectedOperation != null) {
				this.lbShowCodeAndTypeAndTitleData.setText(selectedOperation.getOperationCode().getCode() + ", " + 
						selectedOperation.getOperationType().getDescription());
				this.lbShowShortdescriptionData.setText(selectedOperation.getTitle() + ", " + selectedOperation.getShortDescription());
				this.lbShowAddressAndPlzData.setText(selectedOperation.getAddress() + ", " + selectedOperation.getPostCode());
				this.lbShowBase.setText(selectedOperation.getBase().getName());
			}
		 });
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
		this.accordionSubTables.setExpandedPane(this.tpMember);
		Operation selectedOperation = this.tvOperationData.getSelectionModel().getSelectedItem();
		if (selectedOperation != null) {
			//Open ProgressBarDialog
			FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
			ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
			loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);
			
			Stage stageProgressBarDialog = new Stage();
			try {
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog.setTitle("Loading Members by Operation '" + selectedOperation.getTitle() + "'");
				stageProgressBarDialog.show();	
				stageProgressBarDialog.centerOnScreen();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			
			controllerThreadProgressBarDialog.unbindProgressBar();
			
			//Starting Threading
			Thread threadOperationMemberLoader = new Thread(new OperationMemberLoader(selectedOperation.getOperationId()));
			
			Task<Void> loadingMembersByOperationTask = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					updateProgress(0, 100);
					int lastProgressValue = 0;
					
					threadOperationMemberLoader.start();
					threadOperationMemberLoader.join();
					
					for(int i=0;i<20;i++) {
						lastProgressValue += 2.5;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(75);
					}
					return null;
				}
			};
			loadingMembersByOperationTask.messageProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
					controllerThreadProgressBarDialog.setLabelText(newValue);
				}
			});
			loadingMembersByOperationTask.setOnSucceeded(e -> {
				stageProgressBarDialog.close();
				
				this.obsListTVMembers.clear();
				ArrayList<Member> tempListOfMembers = MemberHandler.getInstance().getMemberListByOperationId();
				
				if (tempListOfMembers != null) {
					this.obsListTVMembers.addAll(tempListOfMembers);

					this.tvMemberData.setItems(this.obsListTVMembers);
					this.btnLoadOperationMembers.setDisable(true);
					this.btnLoadAllMembers.setDisable(false);
				} else {
					this.obsListTVMembers.clear();
					this.tvMemberData.refresh();
					this.tvMemberData.setPlaceholder(new Label("No Members in current selected Operation available"));
				}
			});
			controllerThreadProgressBarDialog.bindProgressBarOnTask(loadingMembersByOperationTask);
			try {
				Thread mainThread = new Thread(loadingMembersByOperationTask);
				mainThread.start();
				mainThread.join();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
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