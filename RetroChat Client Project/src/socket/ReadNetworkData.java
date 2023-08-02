package socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Properties;

import run.Start;

public class ReadNetworkData {
	static String s = Start.s;
	public static final String ROUTE_OF_NETWORKCONFIG = Start.ROUTE_PERSONAL_DATA+s+Start.CONFIG_DIR+s+"config.server";

	public ReadNetworkData() {
		try {
	
			File configFile = new File(ROUTE_OF_NETWORKCONFIG);
			if (!configFile.exists()) {
				Properties properties = new Properties();
				properties.setProperty("ip", "10.0.0.60");
				properties.setProperty("port", "9090");
				properties.store(new FileWriter(ROUTE_OF_NETWORKCONFIG), null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public Properties getConfigSocketData() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(ROUTE_OF_NETWORKCONFIG));
		
		return properties;
	}
}
