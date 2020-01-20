package threadHelper;

import bll.Base;
import handler.BaseHandler;
import manager.BaseManager;

public class BasePostHandler implements Runnable {
	private Base base;

	public BasePostHandler(Base baseToCreate) {
		this.base = baseToCreate;
	}

	@Override
	public void run() {
		Base createdBase = BaseManager.getInstance().postBase(this.base);
		BaseHandler.getInstance().setCreatedBase(createdBase);
	}
}