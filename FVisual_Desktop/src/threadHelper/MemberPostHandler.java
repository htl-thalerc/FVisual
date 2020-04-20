package threadHelper;

import bll.Member;
import manager.MemberManager;
import manager.OperationManager;

public class MemberPostHandler implements Runnable {
	private Member member;
	
	private int operationId = 0;
	private int memberId = 0;
	private int baseId = 0;

	public MemberPostHandler(Member memberToCreate) {
		this.member = memberToCreate;
	}

	public MemberPostHandler(int operationId, int memberId, int baseId) {
		this.operationId = operationId;
		this.memberId = memberId;
		this.baseId = baseId;
	}

	@Override
	public void run() {
		if(this.operationId != 0 && this.memberId != 0 && this.baseId != 0) {
			OperationManager.getInstance().postMemberToOperation(this.operationId, this.memberId, this.baseId);	
		} else {
			MemberManager.getInstance().addMemberToBase(this.member.getBaseId(), this.member);		
		}
	}
}