package handler;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import bll.Base;
import bll.OperationVehicle;
import javafx.collections.ObservableList;

public class OperationVehicleHandler {
	private static OperationVehicleHandler operationVehicleHandler = null;
	
	private ArrayList<OperationVehicle> listOfVehicles = new ArrayList<OperationVehicle>();
	private ArrayList<OperationVehicle> listOfGroupedVehicles = new ArrayList<OperationVehicle>();
	private ArrayList<OperationVehicle> listOfVehiclesByBaseId = new ArrayList<OperationVehicle>();
	
	private OperationVehicle updatedOperationVehicle = null;
	
	public static OperationVehicleHandler getInstance() {
		if(operationVehicleHandler == null) {
			operationVehicleHandler = new OperationVehicleHandler();
		}
		return operationVehicleHandler;
	}
	
	public ArrayList<OperationVehicle> getVehicleList() {
		return this.listOfVehicles;
	}
	
	public void setVehicleList(ArrayList<OperationVehicle> vehicleList) {
		this.listOfVehicles.clear();
		this.listOfVehicles = vehicleList;
	}
	
	public ArrayList<OperationVehicle> getGroupedVehicleList() {
		this.listOfGroupedVehicles.clear();
		Set<OperationVehicle> tempVehicleSet = new TreeSet<OperationVehicle>();
		tempVehicleSet.addAll(this.getVehicleList());
		
		this.listOfGroupedVehicles.addAll(tempVehicleSet);
		return this.listOfGroupedVehicles;
	}
	
	public ArrayList<OperationVehicle> getVehicleListByBaseId() {
		return this.listOfVehiclesByBaseId;
	}

	public void setVehicleListByBaseId(ArrayList<OperationVehicle> listOfOperationVehiclesFilteredByBase) {
		if(listOfOperationVehiclesFilteredByBase != null) {
			ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
			this.listOfVehiclesByBaseId = listOfOperationVehiclesFilteredByBase;
			
			for(int i=0;i<this.listOfVehiclesByBaseId.size();i++) {
				for(int j=0;j<tempListOfBases.size();j++) {
					if(this.listOfVehiclesByBaseId.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
						this.listOfVehiclesByBaseId.get(i).setBase(tempListOfBases.get(j));
						break;
					}
				}
			}
		}
	}
	
	public OperationVehicle getUpdatedOperationVehicle() {
		return this.updatedOperationVehicle;
	}
	
	public void setUpdatedOperationVehicle(OperationVehicle vehicle) {
		this.updatedOperationVehicle = vehicle;
	}

	public boolean checkUniqueVehicle(ObservableList<OperationVehicle> obsListAvailable, ObservableList<OperationVehicle> obsListSelected) {
		boolean isUnique = false;
		ArrayList<OperationVehicle> tempListOfAllVehicles = new ArrayList<OperationVehicle>();
		tempListOfAllVehicles.addAll(this.listOfVehicles);
		
		for(int i=0;i<tempListOfAllVehicles.size();i++) {
			for(int j=0;j<obsListAvailable.size();j++) {
				if(tempListOfAllVehicles.get(i).getDescription().equals(obsListAvailable.get(j).getDescription())) {
					return false;
				}
			}
			for(int j=0;j<obsListSelected.size();j++) {
				if(tempListOfAllVehicles.get(i).getDescription().equals(obsListSelected.get(j).getDescription())) {
					return false;
				}
			}
		}
		
		return isUnique;
	}

	public boolean checkUniqueVehicle(OperationVehicle operationVehicle) {
		ArrayList<OperationVehicle> tempListOfAllVehicles = new ArrayList<OperationVehicle>();
		tempListOfAllVehicles.addAll(this.listOfVehicles);
		
		for(int i=0;i<tempListOfAllVehicles.size();i++) {
			System.out.println("H: " + tempListOfAllVehicles.get(i).toFullString());
			if(tempListOfAllVehicles.get(i).getDescription().equals(operationVehicle.getDescription())) {
				return false;
			}
		}
		return true;
	}
}
