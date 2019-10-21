package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ControllerBaseManagement implements Initializable {
	@FXML
	private TabPane mainTabPaneBaseManagement;
	@FXML
	private Tab tabBaseLookup;
	@FXML
	private Tab tabCreateNewBase;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTabBaseLookup();
		this.initTabCreateNewBase();
	}

	/**
	 * Loads for Tab 'Base Lookup' the Content
	 */
	private void initTabBaseLookup() {
		try {
			this.tabBaseLookup.setContent(FXMLLoader.load(getClass().getResource("/gui/BaseManagementBaseLookup.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads for Tab 'Create New Base' the Content
	 */
	private void initTabCreateNewBase() {

	}
}