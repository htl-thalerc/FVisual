package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import bll.Base;
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

	private ControllerCreateBase controllerCreateBase;

	public ControllerCreateBaseTabBase(ControllerCreateBase controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

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
		AtomicBoolean isValidBaseName = new AtomicBoolean(false);
		AtomicBoolean isValidHouseNr = new AtomicBoolean(false);
		AtomicBoolean isValidPlace = new AtomicBoolean(false);
		AtomicBoolean isValidPostCode = new AtomicBoolean(false);
		AtomicBoolean isValidStreet = new AtomicBoolean(false);

		this.tfBaseName.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() >= 12) {
					this.lbStatusbarBaseName.setText("Valid Base Name");
					this.checkBoxBaseName.setSelected(true);
					isValidBaseName.set(true);
				} else {
					this.lbStatusbarBaseName.setText("Invalid - Base Name is too short");
					this.checkBoxBaseName.setSelected(false);
					isValidBaseName.set(false);
				}
			} else {
				this.lbStatusbarBaseName.setText("Invalid - Inputfield is empty");
				this.checkBoxBaseName.setSelected(false);
				isValidBaseName.set(false);
			}
			setControlDisability(isValidBaseName.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfHouseNr.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() >= 1) {
					this.lbStatusbarHouseNr.setText("Valid House Number");
					this.checkBoxHouseNr.setSelected(true);
					isValidHouseNr.set(true);
				} else {
					this.lbStatusbarHouseNr.setText("Invalid - House Number is too short");
					this.checkBoxHouseNr.setSelected(false);
					isValidHouseNr.set(false);
				}
			} else {
				this.lbStatusbarHouseNr.setText("Invalid - Inputfield is empty");
				this.checkBoxHouseNr.setSelected(false);
				isValidHouseNr.set(false);
			}
			setControlDisability(isValidBaseName.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfPlace.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() >= 3) {
					this.lbStatusbarPlace.setText("Valid Place");
					this.checkBoxPlace.setSelected(true);
					isValidPlace.set(true);
				} else {
					this.lbStatusbarPlace.setText("Invalid - Placename is too short");
					this.checkBoxPlace.setSelected(false);
					isValidPlace.set(false);
				}
			} else {
				this.lbStatusbarPlace.setText("Invalid - Inputfield is empty");
				this.checkBoxPlace.setSelected(false);
				isValidPlace.set(false);
			}
			setControlDisability(isValidBaseName.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfPostCode.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() == 4) {
					this.lbStatusbarPostCode.setText("Valid Postcode");
					this.checkBoxPostCode.setSelected(true);
					isValidPostCode.set(true);
				} else {
					this.lbStatusbarPostCode.setText("Invalid - Post Code must have a length of 4");
					this.checkBoxPostCode.setSelected(false);
					isValidPostCode.set(false);
				}
			} else {
				this.lbStatusbarPostCode.setText("Invalid - Inputfield is empty");
				this.checkBoxPostCode.setSelected(false);
				isValidPostCode.set(false);
			}
			setControlDisability(isValidBaseName.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfStreet.textProperty().addListener((obj, oldVal, newVal) -> {
			if (!newVal.isEmpty() && newVal != null && newVal != "") {
				if (newVal.length() >= 3) {
					this.lbStatusbarStreet.setText("Valid Streetname");
					this.checkBoxStreet.setSelected(true);
					isValidStreet.set(true);
				} else {
					this.lbStatusbarStreet.setText("Invalid - Streetname is too short");
					this.checkBoxStreet.setSelected(false);
					isValidStreet.set(false);
				}
			} else {
				this.lbStatusbarStreet.setText("Invalid - Inputfield is empty");
				this.checkBoxStreet.setSelected(false);
				isValidStreet.set(false);
			}
			setControlDisability(isValidBaseName.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});
	}

	private void setControlDisability(boolean baseName, boolean houseNr, boolean place, boolean postCode,
			boolean street) {
		if (baseName && houseNr && place && postCode && street) {
			this.controllerCreateBase.setButtonNextDisability(false);
			this.controllerCreateBase.setButtonFinishDisability(false);
			this.controllerCreateBase.setTabOperationVehicleDisability(false);
		} else {
			this.controllerCreateBase.setButtonFinishDisability(true);
			this.controllerCreateBase.setButtonNextDisability(true);
			this.controllerCreateBase.setTabOperationVehicleDisability(true);
		}
	}

	public Base getCreatedBaseData() {
		return new Base(0, this.tfBaseName.getText(), this.tfPlace.getText(),
				Integer.parseInt(this.tfPostCode.getText()), this.tfStreet.getText(), this.tfHouseNr.getText());
	}
}