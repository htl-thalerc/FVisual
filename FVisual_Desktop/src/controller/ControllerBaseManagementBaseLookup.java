package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import bll.Base;
import bll.EnumCRUDOption;
import bll.Member;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import bll.TableViewRowData;
import handler.CentralHandler;
import handler.CentralUpdateHandler;
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

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));
		
		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 51.5);
		
		this.tvVehicleData.getColumns().addAll(columnVehicleDescription);
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

		/*colFirstname.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMember().getFirstname()));
		colLastname.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMember().getLastname()));
		colUsername.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMember().getUsername()));
		colContraction.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMember().getContraction()));*/

		colNameBlock.getColumns().addAll(colFirstname, colLastname, colUsername);
		this.tvMemberData.getColumns().addAll(colNameBlock, colContraction);
		this.tvMemberData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void fillTableViews() {
		this.fillTableViewBases();
		this.fillTableViewVehicles();
		this.fillTableViewMembers();
	}
	
	private void fillTableViewBases() {
		this.obsListTVBaseData = FXCollections.observableArrayList();

		ArrayList<Base> listOfBases = BaseManager.getInstance().getBases();
		for (int i = 0; i < listOfBases.size(); i++) {
			this.obsListTVBaseData.add(new Base(new CheckBox(), listOfBases.get(i)));
		}
		this.tvBaseData.setItems(this.obsListTVBaseData.sorted());
	}

	private void fillTableViewVehicles() {
		this.obsListTVVehicles = FXCollections.observableArrayList();

		ArrayList<String> listOfOperationVehiclesAsStringArray = OperationVehicleManager.getInstance().getVehicles();
		ArrayList<OperationVehicle> listOfOperationVehiclesAsObjects = new ArrayList<OperationVehicle>();
		
		for(String s : listOfOperationVehiclesAsStringArray) {
			listOfOperationVehiclesAsObjects.add(new OperationVehicle(s));
		}
		for (int i = 0; i < listOfOperationVehiclesAsObjects.size(); i++) {
			this.obsListTVVehicles.add(listOfOperationVehiclesAsObjects.get(i));
		}
		this.tvVehicleData.setItems(this.obsListTVVehicles.sorted());
	}

	private void fillTableViewMembers() {
		this.obsListTVMembers = FXCollections.observableArrayList();

		ArrayList<Member> listOfMembers = MemberManager.getInstance().getMembers();
		for (int i = 0; i < listOfMembers.size(); i++) {
			this.obsListTVMembers.addAll(listOfMembers.get(i));
		}
		this.tvMemberData.setItems(this.obsListTVMembers.sorted());
	}

	private void initTableViewBaseListener() {
		this.tvBaseData.setOnMouseClicked(event -> {
			Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
			if (selectedBase != null) {
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
			this.obsListTVVehicles.clear();
			this.obsListTVVehicles.addAll(listOfOperationVehiclesFilteredByBase);
			this.tvVehicleData.setItems(this.obsListTVVehicles);
			this.btnLoadVehicles.setDisable(true);
		}
	}
	
	@FXML
	private void onClickBtnLoadMembers(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpMember);
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if(selectedBase != null) {
			ArrayList<Member> listOfOperationVehiclesFilteredByBase = MemberManager.getInstance().getMembersFromBase(selectedBase.getBaseId());
			this.obsListTVMembers.clear();
			this.obsListTVMembers.addAll(listOfOperationVehiclesFilteredByBase);
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