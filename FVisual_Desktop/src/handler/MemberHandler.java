package handler;

import java.util.ArrayList;

import bll.Base;
import bll.Member;
import bll.Rank;

public class MemberHandler {
	private static MemberHandler memberHandler = null;
	
	private ArrayList<Member> listOfMembers = new ArrayList<Member>();
	private ArrayList<Member> listOfMembersByBaseId = new ArrayList<Member>();
	private ArrayList<Member> listOfBaselessMembers = new ArrayList<Member>();
	
	private ArrayList<Member> listOfMembersByOperationId = new ArrayList<Member>();
	
	private Member updatedMember = null;
	
	public static MemberHandler getInstance() {
		if(memberHandler == null) {
			memberHandler = new MemberHandler();
		}
		return memberHandler;
	}
	
	public ArrayList<Member> getMemberList() {
		return this.listOfMembers;
	}
	
	public void setMemberList(ArrayList<Member> memberList) {
		this.listOfMembers.clear();
		this.listOfMembers = memberList;
	}
	
	public ArrayList<Member> getMemberListByBaseId() {
		return this.listOfMembersByBaseId;
	}

	public void setMemberListByBaseId(ArrayList<Member> listOfMembersFilteredByBase) {
		if(listOfMembersFilteredByBase != null) {
			ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
			ArrayList<Rank> tempListOfRanks = RankHandler.getInstance().getRankList();
			this.listOfMembersByBaseId = listOfMembersFilteredByBase;
			
			for(int i=0;i<this.listOfMembersByBaseId.size();i++) {
				for(int j=0;j<tempListOfBases.size();j++) {
					if(this.listOfMembersByBaseId.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
						this.listOfMembersByBaseId.get(i).setBase(tempListOfBases.get(j));
						break;
					}
				}
			}
			
			for(int i=0;i<this.listOfMembersByBaseId.size();i++) {
				for(int j=0;j<tempListOfRanks.size();j++) {
					if(this.listOfMembersByBaseId.get(i).getBase().getBaseId() == tempListOfRanks.get(j).getRankId()) {
						this.listOfMembersByBaseId.get(i).setRank(tempListOfRanks.get(j));
						break;
					}
				}
			}
		}
	}
	
	public ArrayList<Member> getBaselessMemberList() {
		return this.listOfBaselessMembers;
	}
	
	public void setBaselessMemberList(ArrayList<Member> memberList) {
		this.listOfBaselessMembers.clear();
		this.listOfBaselessMembers = memberList;
	}
	
	public Member getUpdatedMember() {
		return this.updatedMember;
	}

	public void setUpdatedMember(Member member) {
		this.updatedMember = member;
	}
	
	public String setGeneratedUsername(Member member) {
		String username = "";
		
		for(int i=0;i<member.getLastname().length();i++) {
			if(i<=4) {
				username += member.getLastname().charAt(i);
			}
		}
		for(int i=0;i<member.getFirstname().length();i++) {
			if(username.length() <= 5) {
				username += member.getFirstname().charAt(i);
			}
		}
		return username.toLowerCase();
	}
	
	public void setOperationListByOperationId(ArrayList<Member> listOfMembersFilteredByOperation) {
		
	}

	public ArrayList<Member> getMemberListByOperationId() {
		return this.listOfMembersByOperationId;
	}
	
	public void setMemberListByOperationId(ArrayList<Member> list) {
		this.listOfMembersByOperationId = list;
	}
}