package loader;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import bll.OtherOrganisation;
import handler.OtherOrganisationHandler;
import manager.OtherOrganisationManager;

public class OtherOrganisationLoader implements Runnable {
	private CountDownLatch countDownLatch;
	
	public OtherOrganisationLoader(CountDownLatch latch) {
		this.countDownLatch = latch;
	}

	@Override
	public void run() {
		ArrayList<OtherOrganisation> listOfAllOtherOrganisations = OtherOrganisationManager.getInstance().getOtherOrganisations();
		OtherOrganisationHandler.getInstance().setOtherOrganisationList(listOfAllOtherOrganisations);
		
		this.countDownLatch.countDown();
	}
}