package manager;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import bll.OperationVehicle;
import helper.CentralHandler;

public class OperationVehicleManager {
	private static OperationVehicleManager operationVehicleManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOperationVehicleServiceForBase = this.webTarget.path(CentralHandler.CONST_BASE_URL);
	private WebTarget webTargetOperationVehicleServiceForOperations = this.webTarget.path(CentralHandler.CONST_OPERATION_URL);
	
	public static OperationVehicleManager getInstance() {
		if (operationVehicleManagerInstance == null) {
			operationVehicleManagerInstance = new OperationVehicleManager();
		}
		return operationVehicleManagerInstance;
	}
	//Base Services
	public ArrayList<OperationVehicle> getVehiclesFromBase(int baseId) {
		ArrayList<OperationVehicle> collOfVehicles = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllVehicles = this.webTargetOperationVehicleServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE);
		try {
			invocationBuilder = webTargetGetAllVehicles.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfVehicles = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfVehicles;
	}
	
	public OperationVehicle getVehicleByIdFromBase(int baseId, int vehicleId) {
		OperationVehicle foundedVehicle = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAll = this.webTargetOperationVehicleServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "?id=" + vehicleId);
		try {
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedVehicle = response.readEntity(OperationVehicle.class);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedVehicle;
	}
	
	public boolean postVehicleToBase(int baseId, OperationVehicle vehicleObj) {
		WebTarget webTargetAddVehicle = this.webTargetOperationVehicleServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE);
		Invocation.Builder invocationBuilder = webTargetAddVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(vehicleObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteVehicleFromBase(int baseId, int vehicleId) {
		WebTarget webTargetRemoveVehicle = this.webTargetOperationVehicleServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleId);
		Invocation.Builder invocationBuilder = webTargetRemoveVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean putVehicleToBase(int baseId, OperationVehicle vehicleObj) {
		WebTarget webTargetUpdateVehicle = this.webTargetOperationVehicleServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleObj.getOperationVehicleId());
		Invocation.Builder invocationBuilder = webTargetUpdateVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(vehicleObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
	//Operation Services
	public ArrayList<OperationVehicle> getVehiclesFromOperation(int operationId) {
		ArrayList<OperationVehicle> collOfVehicles = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllVehicles = this.webTargetOperationVehicleServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE);
		try {
			invocationBuilder = webTargetGetAllVehicles.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfVehicles = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfVehicles;
	}
	
	public OperationVehicle getVehicleByIdFromOperation(int operationId, int vehicleId) {
		OperationVehicle foundedVehicle = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAll = this.webTargetOperationVehicleServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE + "?id=" + vehicleId);
		try {
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedVehicle = response.readEntity(OperationVehicle.class);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedVehicle;
	}
	
	public boolean postVehicleToOperation(int operationId, OperationVehicle vehicleObj) {
		WebTarget webTargetAddVehicle = this.webTargetOperationVehicleServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE);
		Invocation.Builder invocationBuilder = webTargetAddVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(vehicleObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteVehicleFromOperation(int operationId, int vehicleId) {
		WebTarget webTargetRemoveVehicle = this.webTargetOperationVehicleServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleId);
		Invocation.Builder invocationBuilder = webTargetRemoveVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean putVehicleToOperation(int operationId, OperationVehicle vehicleObj) {
		WebTarget webTargetUpdateVehicle = this.webTargetOperationVehicleServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleObj.getOperationVehicleId());
		Invocation.Builder invocationBuilder = webTargetUpdateVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(vehicleObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
}