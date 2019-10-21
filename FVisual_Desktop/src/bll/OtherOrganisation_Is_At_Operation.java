package bll;

public class OtherOrganisation_Is_At_Operation {
	private Operation operation;
	private OtherOrganisation otherOrganisation;
	
	public OtherOrganisation_Is_At_Operation(Operation operation, OtherOrganisation otherOrganisation) {
		super();
		this.operation = operation;
		this.otherOrganisation = otherOrganisation;
	}
	
	public OtherOrganisation_Is_At_Operation() {
		super();
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public OtherOrganisation getOtherOrganisation() {
		return otherOrganisation;
	}

	public void setOtherOrganisation(OtherOrganisation otherOrganisation) {
		this.otherOrganisation = otherOrganisation;
	}
}