package handler;

import java.util.ArrayList;

import bll.Operation;

public class OperationHandler {
	private static OperationHandler operationHandlerInstance = null;
	private ArrayList<Operation> listOfOperations = new ArrayList<Operation>();
	private Operation listOfOperationIdByOperationId = null;
	private Operation createdOperation;
	
	public static OperationHandler getInstance() {
		if(operationHandlerInstance == null) {
			operationHandlerInstance = new OperationHandler();
		}
		return operationHandlerInstance;
	}
	
	public ArrayList<Operation> getOperationList() {
		return this.listOfOperations;
	}
	
	public void setOperationList(ArrayList<Operation> listOfOperation) {
		this.listOfOperations = listOfOperation;
	}
	
	public void setOperationByOperationId(Operation operationById) {
		this.listOfOperationIdByOperationId = operationById;
	}
	
	public Operation getOperationByOperationId() {
		return this.listOfOperationIdByOperationId;
	}

	public void setCreatedOperation(Operation operation) {
		this.createdOperation = operation;
	}
	
	public Operation getCreatedOperation() {
		return this.createdOperation;
	}
}