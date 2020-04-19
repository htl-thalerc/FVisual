package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bll.Base;
import bll.OperationVehicle;
import handler.OperationVehicleHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import loader.BaseVehicleLoader;

public class ControllerCreateOperationTabOperationVehicleManagement implements Initializable {
	@FXML
	private ListView<OperationVehicle> lvAvailableOperationVehicles, lvSelectedOperationVehciles;
	@FXML
	private Button btnAddOneOperationVehicle, btnRemoveOneOperationVehicle, btnAddAllOperationVehicles,
			btnRemoveAllOperationVehicles;

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
	}

	private void initAvailableOperationVehicles() {
		Thread threadLoadVehiclesByBase = new Thread(loadOperationVehiclesByBase());
		Task<Void> mainTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				threadLoadVehiclesByBase.start();
				threadLoadVehiclesByBase.join();
				return null;
			}
		};
		mainTask.setOnSucceeded(e -> {
			this.obsListLVAvailableOperationVehicles = FXCollections.observableArrayList(
					OperationVehicleHandler.getInstance().getVehicleListByBaseId());
			this.lvAvailableOperationVehicles.setItems(this.obsListLVAvailableOperationVehicles);

			this.nrOfTotalOperationVehicles = this.lvAvailableOperationVehicles.getItems().size();
		});
		try {
			Thread threadLoading = new Thread(mainTask);
			threadLoading.start();
			threadLoading.join();
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	private Task<Void> loadOperationVehiclesByBase() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new BaseVehicleLoader(new Base(1, null, null, 0, null, null)));
				thread.start();
				thread.join();
				return null;
			}
		};
	}

	private void initSelecetedOperationVehicles() {
		this.obsListLVSelectedOperationVehciles = FXCollections.observableArrayList();
		this.lvSelectedOperationVehciles.setItems(obsListLVSelectedOperationVehciles);
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