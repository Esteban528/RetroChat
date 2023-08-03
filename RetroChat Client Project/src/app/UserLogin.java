package app;

import java.io.Serializable;

public class UserLogin implements Serializable {
	private String email, password;
	
	public UserLogin (String email, String password) {
		this.email = email;
		this.password = password;
	}

	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
