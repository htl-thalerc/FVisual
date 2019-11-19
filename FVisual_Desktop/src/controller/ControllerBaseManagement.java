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

public class ControllerBaseManagement implements Initializable {
	@FXML
	private TabPane mainTabPaneBaseManagement;

	private ControllerBaseManagementBaseLookup controllerBaseManagementBaseLookup;
	private ControllerCreateBaseManagement controllerCreateBase;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTabBaseLookup();
		this.initTabCreateNewBase();
	}

	/**
	 * Loads for Tab 'Base Lookup' the Content
	 */
	private void initTabBaseLookup() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/BaseManagementBaseLookup.fxml");
		Tab tabBaseLookup = new Tab();
		tabBaseLookup.setText("Base Lookup");
		this.controllerBaseManagementBaseLookup = new ControllerBaseManagementBaseLookup(this);
		loader.setController(this.controllerBaseManagementBaseLookup);

		try {
			tabBaseLookup.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.mainTabPaneBaseManagement.getTabs().add(tabBaseLookup);
	}

	/**
	 * Loads for Tab 'Create New Base' the Content
	 */
	private void initTabCreateNewBase() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateBaseManagement.fxml");
		Tab tabCreateBase = new Tab();
		tabCreateBase.setText("Create Base Management");
		this.controllerCreateBase = new ControllerCreateBaseManagement(this);
		loader.setController(this.controllerCreateBase);

		try {
			tabCreateBase.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.mainTabPaneBaseManagement.getTabs().add(tabCreateBase);
	}
}