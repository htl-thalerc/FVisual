package threadHelper;

import bll.Operation;
import handler.OperationHandler;
import manager.OperationManager;

public class OperationPostHandler implements Runnable {
	private Operation operation;

	public OperationPostHandler(Operation operation) {
		this.operation = operation;
	}

	@Override
	public void run() {
		OperationHandler.getInstance().setCreatedOperation(OperationManager.getInstance().postOperation(this.operation));
	}
}