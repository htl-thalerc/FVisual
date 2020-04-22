package bll;

import java.sql.Timestamp;
import java.util.Date;

public class Operation {
	private int operationId;
	private OperationCode operationCode;
	private OperationType operationType;
	private int postCode;
	private String address;
	private String title;
	private String shortDescription;
	private Date date;
	private Timestamp time;
	
	private Base base;
	private int baseId;
	
	//name of DB Attr
	public static final String CONST_DB_OPERATIONID = "ID";
	public static final String CONST_DB_OPERATIONCODEID = "ID_EINSATZCODE";
	public static final String CONST_DB_OPERATIONTYPEID = "ID_EINSATZART";
	public static final String CONST_DB_POSTCODE = "PLZ";
	public static final String CONST_DB_ADDRESS = "ADRESSE";
	public static final String CONST_DB_TITLE = "TITEL";
	public static final String CONST_DB_SHORTDESCRIPTION = "KURZBESCHREIBUNG";
	public static final String CONST_DB_TIME = "ZEIT";

	public Operation(int operationId, OperationCode operationCode, OperationType operationType, String address, int postCode,
			String title, String shortDescription, Date date, Timestamp time, Base base) {
		super();
		this.operationId = operationId;
		this.operationCode = operationCode;
		this.operationType = operationType;
		this.address = address;
		this.postCode = postCode;
		this.title = title;
		this.shortDescription = shortDescription;
		this.date = date;
		this.time = time;
		this.base = base;
	}

	public Operation() {
		super();
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public OperationCode getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(OperationCode operationCode) {
		this.operationCode = operationCode;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
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

	@Override
	public String toString() {
		return operationId + ", OperationCode: {" + operationCode.toString() + "}, OperationType: {" + operationType.toString() + "}, " + 
				address + ", " + title + ", " + shortDescription + ", PLZ" + postCode + "{" + base.getBaseId() + "}";
	}
}