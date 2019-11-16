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

import bll.Base;
import bll.Operation;
import helper.CentralHandler;

public class OperationManager {
	private static BaseManager baseManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOperationService = this.webTarget.path(CentralHandler.CONST_OPERATION_URL);

	public static BaseManager getInstance() {
		if (baseManagerInstance == null) {
			baseManagerInstance = new BaseManager();
		}
		return baseManagerInstance;
	}
	
	public ArrayList<Operation> getOperations() {
		ArrayList<Operation> collOfOperations = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		try {
			invocationBuilder = this.webTargetOperationService.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfOperations = response.readEntity(new GenericType<ArrayList<Operation>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfOperations;
	}
	
	public Base getOperationById(int operationId) {
		Base foundedOperation = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetById = null;
		
		try {
			webTargetGetById = this.webTarget.path("?id=" + String.valueOf(operationId));
			invocationBuilder = webTargetGetById.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedOperation = response.readEntity(Base.class);	
			}
		} catch(JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedOperation;
	}
	
	public Base getOperationByIdAndTime(int operationId, String date) {
		Base foundedOperation = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetByIdAndTime = null;
		
		try {
			webTargetGetByIdAndTime = this.webTargetOperationService.path("?name=" + operationId + "&eZeit");
			invocationBuilder = webTargetGetByIdAndTime.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedOperation = response.readEntity(Base.class);	
			}
		} catch(JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedOperation;
	}
	
	public boolean postOperation(Operation operationObj) {
		Invocation.Builder invocationBuilder = this.webTargetOperationService.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(operationObj, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteOperation(int operationId) {
		WebTarget webTargetRemoveOperation = this.webTargetOperationService.path(String.valueOf(operationId));
		Invocation.Builder invocationBuilder = webTargetRemoveOperation.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean putOperation(Operation operationObj) {
		WebTarget webTargetUpdateOperation = this.webTargetOperationService.path(String.valueOf(operationObj.getOperationId()));
		Invocation.Builder invocationBuilder = webTargetUpdateOperation.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(operationObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
}