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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.BaseManager;
import manager.MemberManager;
import manager.OperationVehicleManager;

public class ControllerBaseManagementBaseLookup implements Initializable {
	@FXML
	private Accordion dropMenuFilter;
	@FXML
	private TableView<Base> tvBaseData;
	@FXML
	private TableView<TableViewRowData> tvVehicleData;
	@FXML
	private TableView<TableViewRowData> tvMemberData;
	@FXML
	private Label lbShowNameData;
	@FXML
	private Label lbShowPostcodeAndPlaceData;
	@FXML
	private Label lbShowAddressData;
	@FXML
	private Button btnLoadVehicles;
	@FXML
	private Button btnSelectAllOrNone;
	@FXML
	private TableColumn<Base, String> columnSelection;
	@FXML
	private MenuItem mItemRemoveBase;
	@FXML
	private MenuItem mItemUpdateBase;

	private ObservableList<Base> obsListTVBaseData = null;
	private ObservableList<TableViewRowData> obsListTVVehicles = null;
	private ObservableList<TableViewRowData> obsListTVMembers = null;

	private ControllerBaseManagement controllerBaseManagement;

	public ControllerBaseManagementBaseLookup(ControllerBaseManagement controllerBaseManagement) {
		this.controllerBaseManagement = controllerBaseManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTableViewBase();
		this.initTableViewVehicle();
		this.initTableViewMember();
		this.fillTableViews();
		this.initTableViewBaseListener();
		this.initTableViewVehicleListener();
	}

	@SuppressWarnings("unchecked")
	private void initTableViewBase() {
		TableColumn<Base, String> columnName = new TableColumn<Base, String>("Name");
		TableColumn<Base, String> columnPlace = new TableColumn<Base, String>("Place");
		TableColumn<Base, Number> columnPostCode = new TableColumn<Base, Number>("Postcode");
		TableColumn<Base, String> columnStreet = new TableColumn<Base, String>("Street");
		TableColumn<Base, Number> columnHouseNr = new TableColumn<Base, Number>("Nr");

		this.columnSelection.setCellValueFactory(new PropertyValueFactory<Base, String>("selection"));
		columnName.setCellValueFactory(new PropertyValueFactory<Base, String>("name"));
		columnPlace.setCellValueFactory(new PropertyValueFactory<Base, String>("place"));
		columnPostCode.setCellValueFactory(new PropertyValueFactory<Base, Number>("postCode"));
		columnStreet.setCellValueFactory(new PropertyValueFactory<Base, String>("street"));
		columnHouseNr.setCellValueFactory(new PropertyValueFactory<Base, Number>("houseNr"));

		this.tvBaseData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.columnSelection.setMaxWidth(1f * Integer.MAX_VALUE * 10);
		columnName.setMaxWidth(1f * Integer.MAX_VALUE * 32.5);
		columnPlace.setMaxWidth(1f * Integer.MAX_VALUE * 31);
		columnPostCode.setMaxWidth(1f * Integer.MAX_VALUE * 8);
		columnStreet.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnHouseNr.setMaxWidth(1f * Integer.MAX_VALUE * 5);

		this.tvBaseData.getColumns().addAll(columnName, columnPlace, columnPostCode, columnStreet, columnHouseNr);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewVehicle() {
		TableColumn<TableViewRowData, String> columnVehicleDescription = new TableColumn<TableViewRowData, String>(
				"Description");
		TableColumn<TableViewRowData, String> columnCorrespondingBase = new TableColumn<TableViewRowData, String>(
				"Corresponding Base");

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("description"));
		columnCorrespondingBase.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("rank"));

