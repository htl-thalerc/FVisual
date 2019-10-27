package app;

import javafx.fxml.FXMLLoader;

public class Helper {
	private static Helper helper;
	
	public static Helper getInstance() {
		if(helper == null) {
			helper = new Helper();
		}
		return helper;
	}
	
	public static FXMLLoader loadFXML(final String path) {
	    return new FXMLLoader(Main.class.getResource(path));
	}
}