package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import bll.Base;
import bll.EnumCRUDOption;
import bll.Member;
import bll.OperationVehicle;
import handler.BaseHandler;
import handler.CentralHandler;
import handler.CentralUpdateHandler;
import handler.MemberHandler;
import handler.OperationVehicleHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loader.BaseLoader;
import loader.BaseMemberLoader;
import loader.BaseVehicleLoader;
import loader.MemberLoader;
import loader.OperationVehicleDeleteFromBaseLoader;
import loader.OperationVehicleLoader;
import manager.BaseManager;
import manager.MemberManager;

public class ControllerBaseManagementBaseLookup implements Initializable {
	@FXML
	private Accordion dropMenuFilter, accordionSubTables;
	@FXML
	private TitledPane tpOperationVehcile, tpMember;
	@FXML
	private TableView<Base> tvBaseData;
	@FXML
	private TableView<OperationVehicle> tvVehicleData;
	@FXML
	private TableView<Member> tvMemberData;
	@FXML
	private Label lbShowNameData, lbShowPostcodeAndPlaceData, lbShowAddressData;
	@FXML
	private Button btnLoadBaseVehicles, btnLoadBaseMembers, btnLoadAllVehicles, btnLoadAllMembers;
	@FXML
	private MenuItem mItemRemoveBase, mItemUpdateBase, mItemUpdateVehicle, mItemRemoveVehicle, mItemUpdateMember,
			mItemRemoveMember;

	private ObservableList<Base> obsListTVBaseData = null;
	private ObservableList<OperationVehicle> obsListTVVehicles = null;
	private ObservableList<Member> obsListTVMembers = null;

	private ControllerBaseManagement controllerBaseManagement;

	public ControllerBaseManagementBaseLookup(ControllerBaseManagement controllerBaseManagement) {
		this.controllerBaseManagement = controllerBaseManagement;
	}

	public ControllerBaseManagementBaseLookup() {
		super();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.defaultSettings();
		this.initTableViewBase();
		this.initTableViewVehicle();
		this.initTableViewMember();
		this.fillTableViews(false);
		this.initTableViewBaseListener();
		this.initTableViewVehicleListener();
		this.initTableViewMemberListener();
	}

	private void defaultSettings() {
		// todo: enable
		this.mItemRemoveBase.setDisable(true);
		this.mItemRemoveVehicle.setDisable(true);
		this.mItemRemoveMember.setDisable(true);

		this.btnLoadBaseVehicles.setDisable(true);
		this.btnLoadBaseMembers.setDisable(true);
		this.btnLoadAllVehicles.setDisable(true);
		this.btnLoadAllMembers.setDisable(true);

		this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewBase() {
		TableColumn<Base, String> columnName = new TableColumn<Base, String>("Name");
		TableColumn<Base, String> columnPlace = new TableColumn<Base, String>("Place");
		TableColumn<Base, Number> columnPostCode = new TableColumn<Base, Number>("Postcode");
		TableColumn<Base, String> columnStreet = new TableColumn<Base, String>("Street");
		TableColumn<Base, Number> columnHouseNr = new TableColumn<Base, Number>("Nr");

		columnName.setCellValueFactory(new PropertyValueFactory<Base, String>("name"));
		columnPlace.setCellValueFactory(new PropertyValueFactory<Base, String>("place"));
		columnPostCode.setCellValueFactory(new PropertyValueFactory<Base, Number>("postCode"));
		columnStreet.setCellValueFactory(new PropertyValueFactory<Base, String>("street"));
		columnHouseNr.setCellValueFactory(new PropertyValueFactory<Base, Number>("houseNr"));

		this.tvBaseData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnName.setMaxWidth(1f * Integer.MAX_VALUE * 32.5);
		columnPlace.setMaxWidth(1f * Integer.MAX_VALUE * 27);
		columnPostCode.setMaxWidth(1f * Integer.MAX_VALUE * 12);
		columnStreet.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnHouseNr.setMaxWidth(1f * Integer.MAX_VALUE * 5);

		this.tvBaseData.getColumns().addAll(columnName, columnPlace, columnPostCode, columnStreet, columnHouseNr);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewVehicle() {
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>(
				"Description");
		TableColumn<OperationVehicle, String> columnBaseName = new TableColumn<OperationVehicle, String>("Base");

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));
		columnBaseName
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		columnBaseName.setMaxWidth(1f * Integer.MAX_VALUE * 60);

		this.tvVehicleData.getColumns().addAll(columnVehicleDescription, columnBaseName);
	}

