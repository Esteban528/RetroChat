package run;
import gui.*;
import socket.*;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import users.*;

import app.*;

public class Start {
	public static String s = FileSystems.getDefault().getSeparator();
	public static final String ROUTE_DEFAULT_FILES_FOLDER = "files";
	public static String ROUTE_PERSONAL_DATA;
	public static final String CONFIG_DIR = "serverData";
	public static ServerConnect api;

	public static void main(String[] args) { 
		
		if (s.equals("\\")) { 
			ROUTE_PERSONAL_DATA = "C:\\RetroChat";
			
		}else {
			ROUTE_PERSONAL_DATA = "/home/"+System.getProperty("user.name")+"/RetroChat";
		}
		
		File folder = new File(ROUTE_PERSONAL_DATA+s+CONFIG_DIR);
		if (!folder.isDirectory()) {
			folder.mkdirs();
			
		}
		System.out.println("Carpeta de configuración leida en: "+ folder.getAbsolutePath());
		
		try {
			api = new ServerConnect();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println("El servidor rechazó la conexión");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		// TODO Auto-generated method stub
		ContactsData updateContactsList = new ContactsData();
		
		LoginFrame guiPanelLogin = new LoginFrame(new LoginPanel());
		guiPanelLogin.open();
		
		AppFrame guiPanel = new AppFrame(updateContactsList.getContactsFile());
		//guiPanel.open();
		

		
	}

}

