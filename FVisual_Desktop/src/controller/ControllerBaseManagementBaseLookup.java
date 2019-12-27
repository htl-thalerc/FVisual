package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bll.Base;
import bll.EnumCRUDOption;
import bll.Member;
import bll.OperationVehicle;
import bll.Rank;
import handler.BaseHandler;
import handler.CentralHandler;
import handler.CentralUpdateHandler;
import handler.MemberHandler;
import handler.OperationVehicleHandler;
import handler.RankHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.BaseManager;
import manager.MemberManager;
import manager.OperationVehicleManager;
import manager.RankManager;

public class ControllerBaseManagementBaseLookup implements Initializable {
	@FXML
	private Accordion dropMenuFilter, accordionSubTables;
	@FXML
	private TitledPane tpOperationVehcile, tpMember;
	@FXML
	private TableView<Base> tvBaseData;
	@FXML
	private TableView<OperationVehicle> tvVehicleData;
	@FXML
	private TableView<Member> tvMemberData;
	@FXML
	private Label lbShowNameData, lbShowPostcodeAndPlaceData, lbShowAddressData;
	@FXML
	private Button btnLoadVehicles, btnLoadMembers;
	@FXML
	private MenuItem mItemRemoveBase, mItemUpdateBase;

	private ObservableList<Base> obsListTVBaseData = null;
	private ObservableList<OperationVehicle> obsListTVVehicles = null;
	private ObservableList<Member> obsListTVMembers = null;

	private ControllerBaseManagement controllerBaseManagement;

	public ControllerBaseManagementBaseLookup(ControllerBaseManagement controllerBaseManagement) {
		this.controllerBaseManagement = controllerBaseManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.defaultSettings();
		this.initTableViewBase();
		this.initTableViewVehicle();
		this.initTableViewMember();
		this.fillTableViews();
		this.initTableViewBaseListener();
		this.initTableViewVehicleListener();
	}
	
