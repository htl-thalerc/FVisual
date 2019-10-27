package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.Helper;
import bll.Base;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ControllerCreateBase implements Initializable {
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnFinish;

	@FXML
	private TabPane tabPaneBase;

	private ControllerCreateBaseTabBase controllerCreateBaseTabBase;
	private ControllerCreateBaseTabOperationVehicle controllerCreateBaseTabOperationVehicle;

	private ControllerBaseManagement controllerBaseManagement;

	private Tab tabBase;
	private Tab tabOperationVehicle;

	public ControllerCreateBase(ControllerBaseManagement controllerBaseManagement) {
		this.controllerBaseManagement = controllerBaseManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initPaneBase();
		this.initPaneOperationVehicle();
		this.initTabPaneListeners();
		this.initDisability();
	}

	private void initDisability() {
		this.tabOperationVehicle.setDisable(true);
		this.btnBack.setDisable(true);
		this.btnNext.setDisable(true);
		this.btnFinish.setDisable(true);
	}

	private void initPaneBase() {
		FXMLLoader loader = Helper.loadFXML("/gui/CreateBaseTabBase.fxml");
		this.tabBase = new Tab();
		this.tabBase.setText("Base");
		this.controllerCreateBaseTabBase = new ControllerCreateBaseTabBase(this);
		loader.setController(this.controllerCreateBaseTabBase);

		try {
			this.tabBase.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneBase.getTabs().add(this.tabBase);
	}

	private void initPaneOperationVehicle() {
		FXMLLoader loader = Helper.loadFXML("/gui/CreateBaseTabOperationVehicle.fxml");
		this.tabOperationVehicle = new Tab();
		this.tabOperationVehicle.setText("Operationvehicle");
		this.controllerCreateBaseTabOperationVehicle = new ControllerCreateBaseTabOperationVehicle(this);
		loader.setController(this.controllerCreateBaseTabOperationVehicle);

		try {
			this.tabOperationVehicle.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneBase.getTabs().add(this.tabOperationVehicle);
	}

	private void initTabPaneListeners() {
		this.tabBase.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(true);
			this.btnNext.setDisable(false);
		});

		this.tabOperationVehicle.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(true);
		});
	}

	@FXML
	private void onClickBtnCancel(ActionEvent aE) {
		System.out.println("sdfsdf");
	}

	@FXML
	private void onClickBtnBack(ActionEvent aE) {
		this.tabPaneBase.getSelectionModel().select(this.tabBase);
		this.btnNext.setDisable(false);
		this.btnBack.setDisable(true);
	}

	@FXML
	private void onClickBtnNext(ActionEvent aE) {
		this.tabPaneBase.getSelectionModel().select(this.tabOperationVehicle);
		this.btnNext.setDisable(true);
		this.btnBack.setDisable(false);
	}

	@FXML
	private void onClickBtnFinish(ActionEvent aE) {
		Base createdBase = this.controllerCreateBaseTabBase.getCreatedBaseData();

		if (createdBase != null) {
			System.out.println(createdBase.toString());
		}
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
	
	public void setTabOperationVehicleDisability(boolean isDiabled) {
		this.tabOperationVehicle.setDisable(isDiabled);
	}
}