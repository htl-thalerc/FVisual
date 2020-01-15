package loader;

import java.util.ArrayList;

import bll.Base;
import bll.Member;
import handler.MemberHandler;
import manager.MemberManager;

public class BaseMemberLoader implements Runnable {
	private Base base;
	
	public BaseMemberLoader(Base base) {
		this.base = base;
	}
	
	@Override
	public void run() {
		ArrayList<Member> listOfMembersFilteredByBase = MemberManager.getInstance()
				.getMembersFromBase(this.base.getBaseId());

		MemberHandler.getInstance().setMemberListByBaseId(listOfMembersFilteredByBase);
	}
}