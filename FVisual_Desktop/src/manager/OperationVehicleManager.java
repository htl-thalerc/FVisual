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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import bll.ClassTypes;
import bll.EnumCRUDOption;
import bll.OperationVehicle;
import handler.CentralHandler;
import handler.ExceptionHandler;

public class OperationVehicleManager {
	private static OperationVehicleManager operationVehicleManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetOperationVehicleServiceForBase = this.webTarget.path(CentralHandler.CONST_BASE_URL);
	private WebTarget webTargetOperationVehicleServiceForOperations = this.webTarget
			.path(CentralHandler.CONST_OPERATION_URL);
	private WebTarget webTargetOperationVehilceService = this.webTarget.path(CentralHandler.CONST_VEHICLE); // .../fahrzeuge
	private static final Logger LOGGER = LogManager.getLogger(OperationVehicleManager.class.getName());

	public static OperationVehicleManager getInstance() {
		if (operationVehicleManagerInstance == null) {
			operationVehicleManagerInstance = new OperationVehicleManager();
		}
		return operationVehicleManagerInstance;
	}

	// Basic
	public ArrayList<OperationVehicle> getVehicles() {
		ArrayList<OperationVehicle> collOfVehicles = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OPERATION_VEHICLE,
				null);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();
		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = this.webTargetOperationVehilceService.request(MediaType.APPLICATION_JSON)
					.headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfVehicles = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
				});
				LOGGER.info("[OperationVehicleManager] [GET]: OperationVehiles");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfVehicles;
	}
	
	public ArrayList<OperationVehicle> getVehiclesWithOperationAttr() {
		ArrayList<OperationVehicle> collOfVehicles = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					"[{\"description\":\"BEZEICHNUNG\", \"operationVehicleId\":\"ID\", \"base\":{\"baseId\":\"ID_STUETZPUNKT\"}, \"operation\":{\"operationId\":\"ID_EINSATZ\"}}]");
			invocationBuilder = this.webTargetOperationVehilceService.request(MediaType.APPLICATION_JSON)
					.headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfVehicles = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
				});
				LOGGER.info("[OperationVehicleManager] [GET]: OperationVehiles");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfVehicles;
	}

	// Base Services
	public ArrayList<OperationVehicle> getVehiclesFromBase(int baseId) {
		ArrayList<OperationVehicle> collOfVehicles = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OPERATION_VEHICLE,
				null);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();
		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));

		WebTarget webTargetGetAllVehicles = this.webTargetOperationVehicleServiceForBase
				.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE);
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = webTargetGetAllVehicles.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfVehicles = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
				});
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfVehicles;
	}

	public ArrayList<OperationVehicle> getVehicleByIdFromBase(int baseId, int vehicleId) {
		ArrayList<OperationVehicle> foundedVehicle = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;

		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OPERATION_VEHICLE,
				null);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();
		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));

		WebTarget webTargetGetAll = this.webTargetOperationVehicleServiceForBase
				.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleId);
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				foundedVehicle = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
				});
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedVehicle;
	}

	public boolean addVehicleToBase(int baseId, OperationVehicle vehicleObj) {
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OPERATION_VEHICLE, null);

		headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		headers.add(CentralHandler.CONST_METADATA,
				CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
		
		WebTarget webTargetPostVehicle = this.webTargetOperationVehicleServiceForBase
				.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE);
		
		Invocation.Builder invocationBuilder = webTargetPostVehicle.request(MediaType.APPLICATION_JSON).headers(headers);

		String jsonStr = "{\"description\":\"" + vehicleObj.getDescription() + "\"}";

		Response response = invocationBuilder.post(Entity.entity(jsonStr, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.POST);
			return false;
		}
	}

	public boolean deleteVehicleFromBase(int baseId, int vehicleId) {
		WebTarget webTargetRemoveVehicle = this.webTargetOperationVehicleServiceForBase
				.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleId);
		Invocation.Builder invocationBuilder = webTargetRemoveVehicle.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.DELETE);
			return false;
		}
	}

	public boolean updateVehicleFromBase(int baseId, OperationVehicle vehicleObj) {
		WebTarget webTargetUpdateVehicle = this.webTargetOperationVehicleServiceForBase.path(
				String.valueOf(baseId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleObj.getOperationVehicleId());

		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.OPERATION_VEHICLE,
				new ArrayList<String>(Arrays.asList("baseId")));

		headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		headers.add(CentralHandler.CONST_METADATA,
				CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
		Invocation.Builder invocationBuilder = webTargetUpdateVehicle.request(MediaType.APPLICATION_JSON)
				.headers(headers);
		Response response = invocationBuilder.put(
				Entity.entity("{\"description\":\"" + vehicleObj.getDescription() + "\"}", MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.PUT);
			return false;
		}
	}

	// Operation Services
	public ArrayList<OperationVehicle> getVehiclesFromOperation(int operationId) {
		ArrayList<OperationVehicle> collOfVehicles = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllVehicles = this.webTargetOperationVehicleServiceForOperations
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE);
		
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					"[{\"description\":\"BEZEICHNUNG\", \"operationVehicleId\":\"ID\", \"base\":{\"baseId\":\"ID_STUETZPUNKT\"}, \"operation\":{\"operationId\":\"ID_EINSATZ\"}}]");
			invocationBuilder = webTargetGetAllVehicles.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfVehicles = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
				});
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.GET);
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
		WebTarget webTargetGetAll = this.webTargetOperationVehicleServiceForOperations
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE + "?id=" + vehicleId);
		try {
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON)
					.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				foundedVehicle = response.readEntity(OperationVehicle.class);
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedVehicle;
	}

	public boolean addVehicleToOperation(int operationId, OperationVehicle vehicleObj) {
		WebTarget webTargetAddVehicle = this.webTargetOperationVehicleServiceForOperations
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE);
		Invocation.Builder invocationBuilder = webTargetAddVehicle.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(vehicleObj, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.POST);
			return false;
		}
	}

	public boolean deleteVehicleFromOperation(int operationId, int vehicleId) {
		WebTarget webTargetRemoveVehicle = this.webTargetOperationVehicleServiceForOperations
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE + "/" + vehicleId);
		Invocation.Builder invocationBuilder = webTargetRemoveVehicle.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.DELETE);
			return false;
		}
	}

	public boolean updateVehicleFromOperation(int operationId, OperationVehicle vehicleObj) {
		WebTarget webTargetUpdateVehicle = this.webTargetOperationVehicleServiceForOperations
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_VEHICLE + "/"
						+ vehicleObj.getOperationVehicleId());
		Invocation.Builder invocationBuilder = webTargetUpdateVehicle.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(vehicleObj, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.OPERATION_VEHICLE, EnumCRUDOption.PUT);
			return false;
		}
	}
}