package loader;

import handler.OperationHandler;
import manager.OperationManager;

public class OperationLoader implements Runnable {

	@Override
	public void run() {
		OperationHandler.getInstance().setListOfOperations(OperationManager.getInstance().getOperations());
	}
}