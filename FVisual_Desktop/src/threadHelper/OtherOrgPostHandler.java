package threadHelper;

import manager.OperationManager;

public class OtherOrgPostHandler implements Runnable {
	private int otherOrgId = 0;
	
	public OtherOrgPostHandler(int otherOrgId) {
		this.otherOrgId = otherOrgId;
	}

	@Override
	public void run() {
		OperationManager.getInstance().postOtherOrgVehicleToOperation(this.otherOrgId);
	}
}