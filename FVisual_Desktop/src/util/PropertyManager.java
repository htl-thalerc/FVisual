package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import util.PropertyManager;

public class PropertyManager {
	private static PropertyManager instance = null;
	private String configFile = "properties.config";
	private Properties properties = new Properties();
	
	public PropertyManager() {
		try {
			this.fillProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PropertyManager getInstance() throws FileNotFoundException, IOException {
		if(PropertyManager.instance == null) {
			instance = new PropertyManager();
			instance.fillProperties();
		}
		return instance;
	}
	
	public void fillProperties() throws FileNotFoundException, IOException {
		try(FileReader reader = new FileReader(this.configFile)) {
			this.properties.load(reader);
		}
	}
	
	public String readProperty(String key) {
		return this.properties.getProperty(key);
	}
	
	public void writeProperty(String key, String value) throws IOException {
		this.properties.setProperty(key, value);
		this.writeToFile();
	}
	
	private void writeToFile() throws IOException {
		try(FileWriter writer = new FileWriter(this.configFile)) {
			this.properties.store(writer, "Properies:");
		}
	}
	
	public void writeBasicProperties() {
		try {
			this.writeProperty("http", "http");
			this.writeProperty("ipAddress", "192.168.191.148");
			this.writeProperty("port", "3030");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}