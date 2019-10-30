package bll;

public class TableViewRowData {
	private Base base;
	private OperationVehicle vehicle;

	public TableViewRowData(Base base, OperationVehicle vehicle) {
		super();
		this.base = base;
		this.vehicle = vehicle;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public OperationVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(OperationVehicle vehicle) {
		this.vehicle = vehicle;
	}
}