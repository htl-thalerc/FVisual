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

import app.CentralHandler;
import bll.Base;
import bll.Member;
import bll.OperationVehicle;

public class OperationVehicleManager {
	private static OperationVehicleManager operationVehicleManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOperationVehicleService = this.webTarget.path(CentralHandler.CONST_BASE_URL);
	
	public static OperationVehicleManager getInstance() {
		if (operationVehicleManagerInstance == null) {
			operationVehicleManagerInstance = new OperationVehicleManager();
		}
		return operationVehicleManagerInstance;
	}
	
	public ArrayList<OperationVehicle> getVehicles(int baseId) {
		ArrayList<OperationVehicle> collOfVehicles = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllVehicles = this.webTargetOperationVehicleService.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE);
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
	
	public OperationVehicle getVehicleById(int baseId, int vehicleId) {
		OperationVehicle foundedVehicle = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAll = this.webTargetOperationVehicleService.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "?id=" + vehicleId);
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
	
	public boolean postVehicle(int baseId, OperationVehicle vehicleObj) {
		WebTarget webTargetAddVehicle = this.webTargetOperationVehicleService.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE);
		Invocation.Builder invocationBuilder = webTargetAddVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(vehicleObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteVehicle(int baseId, int vehicleId) {
		WebTarget webTargetRemoveVehicle = this.webTargetOperationVehicleService.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleId);
		Invocation.Builder invocationBuilder = webTargetRemoveVehicle.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean putVehicle(int baseId, OperationVehicle vehicleObj) {
		WebTarget webTargetUpdateVehicle = this.webTargetOperationVehicleService.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleObj.getOperationVehicleId());
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