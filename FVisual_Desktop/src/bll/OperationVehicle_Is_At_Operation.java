package bll;

public class OperationVehicle_Is_At_Operation {
	private OperationVehicle operationVehicle;
	private Base base;
	private Operation operation;

	public OperationVehicle_Is_At_Operation(OperationVehicle operationVehicle, Base base, Operation operation) {
		super();
		this.operationVehicle = operationVehicle;
		this.base = base;
		this.operation = operation;
	}

	public OperationVehicle_Is_At_Operation() {
		super();
	}

	public OperationVehicle getOperationVehicle() {
		return operationVehicle;
	}

	public void setOperationVehicle(OperationVehicle operationVehicle) {
		this.operationVehicle = operationVehicle;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}