package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import handler.CentralHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
	
	public ControllerCreateOperationManagement(ControllerOperationManagement controllerOperationManagement) {
		this.controllerOperationManagement = controllerOperationManagement;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initPaneOperation();
		this.initPaneOperationVehicle();
		this.initPaneOtherOrg();
		this.initPaneMember();
	}
	
	private void initPaneOperation() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabOperationManagement.fxml");
		this.tabOperationManagement = new Tab();
		this.tabOperationManagement.setText("Operation Management");
		this.controllerCreateOperationTabOperationManagement = new ControllerCreateOperationTabOperationManagement(this);
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
		this.controllerCreateOperationTabOperationVehicleManagement = new ControllerCreateOperationTabOperationVehicleManagement(this);
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
		this.controllerCreateOperationTabOtherOrganisationManagement = new ControllerCreateOperationTabOtherOrganisationManagement(this);
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

    @FXML
    private void onClickBtnBack(ActionEvent event) {

    }

    @FXML
    private void onClickBtnFinish(ActionEvent event) {

    }

    @FXML
    private void onClickBtnNext(ActionEvent event) {

    }

    @FXML
    private void onClickBtnReset(ActionEvent event) {

    }
}