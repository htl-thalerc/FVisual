package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import bll.Base;
import bll.EnumCRUDOption;
import bll.Member;
import bll.Operation;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import handler.CentralHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import threadHelper.PostFullBaseTask;
import threadHelper.PostFullOperationTask;

public class ControllerCreateOperationManagement implements Initializable {
	@FXML
	private TabPane tabPaneOperation;
	@FXML
	private Button btnReset, btnBack, btnNext, btnFinish;
	@FXML
	private Label lbStatusbar;

	private Tab tabOperationManagement, tabOperationVehicleManagement, tabOtherOrgManagement, tabMemberManagement;

	private ControllerCreateOperationTabOperationManagement controllerCreateOperationTabOperationManagement;
	private ControllerCreateOperationTabOperationVehicleManagement controllerCreateOperationTabOperationVehicleManagement;
	private ControllerCreateOperationTabOtherOrganisationManagement controllerCreateOperationTabOtherOrganisationManagement;
	private ControllerCreateOperationTabMemberManagement controllerCreateOperationTabMemberManagement;
	private ControllerOperationManagement controllerOperationManagement;

	private Operation createdOperation = null;

	public ControllerCreateOperationManagement(ControllerOperationManagement controllerOperationManagement) {
		this.controllerOperationManagement = controllerOperationManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initPaneOperation();
		this.initPaneOperationVehicle();
		this.initPaneOtherOrg();
		this.initPaneMember();
		this.initTabPaneListeners();
		this.initDisability();
	}

	private void initPaneOperation() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabOperationManagement.fxml");
		this.tabOperationManagement = new Tab();
		this.tabOperationManagement.setText("Operation Management");
		this.controllerCreateOperationTabOperationManagement = new ControllerCreateOperationTabOperationManagement(
				this);
		loader.setController(this.controllerCreateOperationTabOperationManagement);

