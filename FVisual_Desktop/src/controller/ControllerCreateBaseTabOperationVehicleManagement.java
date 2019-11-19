package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bll.OperationVehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ControllerCreateBaseTabOperationVehicleManagement implements Initializable {
	@FXML
	private ListView<OperationVehicle> lvAvailableOperationVehicles;
	@FXML
	private ListView<OperationVehicle> lvSelectedOperationVehciles;
	@FXML
	private Button btnAddOneOperationVehicle;
	@FXML
	private Button btnRemoveOneOperationVehicle;
	@FXML
	private Button btnAddAllOperationVehicles;
	@FXML
	private Button btnRemoveAllOperationVehicles;

	private ControllerCreateBaseManagement controllerCreateBase;
	private ObservableList<OperationVehicle> obsListLVAvailableOperationVehicles;
	private ObservableList<OperationVehicle> obsListLVSelectedOperationVehciles;
	private boolean isLVAvailableOperationVehiclesSelected = false;
	private int nrOfTotalOperationVehicles = 0;

	public ControllerCreateBaseTabOperationVehicleManagement(ControllerCreateBaseManagement controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.init();
	}
	
	private void init() {
		this.btnAddOneOperationVehicle.setDisable(true);
		this.btnRemoveOneOperationVehicle.setDisable(true);
		this.initAvailableOperationVehicles();
		this.initSelecetedOperationVehicles();
		this.initListViewListeners();
	}

	private void initAvailableOperationVehicles() {
		ArrayList<OperationVehicle> list = new ArrayList<OperationVehicle>();
		list.add(new OperationVehicle(1, "KRFA", null));
		list.add(new OperationVehicle(2, "TLFA-2000", null));
		list.add(new OperationVehicle(3, "LF-A", null));
		list.add(new OperationVehicle(4, "RTB-50", null));
		list.add(new OperationVehicle(5, "Ölwehranhänger", null));
		list.add(new OperationVehicle(6, "TLFA-4000", null));
		list.add(new OperationVehicle(7, "LFA", null));
		list.add(new OperationVehicle(8, "Katastrophenschutzanhänger", null));

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
			if (this.lvAvailableOperationVehicles.getSelectionModel().getSelectedItem() != null) {
				this.btnAddOneOperationVehicle.setDisable(false);
				this.btnRemoveOneOperationVehicle.setDisable(true);
				this.isLVAvailableOperationVehiclesSelected = true;
			}
		});

		this.lvSelectedOperationVehciles.setOnMouseClicked(event -> {
			if (this.lvSelectedOperationVehciles.getSelectionModel().getSelectedItem() != null) {
				this.btnAddOneOperationVehicle.setDisable(true);
				this.btnRemoveOneOperationVehicle.setDisable(false);
				this.isLVAvailableOperationVehiclesSelected = false;
			}
		});
	}

	@FXML
	private void onClickBtnAddOneOperationVehicle(ActionEvent event) {
		if (this.isLVAvailableOperationVehiclesSelected && this.lvAvailableOperationVehicles.getSelectionModel().getSelectedItem() != null) {
			OperationVehicle selectedOperationVehicle = this.lvAvailableOperationVehicles.getSelectionModel()
					.getSelectedItem();
			
			this.lvSelectedOperationVehciles.getSelectionModel().clearSelection();
			this.lvAvailableOperationVehicles.getSelectionModel().selectNext();
			
			this.btnAddOneOperationVehicle.setDisable(true);
//			if((this.lvAvailableOperationVehicles.getSelectionModel().getSelectedIndex() + 1) < this.lvAvailableOperationVehicles.getItems().size()) {
//				this.lvAvailableOperationVehicles.getSelectionModel().selectNext();
//			} else {
//				this.lvAvailableOperationVehicles.getSelectionModel().selectFirst();
//			}
			
			this.obsListLVAvailableOperationVehicles.remove(selectedOperationVehicle);
			this.lvAvailableOperationVehicles.refresh();

			this.obsListLVSelectedOperationVehciles.add(selectedOperationVehicle);
			this.lvSelectedOperationVehciles.refresh();
			
			if(this.lvSelectedOperationVehciles.getItems().size() == this.nrOfTotalOperationVehicles) {
				this.btnAddAllOperationVehicles.setDisable(true);
				this.btnRemoveAllOperationVehicles.setDisable(false);
			}
		}
	}

	@FXML
	private void onClickBtnRemoveOneOperationVehicle(ActionEvent event) {
		if (!this.isLVAvailableOperationVehiclesSelected && this.lvSelectedOperationVehciles.getSelectionModel().getSelectedItem() != null) {
			OperationVehicle selectedOperationVehicle = this.lvSelectedOperationVehciles.getSelectionModel()
					.getSelectedItem();
			
			this.lvSelectedOperationVehciles.getSelectionModel().selectNext();
			this.lvAvailableOperationVehicles.getSelectionModel().clearSelection();
			
			this.btnRemoveOneOperationVehicle.setDisable(true);
//			if((this.lvSelectedOperationVehciles.getSelectionModel().getSelectedIndex() + 1) < this.lvSelectedOperationVehciles.getItems().size()) {
//				this.lvSelectedOperationVehciles.getSelectionModel().selectNext();	
//			} else {
//				this.lvSelectedOperationVehciles.getSelectionModel().selectFirst();
//			}
			
			this.obsListLVSelectedOperationVehciles.remove(selectedOperationVehicle);
			this.lvSelectedOperationVehciles.refresh();

			this.obsListLVAvailableOperationVehicles.add(selectedOperationVehicle);
			this.lvAvailableOperationVehicles.refresh();
			
			if(this.lvAvailableOperationVehicles.getItems().size() == this.nrOfTotalOperationVehicles) {
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