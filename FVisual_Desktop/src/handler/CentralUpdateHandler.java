package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
import controller.ControllerUpdateFullBaseDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loader.BaseByBaseIdLoader;
import loader.BaseMemberLoader;
import loader.BaseVehicleLoader;
import loader.BaselessMemberLoader;
import loader.MemberByBaseIdLoader;
import loader.MemberLoader;
import loader.OperationLoader;
import loader.OperationVehicleByBaseIdLoader;
import loader.OperationVehicleLoader;
import manager.MemberManager;
import manager.OperationVehicleManager;
import threadHelper.BaseUpdateHandler;
import threadHelper.MemberPostHandler;
import threadHelper.MemberUpdateHandler;
import threadHelper.OperationVehiclePostHandler;
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
		Stage stage = this.initUpdateFullBaseDialog(false);
		this.controllerUpdateFullBaseDialog.selectTabUpdateBase();
		this.controllerUpdateFullBaseDialog.setOldBaseData(selectedBase);

		// Thread threadBaseByBaseId = new Thread(new BaseByBaseIdLoader(null,
		// selectedBase.getBaseId()));
		// threadBaseByBaseId.start();
		// threadBaseByBaseId.join();
		// Thread.sleep(250);

		try {
			Thread threadVehcilesByBaseId = new Thread(new BaseVehicleLoader(selectedBase));
			threadVehcilesByBaseId.start();
			threadVehcilesByBaseId.join();
			Thread.sleep(250);

			Thread threadMembersByBaseId = new Thread(new BaseMemberLoader(selectedBase));
			threadMembersByBaseId.start();
			threadMembersByBaseId.join();
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.controllerUpdateFullBaseDialog.setListOfOperationVehicles(new ArrayList<OperationVehicle>());
		this.controllerUpdateFullBaseDialog.setListOfMembers(new ArrayList<Member>());
		stage.showAndWait();

		if (this.controllerUpdateFullBaseDialog.getBtnSaveState()) {
			Base updatedBaseData = this.controllerUpdateFullBaseDialog.getUpdatedBaseData();
			ArrayList<Member> listOfUpdatedMembers = this.controllerUpdateFullBaseDialog.getListOfNewMembers();
			ArrayList<OperationVehicle> listOfUpdatedVehicles = this.controllerUpdateFullBaseDialog
					.getListOfNewOperationVehicles();
			try {
				//update base
				if (updatedBaseData != null) {
					updatedBaseData.setBaseId(selectedBase.getBaseId());
					this.setUpdatedBase(updatedBaseData);
					
					System.out.println(updatedBaseData.toString());
					
					Thread threadUpdateBase = new Thread(new BaseUpdateHandler(updatedBaseData));
					threadUpdateBase.start();
					threadUpdateBase.join();
					Thread.sleep(250);
				}
				
				//Update member
				for (int i = 0; i < listOfUpdatedMembers.size(); i++) {
					listOfUpdatedMembers.get(i).setBaseId(listOfUpdatedMembers.get(i).getBase().getBaseId());
					listOfUpdatedMembers.get(i).setRankId(listOfUpdatedMembers.get(i).getRank().getRankId());
					
					if (listOfUpdatedMembers.get(i).getMemberId() == -1) {
						Thread threadPostMember = new Thread(new MemberPostHandler(listOfUpdatedMembers.get(i)));
						threadPostMember.start();
						threadPostMember.join();
						Thread.sleep(250);
					} else {
						Thread threadUpdateMember = new Thread(new MemberUpdateHandler(listOfUpdatedMembers.get(i)));
						threadUpdateMember.start();
						threadUpdateMember.join();
						Thread.sleep(250);
					}
				}
				
				//Update vehicle
				for (int i = 0; i < listOfUpdatedVehicles.size(); i++) {
					if (listOfUpdatedVehicles.get(i).getOperationVehicleId() == -1) {
						Thread threadPostVehicle = new Thread(
								new OperationVehiclePostHandler(listOfUpdatedVehicles.get(i)));
						threadPostVehicle.start();
						threadPostVehicle.join();
						Thread.sleep(250);
					} else {
						Thread threadVehicleUpdaterHandler = new Thread(new OperationVehicleUpdateHandler(
								listOfUpdatedVehicles.get(i), this.currBaseToUpdate.getBaseId()));
						threadVehicleUpdaterHandler.start();
						threadVehicleUpdaterHandler.join();
						Thread.sleep(250);
					}
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void initUpdateOperationVehicleDialog(OperationVehicle selectedVehicle) {
		ArrayList<OperationVehicle> tempList = new ArrayList<OperationVehicle>();
		tempList.add(selectedVehicle);
		Stage stage = this.initUpdateFullBaseDialog(true);
		this.controllerUpdateFullBaseDialog.selectTabUpdateOperationVehicle();
		this.controllerUpdateFullBaseDialog.setListOfOperationVehicles(tempList);
		stage.showAndWait();

		if (this.controllerUpdateFullBaseDialog.getBtnSaveState()) {
			ArrayList<OperationVehicle> updatedOperationVehicles = this.controllerUpdateFullBaseDialog
					.getListOfNewOperationVehicles();
			OperationVehicle updatedOperationVehicle = updatedOperationVehicles.get(0);
			if (updatedOperationVehicle != null) {
				updatedOperationVehicle.setOperationVehicleId(selectedVehicle.getOperationVehicleId());
				updatedOperationVehicle.setBase(this.currBaseToUpdate);
				updatedOperationVehicle.setBaseId(this.currBaseToUpdate.getBaseId());
				this.setUpdatedOperationVehicle(updatedOperationVehicle);

				try {
					Thread threadVehicleUpdaterHandler = new Thread(new OperationVehicleUpdateHandler(
							updatedOperationVehicle, this.currBaseToUpdate.getBaseId()));

					threadVehicleUpdaterHandler.start();
					threadVehicleUpdaterHandler.join();

					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void initUpdateMemberDialog(Member selectedMember) {
		ArrayList<Member> tempList = new ArrayList<Member>();
		tempList.add(selectedMember);
		Stage stage = this.initUpdateFullBaseDialog(true);
		this.controllerUpdateFullBaseDialog.selectTabUpdateMember();
		this.controllerUpdateFullBaseDialog.setListOfMembers(tempList);
		stage.showAndWait();

		if (this.controllerUpdateFullBaseDialog.getBtnSaveState()) {
			ArrayList<Member> updatedMembers = this.controllerUpdateFullBaseDialog.getListOfNewMembers();
			Member updatedMember = updatedMembers.get(0);
			
			if (updatedMember != null) {
				updatedMember.setMemberId(selectedMember.getMemberId());
				updatedMember.setUsername(updatedMember.getLastname().substring(0, 4).toLowerCase()
						+ updatedMember.getFirstname().substring(0, 1).toLowerCase());
				updatedMember.setAdmin(selectedMember.isAdmin());
				if (selectedMember.getBase() != null) {
					updatedMember.setBase(this.currBaseToUpdate);
					updatedMember.setBaseId(this.currBaseToUpdate.getBaseId());
				} else {
					updatedMember.setBase(null);
					updatedMember.setBaseId(-1);
				}
				updatedMember.setPassword(selectedMember.getPassword());

				this.setUpdatedMember(updatedMember);

				try {
					Thread threadUpdateHandler = new Thread(new MemberUpdateHandler(updatedMember));

					threadUpdateHandler.start();
					threadUpdateHandler.join();
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Stage initUpdateFullBaseDialog(boolean isSingleUpdate) {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/UpdateFullBaseDialog.fxml");
		Stage curStage = null;
		this.controllerUpdateFullBaseDialog = new ControllerUpdateFullBaseDialog(this, isSingleUpdate);
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