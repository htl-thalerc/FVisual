package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import bll.Base;
import bll.EditingCell;
import bll.OperationVehicle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class ControllerCreateBaseTabOperationVehicle implements Initializable {
	@FXML
	private ComboBox<OperationVehicle> cbAutoCompleteVehicle;
	@FXML
	private Button btnAddSelectedVehicle;
	@FXML
	private Button btnAddEnteredVehicle;
	@FXML
	private Button btnSelectAllOrNone;
	@FXML
	private TextField tfAddVehicleDescription;
	@FXML
	private Label lbStatusbarAddVehicle;
	@FXML
	private CheckBox checkBoxAddVehicle;
	@FXML
	private TableView<OperationVehicle> tvVehicleData;

	private ObservableList<OperationVehicle> obsListOfOperationVehicles;
	private ControllerCreateBase controllerCreateBase;

	private Set<OperationVehicle> setOfAddedVehicles = new HashSet<>();

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
	}

	@SuppressWarnings("unchecked")
	private void initTableView() {
		this.tvVehicleData.setEditable(true);
        Callback<TableColumn<OperationVehicle, String>, TableCell<OperationVehicle, String>> cellFactory =
             new Callback<TableColumn<OperationVehicle, String>, TableCell<OperationVehicle, String>>() {
                 public TableCell<OperationVehicle, String> call(TableColumn<OperationVehicle, String> p) {
                    return new EditingCell();
                 }
             };
		
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>(
				"Vehicle Description");

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		columnVehicleDescription.setCellFactory(cellFactory);
		columnVehicleDescription.setOnEditCommit(
            new EventHandler<CellEditEvent<OperationVehicle, String>>() {
                @Override
                public void handle(CellEditEvent<OperationVehicle, String> t) {
                    ((OperationVehicle) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                }
            }
        );
		
		this.tvVehicleData.getColumns().addAll(columnVehicleDescription);
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
		AtomicBoolean vehicleExists = new AtomicBoolean(false);

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
				for(OperationVehicle currVehicle : this.setOfAddedVehicles) {
					if(currVehicle.getDescription().equals(this.tfAddVehicleDescription.getText().trim())) {
						vehicleExists.set(true);
						break;
					} else {
						vehicleExists.set(false);
					}
				}
				if(!vehicleExists.get()) {
					this.setOfAddedVehicles.add(new OperationVehicle(new CheckBox(), 0,
							this.tfAddVehicleDescription.getText().trim(), this.controllerCreateBase.getCreatedBase()));
				}
				this.obsListOfOperationVehicles.addAll(this.setOfAddedVehicles);
				this.tvVehicleData.setItems(this.obsListOfOperationVehicles);
				this.tvVehicleData.refresh();
			}
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
	private void onClickMItemRemoveVehicle(ActionEvent aE) {
		OperationVehicle selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();

		if (selectedVehicle != null) {
			this.obsListOfOperationVehicles.remove(selectedVehicle);
			this.tvVehicleData.refresh();
		}
	}

	public List<OperationVehicle> getCreatedVehicleData() {
		return this.obsListOfOperationVehicles.stream().collect(Collectors.toList());
	}
	
	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent aE) {
		//ToDo: Build update dialog
	}

	public void clearTextFields() {
		this.tfAddVehicleDescription.clear();
	}

	public void clearTableView() {
		this.obsListOfOperationVehicles.clear();
		this.tvVehicleData.getItems().clear();
	}
}