		columnVehicleDescription.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getVehicle().getDescription()));
		columnCorrespondingBase
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnSelection.setMaxWidth(1f * Integer.MAX_VALUE * 3.5);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 51.5);
		columnCorrespondingBase.setMaxWidth(1f * Integer.MAX_VALUE * 45);

		this.tvVehicleData.getColumns().addAll(columnVehicleDescription, columnCorrespondingBase);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewMember() {
		TableColumn<TableViewRowData, String> colNameBlock = new TableColumn<TableViewRowData, String>("Name");
		TableColumn<TableViewRowData, String> colFirstname = new TableColumn<TableViewRowData, String>("Firstname");
		TableColumn<TableViewRowData, String> colLastname = new TableColumn<TableViewRowData, String>("Lastname");
		TableColumn<TableViewRowData, String> colUsername = new TableColumn<TableViewRowData, String>("Username");
		TableColumn<TableViewRowData, String> colRank = new TableColumn<TableViewRowData, String>("Rank");

		colFirstname.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("firstname"));
		colLastname.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("lastname"));
		colUsername.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("username"));

		colRank.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("rank"));

		colFirstname.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMember().getFirstname()));
		colLastname.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMember().getLastname()));
		colUsername.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMember().getUsername()));

		colRank.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getRank().getDescription()));

		colNameBlock.getColumns().addAll(colFirstname, colLastname, colUsername);
		this.tvMemberData.getColumns().addAll(colNameBlock, colRank);
		this.tvMemberData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void fillTableViewBases() {
		this.obsListTVBaseData = FXCollections.observableArrayList();
		// Fill Base table
		ArrayList<Base> listOfBases = BaseManager.getInstance().getBases();
		for (int i = 0; i < listOfBases.size(); i++) {
			this.obsListTVBaseData.add(new Base(new CheckBox(), listOfBases.get(i)));
		}
		this.tvBaseData.setItems(this.obsListTVBaseData.sorted());
	}

	private void fillTableViewVehicles() {
		this.obsListTVVehicles = FXCollections.observableArrayList();
		// Fill Operationvehicle table
		/*
		 * ArrayList<OperationVehicle> listOfOperationVehicles =
		 * OperationVehicleManager.getInstance().getVehicles(); for(int
		 * i=0;i<listOfOperationVehicles.size();i++) { this.obsListTVVehicles.addAll(new
		 * TableViewRowData(listOfOperationVehicles.get(i))); }
		 * this.tvVehicleData.setItems(this.obsListTVVehicles.sorted());
		 */
	}

	private void fillTableViewMembers() {
		this.obsListTVMembers = FXCollections.observableArrayList();
		// Fill Members table
		/*
		 * ArrayList<Member> listOfMembers = MemberManager.getInstance().getMembers();
		 * for(int i=0;i<listOfMembers.size();i++) { this.obsListTVMembers.addAll(new
		 * TableViewRowData(listOfMembers.get(i))); }
		 * this.tvMemberData.setItems(this.obsListTVMembers.sorted());
		 */
	}

	private void fillTableViews() {
		this.fillTableViewBases();
		this.fillTableViewVehicles();
		this.fillTableViewMembers();
	}

	private void initTableViewBaseListener() {
		this.tvBaseData.setOnMouseClicked(event -> {
			Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
			if (selectedBase != null) {
				this.showBaseData(selectedBase);
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
		ObservableList<Base> collOfAllBases = this.obsListTVBaseData.sorted();
		ObservableList<TableViewRowData> collOfAllVehicles = this.obsListTVVehicles.sorted();
		ObservableList<TableViewRowData> filteredCollOfVehicles = FXCollections.observableArrayList();
		ArrayList<Base> collOfAllSelectedBases = new ArrayList<Base>();

		for (int i = 0; i < collOfAllBases.size(); i++) {
			if (collOfAllBases.get(i).getSelection().isSelected()) {
				collOfAllSelectedBases.add(collOfAllBases.get(i));
			}
		}
		if (collOfAllSelectedBases.size() >= 1) {
			for (int j = 0; j < collOfAllSelectedBases.size(); j++) {
				for (int i = 0; i < collOfAllVehicles.size(); i++) {
					if (collOfAllVehicles.get(i).getBase().getBaseId() == collOfAllSelectedBases.get(j).getBaseId()) {
						filteredCollOfVehicles.add(collOfAllVehicles.get(i));
					}
				}
			}
			this.tvVehicleData.setItems(filteredCollOfVehicles);
		} else {
			this.tvVehicleData.setItems(collOfAllVehicles);
		}
	}

	@FXML
	private void onClickBtnSelectAllOrNone(ActionEvent event) {
		ObservableList<Base> collOfAllBases = this.obsListTVBaseData.sorted();
		if (this.btnSelectAllOrNone.getText().equals("All")) {
			this.btnSelectAllOrNone.setText("None");
			for (int i = 0; i < collOfAllBases.size(); i++) {
				collOfAllBases.get(i).getSelection().setSelected(true);
			}
		} else {
			this.btnSelectAllOrNone.setText("All");
			for (int i = 0; i < collOfAllBases.size(); i++) {
				collOfAllBases.get(i).getSelection().setSelected(false);
			}
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
		TableViewRowData selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if (selectedVehicle != null) {
			OperationVehicleManager.getInstance().deleteVehicleFromBase(selectedVehicle.getBase().getBaseId(), selectedVehicle.getVehicle().getOperationVehicleId());
			this.fillTableViewVehicles();
		}
	}

	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent event) {
		TableViewRowData selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if (selectedVehicle != null) {
			CentralUpdateHandler.getInstance().initUpdateOperationVehicleDialog(selectedVehicle);
			this.fillTableViewVehicles();
		}
	}

	@FXML
	private void onClickMItemRemoveMember(ActionEvent event) {
		TableViewRowData selectedMember = this.tvMemberData.getSelectionModel().getSelectedItem();
		if (selectedMember != null) {
			MemberManager.getInstance().deleteMemberFromBase(selectedMember.getBase().getBaseId(), selectedMember.getMember().getMemberId());
			this.fillTableViewMembers();
		}
	}

	@FXML
	private void onClickMItemUpdateMember(ActionEvent event) {
		TableViewRowData selectedMember = this.tvMemberData.getSelectionModel().getSelectedItem();
		if (selectedMember != null) {
			CentralUpdateHandler.getInstance().initUpdateOperationVehicleDialog(selectedMember);
			this.fillTableViewMembers();
		}
	}
}