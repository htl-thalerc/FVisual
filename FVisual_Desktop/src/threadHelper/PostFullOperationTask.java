package threadHelper;

import java.util.List;

import bll.Member;
import bll.Operation;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import controller.ControllerCreateOperationManagement;
import javafx.concurrent.Task;

public class PostFullOperationTask extends Task<Void> {
	private ControllerCreateOperationManagement controllerCreateOperationManagement = null;
	private Operation operationToCreate;
	private List<Member> collOfMembersToAddToOperation;
	private List<OperationVehicle> collOfOperationVehiclesToAddToOperation;
	private List<OtherOrganisation> collOfOtherOrgsToAddToOperation;

	public PostFullOperationTask(ControllerCreateOperationManagement controllerCreateOperationManagement, Operation operationToCreate,
			List<Member> collOfMembersToAddToOperation, List<OperationVehicle> collOfOperationVehiclesToAddToOperation,
			List<OtherOrganisation> collOfOtherOrgsToAddToOperation) {
		this.controllerCreateOperationManagement = controllerCreateOperationManagement;
		this.operationToCreate =operationToCreate;
		this.collOfMembersToAddToOperation = collOfMembersToAddToOperation;
		this.collOfOperationVehiclesToAddToOperation = collOfOperationVehiclesToAddToOperation;
		this.collOfOtherOrgsToAddToOperation = collOfOtherOrgsToAddToOperation;
	}
	
	@Override
	protected Void call() throws Exception {
		int reqDuringOperationCreation = 1; //post base once
		
		reqDuringOperationCreation+=this.collOfOtherOrgsToAddToOperation.size();
		reqDuringOperationCreation+=this.collOfOperationVehiclesToAddToOperation.size();
		reqDuringOperationCreation+=this.collOfMembersToAddToOperation.size();
		
		Thread threadBasePostLoader = new Thread(new OperationPostHandler(operationToCreate));
		threadBasePostLoader.start();
		threadBasePostLoader.join();
		Thread.sleep(100);
		
		Thread threadPostBaseToOPeration = new Thread(new BasePostOperationHandler(operationToCreate.getBase().getBaseId()));
		threadPostBaseToOPeration.start();
		threadPostBaseToOPeration.join();
		Thread.sleep(100);
		
		this.updateMessage("Posting OperationVehicles");
		if (this.collOfOperationVehiclesToAddToOperation.size() >= 1) {
			for (int i = 0; i < this.collOfOperationVehiclesToAddToOperation.size(); i++) {
				this.updateProgress(i, reqDuringOperationCreation);
					//post into Table FZG_wb_Einsatz
					OperationVehicle operationVehicleToCreate = this.collOfOperationVehiclesToAddToOperation.get(i);

					Thread threadCreateOperationVehicle = new Thread(
							postOperationVehicleToOperation(operationVehicleToCreate));
					threadCreateOperationVehicle.start();
					threadCreateOperationVehicle.join();
			}
		}
		
		this.updateMessage("Posting OtherOrganisations");
		if (this.collOfOtherOrgsToAddToOperation.size() >= 1) {
			for (int i = 0; i < this.collOfOtherOrgsToAddToOperation.size(); i++) {
				this.updateProgress(i, reqDuringOperationCreation);
					//post into Table AORG_WB_EINSATZ
					OtherOrganisation otherOrgsToCreate = this.collOfOtherOrgsToAddToOperation.get(i);

					Thread threadCreateOtherOrgs = new Thread(
							postOtherOrgToOperation(otherOrgsToCreate));
					threadCreateOtherOrgs.start();
					threadCreateOtherOrgs.join();
			}
		}
		
		this.updateMessage("Posting Members");
		if (this.collOfMembersToAddToOperation.size() >= 1) {
			for (int i = 0; i < this.collOfMembersToAddToOperation.size(); i++) {
				this.updateProgress(i, reqDuringOperationCreation);
					//post into Table MTG_WB_EINSATZ
					Member memberToCreate = this.collOfMembersToAddToOperation.get(i);
					
					Thread threadCreateMember = new Thread(postMemberToOperation(memberToCreate));
					threadCreateMember.start();
					threadCreateMember.join();
				} 
			}	
		return null;
	}
	
	private Task<Void> postOperationVehicleToOperation(OperationVehicle operationVehicleToCreate) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new OperationVehiclePostHandler(operationVehicleToCreate.getOperationVehicleId(), 
						operationVehicleToCreate.getBase().getBaseId()));
				thread.start();
				thread.join();
				return null;
			}
		};
	}
	
	private Task<Void> postOtherOrgToOperation(OtherOrganisation otherOrgToCreate) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new OtherOrgPostHandler(otherOrgToCreate.getOtherOrganisationId()));
				thread.start();
				thread.join();
				return null;
			}
		};
	}
	
	private Task<Void> postMemberToOperation(Member memberToCreate) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new MemberPostHandler(memberToCreate.getMemberId(), 
						memberToCreate.getBase().getBaseId()));
				thread.start();
				thread.join();
				return null;
			}
		};
	}
}