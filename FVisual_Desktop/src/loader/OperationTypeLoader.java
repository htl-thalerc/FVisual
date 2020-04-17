package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.OperationType;
import handler.OperationTypeHandler;
import manager.OperationTypeManager;

public class OperationTypeLoader implements Runnable {
	private CountDownLatch countDownLatch = null;

	public OperationTypeLoader(CountDownLatch latch) {
		if(latch != null) {
			this.countDownLatch = latch;	
		}
	}
	
	@Override
	public void run() {
		ArrayList<OperationType> tempListOperationTypes = new ArrayList<OperationType>();
		ArrayList<OperationType> listOfOperationTypes = OperationTypeManager.getInstance().getOperationTypes();
		
		for (int i = 0; i < listOfOperationTypes.size(); i++) {
			tempListOperationTypes.add(listOfOperationTypes.get(i));
		}
		
		OperationTypeHandler.getInstance().setOperationTypeList(tempListOperationTypes);
		if(this.countDownLatch != null) {
			this.countDownLatch.countDown();	
		}
	}
}