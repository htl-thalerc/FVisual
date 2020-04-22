package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import bll.Member;
import bll.Operation;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import handler.CentralHandler;
import handler.MemberHandler;
import handler.OperationHandler;
import handler.OperationVehicleHandler;
import handler.OtherOrganisationHandler;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import loader.BaseLoader;
import loader.MemberLoader;
import loader.OperationLoader;
import loader.OperationMemberLoader;
import loader.OperationVehicleByOperationLoader;
import loader.OperationVehicleLoader;
import loader.OperationVehicleWithOperationAttrLoader;
import loader.OtherOrganisationByOperationLoader;
import loader.OtherOrganisationLoader;

public class ControllerOperationManagementOperationLookup implements Initializable {
	@FXML
	private Accordion dropMenuFilter, accordionSubTables;
	@FXML
	private TableView<Operation> tvOperationData;
	@FXML
	private MenuItem mItemRemoveOperation, mItemUpdateOperation;
	@FXML
	private Label lbShowCodeAndTypeAndTitleData, lbShowShortdescriptionData, lbShowAddressAndPlzData, lbShowBase;
	@FXML
	private TableView<OperationVehicle> tvVehicleData;
	@FXML
	private TableView<OtherOrganisation> tvOtherOrgData;
	@FXML
	private TitledPane tpMember, tpOtherOrg, tpOperationVehcile;
	@FXML
	private TableView<Member> tvMemberData;
	@FXML
	private Button btnLoadAllOtherOrg, btnLoadAllMembers, btnLoadAllVehicles, btnLoadOperationOtherOrg,
			btnLoadOperationMembers, btnLoadOperationVehicles;

	private ObservableList<Operation> obsListTVOperations = null;
	private ObservableList<OperationVehicle> obsListTVOperationVehicles = null;
	private ObservableList<Member> obsListTVMembers = null;
	private ObservableList<OtherOrganisation> obsListTVOtherOrgs = null;

	private ControllerOperationManagement controllerOperationManagement = null;

	public ControllerOperationManagementOperationLookup(ControllerOperationManagement controllerOperationManagement) {
		this.controllerOperationManagement = controllerOperationManagement;
	}

	public ControllerOperationManagementOperationLookup() {
		super();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.defaultSettings();
		this.initTVOperation();
		this.initTVOperationVehicle();
		this.initTVOtherOrg();
		this.initTVMember();

		this.fillTableViews(false);
		this.initTVOperationListeners();
	}

