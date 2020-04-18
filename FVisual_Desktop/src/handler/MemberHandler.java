package handler;

import java.util.ArrayList;

import bll.Base;
import bll.Member;
import bll.Member_Is_In_Operation;
import bll.Operation;
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
		
		for(int i=0;i<memberList.size();i++) {
			
		}
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
	
	public void setMemberListByOperationId(ArrayList<Member> arrayList) {
		if(arrayList != null) {
			ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
			ArrayList<Rank> tempListOfRanks = RankHandler.getInstance().getRankList();
			ArrayList<Operation> tempListOfOperations = OperationHandler.getInstance().getOperationList();
			this.listOfMembersByOperationId = arrayList;
			
			for(int i=0;i<this.listOfMembersByOperationId.size();i++) {
				for(int j=0;j<tempListOfBases.size();j++) {
					if(this.listOfMembersByOperationId.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
						this.listOfMembersByOperationId.get(i).setBase(tempListOfBases.get(j));
						break;
					}
				}
			}
			
			for(int i=0;i<this.listOfMembersByOperationId.size();i++) {
				for(int j=0;j<tempListOfRanks.size();j++) {
					if(this.listOfMembersByOperationId.get(i).getBase().getBaseId() == tempListOfRanks.get(j).getRankId()) {
						this.listOfMembersByOperationId.get(i).setRank(tempListOfRanks.get(j));
						break;
					}
				}
			}
			
			for(int i=0;i<this.listOfMembersByOperationId.size();i++) {
				for(int j=0;j<tempListOfOperations.size();j++) {
					if(this.listOfMembersByOperationId.get(i).getOperation().getOperationId() == tempListOfOperations.get(j).getOperationId()) {
						this.listOfMembersByOperationId.get(i).setOperation(tempListOfOperations.get(j));
						break;
					}
				}
			}
		}
	}
}