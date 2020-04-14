package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import javax.swing.plaf.synth.SynthSpinnerUI;

import handler.BaseHandler;
import handler.CentralHandler;
import handler.MemberHandler;
import handler.OperationVehicleHandler;
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
import loader.OperationVehicleLoader;
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
	}

	@FXML
	private void onClickmItemOperationManagement(ActionEvent event) {
		try {
			this.removeInnerBorderPaneContent();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/OperationManagement.fxml"));
			loader.setController(new ControllerOperationManagement(this));

			this.middlePaneContent.addAll(this.innerContentBorderPane.getChildren());
			this.innerContentBorderPane.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onClickmItemBaseManagement(ActionEvent event) {
		this.removeInnerBorderPaneContent();
		this.progressbar.progressProperty().unbind();
		CountDownLatch latchGETMethods = new CountDownLatch(4);
		// Thread for each GET Method:
		Thread threadGETBase = new Thread(loadBases(latchGETMethods));
		Thread threadGETOperationVehicle = new Thread(loadOperationVehicles(latchGETMethods));
		Thread threadGETRank = new Thread(loadRanks(latchGETMethods));
		Thread threadGETMember = new Thread(loadMembers(latchGETMethods));

		Task<Void> mainTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateProgress(0, 100);
				
				int lastProgressValue = 0;
				updateProgress(0, 100);
				updateMessage("Initializing Components");
				// Set progress for bases
				threadGETBase.start();
				threadGETBase.join();
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

				// set progress for members
				updateProgress(75, 100);
				lastProgressValue = 75;
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
	
	public void removeInnerBorderPaneContent() {
		this.middlePaneContent.clear();
		this.innerContentBorderPane.getChildren().clear();
	}

	public void removeProgressbar() {
		this.mainBorderPane.getChildren().remove(this.gridPane);
	}
}