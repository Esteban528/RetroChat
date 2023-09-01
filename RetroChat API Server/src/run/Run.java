package run;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import app.ReadConfigData;
import app.UsersManager;
import db.DatabaseConnection;
import email.EmailConnect;
import socket.ClientConnect;

public class Run {
	public static ReadConfigData configData;
	public static EmailConnect email;
	public static ClientConnect connection;
	public static UsersManager userData;
	public static DatabaseConnection database;


	public static void main(String[] args) {
		boolean error = false;
		// TODO Auto-generated method stub
		configData = new ReadConfigData();
		userData = new UsersManager();

		email = new EmailConnect(configData.getPropertyFile());

		try {
			database = new DatabaseConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DB Error: " + e.getMessage());
			error = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("DB Error - Archivo de configuración no se encuentra");
			error = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("DB Error IOException: " + e.getMessage());
			error = true;
		}
		
		System.out.println(database == null ? "Error" : "Funciona bien");

		if(!error)
			connection = new ClientConnect();

	}

}
