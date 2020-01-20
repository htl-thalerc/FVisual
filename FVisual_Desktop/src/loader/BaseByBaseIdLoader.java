package loader;

import java.util.concurrent.CountDownLatch;

import bll.Base;
import handler.BaseHandler;
import manager.BaseManager;

public class BaseByBaseIdLoader implements Runnable {
	private CountDownLatch countDownLatch;
	
	public BaseByBaseIdLoader(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		Base foundedBase = BaseManager.getInstance().getBaseById(BaseHandler.getInstance().getCreatedBase().getBaseId());
		
		this.countDownLatch.countDown();
	}
}