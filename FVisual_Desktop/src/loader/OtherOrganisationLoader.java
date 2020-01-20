package loader;

import java.util.ArrayList;

import bll.OtherOrganisation;
import handler.OtherOrganisationHandler;
import manager.OtherOrganisationManager;

public class OtherOrganisationLoader implements Runnable {

	@Override
	public void run() {
		ArrayList<OtherOrganisation> listOfAllOtherOrganisations = OtherOrganisationManager.getInstance().getOtherOrganisations();
		OtherOrganisationHandler.getInstance().setOtherOrganisationList(listOfAllOtherOrganisations);
	}
}