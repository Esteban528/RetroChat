package run;
import gui.*;
import socket.*;

import java.io.*;
import java.net.ConnectException;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import app.*;

public class Start {
	public static String s = FileSystems.getDefault().getSeparator();
	public static final String ROUTE_DEFAULT_FILES_FOLDER = "files";
	public static String ROUTE_PERSONAL_DATA;
	public static final String CONFIG_DIR = "serverData";

	public static void main(String[] args) { 
		if (s.equals("\\")) { 
			ROUTE_PERSONAL_DATA = "C:\\RetroChat";
			
		}else {
			ROUTE_PERSONAL_DATA = "~/RetroChat";
		}
		
		File folder = new File(ROUTE_PERSONAL_DATA+s+CONFIG_DIR);
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}
		
		
		// TODO Auto-generated method stub
		ContactsData updateContactsList = new ContactsData();
		
		LoginFrame guiPanelLogin = new LoginFrame();
		guiPanelLogin.open();
		
		AppFrame guiPanel = new AppFrame(updateContactsList.getContactsFile());
		guiPanel.open();
		

		try {
			new ConnectToServer();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println("El servidor rechazó la conexión");
		}
	}

}

