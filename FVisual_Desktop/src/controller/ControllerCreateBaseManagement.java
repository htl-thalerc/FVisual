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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
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
import threadHelper.PostFullBaseTask;
import handler.CentralHandler;

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
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

				Stage stageProgressBarDialog = new Stage();
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog.setTitle("Post Base");
				stageProgressBarDialog.showAndWait();
				
				controllerThreadProgressBarDialog.unbindProgressBar();
				
				Thread postThread = new Thread(managePostTask(this, baseToCreate, collOfMembersToAddToBase,
						collOfOperationVehiclesToAddToBase));
				
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
							lastProgressValue += 4;
							updateProgress(lastProgressValue, 100);
							Thread.sleep(200);
						}

						// set progress for OperationVehicles
						updateProgress(20, 100);
						lastProgressValue = 20;
						int nrOfOperationVehiclesToCreate = collOfOperationVehiclesToAddToBase.size();
						if (nrOfOperationVehiclesToCreate == 0) {
							nrOfOperationVehiclesToCreate = 1;
						}
						double progressIncreasePerOperationVehicleObject = (40 / nrOfOperationVehiclesToCreate);
						if(progressIncreasePerOperationVehicleObject < 1) {
							progressIncreasePerOperationVehicleObject = 1;
							nrOfOperationVehiclesToCreate = 40;
						}
						for (int i = 0; i < nrOfOperationVehiclesToCreate; i++) {
							lastProgressValue += progressIncreasePerOperationVehicleObject;
							updateProgress(lastProgressValue, 100);
							Thread.sleep(100);
						}

						// set progress for Members
						updateProgress(60, 100);
						lastProgressValue = 60;
						int nrOfMembersToCreate = collOfMembersToAddToBase.size();
						if (nrOfMembersToCreate == 0) {
							nrOfMembersToCreate = 1;
						}
						double progressIncreasePerMemberObject = (40 / nrOfMembersToCreate);
						if(progressIncreasePerMemberObject < 1) {
							progressIncreasePerMemberObject = 1;
							nrOfMembersToCreate = 40;
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
					
					this.resetCreateBaseTabs();
					this.controllerBaseManagement.reloadBaseLookup();
				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(managePostTask);
				new Thread(managePostTask).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Task<Void> managePostTask(ControllerCreateBaseManagement controllerCreateBaseManagement, Base baseToCreate,
			List<Member> collOfMembersToAddToBase, List<OperationVehicle> collOfOperationVehiclesToAddToBase) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread postFullBase = new Thread(new PostFullBaseTask(controllerCreateBaseManagement, baseToCreate,
						collOfMembersToAddToBase, collOfOperationVehiclesToAddToBase));
				postFullBase.start();
				postFullBase.join();
				return null;
			}
		};
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