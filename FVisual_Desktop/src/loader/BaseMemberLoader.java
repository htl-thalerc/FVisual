package loader;

import bll.Base;
import handler.MemberHandler;
import manager.MemberManager;

public class BaseMemberLoader implements Runnable {
	private Base base;
	
	public BaseMemberLoader(Base base) {
		this.base = base;
	}
	
	@Override
	public void run() {
		MemberHandler.getInstance().setMemberListByBaseId(MemberManager.getInstance()
				.getMembersFromBase(this.base.getBaseId()));
	}
}