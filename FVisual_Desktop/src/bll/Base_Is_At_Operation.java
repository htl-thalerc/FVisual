package bll;

public class Base_Is_At_Operation {
	private Base base;
	private Operation operation;

	public Base_Is_At_Operation(Base base, Operation operation) {
		super();
		this.base = base;
		this.operation = operation;
	}

	public Base_Is_At_Operation() {
		super();
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