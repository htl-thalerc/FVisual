package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import bll.Base;
import bll.OperationVehicle;

public class ControllerDialogSaveBase implements Initializable {
	@FXML
	private TextField tfBaseName;
	@FXML
	private TextField tfPostcodePlace;
	@FXML
	private TextField tfAddress;
	@FXML
	private ListView<OperationVehicle> lvVehicleData;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSave;

	private ControllerCreateBase controllerCreateBase = null;
	private boolean buttonState = false;

	public ControllerDialogSaveBase(ControllerCreateBase controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTextFieldDisability();
	}

	private void initTextFieldDisability() {
		this.tfBaseName.setDisable(true);
		this.tfPostcodePlace.setDisable(true);
		this.tfAddress.setDisable(true);
	}

	@FXML
	private void onClickBtnCancel(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.buttonState = false;
		stage.close();
	}

	@FXML
	private void onClickBtnSave(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.buttonState = true;
		stage.close();
	}

	public void setData(Base createdBase, List<OperationVehicle> collOfCreatedVehicles) {
		this.tfBaseName.setText(createdBase.getName());
		this.tfPostcodePlace.setText(createdBase.getPostCode() + ", " + createdBase.getPlace());
		this.tfAddress.setText(createdBase.getStreet() + ", " + createdBase.getHouseNr());

		ObservableList<OperationVehicle> obsListOfVehicles = FXCollections.observableArrayList(collOfCreatedVehicles);
		this.lvVehicleData.setItems(obsListOfVehicles);
	}

	public boolean getButtonState() {
		return this.buttonState;
	}
}