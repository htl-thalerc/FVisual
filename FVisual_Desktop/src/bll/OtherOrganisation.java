package bll;

public class OtherOrganisation {
	private int otherOrganisationId;
	private String name;
	private Operation operation;
	private int operationId;
	
	private static OtherOrganisation otherOrgObj = null;
	
	public static final String CONST_DB_ID = "ID";
	public static final String CONST_DB_NAME = "NAME";
	
	public static OtherOrganisation getInstance() {
		if(otherOrgObj == null) {
			otherOrgObj = new OtherOrganisation();
		}
		return otherOrgObj;
	}

	public OtherOrganisation(int otherOrganisationId, String name, Operation operation) {
		super();
		this.otherOrganisationId = otherOrganisationId;
		this.name = name;
		this.operation = operation;
	}
	
	public OtherOrganisation(int otherOrganisationId, String name) {
		super();
		this.otherOrganisationId = otherOrganisationId;
		this.name = name;
	}

	public OtherOrganisation() {
		super();
	}

	public int getOtherOrganisationId() {
		return otherOrganisationId;
	}

	public void setOtherOrganisationId(int otherOrganisationId) {
		this.otherOrganisationId = otherOrganisationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	@Override
	public String toString() {
		return name;
	}
}