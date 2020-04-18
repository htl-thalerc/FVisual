package handler;

import java.util.ArrayList;

import javax.print.attribute.standard.MediaSize.Other;

import bll.Operation;
import bll.OtherOrganisation;

public class OtherOrganisationHandler {
	private static OtherOrganisationHandler otherOrganisationHandler = null;
	private ArrayList<OtherOrganisation> listOfOtherOrganisations = new ArrayList<OtherOrganisation>();
	
	private ArrayList<OtherOrganisation> listOfOtherOrganisationsByOperation = new ArrayList<OtherOrganisation>();
	
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
		if(listOfAllOtherOrganisations != null) {
			ArrayList<Operation> tempListOfOperations = OperationHandler.getInstance().getOperationList();
			this.listOfOtherOrganisations = listOfAllOtherOrganisations;
			
			for(int i=0;i<this.listOfOtherOrganisations.size();i++) {
				for(int j=0;j<tempListOfOperations.size();j++) {
					if(this.listOfOtherOrganisations.get(i).getOperation().getOperationId() != 0) {
						if(this.listOfOtherOrganisations.get(i).getOperation().getOperationId() == tempListOfOperations.get(j).getOperationId()) {
							this.listOfOtherOrganisations.get(i).setOperation(tempListOfOperations.get(j));
							break;
						}
					} else {
						Operation newOperation = new Operation();
						newOperation.setOperationId(-1);
						newOperation.setTitle("Not assigned yet");
						this.listOfOtherOrganisations.get(i).setOperation(newOperation);
					}
				}
			}
		}
	}
	
	public ArrayList<OtherOrganisation> getOtherOrganisationByOperationId() {
		return this.listOfOtherOrganisationsByOperation;
	}
	
	public void setOtherOrganisationByOperationId(ArrayList<OtherOrganisation> listofOtherOrgsByOperation) {
		System.out.println(listofOtherOrgsByOperation.size());
		if(listofOtherOrgsByOperation != null) {
			ArrayList<Operation> tempListOfOperations = OperationHandler.getInstance().getOperationList();
			this.listOfOtherOrganisationsByOperation = listofOtherOrgsByOperation;
			
			for(int i=0;i<this.listOfOtherOrganisationsByOperation.size();i++) {
				for(int j=0;j<tempListOfOperations.size();j++) {
					if(this.listOfOtherOrganisationsByOperation.get(i).getOperation().getOperationId() != 0) {
						if(this.listOfOtherOrganisationsByOperation.get(i).getOperation().getOperationId() == tempListOfOperations.get(j).getOperationId()) {
							this.listOfOtherOrganisationsByOperation.get(i).setOperation(tempListOfOperations.get(j));
							break;
						}
					} else {
						Operation newOperation = new Operation();
						newOperation.setOperationId(-1);
						newOperation.setTitle("Not assigned yet");
						this.listOfOtherOrganisationsByOperation.get(i).setOperation(newOperation);
					}
				}
			}
		}
	}
}