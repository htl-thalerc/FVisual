package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.Base;
import handler.BaseHandler;
import manager.BaseManager;

public class BaseLoader implements Runnable {
	private CountDownLatch countDownLatch = null;

	public BaseLoader(CountDownLatch latch) {
		if(latch != null) {
			this.countDownLatch = latch;	
		}
	}

	@Override
	public void run() {
		ArrayList<Base> tempList = new ArrayList<Base>();

		ArrayList<Base> listOfBases = BaseManager.getInstance().getBases();
		for (int i = 0; i < listOfBases.size(); i++) {
			tempList.add(listOfBases.get(i));
		}
		
		BaseHandler.getInstance().setBaseList(tempList);
		
		if(this.countDownLatch != null) {
			this.countDownLatch.countDown();;	
		}
	}
}