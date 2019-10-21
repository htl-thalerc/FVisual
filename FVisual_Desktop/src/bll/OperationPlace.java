package bll;

public class OperationPlace {
	private int operationPlaceId;
	private String address;
	
	public OperationPlace(int operationPlaceId, String address) {
		super();
		this.operationPlaceId = operationPlaceId;
		this.address = address;
	}
	
	public OperationPlace() {
		super();
	}

	public int getOperationPlaceId() {
		return operationPlaceId;
	}

	public void setOperationPlaceId(int operationPlaceId) {
		this.operationPlaceId = operationPlaceId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}