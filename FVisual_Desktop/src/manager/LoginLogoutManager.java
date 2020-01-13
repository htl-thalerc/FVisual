package manager;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import bll.Member;

public class LoginLogoutManager {
	private static LoginLogoutManager instance = null;
	
	public static LoginLogoutManager newInstance() {
		
		if (instance == null) {
			instance = new LoginLogoutManager();
		}
		return instance;
	}

	Client client = ClientBuilder.newClient();
	// TODO: auslagern in Config
	String resourceLogin = "http://192.168.191.148:3030/login";
	String resourceLogout = "http://localhost:3030/LogoutManagement";
	String ressourceGetMember = "http://localhost:3030/Mitglieder/{username}";
	WebTarget webTargetLogin = client.target(resourceLogin);
	WebTarget webTagetLogout = client.target(resourceLogout);
	WebTarget webTargetGetMember = client.target(ressourceGetMember);
	
	public boolean loginUser(Member user) {
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
	
	public Member getMemberByUsername(String Username) throws Exception {

		String retAktionsTypAsJson = null;
		List<Member> MemberAsList = null;

		Invocation.Builder invocationBuilder = null;
		Response response = null;

		try {
			WebTarget temp = webTargetGetMember.resolveTemplate("username", Username);
			invocationBuilder = temp.request(MediaType.APPLICATION_JSON);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			MemberAsList = response.readEntity(new GenericType<List<Member>>() {
			});
			System.out.println(response.getStatus());

		} catch (JsonSyntaxException ex) {
			throw new Exception(retAktionsTypAsJson);
		}

		return MemberAsList.get(0);
	}
}
