package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.CentralHandler;
import bll.Base;
import bll.OperationVehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

	private ControllerBaseManagement controllerBaseManagement;
	private ControllerCreateBaseTabBase controllerCreateBaseTabBase;
	private ControllerCreateBaseTabOperationVehicle controllerCreateBaseTabOperationVehicle;
	private ControllerDialogSaveBase controllerDialogSaveBase;

	private Tab tabBase;
	private Tab tabOperationVehicle;

	private Base createdBase = null;

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
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateBaseTabBase.fxml");
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
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateBaseTabOperationVehicle.fxml");
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
		this.setCreatedBase(this.controllerCreateBaseTabBase.getCreatedBaseData());
	}

	@FXML
	private void onClickBtnFinish(ActionEvent aE) {
		this.setCreatedBase(this.controllerCreateBaseTabBase.getCreatedBaseData());
		Base createdBase = this.getCreatedBase();
		List<OperationVehicle> collOfCreatedVehicles = this.controllerCreateBaseTabOperationVehicle
				.getCreatedVehicleData();

		System.out.println(createdBase.toString());
		for (int i = 0; i < collOfCreatedVehicles.size(); i++) {
			System.out.println(collOfCreatedVehicles.get(i).toString());
		}

		FXMLLoader loader = CentralHandler.loadFXML("/gui/DialogSaveBase.fxml");

		this.controllerDialogSaveBase = new ControllerDialogSaveBase(this);
		loader.setController(this.controllerDialogSaveBase);

		try {
			Stage curStage = new Stage();
			Scene scene = new Scene(loader.load(), 600, 300);
			curStage.setScene(scene);
			curStage.initModality(Modality.APPLICATION_MODAL);
			curStage.setTitle("Would you like to save your created base");
			this.controllerDialogSaveBase.setData(createdBase, collOfCreatedVehicles);
			curStage.showAndWait();
			if (this.controllerDialogSaveBase.getButtonState()) {
				System.out.println(createdBase.toString());
				for (int i = 0; i < collOfCreatedVehicles.size(); i++) {
					System.out.println(collOfCreatedVehicles.get(i).toString());
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
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

	private void setCreatedBase(Base createdBase) {
		this.createdBase = createdBase;
	}

	public Base getCreatedBase() {
		return this.createdBase;
	}
}