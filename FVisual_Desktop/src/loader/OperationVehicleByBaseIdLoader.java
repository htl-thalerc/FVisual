package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.OperationVehicle;
import handler.OperationVehicleHandler;
import manager.OperationVehicleManager;

public class OperationVehicleByBaseIdLoader implements Runnable {
	private CountDownLatch countDownLatch =null;
	private int baseId, vehicleId;
	
	public OperationVehicleByBaseIdLoader(CountDownLatch countDownLatch, int baseId, int vehicleId) {
		this.countDownLatch = countDownLatch;
		this.baseId = baseId;
		this.vehicleId = vehicleId;
	}

	@Override
	public void run() {
		ArrayList<OperationVehicle> updatedVehicle = OperationVehicleManager.getInstance().getVehicleByIdFromBase(this.baseId, this.vehicleId);
		OperationVehicleHandler.getInstance().setUpdatedOperationVehicle(updatedVehicle.get(0));
		
		this.countDownLatch.countDown();
	}
}