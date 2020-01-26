package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
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
		try {
			PostFullBaseTask task = new PostFullBaseTask(this.controllerCreateBaseManagement, baseToCreate, collOfMembersToAddToBase, collOfOperationVehiclesToAddToBase);
			
			/*this.progressBar.setProgress(0);
			this.progressBar.progressProperty().unbind();
			this.progressBar.progressProperty().bind(task.progressProperty());*/
			
			Thread threadTask = new Thread(task);
			threadTask.start();
			threadTask.join();
			Thread.sleep(250);	
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}