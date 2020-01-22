package loader;

import java.util.concurrent.CountDownLatch;

import bll.Base;
import handler.BaseHandler;
import manager.BaseManager;

public class BaseByBaseIdLoader implements Runnable {
	private CountDownLatch countDownLatch;
	private int baseId;
	
	public BaseByBaseIdLoader(CountDownLatch countDownLatch, int baseId) {
		this.countDownLatch = countDownLatch;
		this.baseId = baseId;
	}

	@Override
	public void run() {
		if(this.countDownLatch != null && this.baseId == 0) {
			BaseManager.getInstance().getBaseById(BaseHandler.getInstance().getCreatedBase().getBaseId());
			
			this.countDownLatch.countDown();	
		} else if(this.countDownLatch == null && this.baseId != 0) {
			Base foundedBase = BaseManager.getInstance().getBaseById(this.baseId);
			
			BaseHandler.getInstance().setBaseByBaseId(foundedBase);
		}
	}
}