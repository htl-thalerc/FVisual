package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bll.OperationVehicle;
import bll.OtherOrganisation;
import handler.EditingListCellOperationVehicle;
import handler.OperationVehicleHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ControllerCreateOperationTabOperationVehicleManagement implements Initializable {
	@FXML
	private ListView<OperationVehicle> lvAvailableOperationVehicles, lvSelectedOperationVehciles;
	@FXML
	private Button btnAddOneOperationVehicle, btnRemoveOneOperationVehicle, btnAddAllOperationVehicles,
			btnRemoveAllOperationVehicles, btnAddNewOperationVehicle;

	private final String CONST_NAME_FOR_NEW_VEHICLE = "Name for new Vehicle";
	private final String CONST_BTN_TEXT_ADD_NEW_VEHICLE = "Add new Vehicle";

	private ControllerCreateOperationManagement controllerCreateOperationManagement;
	private ObservableList<OperationVehicle> obsListLVAvailableOperationVehicles, obsListLVSelectedOperationVehciles;
	private boolean isLVAvailableOperationVehiclesSelected = false;
	private int nrOfTotalOperationVehicles = 0;

	public ControllerCreateOperationTabOperationVehicleManagement(
			ControllerCreateOperationManagement controllerCreateOperationManagement) {
		this.controllerCreateOperationManagement = controllerCreateOperationManagement;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.init();
	}

	private void init() {
		this.btnAddOneOperationVehicle.setDisable(true);
		this.btnRemoveOneOperationVehicle.setDisable(true);
		this.btnRemoveAllOperationVehicles.setDisable(true);
		this.initAvailableOperationVehicles();
		this.initSelecetedOperationVehicles();
		this.initListViewListeners();
	}

	private void initAvailableOperationVehicles() {
//		ArrayList<OperationVehicle> list = OperationVehicleHandler.getInstance().getGroupedVehicleList();
//
//		this.obsListLVAvailableOperationVehicles = FXCollections.observableArrayList(list);
//		this.lvAvailableOperationVehicles.setItems(obsListLVAvailableOperationVehicles);
//
//		this.nrOfTotalOperationVehicles = this.lvAvailableOperationVehicles.getItems().size();
		
		ArrayList<OperationVehicle> list = new ArrayList<OperationVehicle>();
		list.add(new OperationVehicle(1, "KRFA"));
		list.add(new OperationVehicle(2, "TLFA-2000"));
		list.add(new OperationVehicle(3, "LF-A"));
		list.add(new OperationVehicle(4, "RTB-50"));
		list.add(new OperationVehicle(5, "Ölwehranhnger"));
		
		this.obsListLVAvailableOperationVehicles = FXCollections.observableArrayList(list);
		this.lvAvailableOperationVehicles.setItems(obsListLVAvailableOperationVehicles);

		this.nrOfTotalOperationVehicles = this.lvAvailableOperationVehicles.getItems().size();
	}

	private void initSelecetedOperationVehicles() {
		this.obsListLVSelectedOperationVehciles = FXCollections.observableArrayList();
		this.lvSelectedOperationVehciles.setItems(obsListLVSelectedOperationVehciles);
	}

	private void initListViewListeners() {
		this.lvAvailableOperationVehicles.setOnMouseClicked(event -> {
			if (this.lvAvailableOperationVehicles.getSelectionModel().getSelectedItem() != null
					&& !this.lvAvailableOperationVehicles.getSelectionModel().getSelectedItem().getDescription()
							.equals("Name for new Vehicle")
					&& !this.obsListLVAvailableOperationVehicles
							.contains(new OperationVehicle(-1, CONST_NAME_FOR_NEW_VEHICLE, null, null))) {
				this.btnAddOneOperationVehicle.setDisable(false);
				this.btnRemoveOneOperationVehicle.setDisable(true);
				this.isLVAvailableOperationVehiclesSelected = true;
			}
		});

		this.lvSelectedOperationVehciles.setOnMouseClicked(event -> {
			if (this.lvSelectedOperationVehciles.getSelectionModel().getSelectedItem() != null
					&& !this.lvAvailableOperationVehicles.getSelectionModel().getSelectedItem().getDescription()
							.equals("Name for new Vehicle")
					&& !this.obsListLVAvailableOperationVehicles
							.contains(new OperationVehicle(-1, CONST_NAME_FOR_NEW_VEHICLE, null, null))) {
				this.btnAddOneOperationVehicle.setDisable(true);
				this.btnRemoveOneOperationVehicle.setDisable(false);
				this.isLVAvailableOperationVehiclesSelected = false;
			}
		});
	}

	@FXML
	private void onClickBtnAddOneOperationVehicle(ActionEvent event) {
		if (this.isLVAvailableOperationVehiclesSelected
				&& this.lvAvailableOperationVehicles.getSelectionModel().getSelectedItem() != null) {
			OperationVehicle selectedOperationVehicle = this.lvAvailableOperationVehicles.getSelectionModel()
					.getSelectedItem();

			this.lvSelectedOperationVehciles.getSelectionModel().clearSelection();
			this.lvAvailableOperationVehicles.getSelectionModel().selectNext();

			this.btnAddOneOperationVehicle.setDisable(true);

			this.obsListLVAvailableOperationVehicles.remove(selectedOperationVehicle);
			this.lvAvailableOperationVehicles.refresh();

			this.obsListLVSelectedOperationVehciles.add(selectedOperationVehicle);
			this.lvSelectedOperationVehciles.refresh();

			if (this.lvSelectedOperationVehciles.getItems().size() == this.nrOfTotalOperationVehicles) {
				this.btnAddAllOperationVehicles.setDisable(true);
				this.btnRemoveAllOperationVehicles.setDisable(false);
			}
		}
	}

	@FXML
	private void onClickBtnRemoveOneOperationVehicle(ActionEvent event) {
		if (!this.isLVAvailableOperationVehiclesSelected
				&& this.lvSelectedOperationVehciles.getSelectionModel().getSelectedItem() != null) {
			OperationVehicle selectedOperationVehicle = this.lvSelectedOperationVehciles.getSelectionModel()
					.getSelectedItem();

			this.lvSelectedOperationVehciles.getSelectionModel().selectNext();
			this.lvAvailableOperationVehicles.getSelectionModel().clearSelection();

			this.btnRemoveOneOperationVehicle.setDisable(true);

			this.obsListLVSelectedOperationVehciles.remove(selectedOperationVehicle);
			this.lvSelectedOperationVehciles.refresh();

			this.obsListLVAvailableOperationVehicles.add(selectedOperationVehicle);
			this.lvAvailableOperationVehicles.refresh();

			if (this.lvAvailableOperationVehicles.getItems().size() == this.nrOfTotalOperationVehicles) {
				this.btnAddAllOperationVehicles.setDisable(false);
				this.btnRemoveAllOperationVehicles.setDisable(true);
			}
		}
	}

	@FXML
	private void onClickBtnAddAllOperationVehicles(ActionEvent event) {
		this.obsListLVSelectedOperationVehciles.addAll(this.obsListLVAvailableOperationVehicles);
		this.lvSelectedOperationVehciles.setItems(this.obsListLVSelectedOperationVehciles);

		this.obsListLVAvailableOperationVehicles.clear();
		this.lvAvailableOperationVehicles.getItems().clear();

		this.btnAddOneOperationVehicle.setDisable(true);
		this.btnRemoveOneOperationVehicle.setDisable(true);

		this.btnAddAllOperationVehicles.setDisable(true);
		this.btnRemoveAllOperationVehicles.setDisable(false);
	}

	@FXML
	private void onClickBtnRemoveAllOperationVehicles(ActionEvent event) {
		this.obsListLVAvailableOperationVehicles.addAll(this.obsListLVSelectedOperationVehciles);
		this.lvAvailableOperationVehicles.setItems(this.obsListLVAvailableOperationVehicles);

		this.obsListLVSelectedOperationVehciles.clear();
		this.lvSelectedOperationVehciles.getItems().clear();

		this.btnAddOneOperationVehicle.setDisable(true);
		this.btnRemoveOneOperationVehicle.setDisable(true);

		this.btnAddAllOperationVehicles.setDisable(false);
		this.btnRemoveAllOperationVehicles.setDisable(true);
	}

	@FXML
	private void onClickBtnAddNewOperationVehicle(ActionEvent event) {
		
	}

	public List<OperationVehicle> getOperationVehcilesToCreate() {
		return this.lvSelectedOperationVehciles.getItems();
	}

	public void reset() {
		this.obsListLVAvailableOperationVehicles.clear();
		this.obsListLVSelectedOperationVehciles.clear();
		this.lvAvailableOperationVehicles.refresh();
		this.lvSelectedOperationVehciles.refresh();
		this.init();
	}

}
