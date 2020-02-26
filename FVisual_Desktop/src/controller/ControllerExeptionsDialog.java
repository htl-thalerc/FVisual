package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import handler.ExceptionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import bll.Exception;

public class ControllerExeptionsDialog implements Initializable {
	@FXML
	private ListView<Exception> lvExceptions;
	
	private ControllerMainframe controllerMainframe;
	
	public ControllerExeptionsDialog(ControllerMainframe controllerMainframe) {
		this.controllerMainframe = controllerMainframe;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.loadExceptions();
	}
	
	public void loadExceptions() {
		ArrayList<Exception> tempExceptions = ExceptionHandler.getInstance().getExceptions();
		ObservableList<Exception> obsList = FXCollections.observableArrayList(tempExceptions);
		
		this.lvExceptions.setItems(obsList);
	}
}