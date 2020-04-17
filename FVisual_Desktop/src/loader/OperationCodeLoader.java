package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.OperationCode;
import handler.OperationCodeHandler;
import manager.OperationCodeManager;

public class OperationCodeLoader implements Runnable {
	private CountDownLatch countDownLatch = null;

	public OperationCodeLoader(CountDownLatch latch) {
		if(latch != null) {
			this.countDownLatch = latch;	
		}
	}
	
	@Override
	public void run() {
		ArrayList<OperationCode> tempListOperationCodes = new ArrayList<OperationCode>();
		ArrayList<OperationCode> listOfOperationCodes = OperationCodeManager.getInstance().getOperationCodes();
		
		for (int i = 0; i < listOfOperationCodes.size(); i++) {
			tempListOperationCodes.add(listOfOperationCodes.get(i));
		}
		
		OperationCodeHandler.getInstance().setOperationCodeList(tempListOperationCodes);
		if(this.countDownLatch != null) {
			this.countDownLatch.countDown();	
		}
	}
}