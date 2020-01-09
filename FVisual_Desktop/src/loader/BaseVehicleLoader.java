package loader;

import java.util.ArrayList;

import bll.Base;
import bll.OperationVehicle;
import handler.OperationVehicleHandler;
import manager.OperationVehicleManager;

public class BaseVehicleLoader implements Runnable {
	private Base base;
	
	public BaseVehicleLoader(Base base) {
		this.base = base;
	}

	@Override
	public void run() {
		ArrayList<OperationVehicle> listOfOperationVehiclesFilteredByBase = OperationVehicleManager.getInstance()
				.getVehiclesFromBase(this.base.getBaseId());

		OperationVehicleHandler.getInstance().setVehicleListByBaseId(listOfOperationVehiclesFilteredByBase);
		
		System.out.println("Finished loading Vehicle by Base");
	}
}