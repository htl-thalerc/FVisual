package handler;

import java.util.ArrayList;

import bll.Operation;

public class OperationHandler {
	private static OperationHandler operationHandlerInstance = null;
	private ArrayList<Operation> listOfOperations = new ArrayList<Operation>();
	
	public static OperationHandler getInstance() {
		if(operationHandlerInstance == null) {
			operationHandlerInstance = new OperationHandler();
		}
		return operationHandlerInstance;
	}
	
	public ArrayList<Operation> getListOfOperations() {
		return this.listOfOperations;
	}
	
	public void setListOfOperations(ArrayList<Operation> listOfOperation) {
		this.listOfOperations = listOfOperation;
	}
}
