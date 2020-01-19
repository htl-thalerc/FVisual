package threadHelper;

import bll.OperationVehicle;
import manager.OperationVehicleManager;

public class OperationVehiclePostHandler implements Runnable {
	private OperationVehicle vehicle;

	public OperationVehiclePostHandler(OperationVehicle vehicleToCreate) {
		this.vehicle = vehicleToCreate;
	}

	@Override
	public void run() {
		OperationVehicleManager.getInstance().addVehicleToBase(this.vehicle.getBaseId(), this.vehicle);
	}
}