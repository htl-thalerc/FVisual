package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.Member;
import handler.MemberHandler;
import manager.MemberManager;

public class MemberByBaseIdLoader implements Runnable {
	private CountDownLatch countDownLatch =null;
	private int baseId, memberId;
	
	public MemberByBaseIdLoader(CountDownLatch countDownLatch, int baseId, int memberId) {
		this.countDownLatch = countDownLatch;
		this.baseId = baseId;
		this.memberId = memberId;
	}

	@Override
	public void run() {
		ArrayList<Member> updatedMember = MemberManager.getInstance().getMemberByIdFromBase(this.baseId, this.memberId);
		MemberHandler.getInstance().setUpdatedMember(updatedMember.get(0));
		
		this.countDownLatch.countDown();
	}
}
