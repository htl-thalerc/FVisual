package threadHelper;

import bll.Member;
import manager.MemberManager;

public class MemberPostHandler implements Runnable {
	private Member member;

	public MemberPostHandler(Member memberToCreate) {
		this.member = memberToCreate;
	}

	@Override
	public void run() {
		MemberManager.getInstance().addMemberToBase(this.member.getBaseId(), this.member);	
	}
}