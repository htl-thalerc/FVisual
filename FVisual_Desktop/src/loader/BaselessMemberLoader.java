package loader;

import java.util.ArrayList;

import bll.Member;
import handler.CentralHandler;
import handler.MemberHandler;
import manager.MemberManager;

public class BaselessMemberLoader implements Runnable {

	@Override
	public void run() {
		ArrayList<Member> listOfAllBaselessMembers = MemberManager.getInstance().getBaselessMembers();
		MemberHandler.getInstance().setBaselessMemberList(listOfAllBaselessMembers);

		CentralHandler.getInstance().mergeFullMemberObject(true);
	}
}