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
		
//		this.updateMessage("Posting OperationVehicles");
//		if (this.collOfOperationVehiclesToAddToOperation.size() >= 1) {
//			for (int i = 0; i < collOfOperationVehiclesToAddToOperation.size(); i++) {
//				this.updateProgress(i, reqDuringOperationCreation);
//				
//					// Create Vehicle
//					OperationVehicle vehicleToCreate = collOfOperationVehiclesToAddToOperation.get(i);
//					vehicleToCreate = this
//							.setBaseObjAndBaseIdToVehicle(collOfOperationVehiclesToAddToOperation.get(i));
//
//					Thread threadCreateOperationVehicle = new Thread(
//							new OperationVehiclePostHandler(vehicleToCreate));
//					threadCreateOperationVehicle.start();
//					threadCreateOperationVehicle.join();
//					Thread.sleep(100);
//				
//			}
//		}
//		
//		this.updateMessage("Posting Members");
//		if (this.collOfMembersToAddToBase.size() >= 1) {
//			for (int i = 0; i < collOfMembersToAddToBase.size(); i++) {
//				this.updateProgress(i, reqDuringBaseCreation);
//				if (collOfMembersToAddToBase.get(i).getBaseId() == 0
//						&& collOfMembersToAddToBase.get(i).getMemberId() != -1) {
//					// Members with no base
//					Member memberToUpdate = collOfMembersToAddToBase.get(i);
//					memberToUpdate.setRankId(memberToUpdate.getRank().getRankId());
//					memberToUpdate = this.setBaseObjAndBaseIdToMember(collOfMembersToAddToBase.get(i));
//
//					Thread threadUpdateMember = new Thread(new MemberUpdateHandler(memberToUpdate));
//					threadUpdateMember.start();
//					threadUpdateMember.join();
//					Thread.sleep(100);
//				} 
//			}	
//		}
		return null;
	}
}