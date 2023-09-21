package socket;

import java.util.*;


public class OnReceivedObject {
	boolean busy = false;
	private List<ObjectReceivedListenner> listeners = new ArrayList<>();
	
	public void addEventListenner (ObjectReceivedListenner listener) {
		listeners.add(listener);
	}
	
	public void removeEventListenner (ObjectReceivedListenner listener) {
		Thread thread = new Thread(new RemoveEventObject(listener));
		thread.start();
	}

	public void executeEvent (Object receivedObject) {
		busy = true;
		for (ObjectReceivedListenner listener: listeners) {
			listener.actionPerformed(receivedObject);
		}
		busy = false;
	}
	
	class RemoveEventObject implements Runnable{
		private ObjectReceivedListenner listener;
		
		public RemoveEventObject (ObjectReceivedListenner listener){
			this.listener = listener;
		}
		
		@Override
		public void run() {
			do {
				if (!busy) {
					listeners.remove(listener);
				}
				
			} while(busy);
			Thread.currentThread().interrupt();
		}
		
	}

}
