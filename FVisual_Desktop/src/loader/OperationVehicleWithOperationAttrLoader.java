package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.OperationVehicle;
import handler.OperationVehicleHandler;
import manager.OperationVehicleManager;

public class OperationVehicleWithOperationAttrLoader implements Runnable {
private CountDownLatch countDownLatch = null;	
	
	public OperationVehicleWithOperationAttrLoader(CountDownLatch latch) {
		this.countDownLatch = latch;	
	}

	@Override
	public void run() {
		ArrayList<OperationVehicle> tempListVehicles = new ArrayList<OperationVehicle>();
		ArrayList<OperationVehicle> listOfVehicles = OperationVehicleManager.getInstance().getVehiclesWithOperationAttr();
		for (int i = 0; i < listOfVehicles.size(); i++) {
			tempListVehicles.add(listOfVehicles.get(i));
		}
		
		OperationVehicleHandler.getInstance().setVehicleListWithOperationAttr(tempListVehicles);
		this.countDownLatch.countDown();	
	}
}