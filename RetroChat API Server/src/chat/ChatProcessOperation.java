package chat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.security.Timestamp;

import app.UsersManager;
import db.ChatDriver;
import run.Run;
import users.SendObject;
import users.User;
import users.UserLogin;

public class ChatProcessOperation implements Runnable{
	private UserLogin userL;
	private String action, text;
	private ChatDriver driver;
	private User userSender;
	
	
	public ChatProcessOperation (SendObject sObj, ChatDriver driver) {
		this.userL = sObj.getUserL();
		this.userSender = sObj.getUserSender();
		this.action = sObj.getAction();
		this.driver = driver;
		this.text = sObj.getText();
	}

	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		switch (action) {
		case "get-messages":
			try {
				ArrayList<SendObject> messagesList = driver.getMessages(userL, userSender);
				
				Run.connection.send(userL, messagesList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				showErrors(e, action);
			} 
//			getMessages();
			break;
		case "add-message":
			try {
				driver.addMessage(userL, userSender, text);
				SendObject messageObj = new SendObject(userL);
				messageObj.setText(text);
				messageObj.setTime("Ahora");
				UserLogin sender = UsersManager.getUserConnectedFromEmail(userSender.getEmail());
				if (sender != null) {
					Run.connection.send(sender, messageObj);
				}
				Run.connection.send(userL, messageObj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				showErrors(e, action);
			}
			break;
		}
		Thread.currentThread().interrupt();
	}
	
	private void showErrors(SQLException e, String action) {
		System.out.println("--------------------");
		System.out.println("Error "+action+": "+userL+"  "+userSender);
		System.out.println(e.getMessage() + "\n");
		System.out.println(e.getSQLState());
		System.out.println("--------------------");
	}
}
