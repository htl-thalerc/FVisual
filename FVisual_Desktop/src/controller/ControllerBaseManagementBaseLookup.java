package controller;

import java.net.URL;
import java.util.ResourceBundle;

import bll.Base;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableView;

public class ControllerBaseManagementBaseLookup implements Initializable {
	@FXML private Accordion dropMenuFilter;
	@FXML private TableView<Base> tvBase;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTableView();
		this.fillTableView();
	}
	
	private void initTableView() {
		
	}
	
	private void fillTableView() {
		
	}
}