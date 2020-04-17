package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.Operation;
import handler.CentralHandler;
import handler.OperationHandler;
import manager.OperationManager;

public class OperationLoader implements Runnable {
	private CountDownLatch countDownLatch = null;

	public OperationLoader(CountDownLatch latch) {
		if(latch != null) {
			this.countDownLatch = latch;	
		}
	}
	
	@Override
	public void run() {
		ArrayList<Operation> tempListOperations = new ArrayList<Operation>();
		ArrayList<Operation> listOfOperations = OperationManager.getInstance().getOperations();
		
		for (int i = 0; i < listOfOperations.size(); i++) {
			tempListOperations.add(listOfOperations.get(i));
		}
		
		OperationHandler.getInstance().setOperationList(tempListOperations);
		CentralHandler.getInstance().mergeFullOperationObject();
		
		if(this.countDownLatch != null) {
			this.countDownLatch.countDown();	
		}
	}
}