	private void defaultSettings() {
		this.btnLoadOperationMembers.setDisable(true);
		this.btnLoadOperationOtherOrg.setDisable(true);
		this.btnLoadOperationVehicles.setDisable(true);
		this.btnLoadAllVehicles.setDisable(true);
		this.btnLoadAllMembers.setDisable(true);
		this.btnLoadAllOtherOrg.setDisable(true);

		this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);
	}

	@SuppressWarnings("unchecked")
	private void initTVOperation() {
		TableColumn<Operation, String> columnOperationCode = new TableColumn<Operation, String>("Code");
		TableColumn<Operation, String> columnOperationType = new TableColumn<Operation, String>("Type");
		TableColumn<Operation, String> columnBase = new TableColumn<Operation, String>("Base");
		TableColumn<Operation, String> columnTitle = new TableColumn<Operation, String>("Title");
		TableColumn<Operation, String> columnDescription = new TableColumn<Operation, String>("Description");

		columnOperationCode.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getOperationCode().getCode()));
		columnOperationType.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getOperationType().getDescription()));
		columnBase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));
		columnTitle.setCellValueFactory(new PropertyValueFactory<Operation, String>("title"));
		columnDescription.setCellValueFactory(new PropertyValueFactory<Operation, String>("shortDescription"));

		this.tvOperationData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnOperationCode.setMaxWidth(1f * Integer.MAX_VALUE * 10);
		columnOperationType.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnBase.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnTitle.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		columnDescription.setMaxWidth(1f * Integer.MAX_VALUE * 30);

		this.tvOperationData.getColumns().addAll(columnOperationCode, columnOperationType, columnTitle,
				columnDescription, columnBase);
	}

	@SuppressWarnings("unchecked")
	private void initTVOperationVehicle() {
		TableColumn<OperationVehicle, String> columnVehicleDescription = new TableColumn<OperationVehicle, String>(
				"Description");
		TableColumn<OperationVehicle, String> columnBase = new TableColumn<OperationVehicle, String>("Base");
		TableColumn<OperationVehicle, String> columnOperation = new TableColumn<OperationVehicle, String>("Operation");

		columnVehicleDescription.setCellValueFactory(new PropertyValueFactory<OperationVehicle, String>("description"));
		columnOperation.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getOperation().getTitle()));
		columnBase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));

		columnOperation.setCellFactory(column -> {
			return new TableCell<OperationVehicle, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(getTableView().getItems().get(getIndex()).getOperation().getTitle());
						if (getTableView().getItems().get(getIndex()).getOperation().getTitle()
								.equals("Not assigned yet")) {
							setStyle("-fx-background-color: #FF6666");
						} else {
							setStyle("-fx-background-color: #CCFF99");
						}
					}
				}
			};
		});

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnVehicleDescription.setMaxWidth(1f * Integer.MAX_VALUE * 30);
		columnOperation.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		columnBase.setMaxWidth(1f * Integer.MAX_VALUE * 30);

		this.tvVehicleData.getColumns().addAll(columnVehicleDescription, columnBase, columnOperation);
	}

	@SuppressWarnings("unchecked")
	private void initTVOtherOrg() {
		TableColumn<OtherOrganisation, String> columnName = new TableColumn<OtherOrganisation, String>("Name");
		TableColumn<OtherOrganisation, String> columnOperation = new TableColumn<OtherOrganisation, String>(
				"Operation");

		columnName.setCellValueFactory(new PropertyValueFactory<OtherOrganisation, String>("name"));
		columnOperation.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getOperation().getTitle()));

		columnOperation.setCellFactory(column -> {
			return new TableCell<OtherOrganisation, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(getTableView().getItems().get(getIndex()).getOperation().getTitle());
						if (getTableView().getItems().get(getIndex()).getOperation().getTitle()
								.equals("Not assigned yet")) {
							setStyle("-fx-background-color: #FF6666");
						} else {
							setStyle("-fx-background-color: #CCFF99");
						}
					}
				}
			};
		});

		this.tvVehicleData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		columnName.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		columnOperation.setMaxWidth(1f * Integer.MAX_VALUE * 60);

		this.tvOtherOrgData.getColumns().addAll(columnName, columnOperation);
	}

	@SuppressWarnings("unchecked")
	private void initTVMember() {
		TableColumn<Member, String> colNameBlock = new TableColumn<Member, String>("Name");
		TableColumn<Member, String> colFirstname = new TableColumn<Member, String>("Firstname");
		TableColumn<Member, String> colLastname = new TableColumn<Member, String>("Lastname");
		TableColumn<Member, String> colUsername = new TableColumn<Member, String>("Username");
		TableColumn<Member, String> colContraction = new TableColumn<Member, String>("Rank");
		TableColumn<Member, String> colBase = new TableColumn<Member, String>("Base");

		colFirstname.setCellValueFactory(new PropertyValueFactory<Member, String>("firstname"));
		colLastname.setCellValueFactory(new PropertyValueFactory<Member, String>("lastname"));
		colUsername.setCellValueFactory(new PropertyValueFactory<Member, String>("username"));
		colContraction.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getRank().getContraction()));
		colBase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBase().getName()));

		colNameBlock.getColumns().addAll(colFirstname, colLastname, colUsername);

		this.tvMemberData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		colFirstname.setMaxWidth(1f * Integer.MAX_VALUE * 15);
		colLastname.setMaxWidth(1f * Integer.MAX_VALUE * 15);
		colUsername.setMaxWidth(1f * Integer.MAX_VALUE * 30);
		colContraction.setMaxWidth(1f * Integer.MAX_VALUE * 10);
		colBase.setMaxWidth(1f * Integer.MAX_VALUE * 30);

		colBase.setCellFactory(column -> {
			return new TableCell<Member, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setStyle("");
						setText("");
					} else {
						setText(getTableView().getItems().get(getIndex()).getBase().getName());
						if (!getTableView().getItems().get(getIndex()).getBase().getName().equals("not assinged yet")) {
							setStyle("-fx-background-color: #CCFF99");
						} else {
							setStyle("-fx-background-color: #FF6666");
						}
					}
				}
			};
		});

		this.tvMemberData.getColumns().addAll(colNameBlock, colContraction, colBase);
	}

	public void fillTableViews(boolean isCalledInReloadingData) {
		if (isCalledInReloadingData) {
			try {
				CountDownLatch latchForGETCalls = new CountDownLatch(6);

				new Thread(loadBases(latchForGETCalls)).start();
				new Thread(loadOperation(latchForGETCalls)).start();
				new Thread(loadOperationVehicles(latchForGETCalls)).start();
				new Thread(loadOperationVehiclesWithOperations(latchForGETCalls)).start();
				new Thread(loadOtherOrgs(latchForGETCalls)).start();
				new Thread(loadMembers(latchForGETCalls)).start();

				latchForGETCalls.await();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		this.fillTableViewOperationsFromThread();
		this.fillTableViewOperationVehiclesFromThread();
		this.fillTableViewOtherOrgFromThread();
		this.fillTableViewMembersFromThread();
	}

	private void fillTableViewOperationsFromThread() {
		this.obsListTVOperations = FXCollections.observableArrayList();
		this.obsListTVOperations.addAll(OperationHandler.getInstance().getOperationList());

		this.tvOperationData.setItems(this.obsListTVOperations.sorted());
	}

	private void fillTableViewOperationVehiclesFromThread() {
		this.obsListTVOperationVehicles = FXCollections.observableArrayList();
		this.obsListTVOperationVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleListWithOperationAttr());

		this.tvVehicleData.setItems(this.obsListTVOperationVehicles.sorted());
	}

	private void fillTableViewOtherOrgFromThread() {
		this.obsListTVOtherOrgs = FXCollections.observableArrayList();
		this.obsListTVOtherOrgs.addAll(OtherOrganisationHandler.getInstance().getOtherOrganisationList());

		this.tvOtherOrgData.setItems(this.obsListTVOtherOrgs.sorted());
	}

	private void fillTableViewMembersFromThread() {
		this.obsListTVMembers = FXCollections.observableArrayList();
		this.obsListTVMembers.addAll(MemberHandler.getInstance().getMemberList());

		this.tvMemberData.setItems(this.obsListTVMembers.sorted());
	}

	private void initTVOperationListeners() {
		this.tvOperationData.setOnMouseClicked(event -> {
			Operation selectedOperation = this.tvOperationData.getSelectionModel().getSelectedItem();
			if (selectedOperation != null) {
				this.lbShowCodeAndTypeAndTitleData.setText(selectedOperation.getOperationCode().getCode() + ", "
						+ selectedOperation.getOperationType().getDescription());
				this.lbShowShortdescriptionData
						.setText(selectedOperation.getTitle() + ", " + selectedOperation.getShortDescription());
				this.lbShowAddressAndPlzData
						.setText(selectedOperation.getAddress() + ", " + selectedOperation.getPostCode());
				this.lbShowBase.setText(selectedOperation.getBase().getName());
				this.btnLoadOperationVehicles.setDisable(false);
				this.btnLoadOperationOtherOrg.setDisable(false);
				this.btnLoadOperationMembers.setDisable(false);
			} else {
				this.btnLoadOperationVehicles.setDisable(true);
				this.btnLoadOperationOtherOrg.setDisable(true);
				this.btnLoadOperationMembers.setDisable(true);
			}
		});
	}

	@FXML
	private void onClickBtnLoadAllMembers(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpMember);

		this.obsListTVMembers.clear();
		this.obsListTVMembers.addAll(MemberHandler.getInstance().getMemberList());
		this.tvMemberData.setItems(this.obsListTVMembers);

		this.btnLoadAllMembers.setDisable(true);
		this.btnLoadOperationMembers.setDisable(false);
	}

	@FXML
	private void onClickBtnLoadAllOtherOrg(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpOtherOrg);

		this.obsListTVOtherOrgs.clear();
		this.obsListTVOtherOrgs.addAll(OtherOrganisationHandler.getInstance().getOtherOrganisationList());
		this.tvOtherOrgData.setItems(this.obsListTVOtherOrgs);

		this.btnLoadAllOtherOrg.setDisable(true);
		this.btnLoadOperationOtherOrg.setDisable(false);
	}

	@FXML
	private void onClickBtnLoadAllVehicles(ActionEvent event) {
		this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);

		this.obsListTVOperationVehicles.clear();
		this.obsListTVOperationVehicles.addAll(OperationVehicleHandler.getInstance().getVehicleListWithOperationAttr());
		this.tvVehicleData.setItems(this.obsListTVOperationVehicles);

		this.btnLoadAllVehicles.setDisable(true);
		this.btnLoadOperationVehicles.setDisable(false);
	}

	@FXML
	private void onClickBtnLoadOperationMembers(ActionEvent event) {
		Operation selectedOperation = this.tvOperationData.getSelectionModel().getSelectedItem();
		if (selectedOperation != null) {
			try {
				// Open ProgressBarDialog
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

				Stage stageProgressBarDialog = new Stage();
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog.setTitle("Loading Members by Operation '" + selectedOperation.getTitle() + "'");
				stageProgressBarDialog.show();

				controllerThreadProgressBarDialog.unbindProgressBar();

				// Starting Threading
				Task<Void> taskOperationMemberLoader = loadMembersByOperation(selectedOperation.getOperationId());
						
				Task<Void> loadingMembersByOperationTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						updateProgress(0, 100);
						
						Thread threadOperationMemberLoader = new Thread(taskOperationMemberLoader);
						threadOperationMemberLoader.start();
						threadOperationMemberLoader.join();

						return null;
					}
				};
				loadingMembersByOperationTask.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
						controllerThreadProgressBarDialog.setLabelText(newValue);
					}
				});
				loadingMembersByOperationTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();

					this.obsListTVMembers.clear();
					ArrayList<Member> tempListOfMembers = MemberHandler.getInstance().getMemberListByOperationId();

					if (tempListOfMembers != null) {
						this.obsListTVMembers.addAll(tempListOfMembers);

						this.tvMemberData.setItems(this.obsListTVMembers);
						this.btnLoadOperationMembers.setDisable(true);
						this.btnLoadAllMembers.setDisable(false);
					} else {
						this.obsListTVMembers.clear();
						this.tvMemberData.refresh();
						this.tvMemberData
								.setPlaceholder(new Label("No Members in current selected Operation available"));
					}
					this.accordionSubTables.setExpandedPane(this.tpMember);
				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(loadingMembersByOperationTask);
				controllerThreadProgressBarDialog.bindProgressBarOnTask(taskOperationMemberLoader);

				Thread mainThread = new Thread(loadingMembersByOperationTask);
				mainThread.start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@FXML
	private void onClickBtnLoadOperationOtherOrg(ActionEvent event) {
		Operation selectedOperation = this.tvOperationData.getSelectionModel().getSelectedItem();
		if (selectedOperation != null) {
			try {
				// Open ProgressBarDialog
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

				Stage stageProgressBarDialog = new Stage();
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog
						.setTitle("Loading Other-Organisations by Operation '" + selectedOperation.getTitle() + "'");
				stageProgressBarDialog.show();

				controllerThreadProgressBarDialog.unbindProgressBar();

				// Starting Threading
				Task<Void> taskOtherOrgLoader = loadOtherOrgByOperation(selectedOperation.getOperationId());

				Task<Void> loadingOtherOrgByOperationTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						updateProgress(0, 100);

						Thread threadOtherOrgLoader = new Thread(taskOtherOrgLoader);
						threadOtherOrgLoader.start();
						threadOtherOrgLoader.join();

						return null;
					}
				};
				loadingOtherOrgByOperationTask.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
						controllerThreadProgressBarDialog.setLabelText(newValue);
					}
				});
				loadingOtherOrgByOperationTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();
					
					this.obsListTVOtherOrgs.clear();
					ArrayList<OtherOrganisation> tempListOfVehicles = OtherOrganisationHandler.getInstance()
							.getOtherOrganisationByOperationId();

					if (tempListOfVehicles != null) {
						this.obsListTVOtherOrgs.addAll(tempListOfVehicles);

						this.tvOtherOrgData.setItems(this.obsListTVOtherOrgs);
						this.btnLoadOperationOtherOrg.setDisable(true);
						this.btnLoadAllOtherOrg.setDisable(false);
					} else {
						this.obsListTVOtherOrgs.clear();
						this.tvOtherOrgData.refresh();
						this.tvOtherOrgData.setPlaceholder(
								new Label("No Other-Organisations in current selected Operation available"));
					}
					this.accordionSubTables.setExpandedPane(this.tpOtherOrg);
				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(loadingOtherOrgByOperationTask);
				controllerThreadProgressBarDialog.bindProgressBarOnTask(taskOtherOrgLoader);

				new Thread(loadingOtherOrgByOperationTask).start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@FXML
	private void onClickBtnLoadOperationVehicles(ActionEvent event) {
		Operation selectedOperation = this.tvOperationData.getSelectionModel().getSelectedItem();
		if (selectedOperation != null) {
			try {
				// Open ProgressBarDialog
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

				Stage stageProgressBarDialog = new Stage();
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog
						.setTitle("Loading OperationVehicles by Operation '" + selectedOperation.getTitle() + "'");
				stageProgressBarDialog.show();

				controllerThreadProgressBarDialog.unbindProgressBar();

				// Starting Threading
				Task<Void> taskOperationVehicleLoader = loadOperationVehiclesByOperation(selectedOperation.getOperationId());

				Task<Void> loadingOperationVehiclesByOperationTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						updateProgress(0, 100);
						
						Thread threadOperationVehicleLoader = new Thread(taskOperationVehicleLoader);
						threadOperationVehicleLoader.start();
						threadOperationVehicleLoader.join();

						return null;
					}
				};
				loadingOperationVehiclesByOperationTask.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
						controllerThreadProgressBarDialog.setLabelText(newValue);
					}
				});
				loadingOperationVehiclesByOperationTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();
					
					this.obsListTVOperationVehicles.clear();
					ArrayList<OperationVehicle> tempListOfVehicles = OperationVehicleHandler.getInstance()
							.getVehiclesListByOperationId();

					if (tempListOfVehicles != null) {
						this.obsListTVOperationVehicles.addAll(tempListOfVehicles);

						this.tvVehicleData.setItems(this.obsListTVOperationVehicles);
						this.btnLoadOperationVehicles.setDisable(true);
						this.btnLoadAllVehicles.setDisable(false);
					} else {
						this.obsListTVOperationVehicles.clear();
						this.tvVehicleData.refresh();
						this.tvVehicleData.setPlaceholder(
								new Label("No OperationVehicles in current selected Operation available"));
					}
					this.accordionSubTables.setExpandedPane(this.tpOperationVehcile);

				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(loadingOperationVehiclesByOperationTask);
				controllerThreadProgressBarDialog.bindProgressBarOnTask(taskOperationVehicleLoader);
				
				new Thread(loadingOperationVehiclesByOperationTask).start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private Task<Void> loadOperationVehiclesByOperation(int operationId) {
		return new Task<Void>() {
			double lastProgressValue = 0;
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new OperationVehicleByOperationLoader(operationId));
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
	
	private Task<Void> loadMembersByOperation(int operationId) {
		return new Task<Void>() {
			double lastProgressValue = 0;
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new OperationMemberLoader(operationId));
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
	
	private Task<Void> loadOtherOrgByOperation(int operationId) {
		return new Task<Void>() {
			double lastProgressValue = 0;
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new OtherOrganisationByOperationLoader(operationId));
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

	@FXML
	private void onClickMItemRemoveMember(ActionEvent event) {

	}

	@FXML
	private void onClickMItemRemoveOperation(ActionEvent event) {

	}

	@FXML
	private void onClickMItemRemoveOrg(ActionEvent event) {

	}

	@FXML
	private void onClickMItemRemoveVehicle(ActionEvent event) {

	}

	@FXML
	private void onClickMItemUpdateMember(ActionEvent event) {

	}

	@FXML
	private void onClickMItemUpdateOperation(ActionEvent event) {

	}

	@FXML
	private void onClickMItemUpdateOrg(ActionEvent event) {

	}

	@FXML
	private void onClickMItemUpdateVehicle(ActionEvent event) {

	}

	private Task<Void> loadBases(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadBases = new Thread(new BaseLoader(latch));
				threadLoadBases.start();
				threadLoadBases.join();
				latch.countDown();
				return null;
			}
		};
	}

	private Task<Void> loadOperationVehicles(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadOperationVehicles = new Thread(new OperationVehicleLoader(latch));
				threadLoadOperationVehicles.start();
				threadLoadOperationVehicles.join();
				latch.countDown();
				return null;
			}
		};
	}

	private Task<Void> loadMembers(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadMembers = new Thread(new MemberLoader(latch));
				threadLoadMembers.start();
				threadLoadMembers.join();
				latch.countDown();
				return null;
			}
		};
	}

	private Task<Void> loadOperation(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadOperations = new Thread(new OperationLoader(latch));
				threadLoadOperations.start();
				threadLoadOperations.join();
				latch.countDown();
				return null;
			}
		};
	}

	private Task<Void> loadOtherOrgs(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadOtherOrgs = new Thread(new OtherOrganisationLoader(latch));
				threadLoadOtherOrgs.start();
				threadLoadOtherOrgs.join();
				latch.countDown();
				return null;
			}
		};
	}

	private Task<Void> loadOperationVehiclesWithOperations(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadOtherOrgs = new Thread(new OperationVehicleWithOperationAttrLoader(latch));
				threadLoadOtherOrgs.start();
				threadLoadOtherOrgs.join();
				latch.countDown();
				return null;
			}
		};
	}
}