package users;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class UserLogin extends User implements Serializable {
	private String password, action="";
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(action, id, logged, password);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLogin other = (UserLogin) obj;
		return id == other.id && Objects.equals(getEmail(), other.getEmail());
	}

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
