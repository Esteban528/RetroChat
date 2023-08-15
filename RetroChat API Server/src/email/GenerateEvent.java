package email;

import java.util.*;

public class GenerateEvent {
	private List<EmailSendListenner> listeners = new ArrayList<>();
	
	public void addEventListenner (EmailSendListenner listener) {
		listeners.add(listener);
	}
	
	public void removeEventListenner (EmailSendListenner listener) {
		listeners.remove(listener);
	}
	
	public void executeEvent (String email) {
		for (EmailSendListenner listener: listeners) {
			listener.actionPerformed(email);
		}
	}
}
