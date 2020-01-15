package loader;

import java.util.concurrent.CountDownLatch;

import bll.Base;
import handler.BaseHandler;
import manager.BaseManager;

public class BasePostLoader implements Runnable {
	private CountDownLatch countDownLatch;
	private Base base;
	
	public BasePostLoader(CountDownLatch countDownLatch, Base base) {
		this.countDownLatch = countDownLatch;
		this.base = base;
	}

	@Override
	public void run() {
		Base createdBase = BaseManager.getInstance().postBase(this.base);
		BaseHandler.getInstance().setCreatedBase(createdBase);
		
		this.countDownLatch.countDown();
	}

}
