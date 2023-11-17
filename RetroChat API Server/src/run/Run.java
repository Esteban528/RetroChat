package run;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import app.ReadConfigData;
import app.UsersManager;
import chat.ChatController;
import db.DatabaseConnection;
import email.EmailConnect;
import socket.ClientConnect;

public class Run {
	public static ReadConfigData configData;
	public static EmailConnect email;
	public static ClientConnect connection;
	public static UsersManager userData;
	public static DatabaseConnection database;
	public static ChatController chatController;


	public static void main(String[] args) {
		boolean error = false;
		boolean retry = false;
		byte counter = 0;
		
		// TODO Auto-generated method stub
		configData = new ReadConfigData();
		userData = new UsersManager();
		System.out.println("RetroChat server starting...");
		System.out.println(configData.getPropertyFile().getProperty("dbUser"));

		email = new EmailConnect(configData.getPropertyFile());
		
		chatController=new ChatController();

		do {
			if (counter > 8) break;
			try {
				database = new DatabaseConnection();
				if (!database.getConnection().isClosed()) {
					error = false;
					retry = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DB Error: " + e.getMessage());
				error = true;
				retry = true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("DB Error - Archivo de configuración no se encuentra");
				error = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("DB Error IOException: " + e.getMessage());
				error = true;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			counter++;
		} while (retry == true);
		
			

		if(!error)
			connection = new ClientConnect();

	}

}
