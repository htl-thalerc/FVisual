package controller;

import java.net.URL;
import java.util.ResourceBundle;

import bll.Base;
import bll.OperationVehicle;
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
	@FXML private Accordion dropMenuFilter;
	@FXML private TableView<Base> tvBaseData;
	@FXML private TableView<OperationVehicle> tvVehicleData;
	@FXML private Label lbShowNameData;
	@FXML private Label lbShowPlzAndPlaceData;
	@FXML private Label lbShowAddressData;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTableView();
		this.fillTableView();
		this.initTableViewListeners();
	}
	
	@SuppressWarnings("unchecked")
	private void initTableView() {
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
		
		this.tvBaseData.getColumns().addAll(columnSelection, columnName, columnPlace, columnPlz, columnStreet, columnHouseNr);
	}
	
	private void fillTableView() {
		ObservableList<Base> coll = FXCollections.observableArrayList();
		
		coll.add(new Base(new CheckBox(), 1, "Feuerwehr St. Peter Spittal", "Spittal", 9080, "Auer v. Welsbachstr.", "2"));
		coll.add(new Base(new CheckBox(), 2, "Feuerwehr Olsach-Molzbichl", "Olsach-Molzbichl", 9180, "Lastenweg", "17"));
		
		this.tvBaseData.setItems(coll);
	}
	
	private void initTableViewListeners() {
		this.tvBaseData.setOnMouseClicked(event -> {
			Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
			if(selectedBase != null) {
				this.showBaseData(selectedBase);
			}
		});
	}
	
	private void showBaseData(Base selectedBase) {
		this.lbShowNameData.setText(selectedBase.getName());
		this.lbShowPlzAndPlaceData.setText(selectedBase.getPlz() + "" + selectedBase.getPlace());
		this.lbShowAddressData.setText(selectedBase.getStreet() + ", " + selectedBase.getHouseNr());
	}
}