package handler;

import java.io.IOException;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
import controller.ControllerUpdateFullBaseDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.MemberManager;
import manager.OperationVehicleManager;

public class CentralUpdateHandler {
	private static CentralUpdateHandler helper;
	private Base updatedBaseData;
	private OperationVehicle updatedOperationVehicleData;
	private Member updatedMemberData;
	
	private Base currBaseToUpdate = null;

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
				OperationVehicleManager.getInstance().updateVehicleFromBase(updatedOperationVehicle.getOperationVehicleId(), updatedOperationVehicle);
			}
		}
	}
	
	public void initUpdateMemberDialog(Member selectedMember) {
		Stage stage = this.initUpdateFullBaseDialog();
		this.controllerUpdateFullBaseDialog.selectTabUpdateMember();
		this.controllerUpdateFullBaseDialog.setOldMemberData(selectedMember);
		stage.showAndWait();
		
		if(this.controllerUpdateFullBaseDialog.getBtnSaveState()) {
			Member updatedMember = this.controllerUpdateFullBaseDialog.getUpdatedMemberData();
			if(updatedMember != null) {
				updatedMember.setMemberId(selectedMember.getMemberId());
				updatedMember.setUsername(updatedMember.getLastname().substring(0, 4).toLowerCase() + updatedMember.getFirstname().substring(0, 1).toLowerCase());
				updatedMember.setAdmin(selectedMember.isAdmin());
				if(selectedMember.getBase() != null) {
					updatedMember.setBase(selectedMember.getBase());
				} else {
					updatedMember.setBase(null);
				}
				updatedMember.setPassword(selectedMember.getPassword());
				this.setUpdatedMemberData(updatedMember);
				System.out.println(selectedMember.getBase());
				MemberManager.getInstance().updateMemberFromBase(selectedMember.getBase().getBaseId(), updatedMember);
			}
		}
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
	
	public OperationVehicle getUpdatedOperationVehicleData() {
		return this.updatedOperationVehicleData;
	}
	
	private void setUpdatedOperationVehicleData(OperationVehicle updatedOperationVehicle) {
		this.updatedOperationVehicleData = updatedOperationVehicle;
	}
	
	public Member getUpdatedMemberData() {
		return this.updatedMemberData;
	}
	
	public void setUpdatedMemberData(Member updatedMemberData) {
		this.updatedMemberData = updatedMemberData;
	}

	public void setCurrBaseToUpdate(Base selectedBase) {
		this.currBaseToUpdate = selectedBase;
	}
	
	public Base getCurrBaseToUpdate() {
		return this.currBaseToUpdate;
	}
}