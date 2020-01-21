package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import bll.Base;
import bll.EnumCRUDOption;
import bll.Member;
import bll.OperationVehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import handler.BaseHandler;
import handler.CentralHandler;
import threadHelper.BasePostHandler;
import threadHelper.MemberPostHandler;
import threadHelper.MemberUpdateHandler;
import threadHelper.OperationVehiclePostHandler;

public class ControllerCreateBaseManagement implements Initializable {
	@FXML
	private Button btnReset, btnFinish, btnBack, btnNext;
	@FXML
	private TabPane tabPaneBase;
	@FXML
	private Label lbStatusbar;

	private ControllerBaseManagement controllerBaseManagement;
	private ControllerCreateBaseTabBaseManagement controllerCreateBaseTabBaseManagement;
	private ControllerCreateBaseTabOperationVehicleManagement controllerCreateBaseTabOperationVehicleManagement;
	private ControllerCreateBaseTabMemberManagement controllerCreateBaseTabMemberManagement;

	private Tab tabBaseManagement, tabOperationVehicleManagement, tabMemberManagement;

	private Base createdBase = null;

	public ControllerCreateBaseManagement(ControllerBaseManagement controllerBaseManagement) {
		this.controllerBaseManagement = controllerBaseManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initPaneBase();
		this.initPaneOperationVehicle();
		this.initPaneMember();
		this.initTabPaneListeners();
		this.initDisability();
	}

	private void initDisability() {
		this.tabOperationVehicleManagement.setDisable(true);
		this.tabMemberManagement.setDisable(true);
		this.btnReset.setDisable(true);
		this.btnBack.setDisable(true);
		this.btnNext.setDisable(true);
		this.btnFinish.setDisable(true);
	}

	private void initPaneBase() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateBaseTabBaseManagement.fxml");
		this.tabBaseManagement = new Tab();
		this.tabBaseManagement.setText("Base Management");
		this.controllerCreateBaseTabBaseManagement = new ControllerCreateBaseTabBaseManagement(this);
		loader.setController(this.controllerCreateBaseTabBaseManagement);

		try {
			this.tabBaseManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneBase.getTabs().add(this.tabBaseManagement);
	}

	private void initPaneOperationVehicle() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateBaseTabOperationVehicleManagement.fxml");
		this.tabOperationVehicleManagement = new Tab();
		this.tabOperationVehicleManagement.setText("Operationvehicle Management");
		this.controllerCreateBaseTabOperationVehicleManagement = new ControllerCreateBaseTabOperationVehicleManagement(
				this);
		loader.setController(this.controllerCreateBaseTabOperationVehicleManagement);

		try {
			this.tabOperationVehicleManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneBase.getTabs().add(this.tabOperationVehicleManagement);
	}

	private void initPaneMember() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateBaseTabMemberManagement.fxml");
		this.tabMemberManagement = new Tab();
		this.tabMemberManagement.setText("Member Management");
		this.controllerCreateBaseTabMemberManagement = new ControllerCreateBaseTabMemberManagement(this);
		loader.setController(this.controllerCreateBaseTabMemberManagement);

		try {
			this.tabMemberManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneBase.getTabs().add(this.tabMemberManagement);
	}

