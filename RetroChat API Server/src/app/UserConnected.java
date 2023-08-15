package app;

import email.EmailSendListenner;
import run.Run;
import users.*;

public class UserConnected extends UserLogin implements EmailSendListenner  {

	public UserConnected(String nick, String email, String password, String ip) {
		super(nick, email, password);
		this.setIp(ip);
		
	}
	
	public UserConnected(UserLogin user, int id) {
		super(user.getNick(), user.getEmail(), user.getPassword());
		this.setIp(user.getIp());
		this.setId(id);
	}
	
	@Override
	public void actionPerformed(String email) {
		
	}
		
}
