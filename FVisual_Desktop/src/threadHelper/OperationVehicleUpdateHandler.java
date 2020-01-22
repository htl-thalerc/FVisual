package threadHelper;

import bll.OperationVehicle;
import manager.OperationVehicleManager;

public class OperationVehicleUpdateHandler implements Runnable {
	private OperationVehicle vehicle;
	private int baseId;
	
	public OperationVehicleUpdateHandler(OperationVehicle vehicle, int baseId) {
		this.vehicle = vehicle;
		this.baseId = baseId;
	}

	@Override
	public void run() {
		OperationVehicleManager.getInstance().updateVehicleFromBase(this.baseId, this.vehicle);	
	}
}