	@SuppressWarnings("unchecked")
	private void initTableViewMember() {
		TableColumn<Member, String> colNameBlock = new TableColumn<Member, String>("Name");
		TableColumn<Member, String> colFirstname = new TableColumn<Member, String>("Firstname");
		TableColumn<Member, String> colLastname = new TableColumn<Member, String>("Lastname");
		TableColumn<Member, String> colUsername = new TableColumn<Member, String>("Username");
		TableColumn<Member, String> colContraction = new TableColumn<Member, String>("Rank");

		colFirstname.setCellValueFactory(new PropertyValueFactory<Member, String>("firstname"));
		colLastname.setCellValueFactory(new PropertyValueFactory<Member, String>("lastname"));
		colUsername.setCellValueFactory(new PropertyValueFactory<Member, String>("username"));

		colContraction.setCellValueFactory(new PropertyValueFactory<Member, String>("rank"));

		colNameBlock.getColumns().addAll(colFirstname, colLastname, colUsername);

		this.tvMemberData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		colFirstname.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		colContraction.setMaxWidth(1f * Integer.MAX_VALUE * 60);

		colFirstname.setMaxWidth(1f * Integer.MAX_VALUE * 25);
		colLastname.setMaxWidth(1f * Integer.MAX_VALUE * 25);
		colUsername.setMaxWidth(1f * Integer.MAX_VALUE * 50);

		this.tvMemberData.getColumns().addAll(colNameBlock, colContraction);
	}

	public void fillTableViews(boolean isCalledInReloadingData) {
		if (isCalledInReloadingData) {
			CountDownLatch latch = new CountDownLatch(3);

			BaseLoader baseLoader = new BaseLoader(latch);
			OperationVehicleLoader vehicleLoader = new OperationVehicleLoader(latch);
			MemberLoader memberLoader = new MemberLoader(latch);

			Thread threadBaseLoader = new Thread(baseLoader);
			Thread threadVehicleLoader = new Thread(vehicleLoader);
			Thread threadMemberLoader = new Thread(memberLoader);

			try {
				threadBaseLoader.start();
				threadVehicleLoader.start();
				threadMemberLoader.start();

				latch.await(); // After all 4 Threads from the countdownlatch are finished --> execute
								// following lines
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.fillTableViewBasesFromThread();
		this.fillTableViewVehiclesFromThread(true);
		this.fillTalbeViewMembersFromThread();
	}

	public void fillTableViewBasesFromThread() {
		this.obsListTVBaseData = FXCollections.observableArrayList();
		this.obsListTVBaseData.addAll(BaseHandler.getInstance().getBaseList());

		this.tvBaseData.setItems(this.obsListTVBaseData.sorted());
	}

	public void fillTableViewVehiclesFromThread(boolean isLoadingAllVehicles) {
		this.obsListTVVehicles = FXCollections.observableArrayList();
		if (isLoadingAllVehicles) {
			this.obsListTVVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleList());
		} else {
			this.obsListTVVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleListByBaseId());
		}
		this.tvVehicleData.setItems(this.obsListTVVehicles.sorted());
	}

	public void fillTalbeViewMembersFromThread() {
		this.obsListTVMembers = FXCollections.observableArrayList();

		this.obsListTVMembers.addAll(MemberHandler.getInstance().getMemberList());
		this.tvMemberData.setItems(this.obsListTVMembers.sorted());
	}

	private void initTableViewBaseListener() {
		this.tvBaseData.setOnMouseClicked(event -> {
			Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
			if (selectedBase != null) {
				this.showBaseData(selectedBase);
				this.btnLoadBaseVehicles.setDisable(false);
				this.btnLoadBaseMembers.setDisable(false);
			} else {
				this.btnLoadBaseVehicles.setDisable(true);
				this.btnLoadBaseMembers.setDisable(true);
			}
			CentralUpdateHandler.getInstance().setCurrBaseToUpdate(selectedBase);
		});
	}

	private void showBaseData(Base selectedBase) {
		this.lbShowNameData.setText(selectedBase.getName());
		this.lbShowPostcodeAndPlaceData.setText(selectedBase.getPostCode() + ", " + selectedBase.getPlace());
		this.lbShowAddressData.setText(selectedBase.getStreet() + ", " + selectedBase.getHouseNr());
	}

	private void initTableViewVehicleListener() {
		this.tvVehicleData.setOnMouseClicked(event -> {
		});
	}

	private void initTableViewMemberListener() {
		this.tvMemberData.setOnMouseClicked(event -> {
		});
	}

