package chat;

import db.ChatDriver;
import users.SendObject;
import users.UserLogin;

public class ChatController {
	private ChatDriver driver;
	
	public ChatController () {
		this.driver = new ChatDriver();
	}
	
	public void action (SendObject sObj) {
		if (!sObj.getUserL().getEmail().equals(sObj.getUserSender().getEmail())){
			ChatProcessOperation process = new ChatProcessOperation(sObj, this.driver);
			Thread thread = new Thread(process);
			thread.start();
		}
		
	}
}
