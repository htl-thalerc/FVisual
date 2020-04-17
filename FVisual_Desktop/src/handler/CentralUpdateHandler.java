package handler;

import java.io.IOException;
import java.util.ArrayList;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
import controller.ControllerThreadProgressBarDialog;
import controller.ControllerUpdateFullBaseDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loader.BaseMemberLoader;
import loader.BaseVehicleLoader;
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
	boolean isThreadUpdateBaseInitialized = false;

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

		//Start Threading
		try {
			Thread threadVehcilesByBaseId = new Thread(new BaseVehicleLoader(selectedBase));
			threadVehcilesByBaseId.start();
			threadVehcilesByBaseId.join();

			Thread threadMembersByBaseId = new Thread(new BaseMemberLoader(selectedBase));
			threadMembersByBaseId.start();
			threadMembersByBaseId.join();
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
			
			ArrayList<Thread> listOfPostMemberThreads = new ArrayList<Thread>();
			ArrayList<Thread> listOfUpdateMemberThreads = new ArrayList<Thread>();
			ArrayList<Thread> listOfPostVehicleThreads = new ArrayList<Thread>();
			ArrayList<Thread> listOfUpdateVehicleThreads = new ArrayList<Thread>();
			
			//Set thread for update base
			if (updatedBaseData != null && updatedBaseData.isUpdated()) {
				updatedBaseData.setBaseId(selectedBase.getBaseId());
				this.setUpdatedBase(updatedBaseData);
				isThreadUpdateBaseInitialized = true;
			}
			
			//set Threads for Update member
			for (int i = 0; i < listOfUpdatedMembers.size(); i++) {
				listOfUpdatedMembers.get(i).setBaseId(listOfUpdatedMembers.get(i).getBase().getBaseId());
				listOfUpdatedMembers.get(i).setRankId(listOfUpdatedMembers.get(i).getRank().getRankId());
				
				if (listOfUpdatedMembers.get(i).getMemberId() == -1 && listOfUpdatedMembers.get(i).isUpdated()) {
					Thread threadPostMember = new Thread(new MemberPostHandler(listOfUpdatedMembers.get(i)));
					listOfPostMemberThreads.add(threadPostMember);
				} else if(listOfUpdatedMembers.get(i).getMemberId() != -1 && listOfUpdatedMembers.get(i).isUpdated()) {
					Thread threadUpdateMember = new Thread(new MemberUpdateHandler(listOfUpdatedMembers.get(i)));
					listOfUpdateMemberThreads.add(threadUpdateMember);
				}
			}
			
			//set threads for Update vehicle
			for (int i = 0; i < listOfUpdatedVehicles.size(); i++) {
				if (listOfUpdatedVehicles.get(i).getOperationVehicleId() == -1 && listOfUpdatedVehicles.get(i).isUpdated()) {
					Thread threadPostVehicle = new Thread(
							new OperationVehiclePostHandler(listOfUpdatedVehicles.get(i)));
					listOfPostVehicleThreads.add(threadPostVehicle);
				} else if(listOfUpdatedVehicles.get(i).getOperationVehicleId() != -1 && listOfUpdatedVehicles.get(i).isUpdated()) {
					Thread threadUpdateVehicle = new Thread(new OperationVehicleUpdateHandler(
							listOfUpdatedVehicles.get(i), this.currBaseToUpdate.getBaseId()));
					listOfUpdateVehicleThreads.add(threadUpdateVehicle);
				}
			}
			
			//Open Dialog
			FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
			ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
			loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);

			Stage stageProgressBarDialog = new Stage();
			try {
				Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
				stageProgressBarDialog.setScene(sceneProgressBarDialog);
				stageProgressBarDialog.setTitle("Update Base, Member(s) and Vehicle(s)");
				stageProgressBarDialog.show();	
				stageProgressBarDialog.centerOnScreen();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			
			controllerThreadProgressBarDialog.unbindProgressBar();
			
			Task<Void> mainUpdateTask = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					updateProgress(0, 100);
					int lastProgressValue = 0;
					updateProgress(0, 100);
					
					//Start thread for update Base
					lastProgressValue = 20;
					
					if(isThreadUpdateBaseInitialized) {
						Thread threadUpdateBase = new Thread(new BaseUpdateHandler(updatedBaseData));
						threadUpdateBase.start();
						threadUpdateBase.join();
						
						Thread.sleep(400);
					} else {
						//used that at least a little bit of progress is shwon
						for(int i=0;i<4;i++) {
							lastProgressValue += 5;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					}
					updateProgress(lastProgressValue, 100);
					
					//Start thread for update/post Member(s) --> in total 40% of progressbar (from 20%-60%)
					int nrOfPostMemberThreads = listOfPostMemberThreads.size();
					int nrOfUpdateMemberThreads = listOfUpdateMemberThreads.size();
					
					int progressIncreasePerMemberThread = 0;
					
					if(nrOfPostMemberThreads > 0 && nrOfUpdateMemberThreads == 0) {
						progressIncreasePerMemberThread = (40 / nrOfPostMemberThreads);
						
						for(int i=0;i<nrOfPostMemberThreads;i++) {
							listOfPostMemberThreads.get(i).start();
							listOfPostMemberThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerMemberThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					} else if(nrOfPostMemberThreads > 0 && nrOfUpdateMemberThreads > 0) {
						int totalNrOfPostAndUpdateThreads = nrOfPostMemberThreads;
						totalNrOfPostAndUpdateThreads += nrOfUpdateMemberThreads;
						progressIncreasePerMemberThread = (40 / totalNrOfPostAndUpdateThreads);
						
						for(int i=0;i<nrOfPostMemberThreads;i++) {
							listOfPostMemberThreads.get(i).start();
							listOfPostMemberThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerMemberThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
						for(int i=0;i<nrOfUpdateMemberThreads;i++) {
							listOfUpdateMemberThreads.get(i).start();
							listOfUpdateMemberThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerMemberThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					} else if(nrOfPostMemberThreads == 0 && nrOfUpdateMemberThreads > 0) {
						progressIncreasePerMemberThread = (40 / nrOfUpdateMemberThreads);
						
						for(int i=0;i<nrOfUpdateMemberThreads;i++) {
							listOfUpdateMemberThreads.get(i).start();
							listOfUpdateMemberThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerMemberThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					} else if(nrOfPostMemberThreads == 0 && nrOfUpdateMemberThreads == 0) {
						//used that at least a little bit of progress is shwon
						for(int i=0;i<4;i++) {
							lastProgressValue += 10;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					}
					
					//Start thread for update/post Vehicle(s) --> in total 40% of progressbar (from 60%-100%)
					lastProgressValue = 60;
					updateProgress(60, 100);
					int nrOfPostVehicleThreads = listOfPostVehicleThreads.size();
					int nrOfUpdateVehicleThreads = listOfUpdateVehicleThreads.size();
					
					int progressIncreasePerVehicleThread = 0;
					
					if(nrOfPostVehicleThreads > 0 && nrOfUpdateVehicleThreads == 0) {
						progressIncreasePerVehicleThread = (40 / nrOfPostVehicleThreads);
						
						for(int i=0;i<nrOfPostVehicleThreads;i++) {
							listOfPostVehicleThreads.get(i).start();
							listOfPostVehicleThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerVehicleThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					} else if(nrOfPostVehicleThreads > 0 && nrOfUpdateVehicleThreads > 0) {
						int totalNrOfPostAndUpdateThreads = nrOfPostVehicleThreads;
						totalNrOfPostAndUpdateThreads += nrOfUpdateVehicleThreads;
						progressIncreasePerVehicleThread = (40 / totalNrOfPostAndUpdateThreads);
						
						for(int i=0;i<nrOfPostVehicleThreads;i++) {
							listOfPostVehicleThreads.get(i).start();
							listOfPostVehicleThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerVehicleThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
						for(int i=0;i<nrOfUpdateVehicleThreads;i++) {
							listOfUpdateVehicleThreads.get(i).start();
							listOfUpdateVehicleThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerVehicleThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					} else if(nrOfPostVehicleThreads == 0 && nrOfUpdateVehicleThreads > 0) {
						progressIncreasePerVehicleThread = (40 / nrOfUpdateVehicleThreads);
						
						for(int i=0;i<nrOfUpdateVehicleThreads;i++) {
							listOfUpdateVehicleThreads.get(i).start();
							listOfUpdateVehicleThreads.get(i).join();
							
							lastProgressValue += progressIncreasePerVehicleThread;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					} else if(nrOfPostVehicleThreads == 0 && nrOfUpdateVehicleThreads == 0) {
						//used that at least a little bit of progress is shwon
						for(int i=0;i<4;i++) {
							lastProgressValue += 10;
							updateProgress(lastProgressValue, 100);
							
							Thread.sleep(100);
						}
					}
					
					updateProgress(100, 100);
					updateMessage("Finising");
					Thread.sleep(500);
					
					return null;	
				}
			};
			mainUpdateTask.messageProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
					controllerThreadProgressBarDialog.setLabelText(newValue);
				}
			});
			mainUpdateTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();
			});
			controllerThreadProgressBarDialog.bindProgressBarOnTask(mainUpdateTask);
			try {
				Thread mainThread = new Thread(mainUpdateTask);
				mainThread.start();
				mainThread.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//Single Operation Vehicle update
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

				//Open ProgressBarDialog
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);
				
				Stage stageProgressBarDialog = new Stage();
				try {
					Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
					stageProgressBarDialog.setScene(sceneProgressBarDialog);
					stageProgressBarDialog.setTitle("Update OperationVehilce '" + updatedOperationVehicle.getDescription() + "'");
					stageProgressBarDialog.show();	
					stageProgressBarDialog.centerOnScreen();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
				
				controllerThreadProgressBarDialog.unbindProgressBar();

				//Starting Threading
				Thread updateOperationVehicleThread = new Thread(new OperationVehicleUpdateHandler(
						updatedOperationVehicle, this.currBaseToUpdate.getBaseId()));
				
				Task<Void> manageUpdateOperationVehicleTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						updateProgress(0, 100);
						int lastProgressValue = 0;
						updateProgress(0, 100);
						
						//Start update thread
						updateOperationVehicleThread.start();
						updateOperationVehicleThread.join();

						// set progress for Members
						for (int i = 0; i < 10; i++) {
							lastProgressValue += (i*10);
							updateProgress(lastProgressValue, 100);
							Thread.sleep(100);
						}
						
						updateProgress(100, 100);
						updateMessage("Finising");
						Thread.sleep(500);
						
						return null;
					}
				};
				manageUpdateOperationVehicleTask.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
						controllerThreadProgressBarDialog.setLabelText(newValue);
					}
				});
				manageUpdateOperationVehicleTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();
				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(manageUpdateOperationVehicleTask);
				try {
					Thread mainThread = new Thread(manageUpdateOperationVehicleTask);
					mainThread.start();
					mainThread.join();
				} catch(InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	//Single Member Update
	public void initUpdateMemberDialog(Member selectedMember) {
		ArrayList<Member> tempList = new ArrayList<Member>();
		tempList.add(selectedMember);
		Stage stage = this.initUpdateFullBaseDialog(true);
		this.controllerUpdateFullBaseDialog.selectTabUpdateMember();
		this.controllerUpdateFullBaseDialog.setListOfMembers(tempList);
		stage.showAndWait();

		if (this.controllerUpdateFullBaseDialog.getBtnSaveState()) {
			//Preparing member data
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
				
				//Open ProgressBarDialog
				FXMLLoader loaderProgressBarDialog = CentralHandler.loadFXML("/gui/ThreadProgressBarDialog.fxml");
				ControllerThreadProgressBarDialog controllerThreadProgressBarDialog = new ControllerThreadProgressBarDialog();
				loaderProgressBarDialog.setController(controllerThreadProgressBarDialog);
				
				Stage stageProgressBarDialog = new Stage();
				try {
					Scene sceneProgressBarDialog = new Scene(loaderProgressBarDialog.load());
					stageProgressBarDialog.setScene(sceneProgressBarDialog);
					stageProgressBarDialog.setTitle("Update Member '" + updatedMember.getUsername() + "'");
					stageProgressBarDialog.show();	
					stageProgressBarDialog.centerOnScreen();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
				
				controllerThreadProgressBarDialog.unbindProgressBar();

				//Starting Threading
				Thread updateMemberThead = new Thread(new MemberUpdateHandler(updatedMember));
				
				Task<Void> manageUpdateMemberTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						updateProgress(0, 100);
						int lastProgressValue = 0;
						updateProgress(0, 100);
						
						//Start update thread
						updateMemberThead.start();
						updateMemberThead.join();

						// set progress for Members
						for (int i = 0; i < 10; i++) {
							lastProgressValue += (i*10);
							updateProgress(lastProgressValue, 100);
							Thread.sleep(100);
						}
						
						updateProgress(100, 100);
						updateMessage("Finising");
						Thread.sleep(1000);
						
						return null;
					}
				};
				manageUpdateMemberTask.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
						controllerThreadProgressBarDialog.setLabelText(newValue);
					}
				});
				manageUpdateMemberTask.setOnSucceeded(e -> {
					stageProgressBarDialog.close();
				});
				controllerThreadProgressBarDialog.bindProgressBarOnTask(manageUpdateMemberTask);
				try {
					Thread mainThread = new Thread(manageUpdateMemberTask);
					mainThread.start();
					mainThread.join();
				} catch(InterruptedException ex) {
					ex.printStackTrace();
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