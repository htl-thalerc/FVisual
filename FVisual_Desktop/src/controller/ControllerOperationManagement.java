package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import handler.CentralHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ControllerOperationManagement implements Initializable {
	@FXML
	private TabPane mainTabPaneOperationManagement;
	private ControllerOperationManagementOperationLookup controllerOperationManagementOperationLookup;
	private ControllerCreateOperationManagement controllerCreateOperationManagement;
	private ControllerMainframe controllerMainframe;

	public ControllerOperationManagement(ControllerMainframe controllerMainframe) {
		this.controllerMainframe = controllerMainframe;
		this.controllerMainframe.removeInnerBorderPaneContent();
		this.controllerMainframe.removeProgressbar();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTabOperationLookup();
		this.initTabCreateNewOperation();
	}
	
	/**
	 * Loads for Tab 'Operation Lookup' the Content
	 */
	private void initTabOperationLookup() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/OperationManagementOperationLookup.fxml");
		Tab tabBaseLookup = new Tab();
		tabBaseLookup.setText("Operation Lookup");
		this.controllerOperationManagementOperationLookup = new ControllerOperationManagementOperationLookup(this);
		loader.setController(this.controllerOperationManagementOperationLookup);

		try {
			tabBaseLookup.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.mainTabPaneOperationManagement.getTabs().add(tabBaseLookup);
	}

	/**
	 * Loads for Tab 'Create New Operation' the Content
	 */
	private void initTabCreateNewOperation() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationManagement.fxml");
		Tab tabCreateBase = new Tab();
		tabCreateBase.setText("Create Operation Management");
		this.controllerCreateOperationManagement = new ControllerCreateOperationManagement(this);
		loader.setController(this.controllerCreateOperationManagement);

		try {
			tabCreateBase.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.mainTabPaneOperationManagement.getTabs().add(tabCreateBase);
	}

	public void reloadOperationLookup() {
		this.controllerOperationManagementOperationLookup.fillTableViews(true); //boolean: is for reloading
		this.controllerCreateOperationManagement.resetCreateOperartionTabs();;
		this.mainTabPaneOperationManagement.getSelectionModel().select(0);
	}
}