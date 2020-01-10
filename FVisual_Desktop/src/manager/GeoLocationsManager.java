package manager;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;
import com.lynden.gmapsfx.javascript.object.LatLong;

public class GeoLocationsManager {
private static GeoLocationsManager instance = null;
	
	public static GeoLocationsManager newInstance() {
		
		if (instance == null) {
			instance = new GeoLocationsManager();
		}
		return instance;
	}

	Client client = ClientBuilder.newClient();
	// TODO: auslagern in Config
	String CoordinatesToAdress = "https://api.mapbox.com/geocoding/v5/mapbox.places/{longitude},{latitude}.json?access_token=pk.eyJ1IjoiZmxvMTkwNjQiLCJhIjoiY2szaDAzeHE0MDZ3dTNjbXF5ZzZid2lnaCJ9.0nOQjUertFDeozh7OjnypQ";
	String AdressToCoordinates = "https://api.mapbox.com/geocoding/v5/mapbox.places/{Adress}.json?access_token=pk.eyJ1IjoiZmxvMTkwNjQiLCJhIjoiY2szaDAzeHE0MDZ3dTNjbXF5ZzZid2lnaCJ9.0nOQjUertFDeozh7OjnypQ";
	WebTarget webTargetCoordinatesToAdress = client.target(CoordinatesToAdress);
	WebTarget webTargetAdressToCoordinates = client.target(AdressToCoordinates);
	
	// access-key: pk.eyJ1IjoiZmxvMTkwNjQiLCJhIjoiY2szaDAzeHE0MDZ3dTNjbXF5ZzZid2lnaCJ9.0nOQjUertFDeozh7OjnypQ
	


	public String reverseGeoCoding(LatLong position) throws Exception {
		String adress = "";

		Invocation.Builder invocationBuilder = null;
		Response response = null;

		try {
			WebTarget temp = webTargetCoordinatesToAdress.resolveTemplate("longitude", position.getLongitude()).resolveTemplate("latitude", position.getLatitude());
			invocationBuilder = temp.request(MediaType.APPLICATION_JSON);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			ArrayList result = new ArrayList(Arrays.asList(response.readEntity(String.class).split("\"")));
			adress = (String) result.get(result.indexOf("place_name")+2);

		} catch (JsonSyntaxException ex) {
			throw new Exception();
		}
		return adress;
	}
	
	
	public String GeoCoding(String Adress) throws Exception {
		String ret = null;

		Invocation.Builder invocationBuilder = null;
		Response response = null;

		try {
			WebTarget temp = webTargetAdressToCoordinates.resolveTemplate("Adress",Adress);
			invocationBuilder = temp.request(MediaType.APPLICATION_JSON);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			ret = response.readEntity(String.class);
		} catch (JsonSyntaxException ex) {
			throw new Exception();
		}
		return ret;
	}
}