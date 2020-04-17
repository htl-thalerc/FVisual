package bll;

public class OperationCode {
	private int operationCodeId;
	private String code;
	
	//DB Attr Name
	public static final String CONST_DB_OPERATIONCODEID = "ID";
	public static final String CONST_DB_CODE = "CODE";

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
	
	@Override
	public String toString() {
		return "" + operationCodeId + ", " + code;
	}
}