	@FXML
	private void onClickBtnLoadBaseVehicles(ActionEvent event) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if (selectedBase != null) {
			try {
				// Open ProgressBarDialog
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

				Stage stageProgressBarDialog = new Stage();
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog.setTitle("Loading OperationVehicle by Base '" + selectedBase.getName() + "'");
				stageProgressBarDialog.show();

				controllerThreadProgressBarDialog.unbindProgressBar();

				// Start threading
				Task<Void> taskBaseVehicleLoader = loadOperationVehiclesByBase(selectedBase);

				Task<Void> loadingOperationVehicleByBaseTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						updateProgress(0, 100);

						Thread threadBaseVehicleLoader = new Thread(taskBaseVehicleLoader);
						threadBaseVehicleLoader.start();
						threadBaseVehicleLoader.join();

						return null;
					}
				};
				loadingOperationVehicleByBaseTask.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
						controllerThreadProgressBarDialog.setLabelText(newValue);
					}
				});
				loadingOperationVehicleByBaseTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();
					this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);

					this.obsListTVVehicles.clear();
					ArrayList<OperationVehicle> tempListOfVehicles = OperationVehicleHandler.getInstance()
							.getVehicleListByBaseId();

					if (tempListOfVehicles != null) {
						this.obsListTVVehicles.addAll(tempListOfVehicles);

						this.tvVehicleData.setItems(this.obsListTVVehicles);
						this.btnLoadBaseVehicles.setDisable(true);
						this.btnLoadAllVehicles.setDisable(false);
					} else {
						this.obsListTVVehicles.clear();
						this.tvVehicleData.refresh();
						this.tvVehicleData.setPlaceholder(new Label("No Vehicles in current selected Base available"));
					}
				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(loadingOperationVehicleByBaseTask);
				controllerThreadProgressBarDialog.bindProgressBarOnTask(taskBaseVehicleLoader);

				new Thread(loadingOperationVehicleByBaseTask).start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@FXML
	private void onClickBtnLoadBaseMembers(ActionEvent event) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if (selectedBase != null) {
			try {
			// Open ProgressBarDialog
			FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
			ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
			loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

			Stage stageProgressBarDialog = new Stage();
			
			Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
			stageProgressBarDialog.setScene(sceneProgressBarDialog);
			stageProgressBarDialog.setTitle("Loading Members by Base '" + selectedBase.getName() + "'");
			stageProgressBarDialog.show();

			controllerThreadProgressBarDialog.unbindProgressBar();

			// Starting Threading
			Task<Void> taskBaseMemberLoader = loadMembersByBase(selectedBase);

			Task<Void> loadingMembersByBaseTask = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					updateProgress(0, 100);
					
					Thread threadBaseMemberLoader = new Thread(taskBaseMemberLoader);
					threadBaseMemberLoader.start();
					threadBaseMemberLoader.join();

					return null;
				}
			};
			loadingMembersByBaseTask.messageProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
					controllerThreadProgressBarDialog.setLabelText(newValue);
				}
			});
			loadingMembersByBaseTask.setOnSucceeded(e -> {
				stageProgressBarDialog.close();
				this.accordionSubTables.setExpandedPane(this.tpMember);

				this.obsListTVMembers.clear();
				ArrayList<Member> tempListOfMembers = MemberHandler.getInstance().getMemberListByBaseId();

				if (tempListOfMembers != null) {
					this.obsListTVMembers.addAll(tempListOfMembers);

					this.tvMemberData.setItems(this.obsListTVMembers);
					this.btnLoadBaseMembers.setDisable(true);
					this.btnLoadAllMembers.setDisable(false);
				} else {
					this.obsListTVMembers.clear();
					this.tvMemberData.refresh();
					this.tvMemberData.setPlaceholder(new Label("No Members in current selected Base available"));
				}
			});
			controllerThreadProgressBarDialog.bindProgressBarOnTask(loadingMembersByBaseTask);
			controllerThreadProgressBarDialog.bindProgressBarOnTask(taskBaseMemberLoader);
			
			new Thread(loadingMembersByBaseTask).start();;
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@FXML
	private void onClickBtnLoadAllVehicles(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);

		this.obsListTVVehicles.clear();
		this.obsListTVVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleList());
		this.tvVehicleData.setItems(this.obsListTVVehicles);

		this.btnLoadAllVehicles.setDisable(true);
		this.btnLoadBaseVehicles.setDisable(false);
	}

	@FXML
	private void onClickBtnLoadAllMembers(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpMember);

		this.obsListTVMembers.clear();
		this.obsListTVMembers.addAll(MemberHandler.getInstance().getMemberList());
		this.tvMemberData.setItems(this.obsListTVMembers);

		this.btnLoadAllMembers.setDisable(true);
		this.btnLoadBaseMembers.setDisable(false);
	}

	@FXML
	private void onClickMItemRemoveBase(ActionEvent event) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();

		if (selectedBase != null) {
			FXMLLoader loader = CentralHandler.loadFXML("/gui/BaseManagementDialog.fxml");

			ControllerBaseManagementDialog controllerDialogSaveBase = new ControllerBaseManagementDialog(this,
					EnumCRUDOption.DELETE);
			loader.setController(controllerDialogSaveBase);

			try {
				Stage curStage = new Stage();
				Scene scene = new Scene(loader.load());
				curStage.setScene(scene);
				curStage.initModality(Modality.APPLICATION_MODAL);
				curStage.setTitle("Would you like to remove your selected Base");

				controllerDialogSaveBase.setBaseData(selectedBase);
				controllerDialogSaveBase.setListViewOperationVehicleData(new ArrayList<OperationVehicle>());
				controllerDialogSaveBase.setListViewMemberData(new ArrayList<Member>());
				curStage.showAndWait();
				if (controllerDialogSaveBase.getButtonState()) {
					BaseManager.getInstance().deleteBase(selectedBase.getBaseId());
					this.fillTableViews(true);
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onClickMItemUpdateBase(ActionEvent event) {
		Base selectedBase = this.tvBaseData.getSelectionModel().getSelectedItem();
		if (selectedBase != null) {
			CentralUpdateHandler.getInstance().initUpdateBaseDialog(selectedBase);
			this.fillTableViews(true);
			this.onClickBtnLoadBaseVehicles(new ActionEvent());
			this.onClickBtnLoadBaseMembers(new ActionEvent());
		}
	}

	@FXML
	private void onClickMItemRemoveVehicle(ActionEvent event) {
		OperationVehicle selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if (selectedVehicle != null) {
			OperationVehicleDeleteFromBaseLoader operationVehicleDeleteFromBaseLoader = new OperationVehicleDeleteFromBaseLoader(
					selectedVehicle.getOperationVehicleId(), selectedVehicle.getBaseId());
			Thread threadOperationVehicleDeleteFromBaseLoader = new Thread(operationVehicleDeleteFromBaseLoader);
			threadOperationVehicleDeleteFromBaseLoader.start();
			try {
				threadOperationVehicleDeleteFromBaseLoader.join();

				this.onClickBtnLoadBaseVehicles(new ActionEvent());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent event) {
		OperationVehicle selectedVehicle = this.tvVehicleData.getSelectionModel().getSelectedItem();
		if (selectedVehicle != null) {
			CentralUpdateHandler.getInstance().initUpdateOperationVehicleDialog(selectedVehicle);

			this.onClickBtnLoadBaseVehicles(new ActionEvent());
		}
	}

	@FXML
	private void onClickMItemRemoveMember(ActionEvent event) {
		Member selectedMember = this.tvMemberData.getSelectionModel().getSelectedItem();
		if (selectedMember != null) {
			MemberManager.getInstance().deleteMemberFromBase(selectedMember.getBase().getBaseId(),
					selectedMember.getMemberId());

			this.onClickBtnLoadBaseMembers(new ActionEvent());
		}
	}

	@FXML
	private void onClickMItemUpdateMember(ActionEvent event) {
		Member selectedMember = this.tvMemberData.getSelectionModel().getSelectedItem();
		if (selectedMember != null) {
			CentralUpdateHandler.getInstance().initUpdateMemberDialog(selectedMember);

			this.onClickBtnLoadBaseMembers(new ActionEvent());
		}
	}
	
	private Task<Void> loadOperationVehiclesByBase(Base base) {
		return new Task<Void>() {
			double lastProgressValue = 0;
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new BaseVehicleLoader(base));
				thread.start();
				thread.join();
				
				for (int i = 0; i < 40; i++) {
					lastProgressValue += 2.5;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(50);
				}
				updateProgress(100, 100);
				updateMessage("Finishing");
				return null;
			}
		};
	}
	
	private Task<Void> loadMembersByBase(Base base) {
		return new Task<Void>() {
			double lastProgressValue = 0;
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new BaseMemberLoader(base));
				thread.start();
				thread.join();
				
				for (int i = 0; i < 40; i++) {
					lastProgressValue += 2.5;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(50);
				}
				updateProgress(100, 100);
				updateMessage("Finishing");
				return null;
			}
		};
	}
}