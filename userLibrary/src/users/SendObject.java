package users;

import java.io.Serializable;

public class SendObject implements Serializable{
	public String[] getStrings() {
		return strings;
	}

	public void setStrings(String[] strings) {
		this.strings = strings;
	}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	private UserLogin userL;
	
	private String [] strings;
	private String action="", text;
	private boolean bool;
	
	private int code;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public SendObject (UserLogin userL, String action) {
		this.userL = userL;
	}

	public UserLogin getUserL() {
		return userL;
	}

	public void setUserL(UserLogin userL) {
		this.userL = userL;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
