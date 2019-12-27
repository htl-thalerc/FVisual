package handler;

import java.util.ArrayList;

import bll.Base;

public class BaseHandler {
	private static BaseHandler baseHandler = null;
	
	private ArrayList<Base> listOfBases = new ArrayList<Base>();
	
	public static BaseHandler getInstance() {
		if(baseHandler == null) {
			baseHandler = new BaseHandler();
		}
		return baseHandler;
	}
	
	public ArrayList<Base> getBaseList() {
		return this.listOfBases;
	}

	public void setBaseList(ArrayList<Base> baseList) {
		this.listOfBases.clear();
		this.listOfBases = baseList;
	}
}
