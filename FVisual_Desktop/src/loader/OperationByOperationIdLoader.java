package loader;

import handler.OperationHandler;
import manager.OperationManager;

public class OperationByOperationIdLoader implements Runnable {
	private int operationId;
	
	public OperationByOperationIdLoader(int operationId) {
		this.operationId = operationId;
	}
	
	@Override
	public void run() {
		OperationHandler.getInstance().setOperationByOperationId(OperationManager.getInstance().getOperationById(this.operationId));
	}
}