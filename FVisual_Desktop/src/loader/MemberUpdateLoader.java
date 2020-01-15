package loader;

import java.util.concurrent.CountDownLatch;

import bll.Member;
import manager.MemberManager;

public class MemberUpdateLoader implements Runnable {
	private CountDownLatch countDownLatch;
	private Member member;
	
	public MemberUpdateLoader(CountDownLatch countDownLatch, Member member) {
		this.countDownLatch = countDownLatch;
		this.member = member;
	}

	@Override
	public void run() {
		System.out.println(this.member);
		MemberManager.getInstance().updateMemberFromBase(this.member);	
		
		this.countDownLatch.countDown();
	}
}