package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.Member;
import handler.CentralHandler;
import handler.MemberHandler;
import manager.MemberManager;

public class MemberLoader implements Runnable {
	private CountDownLatch countDownLatch = null;

	public MemberLoader(CountDownLatch latch) {
		if(latch != null) {
			this.countDownLatch = latch;	
		}
	}
	
	@Override
	public void run() {
		ArrayList<Member> tempListMembers = new ArrayList<Member>();
		ArrayList<Member> listOfMembers = MemberManager.getInstance().getMembers();
		for (int i = 0; i < listOfMembers.size(); i++) {
			tempListMembers.add(listOfMembers.get(i));
		}
		
		MemberHandler.getInstance().setMemberList(tempListMembers);
		
		CentralHandler.getInstance().mergeFullMemberObject(false);
		if(this.countDownLatch != null) {
			this.countDownLatch.countDown();	
		}
	}
}