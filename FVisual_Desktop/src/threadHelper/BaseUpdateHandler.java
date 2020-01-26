package threadHelper;

import bll.Base;
import manager.BaseManager;

public class BaseUpdateHandler implements Runnable {
	private Base base;

	public BaseUpdateHandler(Base base) {
		this.base = base;
	}

	@Override
	public void run() {
		BaseManager.getInstance().putBase(this.base);
	}
}