	private void initTabPaneListeners() {
		this.btnReset.setDisable(false);
		this.tabBaseManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(true);
			this.btnNext.setDisable(false);
		});

		this.tabOperationVehicleManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(false);
		});

		this.tabMemberManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(true);
		});
	}

	@FXML
	private void onClickBtnReset(ActionEvent aE) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Input Fields");
		alert.setHeaderText("Would you really reset your Basecreation");
		alert.setContentText("Note: All your Inputs are not going to be saved");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.resetCreateBaseTabs();
			alert.close();
			this.tabPaneBase.getSelectionModel().select(this.tabBaseManagement);
			this.btnReset.setDisable(true);
			this.btnNext.setDisable(true);
		} else {
			alert.close();
		}
	}

	public void resetCreateBaseTabs() {
		this.tabPaneBase.getSelectionModel().select(this.tabBaseManagement);
		this.controllerCreateBaseTabBaseManagement.reset();
		this.controllerCreateBaseTabOperationVehicleManagement.reset();
		this.controllerCreateBaseTabMemberManagement.reset();
	}

	@FXML
	private void onClickBtnBack(ActionEvent aE) {
		if (this.tabOperationVehicleManagement.isSelected()) {
			this.tabPaneBase.getSelectionModel().select(this.tabBaseManagement);
		} else if (this.tabMemberManagement.isSelected()) {
			this.tabPaneBase.getSelectionModel().select(this.tabOperationVehicleManagement);
		}
	}

	@FXML
	private void onClickBtnNext(ActionEvent aE) {
		this.setCreatedBase(this.controllerCreateBaseTabBaseManagement.getCreatedBaseData());
		if (this.tabBaseManagement.isSelected()) {
			this.tabPaneBase.getSelectionModel().select(this.tabOperationVehicleManagement);
		} else if (this.tabOperationVehicleManagement.isSelected()) {
			this.tabPaneBase.getSelectionModel().select(this.tabMemberManagement);
		}
	}

	@FXML
	private void onClickBtnFinish(ActionEvent aE) {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/BaseManagementDialog.fxml");

		ControllerBaseManagementDialog controllerDialogSaveBase = new ControllerBaseManagementDialog(this,
				EnumCRUDOption.POST);
		loader.setController(controllerDialogSaveBase);

		this.setCreatedBase(this.controllerCreateBaseTabBaseManagement.getCreatedBaseData());
		Base baseToCreate = this.getCreatedBase();
		List<OperationVehicle> collOfOperationVehiclesToAddToBase = this.controllerCreateBaseTabOperationVehicleManagement
				.getOperationVehcilesToCreate();
		List<Member> collOfMembersToAddToBase = this.controllerCreateBaseTabMemberManagement.getMembersToCreate();

		try {
			Stage curStage = new Stage();
			Scene scene = new Scene(loader.load());
			curStage.setScene(scene);
			curStage.initModality(Modality.APPLICATION_MODAL);
			curStage.setTitle("Would you like to save your created base");
			controllerDialogSaveBase.setBaseData(baseToCreate);
			controllerDialogSaveBase.setListViewOperationVehicleData(collOfOperationVehiclesToAddToBase);
			controllerDialogSaveBase.setListViewMemberData(collOfMembersToAddToBase);
			curStage.showAndWait();
			if (controllerDialogSaveBase.getButtonState()) {
				Thread threadBasePostLoader = new Thread(new BasePostHandler(baseToCreate));
				threadBasePostLoader.start();
				threadBasePostLoader.join();
				Thread.sleep(250);

				if (collOfOperationVehiclesToAddToBase.size() >= 1) {
					for (int i = 0; i < collOfOperationVehiclesToAddToBase.size(); i++) {
						if (collOfOperationVehiclesToAddToBase.get(i).getOperationVehicleId() == -1) {
							// Create Vehicle
							OperationVehicle vehicleToCreate = collOfOperationVehiclesToAddToBase.get(i);
							vehicleToCreate = this
									.setBaseObjAndBaseIdToVehicle(collOfOperationVehiclesToAddToBase.get(i));

							Thread threadCreateOperationVehicle = new Thread(
									new OperationVehiclePostHandler(vehicleToCreate));
							threadCreateOperationVehicle.start();
							threadCreateOperationVehicle.join();
							Thread.sleep(250);
						} else if (collOfOperationVehiclesToAddToBase.get(i).getOperationVehicleId() != -1) {
							// Update Vehicle not right bec. we dont change id; we reate a new vehcile
							OperationVehicle vehicleToCreateFromExistingVehicleData = collOfOperationVehiclesToAddToBase
									.get(i);
							vehicleToCreateFromExistingVehicleData.setBase(null);
							vehicleToCreateFromExistingVehicleData.setBaseId(-1);
							vehicleToCreateFromExistingVehicleData = this
									.setBaseObjAndBaseIdToVehicle(collOfOperationVehiclesToAddToBase.get(i));

							Thread threadCreateOperationVehicle = new Thread(
									new OperationVehiclePostHandler(vehicleToCreateFromExistingVehicleData));
							threadCreateOperationVehicle.start();
							threadCreateOperationVehicle.join();
							Thread.sleep(250);
						}
					}
				}

				if (collOfMembersToAddToBase.size() >= 1) {
					for (int i = 0; i < collOfMembersToAddToBase.size(); i++) {
						if (collOfMembersToAddToBase.get(i).getBaseId() == 0
								&& collOfMembersToAddToBase.get(i).getMemberId() != -1) {
							// Members with no base
							Member memberToUpdate = collOfMembersToAddToBase.get(i);
							memberToUpdate.setRankId(memberToUpdate.getRank().getRankId());
							memberToUpdate = this.setBaseObjAndBaseIdToMember(collOfMembersToAddToBase.get(i));

							Thread threadUpdateMember = new Thread(new MemberUpdateHandler(memberToUpdate));
							threadUpdateMember.start();
							threadUpdateMember.join();
							Thread.sleep(250);
						} else if (collOfMembersToAddToBase.get(i).getBaseId() == -1
								&& collOfMembersToAddToBase.get(i).getMemberId() == -1) {
							// Complete new Member
							Member memberToCreate = collOfMembersToAddToBase.get(i);
							memberToCreate.setRankId(memberToCreate.getRank().getRankId());
							memberToCreate = this.setBaseObjAndBaseIdToMember(collOfMembersToAddToBase.get(i));

							Thread threadCreateMember = new Thread(new MemberPostHandler(memberToCreate));
							threadCreateMember.start();
							threadCreateMember.join();
							Thread.sleep(250);
						}
					}

					// Update GUI
					this.resetCreateBaseTabs();
					this.controllerBaseManagement.relaodBaseLookup();
				}
			}
		} catch (final IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Member setBaseObjAndBaseIdToMember(Member currMember) {
		Base createdBase = BaseHandler.getInstance().getCreatedBase();

		if (createdBase.getBaseId() != 0 && createdBase != null) {
			currMember.setBase(createdBase);
			currMember.setBaseId(createdBase.getBaseId());
			return currMember;
		} else {
			return null;
		}
	}

	private OperationVehicle setBaseObjAndBaseIdToVehicle(OperationVehicle currVehicle) {
		Base createdBase = BaseHandler.getInstance().getCreatedBase();

		if (createdBase.getBaseId() != 0 && createdBase != null) {
			currVehicle.setBase(createdBase);
			currVehicle.setBaseId(createdBase.getBaseId());
			return currVehicle;
		} else {
			return null;
		}
	}

	public void setButtonResetDisability(boolean isDisabled) {
		this.btnReset.setDisable(isDisabled);
	}

	public void setButtonNextDisability(boolean isDisabled) {
		this.btnNext.setDisable(isDisabled);
	}

	public void setButtonFinishDisability(boolean isDisabled) {
		this.btnFinish.setDisable(isDisabled);
	}

	public void setButtonBackDisability(boolean isDisabled) {
		this.btnBack.setDisable(isDisabled);
	}

	public void setAllOptionButtonsDisability(boolean isDisabled) {
		this.setButtonResetDisability(isDisabled);
		this.setButtonBackDisability(isDisabled);
		this.setButtonNextDisability(isDisabled);
		this.setButtonFinishDisability(isDisabled);
	}

	public void setTabOperationVehicleManagementDisability(boolean isDiabled) {
		this.tabOperationVehicleManagement.setDisable(isDiabled);
	}

	public void setTabMemberManagementDisability(boolean isDisabled) {
		this.tabMemberManagement.setDisable(isDisabled);
	}

	private void setCreatedBase(Base createdBase) {
		this.createdBase = createdBase;
	}

	public void setStatusbarValue(String text) {
		this.lbStatusbar.setText(text);
	}

	public Base getCreatedBase() {
		return this.createdBase;
	}
}