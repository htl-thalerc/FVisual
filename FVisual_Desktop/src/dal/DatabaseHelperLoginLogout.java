package dal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bll.User;

public class DatabaseHelperLoginLogout {
	private static DatabaseHelperLoginLogout instance = null;
	
	public static DatabaseHelperLoginLogout newInstance() {
		
		if (instance == null) {
			instance = new DatabaseHelperLoginLogout();
		}
		return instance;
	}

	Client client = ClientBuilder.newClient();
	// TODO: auslagern in Config
	String resourceLogin = "http://192.168.191.148:3030/login";
	String resourceLogout = "http://localhost:3030/LogoutManagement";
	WebTarget webTargetLogin = client.target(resourceLogin);
	WebTarget webTagetLogout = client.target(resourceLogout);
	
	public boolean loginUser(User user) {
		Invocation.Builder invocationBuilder = this.webTargetLogin.request(MediaType.APPLICATION_JSON).header("flow", "management");
		Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
}
