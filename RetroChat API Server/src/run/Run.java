package run;

import socket.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import app.*;
import db.DatabaseConnection;
import email.EmailManager;

public class Run {
	public static ReadConfigData configData;
	public static EmailManager email;
	public static ClientConnect connection;
	public static UsersManager userData;
	public static DatabaseConnection database;
	
	
	public static void main(String[] args) {
		Boolean error = false;
		// TODO Auto-generated method stub
		configData = new ReadConfigData(); 
		userData = new UsersManager();
		
		try {
			email = new EmailManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Email service error: "+ e.getMessage());
			error = true;
		}
		
		/*try {
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
		}*/
		
		if(!error)
			connection = new ClientConnect();
		
	}

}
