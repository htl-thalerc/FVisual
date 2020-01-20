package handler;

import java.util.ArrayList;

import bll.OtherOrganisation;

public class OtherOrganisationHandler {
	private static OtherOrganisationHandler otherOrganisationHandler = null;
	private ArrayList<OtherOrganisation> listOfOtherOrganisations = new ArrayList<OtherOrganisation>();
	
	public static OtherOrganisationHandler getInstance() {
		if(otherOrganisationHandler == null) {
			otherOrganisationHandler = new OtherOrganisationHandler();
		}
		return otherOrganisationHandler;
	}
	
	public ArrayList<OtherOrganisation> getOtherOrganisationList() {
		return this.listOfOtherOrganisations;
	}

	public void setOtherOrganisationList(ArrayList<OtherOrganisation> listOfAllOtherOrganisations) {
		this.listOfOtherOrganisations.clear();
		this.listOfOtherOrganisations = listOfAllOtherOrganisations;
	}
}
