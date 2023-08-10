package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ReadConfigData {
	public static final String SERVERNAME = "RetroChatServer";
	public static final String ROUTE_OF_NETWORKCONFIG = SERVERNAME+"/config.server";

	public ReadConfigData() {
		try {
	
			File folder = new File(SERVERNAME);
			if (!folder.isDirectory()) {
				folder.mkdirs();
			}
			
			File configFile = new File(ROUTE_OF_NETWORKCONFIG);
			if (!configFile.exists()) {
				Properties properties = new Properties();
				properties.setProperty("port", "9090");
				properties.setProperty("email", "<email>");
				properties.setProperty("password", "<password>");
				properties.store(new FileWriter(ROUTE_OF_NETWORKCONFIG), null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public String getProperty(String propertyName) throws FileNotFoundException, IOException {
		return getConfigSocketData().getProperty(propertyName);
	}
	
	public Properties getConfigSocketData() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(ROUTE_OF_NETWORKCONFIG));
		
		return properties;
	}
}
