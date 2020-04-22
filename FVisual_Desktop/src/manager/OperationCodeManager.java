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
import bll.OperationCode;
import handler.CentralHandler;
import handler.ExceptionHandler;

public class OperationCodeManager {
	private static OperationCodeManager operationcodeManager = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOperationcodeService = this.webTarget.path(CentralHandler.CONST_OPERATION_CODE);
	private static final Logger LOGGER = LogManager.getLogger(RankManager.class.getName());
	
	public static OperationCodeManager getInstance() {
		if (operationcodeManager == null) {
			operationcodeManager = new OperationCodeManager();
		}
		return operationcodeManager;
	}
	
	public ArrayList<OperationCode> getOperationCodes() {
		ArrayList<OperationCode> collOfOperationCodes = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OPERATION_CODE, null);
		
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
			invocationBuilder = this.webTargetOperationcodeService.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfOperationCodes = response.readEntity(new GenericType<ArrayList<OperationCode>>() {
				});
				LOGGER.info("[OPERATION_CODE] [GET]: OperationCodes");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_CODE, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfOperationCodes;
	}
}
