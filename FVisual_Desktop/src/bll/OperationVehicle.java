package bll;

import javafx.scene.control.CheckBox;

public class OperationVehicle {
	private int operationVehicleId;
	private String nickname;
	private Base base;
	
	private CheckBox selection;
	
	public OperationVehicle(int operationVehicleId, String nickname, Base base) {
		super();
		this.operationVehicleId = operationVehicleId;
		this.nickname = nickname;
		this.base = base;
	}
	
	public OperationVehicle(CheckBox selection, int operationVehicleId, String nickname, Base base) {
		super();
		this.selection = selection;
		this.operationVehicleId = operationVehicleId;
		this.nickname = nickname;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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