package loader;

import manager.MemberManager;

public class MemberByOperationIdLoader implements Runnable {
	private int operationId;
	private int memberId;
	
	public MemberByOperationIdLoader(int operationId, int memberId) {
		this.operationId = operationId;
		this.memberId = memberId;
	}

	@Override
	public void run() {
		MemberManager.getInstance().getMemberByIdFromOperation(this.operationId, this.memberId);
	}
}