package threadHelper;

import manager.OperationManager;

public class OtherOrgPostHandler implements Runnable {
	private int otherOrgId = 0;
	private int operationId = 0;
	
	public OtherOrgPostHandler(int otherOrgId, int operationId) {
		this.otherOrgId = otherOrgId;
		this.operationId = operationId;
	}

	@Override
	public void run() {
		OperationManager.getInstance().postOtherOrgVehicleToOperation(this.operationId, this.otherOrgId);
	}
}