package bll;

import java.util.Map.Entry;

import handler.ExceptionHandler;

public class Exception {
	private String errorNum;
	private String message;
	private ClassTypes classType;
	private EnumCRUDOption crudOption;
	
	public Exception() {
	}
	
	public String getErrorNum() {
		return errorNum;
	}
	
	public void setErrorNum(String errorNum) {
		this.errorNum = errorNum;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public ClassTypes getClassType() {
		return classType;
	}

	public void setClassType(ClassTypes classType) {
		this.classType = classType;
	}

	public EnumCRUDOption getCrudOption() {
		return crudOption;
	}

	public void setCrudOption(EnumCRUDOption crudOption) {
		this.crudOption = crudOption;
	}
	
	@Override
	public String toString() {
		return "[" + this.crudOption + ", " + this.errorNum + "] [" + this.classType + "]: " + this.message; 
	}

	public void setMessageFromDefinedExceptions(String errorNum) {
		for(Entry<String, String> currExObj : ExceptionHandler.getMapOfDefinedExeptions().entrySet()) {
			//ORA-1 --> ORA-00001
			String prefix = errorNum.split("-")[0];
			String suffix = errorNum.split("-")[1];
			if(prefix.equals("ORA")) {
				String newSuffix = "";
				while(suffix.length()<=5) {
					newSuffix += "0";
				}
				suffix = newSuffix + suffix;
				String newKey = prefix + "-" + suffix;
				System.out.println(newKey);
				if(currExObj.equals(newKey)) {
					this.message = currExObj.getValue().trim();
				}
			} else {
				this.message = currExObj.getValue().trim();
			}
		}
	}
}