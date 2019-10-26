package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ControllerCreateBaseTabBase implements Initializable {
	@FXML
    private TextField tfBaseName;

    @FXML
    private TextField tfPlace;

    @FXML
    private TextField tfPostCode;

    @FXML
    private TextField tfStreet;

    @FXML
    private TextField tfHouseNr;

    @FXML
    private Label lbStatusbarPlace;

    @FXML
    private Label lbStatusbarPostCode;

    @FXML
    private Label lbStatusbarStreet;

    @FXML
    private Label lbStatusbarHouseNr;

    @FXML
    private CheckBox checkBoxBaseName;

    @FXML
    private CheckBox checkBoxPlace;

    @FXML
    private CheckBox checkBoxPostCode;

    @FXML
    private CheckBox checkBoxStreet;

    @FXML
    private CheckBox checkBoxHouseNr;

    @FXML
    private Label lbStatusbarBaseName;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initCheckBoxDisability();
		this.initTextFieldPatterns();
		this.initTextFieldListeners();
	}
	
	private void initCheckBoxDisability() {
		this.checkBoxBaseName.setDisable(true);
		this.checkBoxHouseNr.setDisable(true);
		this.checkBoxPlace.setDisable(true);
		this.checkBoxPostCode.setDisable(true);
		this.checkBoxStreet.setDisable(true);
	}
	
	private void initTextFieldPatterns() {
		final Pattern pattern = Pattern.compile("\\d{0,4}");
		TextFormatter<?> formatter = new TextFormatter<>(change -> {
		    if (pattern.matcher(change.getControlNewText()).matches()) {
		        return change; 
		    } else {
		        return null;
		    }
		});
        this.tfPostCode.setTextFormatter(formatter);
	}
	
	private void initTextFieldListeners() {
		this.tfBaseName.textProperty().addListener((obj, oldVal, newVal) -> {
			if(!newVal.isEmpty() && newVal != null && newVal != "") {
				if(newVal.length() >= 8) {
					this.lbStatusbarBaseName.setText("OK - Valid Base Name");
					this.checkBoxBaseName.setSelected(true);
				} else {
					this.lbStatusbarBaseName.setText("Error - Base Name is too short");
					this.checkBoxBaseName.setSelected(false);
				}
			} else {
				this.lbStatusbarBaseName.setText("Error - Inputfield is empty");
				this.checkBoxBaseName.setSelected(false);
			}
		});
		
		this.tfHouseNr.textProperty().addListener((obj, oldVal, newVal) -> {
			if(!newVal.isEmpty() && newVal != null && newVal != "") {
				if(newVal.length() >= 1) {
					this.lbStatusbarHouseNr.setText("OK - Valid House Number");
					this.checkBoxHouseNr.setSelected(true);
				} else {
					this.lbStatusbarHouseNr.setText("Error - House Number is too short");
					this.checkBoxHouseNr.setSelected(false);
				}
			} else {
				this.lbStatusbarHouseNr.setText("Error - Inputfield is empty");
				this.checkBoxHouseNr.setSelected(false);
			}
		});
		
		this.tfPlace.textProperty().addListener((obj, oldVal, newVal) -> {
			if(!newVal.isEmpty() && newVal != null && newVal != "") {
				if(newVal.length() >= 3) {
					this.lbStatusbarPlace.setText("OK - Valid Place");
					this.checkBoxPlace.setSelected(true);
				} else {
					this.lbStatusbarPlace.setText("Error - Placename is too short");
					this.checkBoxPlace.setSelected(false);
				}
			} else {
				this.lbStatusbarPlace.setText("Error - Inputfield is empty");
				this.checkBoxPlace.setSelected(false);
			}
		});
		
		this.tfPostCode.textProperty().addListener((obj, oldVal, newVal) -> {
			if(!newVal.isEmpty() && newVal != null && newVal != "") {
				if(newVal.length() == 4) {
					this.lbStatusbarPostCode.setText("OK - Valid Post Code");
					this.checkBoxPostCode.setSelected(true);
				} else {
					this.lbStatusbarPostCode.setText("Error - Post Code must have a length of 4");
					this.checkBoxPostCode.setSelected(false);
				}
			} else {
				this.lbStatusbarPostCode.setText("Error - Inputfield is empty");
				this.checkBoxPostCode.setSelected(false);
			}
		});
		
		this.tfStreet.textProperty().addListener((obj, oldVal, newVal) -> {
			if(!newVal.isEmpty() && newVal != null && newVal != "") {
				if(newVal.length() >= 3) {
					this.lbStatusbarStreet.setText("OK - Valid Streetname");
					this.checkBoxStreet.setSelected(true);
				} else {
					this.lbStatusbarStreet.setText("Error - Streetname is too short");
					this.checkBoxStreet.setSelected(false);
				}
			} else {
				this.lbStatusbarStreet.setText("Error - Inputfield is empty");
				this.checkBoxStreet.setSelected(false);
			}
		});
	}
	
	@FXML private void onActionTfBaseNameCheckInput() {
		if(!this.lbStatusbarBaseName.getText().isEmpty() && this.lbStatusbarBaseName.getText() != null && this.lbStatusbarBaseName.getText() != "") {
			if(this.lbStatusbarBaseName.getText().length() >= 5) {
				this.lbStatusbarBaseName.setText("OK - Valid Base Name");
			} else {
				this.lbStatusbarBaseName.setText("Error - Base Name is too short");
			}
		} else {
			this.lbStatusbarBaseName.setText("Error - Inputfield is empty");
		} 
	}
}