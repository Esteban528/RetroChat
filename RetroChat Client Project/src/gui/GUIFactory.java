package gui;

import app.ContactsData;
import app.DriverMessage;
import app.Notification;
import run.Start;

public class GUIFactory {
	public static final String LOGIN = "login", REGISTER = "registers", APP="full-app";
	
	public void createWindow(String nameFrame) {
		new createFrame(nameFrame);
	}
	
	class createFrame implements Runnable {
		private String nameFrame;
		public createFrame (String nameFrame) {
			this.nameFrame = nameFrame;
			Thread threadFrame = new Thread(this);
			threadFrame.start();
		}
		
		@Override
		public void run() {
			switch (nameFrame) {
			case LOGIN:
				LoginFrame guiPanelLogin = new LoginFrame(new LoginPanel());
				guiPanelLogin.open();
				break;
			case REGISTER:
				LoginFrame guiPanelRegister = new LoginFrame(new RegisterUserPanel());
	        	guiPanelRegister.open();
	        	break;
			case APP:	
				AppFrame guiPanel = new AppFrame();
				guiPanel.open();
				Start.driverMessages = new DriverMessage();
				Notification.i().send("RetroChat", "Iniciaste sesión con éxito", false);
				break;
			default:
				System.out.println("Error "+ nameFrame + " not found");
			}
			
		}
	}
}

