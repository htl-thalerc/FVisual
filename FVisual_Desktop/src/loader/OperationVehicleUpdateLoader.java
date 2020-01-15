package loader;

import java.util.concurrent.CountDownLatch;

import bll.OperationVehicle;
import manager.OperationVehicleManager;

public class OperationVehicleUpdateLoader implements Runnable {
	private CountDownLatch countDownLatch;
	private OperationVehicle vehicle;
	private int baseId;
	
	public OperationVehicleUpdateLoader(CountDownLatch countDownLatch, OperationVehicle vehicle, int baseId) {
		this.countDownLatch = countDownLatch;
		this.vehicle = vehicle;
		this.baseId = baseId;
	}

	@Override
	public void run() {
		OperationVehicleManager.getInstance().updateVehicleFromBase(this.baseId, this.vehicle);	
		
		this.countDownLatch.countDown();
	}
}