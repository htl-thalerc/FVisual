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
	@FXML
	private ComboBox<OperationVehicle> cbAutoCompleteVehicle;
	@FXML
	private Button btnAddSelectedVehicle;
	@FXML
	private Button btnAddEnteredVehicle;
	@FXML
	private Button btnUpdateVehicle;
	@FXML
	private Button btnSelectAllOrNone;
	@FXML
	private Button btnRemoveVehicle;
	@FXML
	private TextField tfAddVehicleDescription;
	@FXML
	private TextField tfUpdateVehicleDescription;
	@FXML
	private Label lbStatusbarAddVehicle;
	@FXML
	private Label lbStatusbarUpdateVehicle;
	@FXML
	private CheckBox checkBoxAddVehicle;
	@FXML
	private CheckBox checkBoxUpdateVehicle;

	@FXML
	private TableView<OperationVehicle> tvVehicleData;
	@FXML
	private TableColumn<OperationVehicle, String> columnSelection;

	private ObservableList<OperationVehicle> obsListOfOperationVehicles;
	private ControllerCreateBase controllerCreateBase;
	private OperationVehicle selectedVehicleToUpdate;

	public ControllerCreateBaseTabOperationVehicle(ControllerCreateBase controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.obsListOfOperationVehicles = FXCollections.observableArrayList();
		this.initDisability();
		this.initTableView();
		this.initComboBox();
		this.initListeners();
	}

	private void initDisability() {
		this.checkBoxAddVehicle.setDisable(true);
		this.checkBoxUpdateVehicle.setDisable(true);
		this.btnUpdateVehicle.setDisable(true);
	}

	@SuppressWarnings("unchecked")
	private void initTableView() {
		TableColumn<OperationVehicle, String> columnBaseName = new TableColumn<OperationVehicle, String>("Base Name");
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>(
				"Vehicle Description");

		this.columnSelection.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("selection"));
		columnBaseName
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));
		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.columnSelection.setMaxWidth(1f * Integer.MAX_VALUE * 10);
		columnBaseName.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 50);

		this.tvVehicleData.getColumns().addAll(columnBaseName, columnVehicleDescription);
	}

	private void initComboBox() {
		ArrayList<OperationVehicle> collOfOperationVehicles = new ArrayList<OperationVehicle>();

		Base b1 = new Base(1, "Feuerwehr St. Peter Spittal", "Spittal", 9080, "Auer v. Welsbachstr.", "2");
		Base b2 = new Base(2, "Feuerwehr Olsach-Molzbichl", "Olsach-Molzbichl", 9180, "Lastenweg", "17");

		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 1, "KRFA", b1));
		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 2, "TLFA-2000", b1));
		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 3, "LF-A", b1));
		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 4, "RTB-50", b1));
		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 5, "Ölwehranhänger", b1));
		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 6, "TLFA-4000", b2));
		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 7, "LFA", b2));
		collOfOperationVehicles.add(new OperationVehicle(new CheckBox(), 8, "Katastrophenschutzanhänger", b2));

		ObservableList<OperationVehicle> obsListOperationVehicle = FXCollections
				.observableArrayList(collOfOperationVehicles);
		this.cbAutoCompleteVehicle.setItems(obsListOperationVehicle);
	}

	private void initListeners() {
		AtomicBoolean isValidDescriptionAddVehicle = new AtomicBoolean(false);
		AtomicBoolean isValidDescriptionUpdateVehicle = new AtomicBoolean(false);

		this.tfAddVehicleDescription.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() >= 3) {
					this.lbStatusbarAddVehicle.setText("Valid Vehicle Description");
					this.checkBoxAddVehicle.setSelected(true);
					isValidDescriptionAddVehicle.set(true);
				} else {
					this.lbStatusbarAddVehicle.setText("Invalid - Vehicle Description is too short");
					this.checkBoxAddVehicle.setSelected(false);
					isValidDescriptionAddVehicle.set(false);
				}
			} else {
				this.lbStatusbarAddVehicle.setText("Invalid - Inputfield is empty");
				this.checkBoxAddVehicle.setSelected(false);
				isValidDescriptionAddVehicle.set(false);
			}
		});

		this.btnAddEnteredVehicle.setOnMouseClicked(event -> {
			if (isValidDescriptionAddVehicle.get()) {
				for (int i = 0; i < this.obsListOfOperationVehicles.size(); i++) {
					if (!this.tfAddVehicleDescription.getText()
							.equals(this.obsListOfOperationVehicles.get(i).getDescription())) {
						this.obsListOfOperationVehicles.add(
								new OperationVehicle(new CheckBox(), 0, this.tfAddVehicleDescription.getText().trim(),
										this.controllerCreateBase.getCreatedBase()));
					}
				}
				this.tvVehicleData.setItems(this.obsListOfOperationVehicles);
				this.tvVehicleData.refresh();
			}
		});

		this.tfUpdateVehicleDescription.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() >= 3) {
					this.lbStatusbarUpdateVehicle.setText("Valid Vehicle Description");
					this.checkBoxUpdateVehicle.setSelected(true);
					isValidDescriptionUpdateVehicle.set(true);
				} else {
					this.lbStatusbarUpdateVehicle.setText("Invalid - Vehicle Description is too short");
					this.checkBoxUpdateVehicle.setSelected(false);
					isValidDescriptionUpdateVehicle.set(false);
				}
			} else {
				this.lbStatusbarUpdateVehicle.setText("Invalid - Inputfield is empty");
				this.checkBoxUpdateVehicle.setSelected(false);
				isValidDescriptionUpdateVehicle.set(false);
			}
		});

		this.btnUpdateVehicle.setOnMouseClicked(event -> {
			if (isValidDescriptionUpdateVehicle.get()) {
				OperationVehicle updatedVehicle = selectedVehicleToUpdate;
				updatedVehicle.setDescription(this.tfUpdateVehicleDescription.getText());

				if (updatedVehicle.getDescription() != null) {
					for (OperationVehicle currVehicle : this.obsListOfOperationVehicles) {
						if (currVehicle.equals(updatedVehicle)) {
							currVehicle = updatedVehicle;
						}
					}
					this.tvVehicleData.setItems(this.obsListOfOperationVehicles);
					this.tvVehicleData.refresh();
				}
			}
			this.tfUpdateVehicleDescription.setText("");
			this.btnUpdateVehicle.setDisable(true);
		});
	}

	@FXML
	private void onClickBtnAddSelectedVehicle(ActionEvent aE) {
		OperationVehicle selectedVehicle = this.cbAutoCompleteVehicle.getSelectionModel().getSelectedItem();
		if (selectedVehicle != null && !this.obsListOfOperationVehicles.contains(selectedVehicle)) {
			this.obsListOfOperationVehicles.add(selectedVehicle);
		}
		this.tvVehicleData.setItems(this.obsListOfOperationVehicles);
		this.tvVehicleData.refresh();
	}

	@FXML
	private void onClickBtnSelectAllOrNone(ActionEvent aE) {
		ObservableList<OperationVehicle> collOfAllVehicles = this.obsListOfOperationVehicles.sorted();
		if (this.btnSelectAllOrNone.getText().equals("All")) {
			this.btnSelectAllOrNone.setText("None");
			for (int i = 0; i < collOfAllVehicles.size(); i++) {
				collOfAllVehicles.get(i).getSelection().setSelected(true);
			}
		} else {
			this.btnSelectAllOrNone.setText("All");
			for (int i = 0; i < collOfAllVehicles.size(); i++) {
				collOfAllVehicles.get(i).getSelection().setSelected(false);
			}
		}
	}

	@FXML
	private void onClickBtnRemoveVehicle(ActionEvent aE) {
		ObservableList<OperationVehicle> collOfAllVehicles = this.obsListOfOperationVehicles.sorted();
		ArrayList<OperationVehicle> collOfRemovingVehicles = new ArrayList<OperationVehicle>();
		for (int i = 0; i < collOfAllVehicles.size(); i++) {
			if (collOfAllVehicles.get(i).getSelection().isSelected()) {
				collOfRemovingVehicles.add(collOfAllVehicles.get(i));
			}
		}
		for (int i = 0; i < collOfRemovingVehicles.size(); i++) {
			this.obsListOfOperationVehicles.remove(collOfRemovingVehicles.get(i));
		}
		this.tvVehicleData.setItems(obsListOfOperationVehicles);
	}

	@FXML
	private void onClickMItemRemoveVehicle(ActionEvent aE) {
		OperationVehicle selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();

		if (selectedVehicle != null) {
			this.obsListOfOperationVehicles.remove(selectedVehicle);
			this.tvVehicleData.refresh();
		}
	}

	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent aE) {
		this.selectedVehicleToUpdate = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if (this.selectedVehicleToUpdate != null) {
			this.btnUpdateVehicle.setDisable(false);
			this.tfUpdateVehicleDescription.setText(this.selectedVehicleToUpdate.getDescription());
		}
	}

	public List<OperationVehicle> getCreatedVehicleData() {
		return this.obsListOfOperationVehicles.stream().collect(Collectors.toList());
	}
}