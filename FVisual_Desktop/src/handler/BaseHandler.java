package handler;

import java.util.ArrayList;

import bll.Base;

public class BaseHandler {
	private static BaseHandler baseHandler = null;
	
	private ArrayList<Base> listOfBases = new ArrayList<Base>();
	
	private Base latestCreatedBase = null;
	
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
	
	public Base getCreatedBase() {
		return this.latestCreatedBase;
	}
	
	public void setCreatedBase(Base base) {
		this.latestCreatedBase = base;
	}
}