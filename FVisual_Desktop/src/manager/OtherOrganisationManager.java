package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import bll.ClassTypes;
import bll.EnumCRUDOption;
import bll.OtherOrganisation;
import handler.CentralHandler;
import handler.ExceptionHandler;

public class OtherOrganisationManager {
	private static OtherOrganisationManager otherOrganisationManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOtherOrganisation = this.webTarget.path(CentralHandler.CONST_OTHER_ORGANISATION);
	private WebTarget webTargetOtherOrganisationServiceForOperations = this.webTarget.path(CentralHandler.CONST_OPERATION_URL);
	
	public static OtherOrganisationManager getInstance() {
		if (otherOrganisationManagerInstance == null) {
			otherOrganisationManagerInstance = new OtherOrganisationManager();
		}
		return otherOrganisationManagerInstance;
	}
	
	public ArrayList<OtherOrganisation> getOtherOrganisations() {
		ArrayList<OtherOrganisation> collOfOtherOrganisations = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OTHER_ORG, null);

		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
			invocationBuilder = this.webTargetOtherOrganisation.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfOtherOrganisations = response.readEntity(new GenericType<ArrayList<OtherOrganisation>>() {
				});
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OTHER_ORG, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfOtherOrganisations;
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
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OTHER_ORG, EnumCRUDOption.GET);
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
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OTHER_ORG, EnumCRUDOption.GET);
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
			ExceptionHandler.getInstance().setException(response, ClassTypes.OTHER_ORG, EnumCRUDOption.POST);
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
			ExceptionHandler.getInstance().setException(response, ClassTypes.OTHER_ORG, EnumCRUDOption.DELETE);
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
			ExceptionHandler.getInstance().setException(response, ClassTypes.OTHER_ORG, EnumCRUDOption.PUT);
			return false;
		}
	}
}