package threadHelper;

import bll.Member;
import manager.MemberManager;

public class MemberUpdateHandler implements Runnable {
	private Member member;
	
	public MemberUpdateHandler(Member memberToUpdate) {
		this.member = memberToUpdate;
	}

	@Override
	public void run() {
		MemberManager.getInstance().updateMemberFromBase(this.member);
	}
}