package email;

import java.util.ArrayList;
import java.util.List;

public class GenerateEvent {
	private boolean busy = false;
	private List<EmailSendListenner> listeners = new ArrayList<>();

	public void addEventListenner (EmailSendListenner listener) {
		listeners.add(listener);
	}

	public void removeEventListenner (EmailSendListenner listener) {
		System.out.println(busy);
		do {
			if (!busy) {
				listeners.remove(listener);
				System.out.println("[remove event listenner from Email sender]: Funciona");
			}
		}while (busy);
		
	}
	

	public void executeEvent (String email) {
		for (EmailSendListenner listener: listeners) {
			this.busy = true;
			listener.actionPerformed(email);
			System.out.println("EJECUTANDOSE EVENTO EMAIL");
		}
		this.busy = false;

	}
}
 