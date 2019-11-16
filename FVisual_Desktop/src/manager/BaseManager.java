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
import handler.CentralHandler;

public class BaseManager {
	private static BaseManager baseManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetBaseService = this.webTarget.path(CentralHandler.CONST_BASE_URL);

	public static BaseManager getInstance() {
		if (baseManagerInstance == null) {
			baseManagerInstance = new BaseManager();
		}
		return baseManagerInstance;
	}

	public ArrayList<Base> getBases() {
		ArrayList<Base> collOfBases = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		try {
			invocationBuilder = this.webTargetBaseService.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfBases = response.readEntity(new GenericType<ArrayList<Base>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfBases;
	}
	
	public Base getBaseById(int baseId) {
		Base foundedBase = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetById = null;
		
		try {
			webTargetGetById = this.webTargetBaseService.path("?id=" + String.valueOf(baseId));
			invocationBuilder = webTargetGetById.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedBase = response.readEntity(Base.class);	
			}
		} catch(JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedBase;
	}
	
	public Base getBaseByName(String baseName) {
		Base foundedBase = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetByName = null;
		
		try {
			webTargetGetByName = this.webTargetBaseService.path("?name=" + baseName);
			invocationBuilder = webTargetGetByName.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedBase = response.readEntity(Base.class);	
			}
		} catch(JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedBase;
	}
	
	public boolean postBase(Base baseObj) {
		Invocation.Builder invocationBuilder = this.webTargetBaseService.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(baseObj, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteBase(int baseId) {
		WebTarget webTargetRemoveBase = this.webTargetBaseService.path(String.valueOf(baseId));
		Invocation.Builder invocationBuilder = webTargetRemoveBase.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean putBase(Base baseObj) {
		WebTarget webTargetUpdateBase = this.webTargetBaseService.path(String.valueOf(baseObj.getBaseId()));
		Invocation.Builder invocationBuilder = webTargetUpdateBase.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(baseObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
}