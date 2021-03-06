package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
import handler.CentralHandler;
import handler.CentralUpdateHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ControllerUpdateFullBaseDialog implements Initializable {
	@FXML private TabPane tabPaneUpdateTabs;
	@FXML private Tab tabUpdateBase, tabUpdateMember, tabUpdateOperationVehicle;
	@FXML private Button btnCancelUpdate, btnSaveUpdate, btnBackUpdate, btnNextUpdate;
	
	private boolean btnSaveState;
	
	private ControllerUpdateTabBase controllerUpdateTabBase;
	private ControllerUpdateTabOperationVehicle controllerUpdateTabOperationVehicle;
	private ControllerUpdateTabMember controllerUpdateTabMember;
	private CentralUpdateHandler centralUpdateHandler;
	private boolean isSingleUpdate;
	
	public ControllerUpdateFullBaseDialog(CentralUpdateHandler centralUpdateHandler, boolean isSingleUpdate) {
		this.centralUpdateHandler = centralUpdateHandler;
		this.isSingleUpdate = isSingleUpdate;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setTabUpdateBaseContent();
		this.setTabUpdateOperationVehicleContent();
		this.setTabUpdateMemberContent();
		this.initTabListeners();
	}
	
	private void setTabUpdateBaseContent() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/UpdateTabBase.fxml");
		this.controllerUpdateTabBase = new ControllerUpdateTabBase(this);
		loader.setController(this.controllerUpdateTabBase);

		try {
			this.tabUpdateBase.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setTabUpdateOperationVehicleContent() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/UpdateTabOperationVehicle.fxml");
		this.controllerUpdateTabOperationVehicle = new ControllerUpdateTabOperationVehicle(this, this.isSingleUpdate);
		loader.setController(this.controllerUpdateTabOperationVehicle);

		try {
			this.tabUpdateOperationVehicle.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setTabUpdateMemberContent() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/UpdateTabMember.fxml");
		this.controllerUpdateTabMember = new ControllerUpdateTabMember(this, this.isSingleUpdate);
		loader.setController(this.controllerUpdateTabMember);

		try {
			this.tabUpdateMember.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initTabListeners() {
		this.btnBackUpdate.setDisable(true);
		if(this.tabPaneUpdateTabs.getSelectionModel().getSelectedItem().equals(this.tabUpdateBase)) {
			this.tabUpdateBase.setOnSelectionChanged(event -> {
				this.btnBackUpdate.setDisable(true);
				this.btnNextUpdate.setDisable(false);
			});
			this.tabUpdateOperationVehicle.setOnSelectionChanged(event -> {
				this.btnBackUpdate.setDisable(false);
				this.btnNextUpdate.setDisable(false);
			});
			this.tabUpdateMember.setOnSelectionChanged(event -> {
				this.btnBackUpdate.setDisable(false);
				this.btnNextUpdate.setDisable(true);
			});
		} else {
			this.btnBackUpdate.setDisable(true);
			this.btnNextUpdate.setDisable(true);
		}
	}
	
	public void selectTabUpdateBase() {
		this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateBase);
	}
	
	public void selectTabUpdateOperationVehicle() {
		this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateOperationVehicle);
		this.tabUpdateBase.setDisable(true);
		this.tabUpdateMember.setDisable(true);
		this.btnBackUpdate.setDisable(true);
		this.btnNextUpdate.setDisable(true);
	}

	public void selectTabUpdateMember() {
		this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateMember);
		this.tabUpdateBase.setDisable(true);
		this.tabUpdateOperationVehicle.setDisable(true);
		this.btnBackUpdate.setDisable(true);
		this.btnNextUpdate.setDisable(true);
	}
	
	@FXML
	private void onClickBtnCancelUpdate(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.btnSaveState = false;
		stage.close();
	}
	
	@FXML
	private void onClickBtnBackUpdate(ActionEvent aE) {
		if(this.tabUpdateOperationVehicle.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateBase);
		} else if(this.tabUpdateMember.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateOperationVehicle);
		}
	}
	
	@FXML
	private void onClickBtnNextUpdate(ActionEvent aE) {
		if(this.tabUpdateBase.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateOperationVehicle);
		} else if(this.tabUpdateOperationVehicle.isSelected()) {
			this.tabPaneUpdateTabs.getSelectionModel().select(this.tabUpdateMember);
		}
	}
	
	@FXML
	private void onClickBtnSaveUpdate(ActionEvent aE) {
		Stage stage = (Stage) ((Button) aE.getSource()).getScene().getWindow();
		this.btnSaveState = true;
		stage.close();
	}
	
	public boolean getBtnSaveState() {
		return this.btnSaveState;
	}
	
	public Base getUpdatedBaseData() {
		return this.controllerUpdateTabBase.getNewBaseData();
	}
	
	public void setOldBaseData(Base oldBaseData) {
		this.controllerUpdateTabBase.setBaseData(oldBaseData);
	}
	
	public ArrayList<OperationVehicle> getListOfNewOperationVehicles() {
		return this.controllerUpdateTabOperationVehicle.getListOfNewOperationVehicles();
	}

	public void setListOfOperationVehicles(ArrayList<OperationVehicle> oldOperationVehicleData) {
		this.controllerUpdateTabOperationVehicle.setListOfOperationVehicles(oldOperationVehicleData);
	}
	
	public ArrayList<Member> getListOfNewMembers() {
		return this.controllerUpdateTabMember.getNewMemberData();
	}

	public void setListOfMembers(ArrayList<Member> oldMemberData) {
		this.controllerUpdateTabMember.setListOfMembers(oldMemberData);
	}
	
	public void setSaveBtnDisability(boolean isDisable) {
		this.btnSaveUpdate.setDisable(isDisable);
	}
}