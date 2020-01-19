package handler;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
import controller.ControllerUpdateFullBaseDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loader.BaseVehicleLoader;
import loader.MemberByBaseIdLoader;
import loader.MemberLoader;
import loader.OperationLoader;
import loader.OperationVehicleByBaseIdLoader;
import loader.OperationVehicleLoader;
import manager.MemberManager;
import manager.OperationVehicleManager;
import threadHelper.MemberUpdateHandler;
import threadHelper.OperationVehicleUpdateHandler;

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
				this.setUpdatedBase(updatedBaseData);
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
				updatedOperationVehicle.setBase(this.currBaseToUpdate);
				updatedOperationVehicle.setBaseId(this.currBaseToUpdate.getBaseId());
				this.setUpdatedOperationVehicle(updatedOperationVehicle);
				
				CountDownLatch countDownLatch = new CountDownLatch(3);
				
				OperationVehicleUpdateHandler operationVehicleUpdateLoader = new OperationVehicleUpdateHandler(countDownLatch, updatedOperationVehicle, this.currBaseToUpdate.getBaseId());
				OperationVehicleByBaseIdLoader operationVehicleByBaseIdLoader = new OperationVehicleByBaseIdLoader(countDownLatch, this.currBaseToUpdate.getBaseId(), updatedOperationVehicle.getBaseId());
				OperationVehicleLoader operationVehicleLoader = new OperationVehicleLoader(countDownLatch);
				
				Thread threadOperationVehicleUpdateLoader = new Thread(operationVehicleUpdateLoader);
				Thread threadOperationVehicleByBaseIdLoader = new Thread(operationVehicleByBaseIdLoader);
				Thread threadOperationVehicleLoader = new Thread(operationVehicleLoader);
				
				threadOperationVehicleUpdateLoader.start();
				threadOperationVehicleByBaseIdLoader.start();
				threadOperationVehicleLoader.start();
				
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
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
					updatedMember.setBase(this.currBaseToUpdate);
					updatedMember.setBaseId(this.currBaseToUpdate.getBaseId());
				} else {
					updatedMember.setBase(null);
					updatedMember.setBaseId(-1);
				}
				updatedMember.setPassword(selectedMember.getPassword());
				
				this.setUpdatedMember(updatedMember);
				
				/*CountDownLatch countDownLatch = new CountDownLatch(3);
				
				MemberUpdateLoader memberUpdateLoader = new MemberUpdateLoader(countDownLatch, updatedMember, this.currBaseToUpdate.getBaseId());
				MemberByBaseIdLoader memberByBaseIdLoader = new MemberByBaseIdLoader(countDownLatch, this.currBaseToUpdate.getBaseId(), updatedMember.getMemberId());
				MemberLoader operationVehicleLoader = new MemberLoader(countDownLatch);
				
				Thread threadMemberUpdateLoader = new Thread(memberUpdateLoader);
				Thread threadMemberByBaseIdLoader = new Thread(memberByBaseIdLoader);
				Thread threadMemberLoader = new Thread(operationVehicleLoader);
				
				threadMemberUpdateLoader.start();
				threadMemberByBaseIdLoader.start();
				//threadMemberLoader.start();
				
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/	
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
	
	public void setUpdatedBase(Base updatedBaseData) {
		this.updatedBaseData = updatedBaseData;
	}
	
	public Base getUpdatedBase() {
		return this.updatedBaseData;
	}
	
	public OperationVehicle getUpdatedOperationVehicle() {
		return this.updatedOperationVehicleData;
	}
	
	public void setUpdatedOperationVehicle(OperationVehicle updatedOperationVehicle) {
		this.updatedOperationVehicleData = updatedOperationVehicle;
	}
	
	public Member getUpdatedMember() {
		return this.updatedMemberData;
	}
	
	public void setUpdatedMember(Member updatedMemberData) {
		this.updatedMemberData = updatedMemberData;
	}

	public void setCurrBaseToUpdate(Base selectedBase) {
		this.currBaseToUpdate = selectedBase;
	}
	
	public Base getCurrBaseToUpdate() {
		return this.currBaseToUpdate;
	}
}