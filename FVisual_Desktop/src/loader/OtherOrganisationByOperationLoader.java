package loader;

import handler.OtherOrganisationHandler;
import manager.OtherOrganisationManager;

public class OtherOrganisationByOperationLoader implements Runnable{
private int operationId;
	
	public OtherOrganisationByOperationLoader(int operationId) {
		this.operationId = operationId;
	}

	@Override
	public void run() {
		OtherOrganisationHandler.getInstance().setOtherOrganisationByOperationId(
				OtherOrganisationManager.getInstance().getOtherOrganisationFromOperation(this.operationId));
	}
}