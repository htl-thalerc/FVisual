package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import handler.BaseHandler;
import handler.CentralHandler;
import handler.MemberHandler;
import handler.OperationCodeHandler;
import handler.OperationHandler;
import handler.OperationTypeHandler;
import handler.OperationVehicleHandler;
import handler.OtherOrganisationHandler;
import handler.RankHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import loader.BaseLoader;
import loader.MemberLoader;
import loader.OperationCodeLoader;
import loader.OperationLoader;
import loader.OperationTypeLoader;
import loader.OperationVehicleLoader;
import loader.OtherOrganisationLoader;
import loader.RankLoader;

public class ControllerMainframe implements Initializable {
	@FXML
	private MenuItem mItemOperationManagement, mItemBaseManagement, mItemProfileSettings, mItemLogout, mItemExceptions;
	@FXML
	private Label lblMessage, lbStatusbar;
	@FXML
	private BorderPane innerContentBorderPane, mainBorderPane;
	@FXML
	private ProgressBar progressbar;
	@FXML
	private GridPane gridPane;

	private CentralHandler ch;
	private ArrayList<Node> middlePaneContent = new ArrayList<>();
	private static Stage currentStage;
	
	private boolean isBasePaneLoaded = false;
	private boolean isOperationPaneLoaded = false;

	static void setStage(Stage stage) {
		currentStage = stage;

		currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.progressbar.setStyle("-fx-accent: red;");
	}

