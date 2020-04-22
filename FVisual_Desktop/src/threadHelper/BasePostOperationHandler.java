package threadHelper;

import manager.OperationManager;

public class BasePostOperationHandler implements Runnable {
	private int baseId;
	
	public BasePostOperationHandler(int baseId) {
		this.baseId = baseId;
	}

	@Override
	public void run() {
		OperationManager.getInstance().postBaseToOperation(this.baseId);
	}
}