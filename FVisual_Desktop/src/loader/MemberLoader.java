package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.Member;
import bll.Rank;
import handler.CentralHandler;
import handler.MemberHandler;
import handler.RankHandler;
import javafx.collections.FXCollections;
import manager.MemberManager;
import manager.RankManager;

public class MemberLoader implements Runnable {
	private CountDownLatch countDownLatch = null;

	public MemberLoader(CountDownLatch latch) {
		this.countDownLatch = latch;
	}
	
	@Override
	public void run() {
		ArrayList<Member> tempListMembers = new ArrayList<Member>();
		ArrayList<Member> listOfMembers = MemberManager.getInstance().getMembers();
		for (int i = 0; i < listOfMembers.size(); i++) {
			tempListMembers.add(listOfMembers.get(i));
		}
		
		MemberHandler.getInstance().setMemberList(tempListMembers);
		ArrayList<Rank> tempListRank = new ArrayList<Rank>();
		ArrayList<Rank> listOfRanks = RankManager.getInstance().getRanks();
		
		for (int i = 0; i < listOfRanks.size(); i++) {
			tempListRank.add(listOfRanks.get(i));
		}
		
		RankHandler.getInstance().setRankList(tempListRank);
		CentralHandler.getInstance().mergeFullMemberObject(false);
		
		System.out.println("Finished loading Members");
		this.countDownLatch.countDown();
	}
}