package handler;

import java.util.ArrayList;

import bll.OperationType;

public class OperationTypeHandler {
	private static OperationTypeHandler operationTypeHandler = null;
	
	private ArrayList<OperationType> listOfOperationTypes = new ArrayList<OperationType>();
	
	public static OperationTypeHandler getInstance() {
		if(operationTypeHandler == null) {
			operationTypeHandler = new OperationTypeHandler();
		}
		return operationTypeHandler;
	}
	
	public ArrayList<OperationType> getOperationTypeList() {
		return this.listOfOperationTypes;
	}
	
	public void setOperationTypeList(ArrayList<OperationType> operationTypeList) {
		this.listOfOperationTypes.clear();
		this.listOfOperationTypes = operationTypeList;
	}
}