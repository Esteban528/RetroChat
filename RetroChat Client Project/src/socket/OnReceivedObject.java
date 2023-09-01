package socket;

import java.util.*;


public class OnReceivedObject {
	boolean busy = false;
	private List<ObjectReceivedListenner> listeners = new ArrayList<>();
	
	public void addEventListenner (ObjectReceivedListenner listener) {
		listeners.add(listener);
	}
	
	public void removeEventListenner (ObjectReceivedListenner listener) {
		do {
			if (!busy) 
				listeners.remove(listener);
		}while (busy);
		
	}

	public void executeEvent (Object receivedObject) {
		for (ObjectReceivedListenner listener: listeners) {
			this.busy = true;
			listener.actionPerformed(receivedObject);
		}
		this.busy = false;
	}
	

}
