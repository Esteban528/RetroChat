package app;

import java.io.Serializable;

public class UserLogin implements Serializable {
	private String name, email, password, action;
	private boolean logged;
	
	public UserLogin (String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getAction() {
		return this.action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public boolean isLogged() {
		return logged;
	}


	public void setLogged(boolean logged) {
		this.logged = logged;
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
