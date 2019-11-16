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

import bll.OtherOrganisation;
import handler.CentralHandler;

public class OtherOrganisationService {
	private static OperationVehicleManager operationVehicleManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOtherOrganisationServiceForOperations = this.webTarget.path(CentralHandler.CONST_OPERATION_URL);
	
	public static OperationVehicleManager getInstance() {
		if (operationVehicleManagerInstance == null) {
			operationVehicleManagerInstance = new OperationVehicleManager();
		}
		return operationVehicleManagerInstance;
	}
	//Operation Services
	public ArrayList<OtherOrganisation> getOtherOrganisationFromOperation(int operationId) {
		ArrayList<OtherOrganisation> collOfOtherOrganisations = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllVehicles = this.webTargetOtherOrganisationServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_OTHER_ORGANISATION);
		try {
			invocationBuilder = webTargetGetAllVehicles.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfOtherOrganisations = response.readEntity(new GenericType<ArrayList<OtherOrganisation>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfOtherOrganisations;
	}
	
	public OtherOrganisation getOtherOrganisationByIdFromOperation(int operationId, int vehicleId) {
		OtherOrganisation foundedOtherOrganisation = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAll = this.webTargetOtherOrganisationServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_OTHER_ORGANISATION + "?id=" + vehicleId);
		try {
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedOtherOrganisation = response.readEntity(OtherOrganisation.class);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedOtherOrganisation;
	}
	
	public boolean postOtherOrganisationToOperation(int operationId, OtherOrganisation otherOrganisationObj) {
		WebTarget webTargetAddOtherOrganisation = this.webTargetOtherOrganisationServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_OTHER_ORGANISATION);
		Invocation.Builder invocationBuilder = webTargetAddOtherOrganisation.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(otherOrganisationObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteOtherOrganisationFromOperation(int operationId, int otherOrganisationId) {
		WebTarget webTargetRemoveOtherOrganisation = this.webTargetOtherOrganisationServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_OTHER_ORGANISATION + "/" + otherOrganisationId);
		Invocation.Builder invocationBuilder = webTargetRemoveOtherOrganisation.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean putOtherOrganisationToOperation(int operationId, OtherOrganisation otherOrganisationObj) {
		WebTarget webTargetUpdateOtherOrganisation = this.webTargetOtherOrganisationServiceForOperations.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_OTHER_ORGANISATION + "/" + otherOrganisationObj.getOtherOrganisationId());
		Invocation.Builder invocationBuilder = webTargetUpdateOtherOrganisation.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(otherOrganisationObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
}