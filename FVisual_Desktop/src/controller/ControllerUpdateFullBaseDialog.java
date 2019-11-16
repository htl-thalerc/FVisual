package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bll.Base;
import bll.OperationVehicle;
import helper.CentralHandler;
import helper.CentralUpdateHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ControllerUpdateFullBaseDialog implements Initializable {
	@FXML private TabPane tabPaneUpdateTabs;
	@FXML private Tab tabUpdateBase;
	@FXML private Tab tabUpdateOperationVehicle;
	@FXML private Tab tabUpdateMember;
	@FXML private Button btnCancelUpdate;
	@FXML private Button btnBackUpdate;
	@FXML private Button btnNextUpdate;
	@FXML private Button btnSaveUpdate;
	
	private boolean btnSaveState;
	
	private ControllerUpdateTabBase controllerUpdateTabBase;
	private ControllerUpdateTabOperationVehicle controllerUpdateTabOperationVehicle;
	private CentralUpdateHandler centralUpdateHandler;
	
	public ControllerUpdateFullBaseDialog(CentralUpdateHandler centralUpdateHandler) {
		this.centralUpdateHandler = centralUpdateHandler;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setTabUpdateBaseContent();
		this.setTabUpdateOperationVehicleContent();
		this.setTabUpdateMemberContent();
		this.initTabListeners();
	}
	
	private void setTabUpdateBaseContent() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/UpdateTabBase.fxml");
		this.controllerUpdateTabBase = new ControllerUpdateTabBase(this);
		loader.setController(this.controllerUpdateTabBase);

		try {
			this.tabUpdateBase.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setTabUpdateOperationVehicleContent() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/UpdateTabOperationVehicle.fxml");
		this.controllerUpdateTabOperationVehicle = new ControllerUpdateTabOperationVehicle(this);
		loader.setController(this.controllerUpdateTabOperationVehicle);

		try {
			this.tabUpdateOperationVehicle.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setTabUpdateMemberContent() {
		//todo: Implement Controller + FXML File
	}
	
	private void initTabListeners() {
		this.btnBackUpdate.setDisable(true);
		if(this.tabPaneUpdateTabs.getSelectionModel().getSelectedItem().equals(this.tabUpdateBase)) {
			this.tabUpdateBase.setOnSelectionChanged(event -> {
				this.btnBackUpdate.setDisable(true);
				this.btnNextUpdate.setDisable(false);
			});
			this.tabUpdateOperationVehicle.setOnSelectionChanged(event -> {
				this.btnBackUpdate.setDisable(false);
				this.btnNextUpdate.setDisable(false);
			});
			this.tabUpdateMember.setOnSelectionChanged(event -> {
				this.btnBackUpdate.setDisable(false);
				this.btnNextUpdate.setDisable(true);
			});
		} else {
			this.btnBackUpdate.setDisable(true);
			this.btnNextUpdate.setDisable(true);
		}
	}
	
	public void selectTabUpdateBase() {
		this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateBase);
	}
	
	public void selectTabUpdateOperationVehicle() {
		this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateOperationVehicle);
		this.tabUpdateBase.setDisable(true);
		this.tabUpdateMember.setDisable(true);
		this.btnBackUpdate.setDisable(true);
		this.btnNextUpdate.setDisable(true);
	}

	public void selectTabUpdateMember() {
	
	}
	
	@FXML
	private void onClickBtnCancelUpdate(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.btnSaveState = false;
		stage.close();
	}
	
	@FXML
	private void onClickBtnBackUpdate(ActionEvent aE) {
		if(this.tabUpdateOperationVehicle.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateBase);
		} else if(this.tabUpdateMember.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateOperationVehicle);
		}
	}
	
	@FXML
	private void onClickBtnNextUpdate(ActionEvent aE) {
		if(this.tabUpdateBase.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateOperationVehicle);
		} else if(this.tabUpdateOperationVehicle.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateMember);
		}
	}
	
	@FXML
	private void onClickBtnSaveUpdate(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.btnSaveState = true;
		stage.close();
	}
	
	public boolean getBtnSaveState() {
		return this.btnSaveState;
	}
	
	public Base getUpdatedBaseData() {
		return this.controllerUpdateTabBase.getNewBaseData();
	}
	
	public void setOldBaseData(Base oldBaseData) {
		this.controllerUpdateTabBase.setBaseData(oldBaseData);
	}
	
	public OperationVehicle getUpdatedOperationVehicleData() {
		return this.controllerUpdateTabOperationVehicle.getNewOperationVehicleData();
	}

	public void setOldOperationVehicleData(OperationVehicle oldOperationVehicleData) {
		this.controllerUpdateTabOperationVehicle.setOperationVehicleData(oldOperationVehicleData);
	}
	
	public void setSaveBtnDisability(boolean isDisable) {
		this.btnSaveUpdate.setDisable(isDisable);
	}
}