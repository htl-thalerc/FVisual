package manager;

import java.util.ArrayList;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import bll.Base;
import bll.ClassTypes;
import handler.CentralHandler;

public class BaseManager {
	private static BaseManager baseManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetBaseService = this.webTarget.path(CentralHandler.CONST_BASE_URL);
	private static final Logger LOGGER = LogManager.getLogger(BaseManager.class.getName());
	
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
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null);
		
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
			invocationBuilder = this.webTargetBaseService.request(MediaType.APPLICATION_JSON).headers(headers); 
			
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfBases = response.readEntity(new GenericType<ArrayList<Base>>() {
				});
				LOGGER.info("[BaseManager] [GET]: Bases");
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
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null);
		
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
			webTargetGetById = this.webTargetBaseService.path(String.valueOf(baseId));
			invocationBuilder = webTargetGetById.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				ArrayList<Base> list = response.readEntity(new GenericType<ArrayList<Base>>() {
				});
				foundedBase = list.get(0);
				LOGGER.info("[BaseManager] [GET]: Base by BaseId");
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
	
	public Base postBase(Base baseObj) {
		Base retVal = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null);
		
		headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
		
		Invocation.Builder invocationBuilder = this.webTargetBaseService.request(MediaType.APPLICATION_JSON).headers(headers);
		
		String jsonStr = "{\"houseNr\":\"" + baseObj.getHouseNr() + "\"," + 
				"\"name\":\"" + baseObj.getName() + "\"," +
				"\"place\":\"" + baseObj.getPlace() + "\"," +
				"\"postCode\":\"" + baseObj.getPostCode() + "\"," +
				"\"street\":\"" + baseObj.getStreet() + "\"}";
		
		Response response = invocationBuilder.post(Entity.entity(jsonStr, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			ArrayList<Base> list = response.readEntity(new GenericType<ArrayList<Base>>() {
			});
			retVal = list.get(0);
		} 
		return retVal;
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
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null);
		
		headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
		
		WebTarget webTargetUpdateBase = this.webTargetBaseService.path(String.valueOf(baseObj.getBaseId()));
		Invocation.Builder invocationBuilder = webTargetUpdateBase.request(MediaType.APPLICATION_JSON).headers(headers);

		String jsonStr = "{\"houseNr\":\"" + baseObj.getHouseNr() + "\"," + 
				"\"name\":\"" + baseObj.getName() + "\"," +
				"\"place\":\"" + baseObj.getPlace() + "\"," +
				"\"postCode\":\"" + baseObj.getPostCode() + "\"," +
				"\"street\":\"" + baseObj.getStreet() + "\"}";
		
		Response response = invocationBuilder.put(Entity.entity(jsonStr, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
}