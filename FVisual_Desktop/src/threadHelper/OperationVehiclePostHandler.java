package threadHelper;

import bll.OperationVehicle;
import manager.OperationManager;
import manager.OperationVehicleManager;

public class OperationVehiclePostHandler implements Runnable {
	private OperationVehicle vehicle;
	
	private int operationId = 0;
	private int operationVehicleId = 0;
	private int baseId = 0;

	public OperationVehiclePostHandler(OperationVehicle vehicleToCreate) {
		this.vehicle = vehicleToCreate;
	}

	public OperationVehiclePostHandler(int operationId, int operationVehicleId, int baseId) {
		this.operationId = operationId;
		this.operationVehicleId = operationVehicleId;
		this.baseId = baseId;
	}

	@Override
	public void run() {
		if(this.operationId != 0 && this.operationVehicleId != 0 && this.baseId != 0) {
			OperationManager.getInstance().postOperationVehicleToOperation(this.operationId, this.operationVehicleId, this.baseId);
		} else {
			OperationVehicleManager.getInstance().addVehicleToBase(this.vehicle.getBaseId(), this.vehicle);	
		}
	}
}