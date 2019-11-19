package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bll.Base;
import bll.EnumCRUDOption;
import bll.Member;
import bll.OperationVehicle;
import bll.OtherOrganisation;
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

public class ControllerBaseManagementBaseLookup implements Initializable {
	@FXML
	private Accordion dropMenuFilter;
	@FXML
	private TableView<Base> tvBaseData;
	@FXML
	private TableView<OperationVehicle> tvVehicleData;
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
	private ObservableList<OperationVehicle> obsListTVVehicles = null;

	private ControllerBaseManagement controllerBaseManagement;

	public ControllerBaseManagementBaseLookup(ControllerBaseManagement controllerBaseManagement) {
		this.controllerBaseManagement = controllerBaseManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTableViewBase();
		this.initTableViewVehicle();
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
		TableColumn<OperationVehicle, CheckBox> columnSelection = new TableColumn<OperationVehicle, CheckBox>("");
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>(
				"Description");
		TableColumn<OperationVehicle, String> columnCorrespondingBase = new TableColumn<OperationVehicle, String>(
				"Corresponding Base");

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));
		columnVehicleDescription
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		columnCorrespondingBase
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnSelection.setMaxWidth(1f * Integer.MAX_VALUE * 3.5);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 51.5);
		columnCorrespondingBase.setMaxWidth(1f * Integer.MAX_VALUE * 45);

		this.tvVehicleData.getColumns().addAll(columnSelection, columnVehicleDescription, columnCorrespondingBase);
	}

	private void fillTableViews() {
		//this.obsListTVBaseData =
		//FXCollections.observableArrayList(BaseManager.getInstance().getBases());
		// this.obsListTVVehicles =
		// FXCollections.observableArrayList(OperationVehicleManager.getInstance().getOperationVehicles());

		this.obsListTVBaseData = FXCollections.observableArrayList();
		this.obsListTVVehicles = FXCollections.observableArrayList();

		Base b1 = new Base(new CheckBox(), 1, "Feuerwehr St. Peter Spittal", "Spittal", 9080, "Auer v. Welsbachstr.",
				"2");
		b1.getSelection().setId("checkBox_" + b1.getBaseId());
		Base b2 = new Base(new CheckBox(), 2, "Feuerwehr Olsach-Molzbichl", "Olsach-Molzbichl", 9180, "Lastenweg",
				"17");
		b2.getSelection().setId("checkBox_" + b2.getBaseId());
		
		this.obsListTVBaseData.add(b1);
		this.obsListTVBaseData.add(b2);

		this.tvBaseData.setItems(this.obsListTVBaseData);

		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 1, "KRFA", b1));
		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 2, "TLFA-2000", b1));
		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 3, "LF-A", b1));
		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 4, "RTB-50", b1));
		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 5, "Ölwehranhänger", b1));
		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 6, "TLFA-4000", b2));
		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 7, "LFA", b2));
		this.obsListTVVehicles.add(new OperationVehicle(new CheckBox(), 8, "Katastrophenschutzanhänger", b2));

		this.tvVehicleData.setItems(this.obsListTVVehicles.sorted());
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
	private void onClickBtnLoadVehicles(ActionEvent aE) {
		ObservableList<Base> collOfAllBases = this.obsListTVBaseData.sorted();
		ObservableList<OperationVehicle> collOfAllVehicles = this.obsListTVVehicles.sorted();
		ObservableList<OperationVehicle> filteredCollOfVehicles = FXCollections.observableArrayList();
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
	private void onClickBtnSelectAllOrNone(ActionEvent aE) {
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
	private void onClickMItemRemoveBase(ActionEvent aE) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		
		if(selectedBase != null) {
			FXMLLoader loader = CentralHandler.loadFXML("/gui/BaseManagementDialog.fxml");

			ControllerBaseManagementDialog controllerDialogSaveBase = new ControllerBaseManagementDialog(this, EnumCRUDOption.DELETE);
			loader.setController(controllerDialogSaveBase);
			
			try {
				Stage curStage = new Stage();
				Scene scene = new Scene(loader.load());
				curStage.setScene(scene);
				curStage.initModality(Modality.APPLICATION_MODAL);
				curStage.setTitle("Would you like to remove your selected Base");
				
				controllerDialogSaveBase.setBaseData(selectedBase);
				controllerDialogSaveBase.setListViewOperationVehicleData(new ArrayList<OperationVehicle>());
				controllerDialogSaveBase.setListViewOtherOrganisationData(new ArrayList<OtherOrganisation>());
				controllerDialogSaveBase.setListViewMemberData(new ArrayList<Member>());
				curStage.showAndWait();
				if (controllerDialogSaveBase.getButtonState()) {
					//ToDo: Remove from DB
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML 
	private void onClickMItemUpdateBase(ActionEvent aE) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if(selectedBase != null) {
			CentralUpdateHandler.getInstance().initUpdateBaseDialog(selectedBase);	
		}
	}
	
	@FXML 
	private void onClickMItemRemoveVehicle(ActionEvent aE) {
		
	}
	
	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent aE) {
		OperationVehicle selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if(selectedVehicle != null) {
			CentralUpdateHandler.getInstance().initUpdateOperationVehicleDialog(selectedVehicle);
		}
	}
}