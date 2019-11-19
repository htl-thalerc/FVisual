package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bll.OtherOrganisation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ControllerCreateBaseTabOtherOrganisationManagement implements Initializable {
	@FXML
	private ListView<OtherOrganisation> lvAvailableOrganisation;
	@FXML
	private ListView<OtherOrganisation> lvSelectedOrganisation;
	@FXML
	private Button btnAddOneOrganisation;
	@FXML
	private Button btnRemoveOneOrganisation;
	@FXML
	private Button btnAddAllOrganisations;
	@FXML
	private Button btnRemoveAllOrganisations;

	private ControllerCreateBaseManagement controllerCreateBase;
	private ObservableList<OtherOrganisation> obsListLVAvailableOrganisation;
	private ObservableList<OtherOrganisation> obsListlVSelectedOrganisation;
	private boolean isLVAvailableOrganisationSelected = false;
	private int nrOfTotalOrganisations = 0;

	public ControllerCreateBaseTabOtherOrganisationManagement(ControllerCreateBaseManagement controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.init();
	}
	
	private void init() {
		this.btnAddOneOrganisation.setDisable(true);
		this.btnRemoveOneOrganisation.setDisable(true);
		this.initAvailableOperationVehicles();
		this.initSelecetedOperationVehicles();
		this.initListViewListeners();
	}
	
	private void initAvailableOperationVehicles() {
		ArrayList<OtherOrganisation> list = new ArrayList<OtherOrganisation>();
		list.add(new OtherOrganisation(1, "Polizei"));
		list.add(new OtherOrganisation(2, "Polizeihubschrauber"));
		list.add(new OtherOrganisation(3, "Rotes Kreuz"));
		list.add(new OtherOrganisation(4, "Notarzt"));
		list.add(new OtherOrganisation(5, "Rettungshubschrauber"));
		
		this.obsListLVAvailableOrganisation = FXCollections.observableArrayList(list);
		this.lvAvailableOrganisation.setItems(this.obsListLVAvailableOrganisation);
		
		this.nrOfTotalOrganisations = this.lvAvailableOrganisation.getItems().size();
	}

	private void initSelecetedOperationVehicles() {
		this.obsListlVSelectedOrganisation = FXCollections.observableArrayList();
		this.lvSelectedOrganisation.setItems(this.obsListlVSelectedOrganisation);
	}

	private void initListViewListeners() {
		this.lvAvailableOrganisation.setOnMouseClicked(event -> {
			if (this.lvAvailableOrganisation.getSelectionModel().getSelectedItem() != null) {
				this.btnAddOneOrganisation.setDisable(false);
				this.btnRemoveOneOrganisation.setDisable(true);
				this.isLVAvailableOrganisationSelected = true;
			}
		});

		this.lvSelectedOrganisation.setOnMouseClicked(event -> {
			if (this.lvSelectedOrganisation.getSelectionModel().getSelectedItem() != null) {
				this.btnAddOneOrganisation.setDisable(true);
				this.btnRemoveOneOrganisation.setDisable(false);
				this.isLVAvailableOrganisationSelected = false;
			}
		});
	}

	@FXML
	private void onClickBtnAddOneOrganisation(ActionEvent event) {
		if (this.isLVAvailableOrganisationSelected && this.lvAvailableOrganisation.getSelectionModel().getSelectedItem() != null) {
			OtherOrganisation selectedOrganisation = this.lvAvailableOrganisation.getSelectionModel()
					.getSelectedItem();
			
			this.lvSelectedOrganisation.getSelectionModel().clearSelection();
			this.lvAvailableOrganisation.getSelectionModel().selectNext();
			
			this.btnAddOneOrganisation.setDisable(true);
//			if((this.lvAvailableOperationVehicles.getSelectionModel().getSelectedIndex() + 1) < this.lvAvailableOperationVehicles.getItems().size()) {
//				this.lvAvailableOperationVehicles.getSelectionModel().selectNext();
//			} else {getCreatedVehicleData
//				this.lvAvailableOperationVehicles.getSelectionModel().selectFirst();
//			}
			
			this.obsListLVAvailableOrganisation.remove(selectedOrganisation);
			this.lvAvailableOrganisation.refresh();

			this.obsListlVSelectedOrganisation.add(selectedOrganisation);
			this.lvSelectedOrganisation.refresh();
			
			if(this.lvSelectedOrganisation.getItems().size() == this.nrOfTotalOrganisations) {
				this.btnAddAllOrganisations.setDisable(true);
				this.btnRemoveAllOrganisations.setDisable(false);
			}
		}
	}

	@FXML
	private void onClickBtnRemoveOneOrganisation(ActionEvent event) {
		if (!this.isLVAvailableOrganisationSelected && this.lvSelectedOrganisation.getSelectionModel().getSelectedItem() != null) {
			OtherOrganisation selectedOrganisation = this.lvSelectedOrganisation.getSelectionModel()
					.getSelectedItem();
			
			this.lvSelectedOrganisation.getSelectionModel().selectNext();
			this.lvAvailableOrganisation.getSelectionModel().clearSelection();
			
			this.btnRemoveOneOrganisation.setDisable(true);
//			if((this.lvSelectedOrganisation.getSelectionModel().getSelectedIndex() + 1) < this.lvSelectedOrganisation.getItems().size()) {
//				this.lvSelectedOrganisation.getSelectionModel().selectNext();	
//			} else {
//				this.lvSelectedOrganisation.getSelectionModel().selectFirst();
//			}
			
			this.obsListlVSelectedOrganisation.remove(selectedOrganisation);
			this.lvSelectedOrganisation.refresh();

			this.obsListLVAvailableOrganisation.add(selectedOrganisation);
			this.lvAvailableOrganisation.refresh();
			
			if(this.lvAvailableOrganisation.getItems().size() == this.nrOfTotalOrganisations) {
				this.btnAddAllOrganisations.setDisable(false);
				this.btnRemoveAllOrganisations.setDisable(true);
			}
		}
	}

	@FXML
	private void onClickBtnAddAllOrganisations(ActionEvent event) {
		this.obsListlVSelectedOrganisation.addAll(this.obsListLVAvailableOrganisation);
		this.lvSelectedOrganisation.setItems(this.obsListlVSelectedOrganisation);

		this.obsListLVAvailableOrganisation.clear();
		this.lvAvailableOrganisation.getItems().clear();
		
		this.btnAddOneOrganisation.setDisable(true);
		this.btnRemoveOneOrganisation.setDisable(true);

		this.btnAddAllOrganisations.setDisable(true);
		this.btnRemoveAllOrganisations.setDisable(false);
	}

	@FXML
	private void onClickBtnRemoveAllOrganisations(ActionEvent event) {
		this.obsListLVAvailableOrganisation.addAll(this.obsListlVSelectedOrganisation);
		this.lvAvailableOrganisation.setItems(this.obsListLVAvailableOrganisation);

		this.obsListlVSelectedOrganisation.clear();
		this.lvSelectedOrganisation.getItems().clear();
		
		this.btnAddOneOrganisation.setDisable(true);
		this.btnRemoveOneOrganisation.setDisable(true);

		this.btnAddAllOrganisations.setDisable(false);
		this.btnRemoveAllOrganisations.setDisable(true);
	}

	public List<OtherOrganisation> getOrganisationsToCreate() {
		return this.lvSelectedOrganisation.getItems();
	}

	public void reset() {
		this.obsListLVAvailableOrganisation.clear();
		this.obsListlVSelectedOrganisation.clear();
		this.lvAvailableOrganisation.refresh();
		this.lvSelectedOrganisation.refresh();
		this.init();
	}
}