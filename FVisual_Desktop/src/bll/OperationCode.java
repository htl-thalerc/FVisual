package bll;

public class OperationCode {
	private int operationCodeId;
	private String code;

	public OperationCode(int operationCodeId, String code) {
		super();
		this.operationCodeId = operationCodeId;
		this.code = code;
	}

	public OperationCode() {
		super();
	}

	public int getOperationCodeId() {
		return operationCodeId;
	}

	public void setOperationCodeId(int operationCodeId) {
		this.operationCodeId = operationCodeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}