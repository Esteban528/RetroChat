package app;

import java.util.LinkedList;

import javax.mail.MessagingException;

import email.EmailSendListenner;
import run.Run;
import users.*;

public class UsersManager {
	private LinkedList<UserLogin> users;
	
	public UsersManager () {
		users = new LinkedList<>();
	}
	
	public void addUser(UserLogin userL) {
		users.add(userL);
	}
	
	public UserLogin getUserFromEmail (String email) {
		for (UserLogin userLogin : users) {
			if (userLogin.equals(email)) {
				return userLogin;
			}
		}
		return null;
	}
	
	public void removeUser(UserLogin userL) {
		users.remove(userL);
	}
	
	public void removeUser(int userIndex) {
		users.remove(userIndex);
	}
	
	public void action (UserLogin userL) {
		System.out.println(userL);
		
		int code = DriverData.generateVerificationCode();
		
		UserConnected userTemporal = new UserConnected(userL, code) {
			@Override
			public void actionPerformed(String email) {
				Run.connection.send(this,new SendObject(this, "verification-code"));
				System.out.println("Code to "+email+": "+code);
			}
		};
		
		this.addUser(userTemporal);
		
		sendMessageEmail(userTemporal, "Código de verificación", "Tu código de verificación es: "+code);
	}

	public void sendMessageEmail (UserConnected userTemporal, String subject, String message) {
		try {
			Run.email.emailEvent.addEventListenner(userTemporal);
			Run.email.send(userTemporal.getEmail(),"RetroChat "+subject, message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println("Mail error: "+e.getMessage());
		}
	}

	// DB Methods
	//crear usuarios, enviar codigo al correo
}
