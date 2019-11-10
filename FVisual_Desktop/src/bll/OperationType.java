package bll;

public class OperationType {
	private int operationTypeId;
	private String description;

	public OperationType(int operationTypeId, String description) {
		super();
		this.operationTypeId = operationTypeId;
		this.description = description;
	}

	public OperationType() {
		super();
	}

	public int getOperationTypeId() {
		return operationTypeId;
	}

	public void setOperationTypeId(int operationTypeId) {
		this.operationTypeId = operationTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}