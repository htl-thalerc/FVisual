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

import bll.Rank;
import handler.CentralHandler;

public class RankManager {
	private static RankManager rankManager = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetRankService = this.webTarget.path(CentralHandler.CONST_RANK);
	
	public static RankManager getInstance() {
		if (rankManager == null) {
			rankManager = new RankManager();
		}
		return rankManager;
	}
	//Operation Services
	public ArrayList<Rank> getRanks() {
		ArrayList<Rank> collOfRanks = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		try {
			invocationBuilder = this.webTargetRankService.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfRanks = response.readEntity(new GenericType<ArrayList<Rank>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfRanks;
	}
}