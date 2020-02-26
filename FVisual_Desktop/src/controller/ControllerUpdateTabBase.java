package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import bll.Base;
import handler.BaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ControllerUpdateTabBase implements Initializable {
	@FXML
	private Label lbOldBasename, lbOldPlace, lbOldPostCode, lbOldStreet, lbOldHouseNr, lbStatusbar;
	@FXML
	private TextField tfNewBasename, tfNewPlace, tfNewPostCode, tfNewStreet, tfNewHouseNr;

	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog;
	private AtomicBoolean isValidBasename = new AtomicBoolean(false);
	private AtomicBoolean isValidPlace = new AtomicBoolean(false);
	private AtomicBoolean isValidStreet = new AtomicBoolean(false);
	private AtomicBoolean isValidHouseNr = new AtomicBoolean(false);
	private AtomicBoolean isValidPostCode = new AtomicBoolean(false);

	public ControllerUpdateTabBase(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTextFieldPatterns();
		this.initTextFieldListeners();
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
		this.tfNewPostCode.setTextFormatter(formatter);
	}

	private void initTextFieldListeners() {
		// ToDo: Outsource to CentralHandler
		this.tfNewBasename.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 12) {
				this.isValidBasename.set(true);
				this.lbStatusbar.setText("Valid Inputvalue for Basename");
				this.tfNewBasename.setStyle("-fx-text-box-border: green;");
				this.tfNewBasename.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				if ((oldVal != "" || oldVal != null) && oldVal.length() >= 1) {
					if(this.isValidHouseNr.get() || this.isValidPlace.get() || this.isValidPostCode.get() || this.isValidStreet.get()) {
						this.isValidBasename.set(false);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
						this.lbStatusbar.setText("");
					} else {
						this.isValidBasename.set(true);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
					}
				} else {
					this.isValidBasename.set(false);
					this.lbStatusbar.setText("Invalid Inputvalue for Basename is too short (length >= 12)");
					this.tfNewBasename.setStyle("-fx-text-box-border: red;");
					this.tfNewBasename.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				}
			}
		});

		this.tfNewHouseNr.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 1) {
				this.isValidHouseNr.set(true);
				this.lbStatusbar.setText("Valid Inputvalue for HouseNr");
				this.tfNewHouseNr.setStyle("-fx-text-box-border: green;");
				this.tfNewHouseNr.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				if ((oldVal != "" || oldVal != null) && oldVal.length() >= 1) {
					if(this.isValidBasename.get() || this.isValidPlace.get() || this.isValidPostCode.get() || this.isValidStreet.get()) {
						this.isValidHouseNr.set(false);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
						this.lbStatusbar.setText("");
					} else {
						this.isValidHouseNr.set(true);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
					}
				} else {
					this.isValidHouseNr.set(false);
					this.lbStatusbar.setText("Invalid Inputvalue for HouseNr is too short (length >= 1)");
					this.tfNewHouseNr.setStyle("-fx-text-box-border: red;");
					this.tfNewHouseNr.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				}
			}
		});

		this.tfNewPlace.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				this.isValidPlace.set(true);
				this.lbStatusbar.setText("Valid Inputvalue for Place");
				this.tfNewPlace.setStyle("-fx-text-box-border: green;");
				this.tfNewPlace.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				if ((oldVal != "" || oldVal != null) && oldVal.length() >= 1) {
					if(this.isValidHouseNr.get() || this.isValidBasename.get() || this.isValidPostCode.get() || this.isValidStreet.get()) {
						this.isValidPlace.set(false);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
						this.lbStatusbar.setText("");
					} else {
						this.isValidPlace.set(true);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
					}
				} else {
					this.isValidPlace.set(false);
					this.lbStatusbar.setText("Invalid Inputvalue for Place is too short (length >= 3)");
					this.tfNewPlace.setStyle("-fx-text-box-border: red;");
					this.tfNewPlace.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				}
			}
		});

		this.tfNewPostCode.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() == 4) {
				this.isValidPostCode.set(true);
				this.lbStatusbar.setText("Valid Inputvalue for PostCode");
				this.tfNewPostCode.setStyle("-fx-text-box-border: green;");
				this.tfNewPostCode.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				if ((oldVal != "" || oldVal != null) && oldVal.length() >= 1) {
					if(this.isValidHouseNr.get() || this.isValidPlace.get() || this.isValidBasename.get() || this.isValidStreet.get()) {
						this.isValidPostCode.set(false);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
						this.lbStatusbar.setText("");
					} else {
						this.isValidPostCode.set(true);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
					}
				} else {
					this.isValidPostCode.set(false);
					this.lbStatusbar.setText("Invalid Inputvalue for PostCode is too short (length = 4)");
					this.tfNewPostCode.setStyle("-fx-text-box-border: red;");
					this.tfNewPostCode.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				}
			}
		});

		this.tfNewStreet.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				this.isValidStreet.set(true);
				this.lbStatusbar.setText("Valid Inputvalue for Street");
				this.tfNewStreet.setStyle("-fx-text-box-border: green;");
				this.tfNewStreet.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				if ((oldVal != "" || oldVal != null) && oldVal.length() >= 1) {
					if(this.isValidHouseNr.get() || this.isValidPlace.get() || this.isValidPostCode.get() || this.isValidBasename.get()) {
						this.isValidStreet.set(false);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
						this.lbStatusbar.setText("");
					} else {
						this.isValidStreet.set(true);
						this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
					}
				} else {
					this.isValidStreet.set(false);
					this.lbStatusbar.setText("Invalid Inputvalue for Street is too short (length >= 3)");
					this.tfNewStreet.setStyle("-fx-text-box-border: red;");
					this.tfNewStreet.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
				}
			}
		});
	}

	public void setBaseData(Base selectedBase) {
		if (selectedBase != null) {
			this.lbOldBasename.setText(selectedBase.getName());
			this.lbOldHouseNr.setText(selectedBase.getHouseNr());
			this.lbOldPlace.setText(selectedBase.getPlace());
			this.lbOldPostCode.setText(String.valueOf(selectedBase.getPostCode()));
			this.lbOldStreet.setText(selectedBase.getStreet());
		}
	}

	public Base getNewBaseData() {
		Base retVal = new Base();
		if (this.tfNewBasename.getText().isEmpty()) {
			retVal.setName(this.lbOldBasename.getText());
		} else {
			retVal.setName(this.tfNewBasename.getText());
		}
		if (this.tfNewHouseNr.getText().isEmpty()) {
			retVal.setHouseNr(this.lbOldHouseNr.getText());
		} else {
			retVal.setHouseNr(this.tfNewHouseNr.getText());
		}
		if (this.tfNewPlace.getText().isEmpty()) {
			retVal.setPlace(this.lbOldPlace.getText());
		} else {
			retVal.setPlace(this.tfNewPlace.getText());
		}
		if (this.tfNewPostCode.getText().isEmpty()) {
			retVal.setPostCode(Integer.parseInt(this.lbOldPostCode.getText()));
		} else {
			retVal.setPostCode(Integer.parseInt(this.tfNewPostCode.getText()));
		}
		if (this.tfNewStreet.getText().isEmpty()) {
			retVal.setStreet(this.lbOldStreet.getText());
		} else {
			retVal.setStreet(this.tfNewStreet.getText());
		}
		
		//checks if base has updated
		ArrayList<Base> tempBases = BaseHandler.getInstance().getBaseList();
		for(int i=0;i<tempBases.size();i++) {
			if(tempBases.get(i).equals(retVal)) {
				retVal.setUpdated(false);
			} else {
				retVal.setUpdated(true);
			}
		}
		return retVal;
	}
}