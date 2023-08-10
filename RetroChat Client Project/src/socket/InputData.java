package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

class InputData implements Runnable{
	Thread thread;
	ServerConnect serverConnect;
	
	public InputData(ServerConnect serverConnect) {
		// TODO Auto-generated constructor stub
		this.serverConnect = serverConnect;
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket clientSocket = new ServerSocket(9291);
			while(true) {
				Thread thread = new Thread(new InputHandler(clientSocket.accept()));
				thread.start(); 
			}
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Input errors: "+e.getMessage());
		}
	}
	
	class InputHandler implements Runnable {
		private Socket socket;
		
		public InputHandler(Socket socket) {
			this.socket = socket;
		}
			
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
				
				serverConnect.getReceivedObjectListenner().executeEvent(objectInput);
				
				objectInput.close();
				socket.close();
				Thread.interrupted();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error al recibir: "+e.getMessage());
			}
		}
		
	}
}



