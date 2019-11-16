package controller;

import java.net.URL;
import java.util.ResourceBundle;

import bll.Base;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerUpdateTabBase implements Initializable {
	@FXML
	private Label lbOldBasename;
	@FXML
	private Label lbOldPlace;
	@FXML
	private Label lbOldPostCode;
	@FXML
	private Label lbOldStreet;
	@FXML
	private Label lbOldHouseNr;
	@FXML
	private TextField tfNewBasename;
	@FXML
	private TextField tfNewPlace;
	@FXML
	private TextField tfNewPostCode;
	@FXML
	private TextField tfNewStreet;
	@FXML
	private TextField tfNewHouseNr;
	
	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog;
	
	public ControllerUpdateTabBase(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	public void setBaseData(Base selectedBase) {
		if(selectedBase != null) {
			this.lbOldBasename.setText(selectedBase.getName());
			this.lbOldHouseNr.setText(selectedBase.getHouseNr());
			this.lbOldPlace.setText(selectedBase.getPlace());
			this.lbOldPostCode.setText(String.valueOf(selectedBase.getPostCode()));
			this.lbOldStreet.setText(selectedBase.getStreet());
		}
	}
	
	public Base getNewBaseData() {
		Base retVal = new Base();
		if(this.tfNewBasename.getText().isEmpty()) {
			retVal.setName(this.lbOldBasename.getText());
		} else {
			retVal.setName(this.tfNewBasename.getText());
		}
		if(this.tfNewHouseNr.getText().isEmpty()) {
			retVal.setHouseNr(this.lbOldHouseNr.getText());
		} else {
			retVal.setHouseNr(this.tfNewHouseNr.getText());
		}
		if(this.tfNewPlace.getText().isEmpty()) {
			retVal.setPlace(this.lbOldPlace.getText());
		} else {
			retVal.setPlace(this.tfNewPlace.getText());
		}
		if(this.tfNewPostCode.getText().isEmpty()) {
			retVal.setPostCode(Integer.parseInt(this.lbOldPostCode.getText()));
		} else {
			retVal.setPostCode(Integer.parseInt(this.tfNewPostCode.getText()));
		}
		if(this.tfNewStreet.getText().isEmpty()) {
			retVal.setStreet(this.lbOldStreet.getText());
		} else {
			retVal.setStreet(this.tfNewStreet.getText());
		}
		return retVal;
	}
}