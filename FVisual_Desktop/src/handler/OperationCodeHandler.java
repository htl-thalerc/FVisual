package handler;

import java.util.ArrayList;

import bll.OperationCode;

public class OperationCodeHandler {
	private static OperationCodeHandler operationCodeHandler = null;
	
	private ArrayList<OperationCode> listOfOperationCodes = new ArrayList<OperationCode>();
	
	public static OperationCodeHandler getInstance() {
		if(operationCodeHandler == null) {
			operationCodeHandler = new OperationCodeHandler();
		}
		return operationCodeHandler;
	}
	
	public ArrayList<OperationCode> getOperationCodeList() {
		return this.listOfOperationCodes;
	}
	
	public void setOperationCodeList(ArrayList<OperationCode> operationCodeList) {
		this.listOfOperationCodes.clear();
		this.listOfOperationCodes = operationCodeList;
	}
}