	private void defaultSettings() {
		this.btnLoadVehicles.setDisable(true);
		this.btnLoadMembers.setDisable(true);
		
		this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewBase() {
		TableColumn<Base, String> columnName = new TableColumn<Base, String>("Name");
		TableColumn<Base, String> columnPlace = new TableColumn<Base, String>("Place");
		TableColumn<Base, Number> columnPostCode = new TableColumn<Base, Number>("Postcode");
		TableColumn<Base, String> columnStreet = new TableColumn<Base, String>("Street");
		TableColumn<Base, Number> columnHouseNr = new TableColumn<Base, Number>("Nr");

		columnName.setCellValueFactory(new PropertyValueFactory<Base, String>("name"));
		columnPlace.setCellValueFactory(new PropertyValueFactory<Base, String>("place"));
		columnPostCode.setCellValueFactory(new PropertyValueFactory<Base, Number>("postCode"));
		columnStreet.setCellValueFactory(new PropertyValueFactory<Base, String>("street"));
		columnHouseNr.setCellValueFactory(new PropertyValueFactory<Base, Number>("houseNr"));

		this.tvBaseData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnName.setMaxWidth(1f * Integer.MAX_VALUE * 32.5);
		columnPlace.setMaxWidth(1f * Integer.MAX_VALUE * 31);
		columnPostCode.setMaxWidth(1f * Integer.MAX_VALUE * 8);
		columnStreet.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnHouseNr.setMaxWidth(1f * Integer.MAX_VALUE * 5);

		this.tvBaseData.getColumns().addAll(columnName, columnPlace, columnPostCode, columnStreet, columnHouseNr);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewVehicle() {
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>(
				"Description");
		TableColumn<OperationVehicle, String> columnBaseName = new TableColumn<OperationVehicle, String>(
				"Base");

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));
		columnBaseName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));
		
		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		columnBaseName.setMaxWidth(1f * Integer.MAX_VALUE * 60);
		
		this.tvVehicleData.getColumns().addAll(columnVehicleDescription, columnBaseName);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewMember() {
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

	private void fillTableViews() {
		this.fillTableViewBases();
		this.fillTableViewVehicles();
		this.fillTableViewMembers();
	}
	
	private void fillTableViewBases() {
		this.obsListTVBaseData = FXCollections.observableArrayList();
		ArrayList<Base> tempList = new ArrayList<Base>();

		ArrayList<Base> listOfBases = BaseManager.getInstance().getBases();
		for (int i = 0; i < listOfBases.size(); i++) {
			this.obsListTVBaseData.add(new Base(new CheckBox(), listOfBases.get(i)));
			tempList.add(listOfBases.get(i));
		}
		this.tvBaseData.setItems(this.obsListTVBaseData.sorted());
		BaseHandler.getInstance().setBaseList(tempList);
	}

	private void fillTableViewVehicles() {
		this.obsListTVVehicles = FXCollections.observableArrayList();
		ArrayList<OperationVehicle> tempListVehicles = new ArrayList<OperationVehicle>();
		
		ArrayList<OperationVehicle> listOfVehicles = OperationVehicleManager.getInstance().getVehicles();
		for (int i = 0; i < listOfVehicles.size(); i++) {
			tempListVehicles.add(listOfVehicles.get(i));
		}
		OperationVehicleHandler.getInstance().setVehicleList(tempListVehicles);
		CentralHandler.getInstance().mergeFullVehicleObject();
		
		this.obsListTVVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleList());
		this.tvVehicleData.setItems(this.obsListTVVehicles.sorted());
	}

	private void fillTableViewMembers() {
		this.obsListTVMembers = FXCollections.observableArrayList();
		ArrayList<Member> tempListMembers = new ArrayList<Member>();

		ArrayList<Member> listOfMembers = MemberManager.getInstance().getMembers();
		for (int i = 0; i < listOfMembers.size(); i++) {
			tempListMembers.add(listOfMembers.get(i));
		}
		MemberHandler.getInstance().setMemberList(tempListMembers);
		
		ArrayList<Rank> tempListRank = new ArrayList<Rank>();
		ArrayList<Rank> listOfRanks = RankManager.getInstance().getRanks();
		for(int i=0;i<listOfRanks.size();i++) {
			tempListRank.add(listOfRanks.get(i));
		}
		RankHandler.getInstance().setRankList(tempListRank);
		CentralHandler.getInstance().mergeFullMemberObject(false);
		
		this.obsListTVMembers.addAll(MemberHandler.getInstance().getMemberList());
		this.tvMemberData.setItems(this.obsListTVMembers.sorted());
	}

	private void initTableViewBaseListener() {
		this.tvBaseData.setOnMouseClicked(event -> {
			Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
			if (selectedBase != null) {
				System.out.println("Selected base: " + selectedBase.toString());
				this.showBaseData(selectedBase);
				this.btnLoadVehicles.setDisable(false);
				this.btnLoadMembers.setDisable(false);
			} else {
				this.btnLoadVehicles.setDisable(true);
				this.btnLoadMembers.setDisable(true);
			}
		});
	}

	private void showBaseData(Base selectedBase) {
		this.lbShowNameData.setText(selectedBase.getName());
		this.lbShowPostcodeAndPlaceData.setText(selectedBase.getPostCode() + ", " + selectedBase.getPlace());
		this.lbShowAddressData.setText(selectedBase.getStreet() + ", " + selectedBase.getHouseNr());
	}

	private void initTableViewVehicleListener() {

	}

	@FXML
	private void onClickBtnLoadVehicles(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if(selectedBase != null) {
			ArrayList<OperationVehicle> listOfOperationVehiclesFilteredByBase = OperationVehicleManager.getInstance().getVehiclesFromBase(selectedBase.getBaseId());
			
			OperationVehicleHandler.getInstance().setVehicleListByBaseId(listOfOperationVehiclesFilteredByBase);
			
			this.obsListTVVehicles.clear();
			this.obsListTVVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleListByBaseId());
			
			this.tvVehicleData.setItems(this.obsListTVVehicles);
			this.btnLoadVehicles.setDisable(true);
		}
	}
	
	@FXML
	private void onClickBtnLoadMembers(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpMember);
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if(selectedBase != null) {
			ArrayList<Member> listOfMembersFilteredByBase = MemberManager.getInstance().getMembersFromBase(selectedBase.getBaseId());
			
			MemberHandler.getInstance().setMemberListByBaseId(listOfMembersFilteredByBase);
			
			this.obsListTVMembers.clear();
			this.obsListTVMembers.addAll(MemberHandler.getInstance().getMemberListByBaseId());
			
			this.tvMemberData.setItems(this.obsListTVMembers);
			this.btnLoadMembers.setDisable(true);
		}
	}

	@FXML
	private void onClickMItemRemoveBase(ActionEvent event) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();

		if (selectedBase != null) {
			FXMLLoader loader = CentralHandler.loadFXML("/gui/BaseManagementDialog.fxml");

			ControllerBaseManagementDialog controllerDialogSaveBase = new ControllerBaseManagementDialog(this,
					EnumCRUDOption.DELETE);
			loader.setController(controllerDialogSaveBase);

			try {
				Stage curStage = new Stage();
				Scene scene = new Scene(loader.load());
				curStage.setScene(scene);
				curStage.initModality(Modality.APPLICATION_MODAL);
				curStage.setTitle("Would you like to remove your selected Base");

				controllerDialogSaveBase.setBaseData(selectedBase);
				controllerDialogSaveBase.setListViewOperationVehicleData(new ArrayList<OperationVehicle>());
				controllerDialogSaveBase.setListViewMemberData(new ArrayList<Member>());
				curStage.showAndWait();
				if (controllerDialogSaveBase.getButtonState()) {
					BaseManager.getInstance().deleteBase(selectedBase.getBaseId());
					this.fillTableViews();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onClickMItemUpdateBase(ActionEvent event) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if (selectedBase != null) {
			CentralUpdateHandler.getInstance().initUpdateBaseDialog(selectedBase);
			this.fillTableViews();
		}
	}

	@FXML
	private void onClickMItemRemoveVehicle(ActionEvent event) {
		OperationVehicle selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if (selectedVehicle != null) {
			OperationVehicleManager.getInstance().deleteVehicleFromBase(selectedVehicle.getBase().getBaseId(),
					selectedVehicle.getOperationVehicleId());
			this.fillTableViewVehicles();
		}
	}

	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent event) {
		OperationVehicle selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if (selectedVehicle != null) {
			CentralUpdateHandler.getInstance().initUpdateOperationVehicleDialog(selectedVehicle);
			this.fillTableViewVehicles();
		}
	}

	@FXML
	private void onClickMItemRemoveMember(ActionEvent event) {
		Member selectedMember = this.tvMemberData.getSelectionModel().getSelectedItem();
		if (selectedMember != null) {
			MemberManager.getInstance().deleteMemberFromBase(selectedMember.getBase().getBaseId(),
					selectedMember.getMemberId());
			this.fillTableViewMembers();
		}
	}

	@FXML
	private void onClickMItemUpdateMember(ActionEvent event) {
		Member selectedMember = this.tvMemberData.getSelectionModel().getSelectedItem();
		if (selectedMember != null) {
			CentralUpdateHandler.getInstance().initUpdateMemberDialog(selectedMember);
			this.fillTableViewMembers();
		}
	}
}