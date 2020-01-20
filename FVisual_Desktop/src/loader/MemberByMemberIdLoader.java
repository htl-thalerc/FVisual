package loader;

import bll.Member;
import manager.MemberManager;

public class MemberByMemberIdLoader implements Runnable {
	private Member member;

	@Override
	public void run() {
		MemberManager.getInstance().getMemberById(this.member);
	}
}