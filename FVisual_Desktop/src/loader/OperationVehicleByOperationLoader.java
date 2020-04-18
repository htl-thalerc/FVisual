package loader;

import handler.OperationVehicleHandler;
import manager.OperationVehicleManager;

public class OperationVehicleByOperationLoader implements Runnable {
	private int operationId;
	
	public OperationVehicleByOperationLoader(int operationId) {
		this.operationId = operationId;
	}

	@Override
	public void run() {
		OperationVehicleHandler.getInstance().setVehicleListByOperationId(
				OperationVehicleManager.getInstance().getVehiclesFromOperation(this.operationId));
	}
}