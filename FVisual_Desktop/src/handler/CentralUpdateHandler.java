package handler;

import java.io.IOException;

import bll.Base;
import bll.OperationVehicle;
import controller.ControllerUpdateFullBaseDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CentralUpdateHandler {
	private static CentralUpdateHandler helper;
	private Base updatedBaseData;
	private OperationVehicle updatedOperationVehicleData;

	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog;
	
	public static CentralUpdateHandler getInstance() {
		if (helper == null) {
			helper = new CentralUpdateHandler();
		}
		return helper;
	}
	
	public void initUpdateBaseDialog(Base selectedBase) {
		Stage stage = this.initUpdateFullBaseDialog();
		this.controllerUpdateFullBaseDialog.selectTabUpdateBase();
		this.controllerUpdateFullBaseDialog.setOldBaseData(selectedBase);
		/*this.controllerUpdateFullBaseDialog.setOldOperationVehicleData();
		this.controllerUpdateFullBaseDialog.setOldMemberData();*/
		stage.showAndWait();
		
		if(this.controllerUpdateFullBaseDialog.getBtnSaveState()) {
			Base updatedBaseData = this.controllerUpdateFullBaseDialog.getUpdatedBaseData();
			if(updatedBaseData != null) {
				updatedBaseData.setBaseId(selectedBase.getBaseId());
				this.setUpdatedBaseData(updatedBaseData);
				System.out.println(updatedBaseData.toString());
			}
		}
	}
	
	public void initUpdateOperationVehicleDialog(OperationVehicle selectedVehicle) {
		Stage stage = this.initUpdateFullBaseDialog();
		this.controllerUpdateFullBaseDialog.selectTabUpdateOperationVehicle();
		this.controllerUpdateFullBaseDialog.setOldOperationVehicleData(selectedVehicle);
		stage.showAndWait();
		
		if(this.controllerUpdateFullBaseDialog.getBtnSaveState()) {
			OperationVehicle updatedOperationVehicle = this.controllerUpdateFullBaseDialog.getUpdatedOperationVehicleData();
			if(updatedOperationVehicle != null) {
				updatedOperationVehicle.setOperationVehicleId(selectedVehicle.getOperationVehicleId());
				this.setUpdatedOperationVehicleData(updatedOperationVehicle);
				System.out.println(updatedOperationVehicle);
			}
		}
	}

	public void initUpdateMemberDialog() {
		this.controllerUpdateFullBaseDialog.selectTabUpdateMember();
	}

	public Stage initUpdateFullBaseDialog() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/UpdateFullBaseDialog.fxml");
		Stage curStage = null;
		this.controllerUpdateFullBaseDialog = new ControllerUpdateFullBaseDialog(this);
		loader.setController(this.controllerUpdateFullBaseDialog);

		try {
			curStage = new Stage();
			Scene scene = new Scene(loader.load());
			curStage.setScene(scene);
			curStage.initModality(Modality.APPLICATION_MODAL);
			curStage.setTitle("Update Base");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return curStage;
	}
	
	public void setUpdatedBaseData(Base updatedBaseData) {
		this.updatedBaseData = updatedBaseData;
	}
	
	public Base getUpdatedBaseData() {
		return this.updatedBaseData;
	}
	
	private void setUpdatedOperationVehicleData(OperationVehicle updatedOperationVehicle) {
		this.updatedOperationVehicleData = updatedOperationVehicle;
	}
	
	public OperationVehicle getUpdatedOperationVehicleData() {
		return this.updatedOperationVehicleData;
	}
}
