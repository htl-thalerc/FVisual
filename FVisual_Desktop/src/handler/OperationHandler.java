package handler;

import java.util.ArrayList;

import bll.Base;
import bll.Operation;

public class OperationHandler {
	private static OperationHandler operationHandlerInstance = null;
	private ArrayList<Operation> listOfOperations = new ArrayList<Operation>();
	private Operation listOfOperationIdByOperationId = null;
	
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
}