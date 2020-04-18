package handler;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import bll.Base;
import bll.Operation;
import bll.OperationVehicle;
import javafx.collections.ObservableList;

public class OperationVehicleHandler {
	private static OperationVehicleHandler operationVehicleHandler = null;
	
	private ArrayList<OperationVehicle> listOfVehicles = new ArrayList<OperationVehicle>();
	private ArrayList<OperationVehicle> listOfGroupedVehicles = new ArrayList<OperationVehicle>();
	private ArrayList<OperationVehicle> listOfVehiclesByBaseId = new ArrayList<OperationVehicle>();
	
	private ArrayList<OperationVehicle> listOfVehiclesByOperationId = new ArrayList<OperationVehicle>();
	private ArrayList<OperationVehicle> listOfVehiclesWithOperationAttr = new ArrayList<OperationVehicle>();
	
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
			if(tempListOfAllVehicles.get(i).getDescription().equals(operationVehicle.getDescription())) {
				return false;
			}
		}
		return true;
	}
	
	public void setVehicleListByOperationId(ArrayList<OperationVehicle> list) {
		if(list != null) {
			ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
			ArrayList<Operation> tempListOfOperations = OperationHandler.getInstance().getOperationList();
			this.listOfVehiclesByOperationId = list;
			
			for(int i=0;i<this.listOfVehiclesByOperationId.size();i++) {
				for(int j=0;j<tempListOfBases.size();j++) {
					if(this.listOfVehiclesByOperationId.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
						this.listOfVehiclesByOperationId.get(i).setBase(tempListOfBases.get(j));
						break;
					}
				}
			}
			
			for(int i=0;i<this.listOfVehiclesByOperationId.size();i++) {
				for(int j=0;j<tempListOfOperations.size();j++) {
					if(this.listOfVehiclesByOperationId.get(i).getOperation().getOperationId() == tempListOfOperations.get(j).getOperationId()) {
						this.listOfVehiclesByOperationId.get(i).setOperation(tempListOfOperations.get(j));
						break;
					}
				}
			}
		}
	}
	
	public ArrayList<OperationVehicle> getVehiclesListByOperationId() {
		return this.listOfVehiclesByOperationId;
	}
	
	public void setVehicleListWithOperationAttr(ArrayList<OperationVehicle> list) {
		if(list != null) {
			ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
			ArrayList<Operation> tempListOfOperations = OperationHandler.getInstance().getOperationList();
			this.listOfVehiclesWithOperationAttr = list;
			
			//Set base
			for(int i=0;i<this.listOfVehiclesWithOperationAttr.size();i++) {
				for(int j=0;j<tempListOfBases.size();j++) {
					if(this.listOfVehiclesWithOperationAttr.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
						this.listOfVehiclesWithOperationAttr.get(i).setBase(tempListOfBases.get(j));
						break;
					}
				}
			}
			
			//Set operation
			for(int i=0;i<this.listOfVehiclesWithOperationAttr.size();i++) {
				for(int j=0;j<tempListOfOperations.size();j++) {
					if(this.listOfVehiclesWithOperationAttr.get(i).getOperation().getOperationId() != 0) {
						if(this.listOfVehiclesWithOperationAttr.get(i).getOperation().getOperationId() == tempListOfOperations.get(j).getOperationId()) {
							this.listOfVehiclesWithOperationAttr.get(i).setOperation(tempListOfOperations.get(j));
							break;
						}
					} else {
						Operation newOperation = new Operation();
						newOperation.setOperationId(-1);
						newOperation.setTitle("Not assigned yet");
						this.listOfVehiclesWithOperationAttr.get(i).setOperation(newOperation);
					}
				}
			}
		}
	}
	
	public ArrayList<OperationVehicle> getVehicleListWithOperationAttr() {
		return this.listOfVehiclesWithOperationAttr;
	}
}