package socket;

import java.util.*;

public class OnReceivedObject {
	private List<ObjectReceivedListenner> listeners = new ArrayList<>();
	
	public void addEventListenner (ObjectReceivedListenner listener) {
		listeners.add(listener);
	}
	
	public void removeEventListenner (ObjectReceivedListenner listener) {
		listeners.remove(listener);
	}
	
	public void executeEvent (Object receivedObject) {
		for (ObjectReceivedListenner listener: listeners) {
			listener.actionPerformed(receivedObject);
		}
	}
}
