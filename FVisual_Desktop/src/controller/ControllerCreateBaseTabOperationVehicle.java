package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import bll.Base;
import bll.OperationVehicle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerCreateBaseTabOperationVehicle implements Initializable {
	@FXML private ComboBox<OperationVehicle> cbAutoCompleteVehicle;
	@FXML private Button btnAddSelectedVehicle;
	@FXML private Button btnAddEnteredVehicle;
	@FXML private TextField tfDescription;
	@FXML private Label lbStatusbarDescription;
	@FXML private CheckBox checkBoxDescription;
	
	@FXML private TableView<OperationVehicle> tvVehicleData;
	private ObservableList<OperationVehicle> obsListOfOperationVehicles;
	
	private ControllerCreateBase controllerCreateBase;
	
	public ControllerCreateBaseTabOperationVehicle(ControllerCreateBase controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.obsListOfOperationVehicles = FXCollections.observableArrayList();
		this.checkBoxDescription.setDisable(true);
		
		this.initTableView();
		this.initComboBox();
		this.initListeners();
	}
	
	@SuppressWarnings("unchecked")
	private void initTableView() {
		TableColumn<OperationVehicle, String> columnBaseName = new TableColumn<OperationVehicle, String>("Base Name");
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>("Vehicle Description");
		
		columnBaseName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));
		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));
		
		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnBaseName.setMaxWidth(1f * Integer.MAX_VALUE * 30);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 70);

		this.tvVehicleData.getColumns().addAll(columnBaseName, columnVehicleDescription);
	}
	
	private void initComboBox() {
		ArrayList<OperationVehicle> collOfOperationVehicles = new ArrayList<OperationVehicle>();
		
		Base b1 = new Base(1, "Feuerwehr St. Peter Spittal", "Spittal", 9080, "Auer v. Welsbachstr.", "2");
		Base b2 = new Base(2, "Feuerwehr Olsach-Molzbichl", "Olsach-Molzbichl", 9180, "Lastenweg", "17");

		collOfOperationVehicles.add(new OperationVehicle(1, "KRFA", b1));
		collOfOperationVehicles.add(new OperationVehicle(2, "TLFA-2000", b1));
		collOfOperationVehicles.add(new OperationVehicle(3, "LF-A", b1));
		collOfOperationVehicles.add(new OperationVehicle(4, "RTB-50", b1));
		collOfOperationVehicles.add(new OperationVehicle(5, "Ölwehranhänger", b1));
		collOfOperationVehicles.add(new OperationVehicle(6, "TLFA-4000", b2));
		collOfOperationVehicles.add(new OperationVehicle(7, "LFA", b2));
		collOfOperationVehicles.add(new OperationVehicle(8, "Katastrophenschutzanhänger", b2));
		
		ObservableList<OperationVehicle> obsListOperationVehicle = FXCollections.observableArrayList(collOfOperationVehicles);
		this.cbAutoCompleteVehicle.setItems(obsListOperationVehicle);
	}
	
	private void initListeners() { 
		AtomicBoolean isValidDescription = new AtomicBoolean(false);
		this.tfDescription.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() >= 3) {
					this.lbStatusbarDescription.setText("Valid Vehicle Description");
					this.checkBoxDescription.setSelected(true);
					isValidDescription.set(true);
				} else {
					this.lbStatusbarDescription.setText("Invalid - Vehicle Description is too short");
					this.checkBoxDescription.setSelected(false);
					isValidDescription.set(false);
				}
			} else {
				this.lbStatusbarDescription.setText("Invalid - Inputfield is empty");
				this.checkBoxDescription.setSelected(false);
				isValidDescription.set(false);
			}
		});
		
		this.btnAddEnteredVehicle.setOnMouseClicked(event -> {
			if(isValidDescription.get()) {
				this.obsListOfOperationVehicles.add(new OperationVehicle(0, this.tfDescription.getText().trim(), controllerCreateBase.getCreatedBase()));
			}
			this.tvVehicleData.setItems(this.obsListOfOperationVehicles);
			this.tvVehicleData.refresh();
		});
	}
	
	@FXML private void onClickBtnAddSelectedVehicle(ActionEvent aE) {
		OperationVehicle selectedVehicle = this.cbAutoCompleteVehicle.getSelectionModel().getSelectedItem();
		if(selectedVehicle != null) {
			this.obsListOfOperationVehicles.add(selectedVehicle);
		}
		this.tvVehicleData.setItems(this.obsListOfOperationVehicles);
		this.tvVehicleData.refresh();
	}

	public List<OperationVehicle> getCreatedVehicleData() {
		return this.obsListOfOperationVehicles.stream().collect(Collectors.toList());
	}
}