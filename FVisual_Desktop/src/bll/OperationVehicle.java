package bll;

import javafx.scene.control.CheckBox;

public class OperationVehicle implements Comparable<OperationVehicle> {
	private int operationVehicleId;
	private String description;
	
	private Base base;
	private int baseId;
	private Operation operation;
	private int operationId;
	
	private boolean isUpdated = false;
	
	private CheckBox selection;
	
	private static OperationVehicle operationVehicleObj = null;
	
	public static final String CONST_DB_ID = "ID";
	public static final String CONST_DB_DESCRIPTION = "BEZEICHNUNG";
	public static final String CONST_DB_BASEID = "ID_STUETZPUNKT";
	
	public static OperationVehicle getInstance() {
		if(operationVehicleObj == null) {
			operationVehicleObj = new OperationVehicle();
		}
		return operationVehicleObj;
	}

	public OperationVehicle(int operationVehicleId, String description, Base base, Operation operation) {
		super();
		this.operationVehicleId = operationVehicleId;
		this.description = description;
		this.base = base;
		this.operation = operation;
	}

	public OperationVehicle(CheckBox selection, int operationVehicleId, String description, Base base, Operation operation) {
		super();
		this.selection = selection;
		this.operationVehicleId = operationVehicleId;
		this.description = description;
		this.base = base;
		this.operation = operation;
	}
	
	public OperationVehicle(String description) {
		super();
		this.description = description;
	}
	
	public OperationVehicle(int operationVehicleId, String description, int baseId, int operationId) {
		super();
		this.operationVehicleId = operationVehicleId;
		this.description = description;
		this.baseId = baseId;
		this.operationId = operationId;
	}
	
	public OperationVehicle(int operationVehicleId, String description) {
		super();
		this.operationVehicleId = operationVehicleId;
		this.description = description;
	}

	public OperationVehicle() {
		super();
	}

	public int getOperationVehicleId() {
		return operationVehicleId;
	}

	public void setOperationVehicleId(int operationVehicleId) {
		this.operationVehicleId = operationVehicleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
	
	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
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

	public CheckBox getSelection() {
		return selection;
	}
	
	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public String toString() {
		return this.description;
	}
	
	public String toFullString() {
		return operationVehicleId + ", " + description + ", " + base.toString() + ", " + isUpdated + ", " + operation.toString();
	}

	@Override
	public int compareTo(OperationVehicle o1) {
		return this.description.compareTo(o1.description);
	}
}