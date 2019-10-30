package app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import util.PropertyManager;

public class CentralHandler {
	private static CentralHandler helper;
	
	public static final String CONST_AUTHORIZATION = "authorization";
	//Consts for base URLs
	public static final String CONST_BASE_URL = "stuetzpunkte";
	public static final String CONST_MEMBER_URL = "mitglieder";
	public static final String CONST_VEHICLE = "fahrzeuge";

	public static CentralHandler getInstance() {
		if (helper == null) {
			helper = new CentralHandler();
		}
		return helper;
	}

	public static FXMLLoader loadFXML(final String path) {
		return new FXMLLoader(Main.class.getResource(path));
	}
	
	public String getRessource() {
		PropertyManager pm = null;
		try {
			pm = PropertyManager.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pm.readProperty("http") + "://" + pm.readProperty("ipAddress") + ":" + pm.readProperty("port");
	}
	
	public String getHeaderAuthorization() {
		return "{'username':'heinzi','flow':'management'}";
	}
}