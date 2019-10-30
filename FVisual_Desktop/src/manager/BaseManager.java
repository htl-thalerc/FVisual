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

import bll.Base;

public class BaseManager {
	private static BaseManager baseManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private String resource = "http://192.168.191.148:3030/";
	private WebTarget webTarget = client.target(resource);
	private WebTarget webTargetBaseService = webTarget.path("stuetzpunkte");

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
			invocationBuilder = this.webTargetBaseService.request(MediaType.APPLICATION_JSON).header("authorization",
					"{'username':'heinzi','flow':'management'}");
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			collOfBases = response.readEntity(new GenericType<ArrayList<Base>>() {
			});
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return collOfBases;
	}
}