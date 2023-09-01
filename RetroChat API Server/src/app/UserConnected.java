package app;


import email.EmailSendListenner;
import run.Run;
import users.UserLogin;

public class UserConnected implements EmailSendListenner {
	UserLogin userL;
	
	public UserConnected(String nick, String email, String password, String ip) {	
		userL = new UserLogin(nick, email, password);
		userL.setIp(ip);
	}

	public UserConnected(UserLogin userL) {
		this.userL = userL;
	}

	@Override
	public void actionPerformed(String email) {
		
		System.out.println("Callback > "+email+": "+userL.getId()+" IP? "+userL.getIp());
	}

	public UserLogin getUserL() {
		return userL;
	}

	public void setUserL(UserLogin userL) {
		this.userL = userL;
	}

}
