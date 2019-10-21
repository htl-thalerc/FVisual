package bll;

import java.sql.Time;
import java.util.Date;

public class Operation {
	private int operationId;
	private OperationCode operationCode;
	private OperationType operationType;
	private OperationPlace operationPlace;
	private String title;
	private String shortDescription;
	private Date date;
	private Time time;
	
	public Operation(int operationId, OperationCode operationCode, OperationType operationType,
			OperationPlace operationPlace, String title, String shortDescription, Date date, Time time) {
		super();
		this.operationId = operationId;
		this.operationCode = operationCode;
		this.operationType = operationType;
		this.operationPlace = operationPlace;
		this.title = title;
		this.shortDescription = shortDescription;
		this.date = date;
		this.time = time;
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

	public OperationPlace getOperationPlace() {
		return operationPlace;
	}

	public void setOperationPlace(OperationPlace operationPlace) {
		this.operationPlace = operationPlace;
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

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
	
	
}