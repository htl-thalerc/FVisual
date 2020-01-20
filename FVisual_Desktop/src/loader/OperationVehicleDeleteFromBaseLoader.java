package loader;

import manager.OperationVehicleManager;

public class OperationVehicleDeleteFromBaseLoader implements Runnable {
	private int baseId, vehicleId;
	
	public OperationVehicleDeleteFromBaseLoader(int operationVehicleId, int baseId) {
		this.vehicleId = operationVehicleId;
		this.baseId = baseId;
	}

	@Override
	public void run() {
		OperationVehicleManager.getInstance().deleteVehicleFromBase(this.baseId, this.vehicleId);
	}
}