package handler;

import java.io.IOException;

import app.Main;
import bll.Member;
import javafx.fxml.FXMLLoader;
import util.PropertyManager;

public class CentralHandler {
	private static CentralHandler helper;

	public static final String CONST_AUTHORIZATION = "authorization";

	public static final String CONST_BASE_URL = "stuetzpunkte";
	public static final String CONST_OPERATION_URL = "einsaetze";

	public static final String CONST_MEMBER_URL = "mitglieder";
	public static final String CONST_VEHICLE = "fahrzeuge";
	public static final String CONST_OTHER_ORGANISATION = "andere_organisation";
	public static final String CONST_RANK = "dienstgrade";

	private Member member;
		
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

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}