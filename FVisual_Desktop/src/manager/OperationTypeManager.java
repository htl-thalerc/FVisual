package manager;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import bll.ClassTypes;
import bll.EnumCRUDOption;
import bll.OperationType;
import handler.CentralHandler;
import handler.ExceptionHandler;

public class OperationTypeManager {
	private static OperationTypeManager operationtypeManager = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOperationtypeService = this.webTarget.path(CentralHandler.CONST_OPERATION_TYPE);
	private static final Logger LOGGER = LogManager.getLogger(RankManager.class.getName());
	
	public static OperationTypeManager getInstance() {
		if (operationtypeManager == null) {
			operationtypeManager = new OperationTypeManager();
		}
		return operationtypeManager;
	}
	
	public ArrayList<OperationType> getOperationTypes() {
		ArrayList<OperationType> collOfOperationTypes = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OPERATION_TYPE, null);
		
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
			invocationBuilder = this.webTargetOperationtypeService.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfOperationTypes = response.readEntity(new GenericType<ArrayList<OperationType>>() {
				});
				LOGGER.info("[OperationType] [GET]: OperationTypes");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_TYPE, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfOperationTypes;
	}
}