package loader;

import handler.MemberHandler;
import manager.MemberManager;

public class OperationMemberLoader implements Runnable {
	private int operationId;

	public OperationMemberLoader(int operationId) {
		this.operationId = operationId;
	}

	@Override
	public void run() {
		MemberHandler.getInstance().setOperationListByOperationId(
				MemberManager.getInstance().getMembersFromOperation(this.operationId));
	}
}