package manager;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import bll.OperationVehicle;

public class OperationVehicleManager {
	private static OperationVehicleManager operationVehicleManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private String resource = "http://localhost:3030/";
	private WebTarget webTarget = client.target(resource);
	private WebTarget webTargetOperationVehicleService = webTarget
			.path("01_Schulbedarf_Webservice_Assek/api/actiontypes");

	public static OperationVehicleManager getInstance() {
		if (operationVehicleManagerInstance == null) {
			operationVehicleManagerInstance = new OperationVehicleManager();
		}
		return operationVehicleManagerInstance;
	}

	public ArrayList<OperationVehicle> getOperationVehicles() {
		ArrayList<OperationVehicle> collOfOperationVehicles = null;

		Invocation.Builder invocationBuilder = null;
		Response response = null;
		try {
			invocationBuilder = this.webTargetOperationVehicleService.request(MediaType.APPLICATION_JSON);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			collOfOperationVehicles = response.readEntity(new GenericType<ArrayList<OperationVehicle>>() {
			});
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return collOfOperationVehicles;
	}
}