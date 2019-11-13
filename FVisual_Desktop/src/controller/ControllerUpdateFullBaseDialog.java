package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.CentralHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ControllerUpdateFullBaseDialog implements Initializable {
	@FXML private TabPane tabPaneUpdateTabs;
	@FXML private Tab tabUpdateBase;
	@FXML private Tab tabUpdateOprationVehicle;
	@FXML private Tab tabUpdateMember;
	@FXML private Button btnCancelUpdate;
	@FXML private Button btnBackUpdate;
	@FXML private Button btnNextUpdate;
	@FXML private Button btnSaveUpdate;
	
	private ControllerUpdateTabBase controllerUpdateTabBase;
	private ControllerUpdateTabOperationVehicle controllerUpdateTabOperationVehicle;
	private ControllerBaseManagementBaseLookup controllerBaseManagementBaseLookup;
	
	public ControllerUpdateFullBaseDialog(ControllerBaseManagementBaseLookup controllerBaseManagementBaseLookup) {
		this.controllerBaseManagementBaseLookup = controllerBaseManagementBaseLookup;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setTabUpdateBaseContent();
		this.setTabUpdateOperationVehicleContent();
		this.setTabUpdateMemberContent();
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
			this.tabUpdateBase.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setTabUpdateMemberContent() {
		//todo: Implement Controller + FXML File
	}
	
	public void selectTabUpdateBase() {
		this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateBase);
		this.tabUpdateOprationVehicle.setDisable(true);
		this.tabUpdateMember.setDisable(true);
	}
	
	public void selectTabUpdateOperationVehicle() {
		this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateOprationVehicle);
		this.tabUpdateBase.setDisable(true);
		this.tabUpdateMember.setDisable(true);
	}

	public void selectTabUpdateMember() {
	
	}
}