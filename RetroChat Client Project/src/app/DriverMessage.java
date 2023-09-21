package app;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;

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
	
	
	public void updateTextArea(CustomDocument area, User user) {
		SendObject sendObj = new SendObject(user);
		sendObj.setUserL(Start.userLogin);
		sendObj.setAction("get-messages");
		this.textArea = area;
		try {
			Start.api.outputData.send(sendObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message, User user) {
		SendObject sendObj = new SendObject(user);
		sendObj.setUserL(Start.userLogin);
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
				addMessage(sObj);
			});
			
			this.temporalMessages = messages;
			
		}else if (receivedObject instanceof SendObject) {
			SendObject sObj = (SendObject) receivedObject;
			addMessage(sObj);
		}
	}
	
	public void addMessage(SendObject sObj) {
		String nick;
		Boolean you = false;
		if (sObj.getUserSender().getEmail().equals(Start.userLogin.getEmail())) {
			nick = "Tú";
			you = true;
		}else
			nick = sObj.getUserSender().getNick();
		
		textArea.append(nick, sObj.getText(), sObj.getTime(), you);
		
	}
}
