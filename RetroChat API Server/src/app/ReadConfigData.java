package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import run.Run;

public class ReadConfigData {
	public static final String SERVERNAME = "RetroChatServer";
	public static final String ROUTE_OF_NETWORKCONFIG = SERVERNAME+"/server.conf";
	public Properties propertiesFile;

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
				
				properties.setProperty("dbName", "retrochat_crud");
				properties.setProperty("dbIP", "127.0.1.1");
				properties.setProperty("dbPort", "3306");
				
				properties.setProperty("dbUser", "root");
				properties.setProperty("dbPassword", "este5ban9");

				properties.setProperty("ipEmailHost", "127.0.0.1");
				properties.setProperty("portEmailHost", "9091");
				properties.setProperty("userEmailHost", "admin");
				properties.setProperty("passwordEmailHost", "admin");
				


				properties.store(new FileWriter(ROUTE_OF_NETWORKCONFIG), "Server configuration file");
			}

			propertiesFile = new Properties();
			propertiesFile.load(new FileReader(ROUTE_OF_NETWORKCONFIG));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public String getProperty(String propertyName) throws FileNotFoundException, IOException {
		return getPropertyFile().getProperty(propertyName);
	}

	public Properties getPropertyFile() {

		return this.propertiesFile;
	}
}
