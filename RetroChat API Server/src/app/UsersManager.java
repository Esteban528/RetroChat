package app;

import java.sql.SQLException;
import java.util.LinkedList;

import db.UserDBDriver;
import run.Run;
import users.UserLogin;

public class UsersManager {
	private LinkedList<UserLogin> userTemporals;
	private UserDBDriver usersController;


	public UsersManager () {
		this.userTemporals = new LinkedList<>();
		this.usersController = new UserDBDriver();
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
		}	
	}

	public void sendMessageEmail (UserLogin userL, String subject, String message) {
		Run.email.send(userL.getEmail(),"RetroChat "+subject, message);
		
	}

	// DB Methods
	//crear usuarios, enviar codigo al correo
}
