package app;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import db.UserDBDriver;
import run.Run;
import users.UserLogin;

public class UsersManager {
	private LinkedList<UserLogin> userTemporals;
	private UserDBDriver usersController;
	private static LinkedList<UserLogin> usersConnected = new LinkedList<>();
	public static UserLogin temporalUser;

	public UsersManager () {
		this.userTemporals = new LinkedList<>();
		this.usersController = new UserDBDriver();
		   /*Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	System.out.println("-------------[Users Connections]----------------");
	        		UsersManager.getUsersConnected().forEach(userL ->{
	        			System.out.println(" -->"+userL.getEmail());
	        		});
	        		System.out.println("------------------------------------------------------");
	            }
	        }, 0, 10000); */
		
	}
	
	public static UserLogin getUserConnectedFromEmail(String email) {
		temporalUser = null;
		usersConnected.forEach(userLogin ->{
			if (userLogin.getEmail().equals(email)) {
				UsersManager.temporalUser = userLogin;
			}
		});
		
		return temporalUser;
	}
	
	public static LinkedList<UserLogin> getUsersConnected() {
		return usersConnected;
	}
	
	private static void addUserConnect(UserLogin userL) {
		usersConnected.add(userL);
	}
	
	public static void removeUserConnect(UserLogin userL) {
		usersConnected.remove(userL);
	}

	public void addUser(UserLogin userL) {
		userTemporals.add(userL);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public UserLogin getUserFromEmail (String email) {
		for (UserLogin userLogin : userTemporals) {
			if (userLogin.equals(email)) {
				return userLogin;
			}
		}
		return null;
	}

	public void removeUser(UserLogin userL) {
		userTemporals.remove(userL);
	}

	public void removeUser(int userIndex) {
		userTemporals.remove(userIndex);
	}

	public void action (UserLogin userL) throws SQLException {
		switch(userL.getAction()) {
		case "createUser":	
			String email = userL.getEmail();
			boolean emailAvaible = usersController.isUser(email);
			if (!emailAvaible) {
				int code;
				code = DriverData.generateVerificationCode(userL.getEmail());
				
				userL.setId(code);
				userL.setAction("verification-code");
				this.addUser(userL);
				sendMessageEmail(userL, "Código de verificación", "Tu código de verificación es: "+code);
								
			}else {
				userL.setId(0);
				userL.setAction("email-occuped");
			}
			Run.connection.send(userL, userL);
			break;
		case "email-verificated": 
			this.removeUser(userL);
			boolean accert = usersController.createUser(userL) ;
			String message = accert ? "Account created succesfull ["+userL.getEmail()+"]" : "Error at create account";
			System.out.println(message);
			break;
		case "login":
			String password = usersController.getPasswordFromEmail(userL.getEmail());
			if (password != null) {
				if (password.equals(userL.getPassword())) {
					userL.setLogged(true);
					userL.setAction("user-logged");
					addUserConnect(userL);
				}else {
					userL.setAction("password-incorrect");
				}
			}else {
				userL.setAction("email-incorrect");
			}
			Run.connection.send(userL, userL);
			break;
		case "logout":
			removeUserConnect(userL);
			break;
		}
		
	}

	public void sendMessageEmail (UserLogin userL, String subject, String message) {
		Run.email.send(userL.getEmail(),"RetroChat "+subject, message);
		
	}

	// DB Methods
	//crear usuarios, enviar codigo al correo
}

