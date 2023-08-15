package users;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserLogin extends User implements Serializable {
	private String password, action;
	private boolean logged;
	private int id;
	
	public UserLogin (String nick, String email, String password) {
		super(nick, email);
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLogin [action=" + action + ", logged=" + logged + ", id=" + id + "]";
	}
	
}
