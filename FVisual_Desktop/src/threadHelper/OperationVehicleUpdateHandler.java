package threadHelper;

import java.util.concurrent.CountDownLatch;

import bll.OperationVehicle;
import manager.OperationVehicleManager;

public class OperationVehicleUpdateHandler implements Runnable {
	private CountDownLatch countDownLatch;
	private OperationVehicle vehicle;
	private int baseId;
	
	public OperationVehicleUpdateHandler(CountDownLatch countDownLatch, OperationVehicle vehicle, int baseId) {
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