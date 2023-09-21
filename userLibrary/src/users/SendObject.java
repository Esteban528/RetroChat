package users;

import java.io.Serializable;

public class SendObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text, sender, action, time;
	private int chat_id;
	private UserLogin userL;
	private String ip;
	private User userSender;
	
	public SendObject (User user) {
		setUserSender(user);
	}
	
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUserSender() {
		return userSender;
	}

	private void setUserSender(User userSender) {
		this.userSender = userSender;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public UserLogin getUserL() {
		return userL;
	}

	public void setUserL(UserLogin userL) {
		this.userL = userL;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getChat_id() {
		return chat_id;
	}

	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}
	
}