	@FXML
	private void onClickmItemOperationManagement(ActionEvent event) {
		this.removeInnerBorderPaneContent();
		currentStage.setTitle("Operation-Management");
		this.isOperationPaneLoaded = true;
		if(this.isBasePaneLoaded) {
			this.addProgressbar();
		}
		this.progressbar.progressProperty().unbind();
		CountDownLatch latchGETMethods = new CountDownLatch(8);
		
		Task<Void> mainTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateProgress(0, 100);
				// Thread for each GET Method:
				Thread threadGETOperationTypes = null;
				Thread threadGETOperationCodes = null;
				Thread threadGETRanks = null;
				if(OperationTypeHandler.getInstance().getOperationTypeList().size() == 0) {
					threadGETOperationTypes = new Thread(loadOperationTypes(latchGETMethods));	
				} else {
					latchGETMethods.countDown();
				}
				if(OperationCodeHandler.getInstance().getOperationCodeList().size() == 0) {
					threadGETOperationCodes = new Thread(loadOperationCodes(latchGETMethods));	
				} else {
					latchGETMethods.countDown();
				}
				if(RankHandler.getInstance().getRankList().size() == 0) {
					threadGETRanks = new Thread(loadRanks(latchGETMethods));	
				} else {
					latchGETMethods.countDown();
				}
				Thread threadGETBases = new Thread(loadBases(latchGETMethods));
				Thread threadGETOperations = new Thread(loadOperation(latchGETMethods));
				Thread threadGETMembers = new Thread(loadMembers(latchGETMethods));
				Thread threadGETOperationVehicles = new Thread(loadOperationVehicles(latchGETMethods));
				Thread threadGETOtherOrgs = new Thread(loadOtherOrgs(latchGETMethods));
				
				int lastProgressValue = 0;
				updateMessage("Initializing Components");
				
				//set progress for operationTypes
				updateProgress(0, 100);
				if(threadGETOperationTypes != null) {
					threadGETOperationTypes.start();
					threadGETOperationTypes.join();	
					updateMessage("Loading OperationTypes from Database");
					int nrOfOperationTypeObj =  OperationTypeHandler.getInstance().getOperationTypeList().size();
					double processIncreasePerOperationTypeObj = (10 / nrOfOperationTypeObj);
					if(processIncreasePerOperationTypeObj < 1) {
						nrOfOperationTypeObj = 5;
						processIncreasePerOperationTypeObj = 1;
					}
					for(int i=0;i<nrOfOperationTypeObj;i++) {
						lastProgressValue += processIncreasePerOperationTypeObj;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}
				} else {
					updateMessage("Loading OperationTypes from Memory");
					for(int i=0;i<5;i++) {
						lastProgressValue += 2;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}
				}
				
				//set progress for operationCodes
				lastProgressValue = 10;
				updateProgress(lastProgressValue, 100);
				if(threadGETOperationCodes != null) {
					updateMessage("Loading OperationCodes from Database");
					lastProgressValue = 10;
					threadGETOperationCodes.start();
					threadGETOperationCodes.join();	
					int nrOfOperationCodeObj =  OperationCodeHandler.getInstance().getOperationCodeList().size();
					double processIncreasePerOperationCodeObj = (10 / nrOfOperationCodeObj);
					if(processIncreasePerOperationCodeObj < 1) {
						nrOfOperationCodeObj = 5;
						processIncreasePerOperationCodeObj = 1;
					}
					for(int i=0;i<nrOfOperationCodeObj;i++) {
						lastProgressValue += processIncreasePerOperationCodeObj;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}
				} else {
					updateMessage("Loading OperationCodes from Memory");
					for(int i=0;i<5;i++) {
						lastProgressValue += 2;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}
				}
				
				//Set progress for operation
				lastProgressValue = 20;
				updateProgress(lastProgressValue, 100);
				updateMessage("Loading Bases from Database");
				threadGETBases.start();
				threadGETBases.join();
				
				int nrOfBaseObj = BaseHandler.getInstance().getBaseList().size();
				double processIncreasePerBaseObj = (10 / nrOfBaseObj);
				if(processIncreasePerBaseObj < 1) {
					nrOfBaseObj = 10;
					processIncreasePerBaseObj = 2.5;
				}
				for(int i=0;i<nrOfBaseObj;i++) {
					lastProgressValue += processIncreasePerBaseObj;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				//Set progress for operation
				lastProgressValue = 35;
				updateProgress(lastProgressValue, 100);
				updateMessage("Loading Operations from Database");
				threadGETOperations.start();
				threadGETOperations.join();
				
				int nrOfOperationObj =  OperationHandler.getInstance().getOperationList().size();
				double processIncreasePerOperationObj = (15 / nrOfOperationObj);
				if(processIncreasePerOperationObj < 1) {
					nrOfOperationObj = 10;
					processIncreasePerOperationObj = 2.5;
				}
				for(int i=0;i<nrOfOperationObj;i++) {
					lastProgressValue += processIncreasePerOperationObj;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				// set progress for rank
				lastProgressValue = 45;
				updateProgress(lastProgressValue, 100);
				if(threadGETRanks != null) {
					updateMessage("Loading Ranks from Database");
					threadGETRanks.start();
					threadGETRanks.join();	
					int nrOfRankObj =  RankHandler.getInstance().getRankList().size();
					double processIncreasePerRankObj = (10 / nrOfRankObj);
					if(processIncreasePerRankObj < 1) {
						nrOfRankObj = 5;
						processIncreasePerRankObj = 1;
					}
					for(int i=0;i<nrOfRankObj;i++) {
						lastProgressValue += processIncreasePerRankObj;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}
				} else {
					updateMessage("Loading Ranks from Memory");
					for(int i=0;i<5;i++) {
						lastProgressValue += 2;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}
				}
				
				//set progress for members
				lastProgressValue = 55;
				updateProgress(lastProgressValue, 100);
				updateMessage("Loading Members from Database");
				threadGETMembers.start();
				threadGETMembers.join();
				
				int nrOfMemberObj =  OperationHandler.getInstance().getOperationList().size();
				double processIncreasePerMemberObj = (15 / nrOfMemberObj);
				if(processIncreasePerMemberObj < 1) {
					nrOfMemberObj = 10;
					processIncreasePerMemberObj = 1.5;
				}
				for(int i=0;i<nrOfMemberObj;i++) {
					lastProgressValue += processIncreasePerMemberObj;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				//set progress for ov
				lastProgressValue = 70;
				updateProgress(lastProgressValue, 100);
				updateMessage("Loading OperationVehicles from Database");
				threadGETOperationVehicles.start();
				threadGETOperationVehicles.join();
				
				int nrOfVehicleObj =  OperationVehicleHandler.getInstance().getVehicleList().size();
				double processIncreasePerVehicleObj = (15 / nrOfVehicleObj);
				if(processIncreasePerVehicleObj < 1) {
					nrOfVehicleObj = 10;
					processIncreasePerVehicleObj = 1.5;
				}
				for(int i=0;i<nrOfVehicleObj;i++) {
					lastProgressValue += processIncreasePerVehicleObj;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				//set progress for otherorgs
				lastProgressValue = 85;
				updateProgress(85, 100);
				updateMessage("Loading Other-Organisations from Database");
				threadGETOtherOrgs.start();
				threadGETOtherOrgs.join();
				
				int nrOfOtherOrgObj =  OtherOrganisationHandler.getInstance().getOtherOrganisationList().size();
				double processIncreasePerOtherOrgObj = (15 / nrOfOtherOrgObj);
				if(processIncreasePerOtherOrgObj < 1) {
					nrOfOtherOrgObj = 10;
					processIncreasePerOtherOrgObj = 1.5;
				}
				for(int i=0;i<nrOfOtherOrgObj;i++) {
					lastProgressValue += processIncreasePerOtherOrgObj;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				latchGETMethods.await();

				updateProgress(99, 100);
				updateMessage("");
				updateProgress(100, 100);
				
				return null;
			}
		};
		mainTask.messageProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
				lbStatusbar.setText(newValue);
			}
		});
		mainTask.setOnSucceeded(e -> {
			try {
				this.removeInnerBorderPaneContent();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/gui/OperationManagement.fxml"));
				loader.setController(new ControllerOperationManagement(this));

				this.middlePaneContent.addAll(this.innerContentBorderPane.getChildren());
				this.innerContentBorderPane.getChildren().add(loader.load());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		this.progressbar.progressProperty().bind(mainTask.progressProperty());
		new Thread(mainTask).start();
	}

	@FXML
	private void onClickmItemBaseManagement(ActionEvent event) {
		this.removeInnerBorderPaneContent();
		currentStage.setTitle("Base-Management");
		this.isBasePaneLoaded = true;
		if(this.isOperationPaneLoaded) {
			this.addProgressbar();
		}
		this.progressbar.progressProperty().unbind();
		CountDownLatch latchGETMethods = new CountDownLatch(4);
		

		Task<Void> mainTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateProgress(0, 100);
				// Thread for each GET Method:
				Thread threadGETBase = new Thread(loadBases(latchGETMethods));
				Thread threadGETOperationVehicle = new Thread(loadOperationVehicles(latchGETMethods));
				Thread threadGETRank = null;
				if(RankHandler.getInstance().getRankList().size() == 0) {
					threadGETRank = new Thread(loadRanks(latchGETMethods));
				}
				Thread threadGETMember = new Thread(loadMembers(latchGETMethods));
				
				int lastProgressValue = 0;
				updateProgress(0, 100);
				updateMessage("Initializing Components");
				
				// Set progress for bases
				threadGETBase.start();
				threadGETBase.join();
				updateMessage("Loading Bases from Database");
				double progressIncreasePerBaseObject = (25 / BaseHandler.getInstance().getBaseList().size());
				int nrOfBaseObjects = BaseHandler.getInstance().getBaseList().size();
				// Progressbar lags when to many objects are there
				if (progressIncreasePerBaseObject < 1) {
					progressIncreasePerBaseObject = 1;
					nrOfBaseObjects = 25;
				}
				for (int i = 0; i < nrOfBaseObjects; i++) {
					lastProgressValue += progressIncreasePerBaseObject;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}

				// set progress for operationvehicles
				updateProgress(25, 100);
				lastProgressValue = 25;
				updateMessage("Loading OperationVehicles from Database");
				threadGETOperationVehicle.start();
				threadGETOperationVehicle.join();
				double progressIncreasePerOperationVehicleObject = (25
						/ OperationVehicleHandler.getInstance().getVehicleList().size());
				int nrOfOperationVehicleObjects = OperationVehicleHandler.getInstance().getVehicleList().size();
				// Progressbar lags when to many objects are there
				if (progressIncreasePerOperationVehicleObject < 1) {
					progressIncreasePerOperationVehicleObject = 1;
					nrOfOperationVehicleObjects = 25;
				}
				for (int i = 0; i < nrOfOperationVehicleObjects; i++) {
					lastProgressValue += progressIncreasePerOperationVehicleObject;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}

				// set progress for ranks
				updateProgress(50, 100);
				lastProgressValue = 50;
				
				if(threadGETRank != null) {
					updateMessage("Loading Ranks from Database");
					threadGETRank.start();
					threadGETRank.join();
					double progressIncreasePerRankObject = (25 / RankHandler.getInstance().getRankList().size());
					int nrOfRankObjects = RankHandler.getInstance().getRankList().size();
					if (progressIncreasePerRankObject < 1) {
						progressIncreasePerRankObject = 1;
						nrOfRankObjects = 25;
					}
					for (int i = 0; i < nrOfRankObjects; i++) {
						lastProgressValue += progressIncreasePerRankObject;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}	
				} else {
					updateMessage("Loading Ranks from Memory");
					for (int i = 0; i < 5; i++) {
						lastProgressValue += 5;
						updateProgress(lastProgressValue, 100);
						Thread.sleep(100);
					}
				}

				// set progress for members
				updateProgress(75, 100);
				lastProgressValue = 75;
				updateMessage("Loading Members from Database");
				threadGETMember.start();
				threadGETMember.join();
				double progressIncreasePerMemberObject = (25 / MemberHandler.getInstance().getMemberList().size());
				int nrOfMemberObjects = MemberHandler.getInstance().getMemberList().size();
				if (progressIncreasePerMemberObject < 1) {
					progressIncreasePerMemberObject = 1;
					nrOfMemberObjects = 25;
				}
				for (int i = 0; i < nrOfMemberObjects; i++) {
					lastProgressValue += progressIncreasePerMemberObject;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				latchGETMethods.await();

				updateProgress(99, 100);
				updateMessage("");
				updateProgress(100, 100);

				return null;
			}
		};
		mainTask.messageProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
				lbStatusbar.setText(newValue);
			}
		});
		mainTask.setOnSucceeded(e -> {
			try {
				this.removeInnerBorderPaneContent();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/gui/BaseManagement.fxml"));
				loader.setController(new ControllerBaseManagement(this));

				this.middlePaneContent.addAll(this.innerContentBorderPane.getChildren());
				this.innerContentBorderPane.getChildren().add(loader.load());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		this.progressbar.progressProperty().bind(mainTask.progressProperty());
		new Thread(mainTask).start();
	}

	@FXML
	private void onClickmItemProfileSettings(ActionEvent event) throws IOException {
		ch = CentralHandler.getInstance();
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/gui/EditProfile.fxml"));
		fxmlLoader.setController(new ControllerEditProfile());
		Scene scene = new Scene(fxmlLoader.load(), 476, 657);
		Stage stage = new Stage();
		stage.setScene(scene);
		ControllerEditProfile.setStage(stage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
		// if(ch.getMember() == null) {
		// currentStage.close();
		// }
	}

	@FXML
	private void onClickmItemLogout(ActionEvent event) {
		currentStage.close();
	}

	@FXML
	private void onClickmItemExceptions(ActionEvent event) {
		FXMLLoader loader = CentralHandler.loadFXML("/gui/ExceptionDialog.fxml");
		loader.setController(new ControllerExeptionsDialog(this));

		try {
			Stage curStage = new Stage();
			Scene scene = new Scene(loader.load());
			curStage.setTitle("List of Exeptions");
			curStage.setScene(scene);
			curStage.initModality(Modality.APPLICATION_MODAL);

			curStage.showAndWait();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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

	private Task<Void> loadRanks(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadRanks = new Thread(new RankLoader(latch));
				threadLoadRanks.start();
				threadLoadRanks.join();
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
	
	private Task<Void> loadOperationTypes(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadOperationTypes = new Thread(new OperationTypeLoader(latch));
				threadLoadOperationTypes.start();
				threadLoadOperationTypes.join();
				latch.countDown();
				return null;
			}
		};
	}
	
	private Task<Void> loadOperationCodes(CountDownLatch latch) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread threadLoadOperationCodes = new Thread(new OperationCodeLoader(latch));
				threadLoadOperationCodes.start();
				threadLoadOperationCodes.join();
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
	
	public void removeInnerBorderPaneContent() {
		this.middlePaneContent.clear();
		this.innerContentBorderPane.getChildren().clear();
	}

	public void removeProgressbar() {
		this.mainBorderPane.getChildren().remove(this.gridPane);
	}
	
	public void addProgressbar() {
		this.mainBorderPane.getChildren().add(this.gridPane);
	}
}