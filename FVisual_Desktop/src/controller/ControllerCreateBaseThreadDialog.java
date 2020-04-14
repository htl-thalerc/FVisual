package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import threadHelper.PostFullBaseTask;

public class ControllerCreateBaseThreadDialog implements Initializable {
	@FXML 
	private Label lbStatusbar;
	@FXML
	private ProgressBar progressBar;
	
	private ControllerCreateBaseManagement controllerCreateBaseManagement;
	private Base baseToCreate;
	private List<Member> collOfMembersToAddToBase;
	private List<OperationVehicle> collOfOperationVehiclesToAddToBase;
	
	public ControllerCreateBaseThreadDialog(ControllerCreateBaseManagement controllerCreateBaseManagement, Base baseToCreate, List<Member> collOfMembersToAddToBase, List<OperationVehicle> collOfOperationVehiclesToAddToBase) {
		this.controllerCreateBaseManagement = controllerCreateBaseManagement;
		this.baseToCreate = baseToCreate;
		this.collOfMembersToAddToBase = collOfMembersToAddToBase;
		this.collOfOperationVehiclesToAddToBase = collOfOperationVehiclesToAddToBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public Runnable postFullBase() {
		Thread threadPost = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				System.out.println("called");
				Thread postFullBase = new Thread(new PostFullBaseTask(controllerCreateBaseManagement, baseToCreate, collOfMembersToAddToBase, collOfOperationVehiclesToAddToBase));
				postFullBase.start();
				postFullBase.join();
				return null;
			}
		});
		Task<Void> postTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				System.out.println("Called2");
				updateProgress(0, 100);
				int lastProgressValue = 0;
				
				threadPost.start();
				threadPost.join();
				
				//Set progress for base
				lastProgressValue = 20;
				updateProgress(lastProgressValue, 100);
				
				//set progress for OperationVehicles
				int nrOfOperationVehiclesToCreate = collOfOperationVehiclesToAddToBase.size();
				if(nrOfOperationVehiclesToCreate == 0) {
					nrOfOperationVehiclesToCreate = 1;
				}
				double progressIncreasePerOperationVehicleObject = (40 / nrOfOperationVehiclesToCreate);
				
				for (int i = 0; i < nrOfOperationVehiclesToCreate; i++) {
					lastProgressValue += progressIncreasePerOperationVehicleObject;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				//set progress for Members
				lastProgressValue = 60;
				updateProgress(lastProgressValue, 100);
				int nrOfMembersToCreate = collOfMembersToAddToBase.size();
				if(nrOfMembersToCreate == 0) {
					nrOfMembersToCreate = 1;
				}
				double progressIncreasePerMemberObject = (40 / nrOfMembersToCreate);
				
				for (int i = 0; i < nrOfMembersToCreate; i++) {
					lastProgressValue += progressIncreasePerMemberObject;
					updateProgress(lastProgressValue, 100);
					Thread.sleep(100);
				}
				
				Thread.sleep(10000);
				updateProgress(100, 100);
				
				return null;
			}
		};
		new Thread(postTask).start();
		return null;
	}
}