		try {
			this.tabOperationManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabOperationManagement);
	}

	private void initPaneOperationVehicle() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabOperationVehicleManagement.fxml");
		this.tabOperationVehicleManagement = new Tab();
		this.tabOperationVehicleManagement.setText("Operation Vehicle Management");
		this.controllerCreateOperationTabOperationVehicleManagement = new ControllerCreateOperationTabOperationVehicleManagement(
				this);
		loader.setController(this.controllerCreateOperationTabOperationVehicleManagement);

		try {
			this.tabOperationVehicleManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabOperationVehicleManagement);
	}

	private void initPaneOtherOrg() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabOtherOrganisationManagement.fxml");
		this.tabOtherOrgManagement = new Tab();
		this.tabOtherOrgManagement.setText("Other Organisation Management");
		this.controllerCreateOperationTabOtherOrganisationManagement = new ControllerCreateOperationTabOtherOrganisationManagement(
				this);
		loader.setController(this.controllerCreateOperationTabOtherOrganisationManagement);

		try {
			this.tabOtherOrgManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabOtherOrgManagement);
	}

	private void initPaneMember() {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/CreateOperationTabMemberManagement.fxml");
		this.tabMemberManagement = new Tab();
		this.tabMemberManagement.setText("Member Management");
		this.controllerCreateOperationTabMemberManagement = new ControllerCreateOperationTabMemberManagement(this);
		loader.setController(this.controllerCreateOperationTabMemberManagement);

		try {
			this.tabMemberManagement.setContent(loader.load());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.tabPaneOperation.getTabs().add(this.tabMemberManagement);
	}

	private void initDisability() {
		this.tabOperationVehicleManagement.setDisable(true);
		this.tabOtherOrgManagement.setDisable(true);
		this.tabMemberManagement.setDisable(true);
		this.btnReset.setDisable(true);
		this.btnBack.setDisable(true);
		this.btnNext.setDisable(true);
		this.btnFinish.setDisable(true);
	}

	private void initTabPaneListeners() {
		this.btnReset.setDisable(false);
		this.tabOperationManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(true);
			this.btnNext.setDisable(false);
		});

		this.tabOperationVehicleManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(false);
		});

		this.tabOtherOrgManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(false);
		});

		this.tabMemberManagement.setOnSelectionChanged(event -> {
			this.btnBack.setDisable(false);
			this.btnNext.setDisable(true);
		});
	}

	@FXML
	private void onClickBtnBack(ActionEvent event) {
		if (this.tabOperationVehicleManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationManagement);
		} else if (this.tabOtherOrgManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationVehicleManagement);
		}
		if (this.tabMemberManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOtherOrgManagement);
		}
	}

	@FXML
	private void onClickBtnFinish(ActionEvent event) {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/OperationManagementDialog.fxml");

		ControllerOperationManagementDialog controllerDialogSaveBase = new ControllerOperationManagementDialog(this,
				EnumCRUDOption.POST);
		loader.setController(controllerDialogSaveBase);

		this.setCreatedOperation(this.controllerCreateOperationTabOperationManagement.getCreatedOperationData());
		Operation operationToCreate = this.getCreatedOperation();
		List<OperationVehicle> collOfOperationVehiclesToAddToOperation = this.controllerCreateOperationTabOperationVehicleManagement
				.getOperationVehcilesToCreate();
		List<Member> collOfMembersToAddToOperation = this.controllerCreateOperationTabMemberManagement.getMembersToCreate();
		List<OtherOrganisation> collOfOtherOrgsToAddToOperation = this.controllerCreateOperationTabOtherOrganisationManagement.getOrganisationsToCreate();

		try {
			Stage curStage = new Stage();
			Scene scene = new Scene(loader.load());
			curStage.setScene(scene);
			curStage.initModality(Modality.APPLICATION_MODAL);
			curStage.setTitle("Would you like to save your created base");
			controllerDialogSaveBase.setOperationData(createdOperation);
			controllerDialogSaveBase.setListViewMemberData(collOfMembersToAddToOperation);
			controllerDialogSaveBase.setListViewOperationVehicleData(collOfOperationVehiclesToAddToOperation);
			controllerDialogSaveBase.setListViewOtherOrgData(collOfOtherOrgsToAddToOperation);
			curStage.showAndWait();
			if (controllerDialogSaveBase.getButtonState()) {
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

				Stage stageProgressBarDialog = new Stage();
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog.setTitle("Post Operation");
				stageProgressBarDialog.show();
				
				controllerThreadProgressBarDialog.unbindProgressBar();
				
				Thread postThread = new Thread(managePostTask(this, operationToCreate, collOfMembersToAddToOperation,
						collOfOperationVehiclesToAddToOperation, collOfOtherOrgsToAddToOperation));
				
				Task<Void> managePostTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						updateProgress(0, 100);
						int lastProgressValue = 0;
						updateProgress(0, 100);
						
						//Start post thread
						postThread.start();
						postThread.join();

						// Set progress for base
						for(int i=0;i<5;i++) {
							lastProgressValue += 5;
							updateProgress(lastProgressValue, 100);
							Thread.sleep(125);
						}

						// set progress for OperationVehicles
						updateProgress(25, 100);
						lastProgressValue = 25;
						int nrOfOperationVehiclesToCreate = collOfOperationVehiclesToAddToOperation.size();
						if (nrOfOperationVehiclesToCreate == 0) {
							nrOfOperationVehiclesToCreate = 1;
						}
						double progressIncreasePerOperationVehicleObject = (25 / nrOfOperationVehiclesToCreate);
						if(progressIncreasePerOperationVehicleObject < 1) {
							progressIncreasePerOperationVehicleObject = 1;
							nrOfOperationVehiclesToCreate = 25;
						}
						for (int i = 0; i < nrOfOperationVehiclesToCreate; i++) {
							lastProgressValue += progressIncreasePerOperationVehicleObject;
							updateProgress(lastProgressValue, 100);
							Thread.sleep(100);
						}
						
						// set progress for OtherOrgs
						updateProgress(50, 100);
						lastProgressValue = 50;
						int nrOfOtherOrgsToCreate = collOfOtherOrgsToAddToOperation.size();
						if (nrOfOtherOrgsToCreate == 0) {
							nrOfOtherOrgsToCreate = 1;
						}
						double progressIncreasePerOterOrgObject = (25 / nrOfOtherOrgsToCreate);
						if(progressIncreasePerOterOrgObject < 1) {
							progressIncreasePerOterOrgObject = 1;
							nrOfOtherOrgsToCreate = 25;
						}
						for (int i = 0; i < nrOfOtherOrgsToCreate; i++) {
							lastProgressValue += progressIncreasePerOterOrgObject;
							updateProgress(lastProgressValue, 100);
							Thread.sleep(100);
						}

						// set progress for Members
						updateProgress(75, 100);
						lastProgressValue = 75;
						int nrOfMembersToCreate = collOfMembersToAddToOperation.size();
						if (nrOfMembersToCreate == 0) {
							nrOfMembersToCreate = 1;
						}
						double progressIncreasePerMemberObject = (25 / nrOfMembersToCreate);
						if(progressIncreasePerMemberObject < 1) {
							progressIncreasePerMemberObject = 1;
							nrOfMembersToCreate = 25;
						}
						for (int i = 0; i < nrOfMembersToCreate; i++) {
							lastProgressValue += progressIncreasePerMemberObject;
							updateProgress(lastProgressValue, 100);
							Thread.sleep(100);
						}
						
						updateProgress(100, 100);
						updateMessage("Finising");
						
						return null;
					}
				};
				managePostTask.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
						controllerThreadProgressBarDialog.setLabelText(newValue);
					}
				});
				managePostTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();
					
					this.resetCreateOperartionTabs();
					this.controllerOperationManagement.reloadOperationLookup();
				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(managePostTask);
				new Thread(managePostTask).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Task<Void> managePostTask(ControllerCreateOperationManagement controllerCreateOperationManagement, Operation operationToCreate, List<Member> collOfMembersToAddToOperation, List<OperationVehicle> collOfOperationVehiclesToAddToOperation, List<OtherOrganisation> collOfOtherOrgsToAddToOperation) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new PostFullOperationTask(controllerCreateOperationManagement, operationToCreate, collOfMembersToAddToOperation, collOfOperationVehiclesToAddToOperation, collOfOtherOrgsToAddToOperation));
				thread.start();
				thread.join();
				return null;
			}
		};
	}

	@FXML
	private void onClickBtnNext(ActionEvent event) {
		// this.setCreatedBase(this.controllerCreateBaseTabBaseManagement.getCreatedBaseData());
		if (this.tabOperationManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationVehicleManagement);
		} else if (this.tabOperationVehicleManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabOtherOrgManagement);
		} else if (this.tabOtherOrgManagement.isSelected()) {
			this.tabPaneOperation.getSelectionModel().select(this.tabMemberManagement);
		}
	}

	@FXML
	private void onClickBtnReset(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Input Fields");
		alert.setHeaderText("Would you really reset your Operationcreation");
		alert.setContentText("Note: All your Inputs are not going to be saved");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.resetCreateOperartionTabs();
			alert.close();
			this.tabPaneOperation.getSelectionModel().select(this.tabOperationManagement);
			this.btnReset.setDisable(true);
			this.btnNext.setDisable(true);
		} else {
			alert.close();
		}
	}

	public void resetCreateOperartionTabs() {
		this.tabPaneOperation.getSelectionModel().select(this.tabOperationManagement);
		this.controllerCreateOperationTabOperationVehicleManagement.reset();
		this.controllerCreateOperationTabOtherOrganisationManagement.reset();
		this.controllerCreateOperationTabMemberManagement.reset();
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
	
	public void setTabOtherOrganisationManagementDisability(boolean isDisabled) {
		this.tabOtherOrgManagement.setDisable(isDisabled);
	}

	public void setTabMemberManagementDisability(boolean isDisabled) {
		this.tabMemberManagement.setDisable(isDisabled);
	}

	private void setCreatedOperation(Operation createdOperation) {
		this.createdOperation = createdOperation;
	}

	public Operation getCreatedOperation() {
		return this.createdOperation;
	}

	public void setStatusbarValue(String text) {
		this.lbStatusbar.setText(text);
	}
}