package bll;

import javafx.scene.control.CheckBox;

public class OperationVehicle {
	private int operationVehicleId;
	private String description;
	private Base base;
	
	private CheckBox selection;
	
	public OperationVehicle(int operationVehicleId, String description, Base base) {
		super();
		this.operationVehicleId = operationVehicleId;
		this.description = description;
		this.base = base;
	}
	
	public OperationVehicle(CheckBox selection, int operationVehicleId, String nickname, Base base) {
		super();
		this.selection = selection;
		this.operationVehicleId = operationVehicleId;
		this.description = description;
		this.base = base;
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
	
	public CheckBox getSelection() {
		return selection;
	}
}