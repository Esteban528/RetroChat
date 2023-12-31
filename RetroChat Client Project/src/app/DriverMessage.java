package app;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;

import gui.AppPanels;
import gui.CustomDocument;
import gui.LoginPanel;
import run.Start;
import socket.ObjectReceivedListenner;
import users.SendObject;
import users.User;
import users.UserLogin;

public class DriverMessage implements ObjectReceivedListenner{
	private ArrayList<SendObject> temporalMessages;
	private CustomDocument textArea;
	
	public DriverMessage() {
		Start.api.getReceivedObjectListenner().addEventListenner(this);
	}
	
	
	public void updateTextArea(CustomDocument area) {
		User user = AppData.i().getTemporalUserSelected();
		SendObject sendObj = new SendObject(user);
		sendObj.setUserL(AppData.i().getUserL());
		sendObj.setAction("get-messages");
		this.textArea = area;
		try {
			Start.api.outputData.send(sendObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		User user = AppData.i().getTemporalUserSelected();
		
		SendObject sendObj = new SendObject(user);
		sendObj.setUserL(AppData.i().getUserL());
		sendObj.setText(message);
		sendObj.setAction("add-message");
		try {
			Start.api.outputData.send(sendObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<SendObject> getChatTemporalMessages() {
		return this.temporalMessages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(Object receivedObject) {
		
		// TODO Auto-generated method stub
		if (receivedObject instanceof ArrayList) {
			textArea.restartDocument();
			ArrayList<SendObject> messages = (ArrayList<SendObject>) receivedObject;
			
			messages.forEach(sObj -> {
				addMessage(sObj, false);
			});
			
			this.temporalMessages = messages;
			textArea.updateText();
			
		}else if (receivedObject instanceof SendObject) {
			SendObject sObj = (SendObject) receivedObject;
			addMessage(sObj, true);
			Notification.i().send("Nuevo mensaje", sObj.getUserL().getNick()+": "+sObj.getText(), true);		
		}
	}
	
	public boolean addMessage(SendObject sObj, boolean onlyLoad) {
		
		String nick;
		Boolean you = false;
		if (sObj.getUserSender().getEmail().equals(AppData.i().getUserL().getEmail())) {
			nick = "Tú";
			you = true;
		}else {
			nick = sObj.getUserSender().getNick();
					
		}
		
		
		
		if(!you && !sObj.getUserSender().getEmail().equals(AppData.i().getTemporalUserSelected().getEmail())) {
			if (!Start.updateContactsList.isEmailContact (sObj.getUserSender().getEmail())) {
				sObj.getUserSender().setNick(nick);
				AppPanels.getContactZone().addContact(sObj.getUserSender());
			}
			
			return false;
		}
		
		
		textArea.append(nick, sObj.getText(), sObj.getTime(), you, onlyLoad);
		return true;
	}
}
