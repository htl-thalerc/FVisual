package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.OperationVehicle;
import handler.CentralHandler;
import handler.OperationVehicleHandler;
import manager.OperationVehicleManager;

public class OperationVehicleLoader implements Runnable {
	private CountDownLatch countDownLatch = null;
			
	public OperationVehicleLoader(CountDownLatch latch) {
		this.countDownLatch = latch;
	}

	@Override
	public void run() {
		ArrayList<OperationVehicle> tempListVehicles = new ArrayList<OperationVehicle>();
		ArrayList<OperationVehicle> listOfVehicles = OperationVehicleManager.getInstance().getVehicles();
		for (int i = 0; i < listOfVehicles.size(); i++) {
			tempListVehicles.add(listOfVehicles.get(i));
		}
		
		OperationVehicleHandler.getInstance().setVehicleList(tempListVehicles);
		CentralHandler.getInstance().mergeFullVehicleObject();
		
		System.out.println("Finished laoding Vehicles");

		this.countDownLatch.countDown();
	}
}