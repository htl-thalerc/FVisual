package threadHelper;

import java.util.List;

import bll.Base;
import bll.Member;
import bll.OperationVehicle;
import controller.ControllerCreateBaseManagement;
import handler.BaseHandler;
import javafx.concurrent.Task;

public class PostFullBaseTask extends Task<Void>{
	private ControllerCreateBaseManagement controllerCreateBaseManagement = null;
	private Base baseToCreate;
	private List<Member> collOfMembersToAddToBase;
	private List<OperationVehicle> collOfOperationVehiclesToAddToBase;

	public PostFullBaseTask(ControllerCreateBaseManagement controllerCreateBaseManagement, Base baseToCreate,
			List<Member> collOfMembersToAddToBase, List<OperationVehicle> collOfOperationVehiclesToAddToBase) {
		this.controllerCreateBaseManagement = controllerCreateBaseManagement;
		this.baseToCreate = baseToCreate;
		this.collOfMembersToAddToBase = collOfMembersToAddToBase;
		this.collOfOperationVehiclesToAddToBase = collOfOperationVehiclesToAddToBase;
	}

	@Override
	protected Void call() throws Exception {
		int reqDuringBaseCreation = 1; //post base once
		
		reqDuringBaseCreation+=this.collOfMembersToAddToBase.size();
		reqDuringBaseCreation+=this.collOfOperationVehiclesToAddToBase.size();
		
		//this.updateMessage("Creating base");
		Thread threadBasePostLoader = new Thread(new BasePostHandler(baseToCreate));
		threadBasePostLoader.start();
		threadBasePostLoader.join();
		Thread.sleep(100);
		
		//this.updateProgress(33, nrOfPosts);

		//this.updateMessage("Creating OperationVehicles");
		if (this.collOfOperationVehiclesToAddToBase.size() >= 1) {
			for (int i = 0; i < collOfOperationVehiclesToAddToBase.size(); i++) {
				this.updateProgress(i, reqDuringBaseCreation);
				if (collOfOperationVehiclesToAddToBase.get(i).getOperationVehicleId() == -1) {
					// Create Vehicle
					OperationVehicle vehicleToCreate = collOfOperationVehiclesToAddToBase.get(i);
					vehicleToCreate = this
							.setBaseObjAndBaseIdToVehicle(collOfOperationVehiclesToAddToBase.get(i));

					Thread threadCreateOperationVehicle = new Thread(
							new OperationVehiclePostHandler(vehicleToCreate));
					threadCreateOperationVehicle.start();
					threadCreateOperationVehicle.join();
					Thread.sleep(100);
				} else if (collOfOperationVehiclesToAddToBase.get(i).getOperationVehicleId() != -1) {
					// Update Vehicle not right bec. we dont change id; we reate a new vehcile
					OperationVehicle vehicleToCreateFromExistingVehicleData = collOfOperationVehiclesToAddToBase
							.get(i);
					vehicleToCreateFromExistingVehicleData.setBase(null);
					vehicleToCreateFromExistingVehicleData.setBaseId(-1);
					vehicleToCreateFromExistingVehicleData = this
							.setBaseObjAndBaseIdToVehicle(collOfOperationVehiclesToAddToBase.get(i));

					Thread threadCreateOperationVehicle = new Thread(
							new OperationVehiclePostHandler(vehicleToCreateFromExistingVehicleData));
					threadCreateOperationVehicle.start();
					threadCreateOperationVehicle.join();
					Thread.sleep(100);
				}
			}
		}
		
		this.updateMessage("Creating Members");
		if (this.collOfMembersToAddToBase.size() >= 1) {
			for (int i = 0; i < collOfMembersToAddToBase.size(); i++) {
				this.updateProgress(i, reqDuringBaseCreation);
				if (collOfMembersToAddToBase.get(i).getBaseId() == 0
						&& collOfMembersToAddToBase.get(i).getMemberId() != -1) {
					// Members with no base
					Member memberToUpdate = collOfMembersToAddToBase.get(i);
					memberToUpdate.setRankId(memberToUpdate.getRank().getRankId());
					memberToUpdate = this.setBaseObjAndBaseIdToMember(collOfMembersToAddToBase.get(i));

					Thread threadUpdateMember = new Thread(new MemberUpdateHandler(memberToUpdate));
					threadUpdateMember.start();
					threadUpdateMember.join();
					Thread.sleep(100);
				} else if (collOfMembersToAddToBase.get(i).getBaseId() == -1
						&& collOfMembersToAddToBase.get(i).getMemberId() == -1) {
					// Complete new Member
					Member memberToCreate = collOfMembersToAddToBase.get(i);
					memberToCreate.setRankId(memberToCreate.getRank().getRankId());
					memberToCreate = this.setBaseObjAndBaseIdToMember(collOfMembersToAddToBase.get(i));

					Thread threadCreateMember = new Thread(new MemberPostHandler(memberToCreate));
					threadCreateMember.start();
					threadCreateMember.join();
					Thread.sleep(100);
				}
			}	
		}
		return null;
	}
	
	private Member setBaseObjAndBaseIdToMember(Member currMember) {
		Base createdBase = BaseHandler.getInstance().getCreatedBase();

		if (createdBase.getBaseId() != 0 && createdBase != null) {
			currMember.setBase(createdBase);
			currMember.setBaseId(createdBase.getBaseId());
			return currMember;
		} else {
			return null;
		}
	}

	private OperationVehicle setBaseObjAndBaseIdToVehicle(OperationVehicle currVehicle) {
		Base createdBase = BaseHandler.getInstance().getCreatedBase();

		if (createdBase.getBaseId() != 0 && createdBase != null) {
			currVehicle.setBase(createdBase);
			currVehicle.setBaseId(createdBase.getBaseId());
			return currVehicle;
		} else {
			return null;
		}
	}
}