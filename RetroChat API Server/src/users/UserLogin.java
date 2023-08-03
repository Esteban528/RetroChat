package users;

import java.io.Serializable;

public class UserLogin implements Serializable {
	private String name, email, password;
	
	public UserLogin (String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
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
