package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.Rank;
import handler.RankHandler;
import manager.RankManager;

public class RankLoader implements Runnable {
	private CountDownLatch countDownLatch = null;

	public RankLoader(CountDownLatch latch) {
		this.countDownLatch = latch;
	}
	
	@Override
	public void run() {
		ArrayList<Rank> tempListRank = new ArrayList<Rank>();
		ArrayList<Rank> listOfRanks = RankManager.getInstance().getRanks();
		
		for (int i = 0; i < listOfRanks.size(); i++) {
			tempListRank.add(listOfRanks.get(i));
		}
		
		RankHandler.getInstance().setRankList(tempListRank);
		
		this.countDownLatch.countDown();
	}
}