package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class ControllerUpdateTabBase implements Initializable {
	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog;
	
	public ControllerUpdateTabBase(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}