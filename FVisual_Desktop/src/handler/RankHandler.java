package handler;

import java.util.ArrayList;

import bll.Rank;

public class RankHandler {
	private static RankHandler rankHandler = null;
	
	private ArrayList<Rank> listOfRanks = new ArrayList<Rank>();
	
	public static RankHandler getInstance() {
		if(rankHandler == null) {
			rankHandler = new RankHandler();
		}
		return rankHandler;
	}
	
	public ArrayList<Rank> getRankList() {
		return this.listOfRanks;
	}
	
	public void setRankList(ArrayList<Rank> rankList) {
		this.listOfRanks.clear();
		this.listOfRanks = rankList;
	}
}
