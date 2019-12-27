package handler;

import java.util.ArrayList;

import bll.Base;
import bll.Member;

public class MemberHandler {
	private static MemberHandler memberHandler = null;
	
	private ArrayList<Member> listOfMembers = new ArrayList<Member>();
	private ArrayList<Member> listOfMembersByBaseId = new ArrayList<Member>();
	
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
		ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
		this.listOfMembersByBaseId = listOfMembersFilteredByBase;
		
		for(int i=0;i<this.listOfMembersByBaseId.size();i++) {
			for(int j=0;j<tempListOfBases.size();j++) {
				if(this.listOfMembersByBaseId.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
					this.listOfMembersByBaseId.get(i).setBase(tempListOfBases.get(j));
					break;
				}
			}
		}
	}
}
