package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import bll.Base;
import bll.Operation;
import handler.CentralHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;

public class ControllerCreateOperationManagement implements Initializable {
	@FXML
	private TabPane tabPaneOperation;
	@FXML
	private Button btnReset, btnBack, btnNext, btnFinish;
	@FXML
	private Label lbStatusbar;

	private Tab tabOperationManagement, tabOperationVehicleManagement, tabOtherOrgManagement, tabMemberManagement;

	private ControllerCreateOperationTabOperationManagement controllerCreateOperationTabOperationManagement;
	private ControllerCreateOperationTabOperationVehicleManagement controllerCreateOperationTabOperationVehicleManagement;
	private ControllerCreateOperationTabOtherOrganisationManagement controllerCreateOperationTabOtherOrganisationManagement;
	private ControllerCreateOperationTabMemberManagement controllerCreateOperationTabMemberManagement;
	private ControllerOperationManagement controllerOperationManagement;

	private Operation createdOperation = null;

	public ControllerCreateOperationManagement(ControllerOperationManagement controllerOperationManagement) {
		this.controllerOperationManagement = controllerOperationManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initPaneOperation();
		this.initPaneOperationVehicle();
		this.initPaneOtherOrg();
		this.initPaneMember();
		this.initTabPaneListeners();
		this.initDisability();
	}

	private void initPaneOperation() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabOperationManagement.fxml");
		this.tabOperationManagement = new Tab();
		this.tabOperationManagement.setText("Operation Management");
		this.controllerCreateOperationTabOperationManagement = new ControllerCreateOperationTabOperationManagement(
				this);
		loader.setController(this.controllerCreateOperationTabOperationManagement);

		try {
			this.tabOperationManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabOperationManagement);
	}

	private void initPaneOperationVehicle() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabOperationVehicleManagement.fxml");
		this.tabOperationVehicleManagement = new Tab();
		this.tabOperationVehicleManagement.setText("Operation Vehicle Management");
		this.controllerCreateOperationTabOperationVehicleManagement = new ControllerCreateOperationTabOperationVehicleManagement(
				this);
		loader.setController(this.controllerCreateOperationTabOperationVehicleManagement);

		try {
			this.tabOperationVehicleManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabOperationVehicleManagement);
	}

	private void initPaneOtherOrg() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabOtherOrganisationManagement.fxml");
		this.tabOtherOrgManagement = new Tab();
		this.tabOtherOrgManagement.setText("Other Organisation Management");
		this.controllerCreateOperationTabOtherOrganisationManagement = new ControllerCreateOperationTabOtherOrganisationManagement(
				this);
		loader.setController(this.controllerCreateOperationTabOtherOrganisationManagement);

		try {
			this.tabOtherOrgManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabOtherOrgManagement);
	}

	private void initPaneMember() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabMemberManagement.fxml");
		this.tabMemberManagement = new Tab();
		this.tabMemberManagement.setText("Member Management");
		this.controllerCreateOperationTabMemberManagement = new ControllerCreateOperationTabMemberManagement(this);
		loader.setController(this.controllerCreateOperationTabMemberManagement);

		try {
			this.tabMemberManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabMemberManagement);
	}

	private void initDisability() {
		this.tabOperationVehicleManagement.setDisable(true);
		this.tabOtherOrgManagement.setDisable(true);
		this.tabMemberManagement.setDisable(true);
		this.btnReset.setDisable(true);
		this.btnBack.setDisable(true);
		this.btnNext.setDisable(true);
		this.btnFinish.setDisable(true);
	}

	private void initTabPaneListeners() {
		this.btnReset.setDisable(false);
		this.tabOperationManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(true);
			this.btnNext.setDisable(false);
		});

		this.tabOperationVehicleManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(false);
		});

		this.tabOtherOrgManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(false);
		});

		this.tabMemberManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(true);
		});
	}

	@FXML
	private void onClickBtnBack(ActionEvent event) {
		if (this.tabOperationVehicleManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationManagement);
		} else if (this.tabOtherOrgManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationVehicleManagement);
		}
		if (this.tabMemberManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOtherOrgManagement);
		}
	}

	@FXML
	private void onClickBtnFinish(ActionEvent event) {
		// ToDo: Implement
	}

	@FXML
	private void onClickBtnNext(ActionEvent event) {
		// this.setCreatedBase(this.controllerCreateBaseTabBaseManagement.getCreatedBaseData());
		if (this.tabOperationManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationVehicleManagement);
		} else if (this.tabOperationVehicleManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOtherOrgManagement);
		} else if (this.tabOtherOrgManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabMemberManagement);
		}
	}

	@FXML
	private void onClickBtnReset(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Input Fields");
		alert.setHeaderText("Would you really reset your Operationcreation");
		alert.setContentText("Note: All your Inputs are not going to be saved");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.resetCreateOperartionTabs();
			alert.close();
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationManagement);
			this.btnReset.setDisable(true);
			this.btnNext.setDisable(true);
		} else {
			alert.close();
		}
	}

	public void resetCreateOperartionTabs() {
		this.tabPaneOperation.getSelectionModel().select(this.tabOperationManagement);
		this.controllerCreateOperationTabOperationVehicleManagement.reset();
		this.controllerCreateOperationTabOtherOrganisationManagement.reset();
		this.controllerCreateOperationTabMemberManagement.reset();
	}

	public void setButtonResetDisability(boolean isDisabled) {
		this.btnReset.setDisable(isDisabled);
	}

	public void setButtonNextDisability(boolean isDisabled) {
		this.btnNext.setDisable(isDisabled);
	}

	public void setButtonFinishDisability(boolean isDisabled) {
		this.btnFinish.setDisable(isDisabled);
	}

	public void setButtonBackDisability(boolean isDisabled) {
		this.btnBack.setDisable(isDisabled);
	}

	public void setAllOptionButtonsDisability(boolean isDisabled) {
		this.setButtonResetDisability(isDisabled);
		this.setButtonBackDisability(isDisabled);
		this.setButtonNextDisability(isDisabled);
		this.setButtonFinishDisability(isDisabled);
	}

	public void setTabOperationVehicleManagementDisability(boolean isDiabled) {
		this.tabOperationVehicleManagement.setDisable(isDiabled);
	}
	
	public void setTabOtherOrganisationManagementDisability(boolean isDisabled) {
		this.tabOtherOrgManagement.setDisable(isDisabled);
	}

	public void setTabMemberManagementDisability(boolean isDisabled) {
		this.tabMemberManagement.setDisable(isDisabled);
	}

	private void setCreatedOperation(Operation createdOperation) {
		this.createdOperation = createdOperation;
	}

	public Operation getCreatedOperation() {
		return this.createdOperation;
	}

	public void setStatusbarValue(String text) {
		this.lbStatusbar.setText(text);
	}
}