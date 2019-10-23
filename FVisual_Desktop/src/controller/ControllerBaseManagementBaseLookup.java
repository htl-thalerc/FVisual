package controller;

import java.net.URL;
import java.util.ResourceBundle;

import bll.Base;
import bll.OperationVehicle;
import bll.TableViewRowData;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerBaseManagementBaseLookup implements Initializable {
	@FXML
	private Accordion dropMenuFilter;
	@FXML
	private TableView<Base> tvBaseData;
	@FXML
	private TableView<TableViewRowData> tvVehicleData;
	@FXML
	private Label lbShowNameData;
	@FXML
	private Label lbShowPlzAndPlaceData;
	@FXML
	private Label lbShowAddressData;

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
		TableColumn<Base, String> columnSelection = new TableColumn<Base, String>("");
		TableColumn<Base, String> columnName = new TableColumn<Base, String>("Name");
		TableColumn<Base, String> columnPlace = new TableColumn<Base, String>("Place");
		TableColumn<Base, Number> columnPlz = new TableColumn<Base, Number>("Plz");
		TableColumn<Base, String> columnStreet = new TableColumn<Base, String>("Street");
		TableColumn<Base, Number> columnHouseNr = new TableColumn<Base, Number>("Nr");

		columnSelection.setCellValueFactory(new PropertyValueFactory<Base, String>("selection"));
		columnName.setCellValueFactory(new PropertyValueFactory<Base, String>("name"));
		columnPlace.setCellValueFactory(new PropertyValueFactory<Base, String>("place"));
		columnPlz.setCellValueFactory(new PropertyValueFactory<Base, Number>("plz"));
		columnStreet.setCellValueFactory(new PropertyValueFactory<Base, String>("street"));
		columnHouseNr.setCellValueFactory(new PropertyValueFactory<Base, Number>("houseNr"));

		this.tvBaseData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnSelection.setMaxWidth(1f * Integer.MAX_VALUE * 3.5);
		columnName.setMaxWidth(1f * Integer.MAX_VALUE * 32.5);
		columnPlace.setMaxWidth(1f * Integer.MAX_VALUE * 31);
		columnPlz.setMaxWidth(1f * Integer.MAX_VALUE * 8);
		columnStreet.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnHouseNr.setMaxWidth(1f * Integer.MAX_VALUE * 5);

		this.tvBaseData.getColumns().addAll(columnSelection, columnName, columnPlace, columnPlz, columnStreet,
				columnHouseNr);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewVehicle() {
		TableColumn<TableViewRowData, CheckBox> columnSelection = new TableColumn<TableViewRowData, CheckBox>("");
		TableColumn<TableViewRowData, String> columnNickname = new TableColumn<TableViewRowData, String>("Nickname");
		TableColumn<TableViewRowData, String> columnCorrespondingBase = new TableColumn<TableViewRowData, String>("Corresponding Base");

		//columnSelection.setCellValueFactory(cellData -> cellData.getValue().getVehicle().getSelection());
		columnNickname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicle().getNickname()));
		columnCorrespondingBase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnSelection.setMaxWidth(1f * Integer.MAX_VALUE * 3.5);
		columnNickname.setMaxWidth(1f * Integer.MAX_VALUE * 51.5);
		columnCorrespondingBase.setMaxWidth(1f * Integer.MAX_VALUE * 45);

		this.tvVehicleData.getColumns().addAll(columnSelection, columnNickname, columnCorrespondingBase);
	}

	private void fillTableViews() {
		ObservableList<Base> coll = FXCollections.observableArrayList();
		
		Base b1 = new Base(new CheckBox(), 1, "Feuerwehr St. Peter Spittal", "Spittal", 9080, "Auer v. Welsbachstr.","2");
		Base b2 = new Base(new CheckBox(), 2, "Feuerwehr Olsach-Molzbichl", "Olsach-Molzbichl", 9180, "Lastenweg", "17");

		coll.add(b1);
		coll.add(b2);

		this.tvBaseData.setItems(coll);
		
		ObservableList<TableViewRowData> coll2 = FXCollections.observableArrayList();

		coll2.add(new TableViewRowData(b1, new OperationVehicle(new CheckBox(), 1, "KRFA", b1)));
		coll2.add(new TableViewRowData(b1, new OperationVehicle(new CheckBox(), 2, "TLFA-2000", b1)));
		coll2.add(new TableViewRowData(b1, new OperationVehicle(new CheckBox(), 3, "LF-A", b1)));
		coll2.add(new TableViewRowData(b1, new OperationVehicle(new CheckBox(), 4, "RTB-50", b1)));
		coll2.add(new TableViewRowData(b1, new OperationVehicle(new CheckBox(), 5, "Ölwehranhänger", b1)));
		coll2.add(new TableViewRowData(b2, new OperationVehicle(new CheckBox(), 6, "TLFA-4000", b2)));
		coll2.add(new TableViewRowData(b2, new OperationVehicle(new CheckBox(), 7, "LFA", b2)));
		coll2.add(new TableViewRowData(b2, new OperationVehicle(new CheckBox(), 8, "Katastrophenschutzanhänger", b2)));

		this.tvVehicleData.setItems(coll2);
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
		this.lbShowPlzAndPlaceData.setText(selectedBase.getPlz() + ", " + selectedBase.getPlace());
		this.lbShowAddressData.setText(selectedBase.getStreet() + ", " + selectedBase.getHouseNr());
	}

	private void initTableViewVehicleListener() {

	}
}