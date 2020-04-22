package handler;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.Response;

import bll.ClassTypes;
import bll.EnumCRUDOption;
import bll.Exception;

public class ExceptionHandler {
	private static ExceptionHandler instance = null;
	private ArrayList<Exception> listOfExceptionsDuringRuntime = new ArrayList<Exception>();
	private static HashMap<String, String> mapOfDefinedExeptiones = new HashMap<String, String>();

	public static ExceptionHandler getInstance() {
		if (instance == null) {
			instance = new ExceptionHandler();
			fillDefinedExeptions();
		}
		return instance;
	}
	
	private static void fillDefinedExeptions() {
		//exceptions from oracle
		mapOfDefinedExeptiones.put("ORA-23170", "Connection timeout occurred");
		mapOfDefinedExeptiones.put("ORA-12514", "Listener does not currently know of service requested in connect descriptor");
		mapOfDefinedExeptiones.put("ORA-00001", "Unique constraint violation");
		mapOfDefinedExeptiones.put("ORA-19090", "Not found");
		
		//self-defined exceptions from WebService
		mapOfDefinedExeptiones.put("WS-00001", "Invalid paramId supplied");
		mapOfDefinedExeptiones.put("WS-00002", "Invalid metaData supplied");
		mapOfDefinedExeptiones.put("WS-00003", "Invalid bodyData supplied");
		mapOfDefinedExeptiones.put("WS-00004", "Invalid subQuery supplied");
		mapOfDefinedExeptiones.put("WS-00005", "Not implemented yet");
	}
	
	public static HashMap<String, String> getMapOfDefinedExeptions() {
		return mapOfDefinedExeptiones;
	}

	public ArrayList<Exception> getExceptions() {
		return this.listOfExceptionsDuringRuntime;
	}

	public void setException(Response res, ClassTypes classType, EnumCRUDOption crud) {
		Exception resAsExeption = res.readEntity(Exception.class);
		resAsExeption.setClassType(classType);
		resAsExeption.setCrudOption(crud);
		//resAsExeption.setMessageFromDefinedExceptions(resAsExeption.getErrorNum());
		
		this.listOfExceptionsDuringRuntime.add(resAsExeption);
	}
}