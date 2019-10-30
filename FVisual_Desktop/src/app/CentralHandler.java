package app;

import javafx.fxml.FXMLLoader;

public class CentralHandler {
	private static CentralHandler helper;

	public static CentralHandler getInstance() {
		if (helper == null) {
			helper = new CentralHandler();
		}
		return helper;
	}

	public static FXMLLoader loadFXML(final String path) {
		return new FXMLLoader(Main.class.getResource(path));
	}
}