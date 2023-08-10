package socket;

import java.io.*;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import app.User;
import app.UserLogin;
import run.Run;

public class ClientConnect implements Runnable{
	//Socket socketOutput;
	//ObjectOutputStream objectOutput;
	
	public ClientConnect () {
		Thread threadSocket = new Thread(this);
		threadSocket.start();
		System.out.println("Servidor encendido");
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			
			int port = Integer.parseInt(Run.configData.getProperty("port"));
			

			if(port != 0) {
				try (ServerSocket serverSocket = new ServerSocket(port)) {
					while(true) {
						//Input
						Thread clientThread = new Thread(new InputHandler(serverSocket.accept()));
						clientThread.start();
					}
				}
			}else {
				System.out.println("Invalid port. Please verify server configuration");
			}
			
			
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Listen error: "+e.getMessage());
		} 
	}
	
	public void send(User user, Object send) {
		Socket clientSocket;
		try {
			clientSocket = new Socket(user.getIp(), 9291);
			ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOutput.writeObject(send);
			objectOutput.flush();
			clientSocket.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("La ip ya no está conectada");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Send: "+ e.getMessage());
		}
	}
	
	private class InputHandler implements Runnable{
		Socket socket;
		public InputHandler (Socket socket) throws IOException, ClassNotFoundException  {
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ObjectInputStream objectInput;
			try {
				objectInput = new ObjectInputStream(socket.getInputStream());
				Object receivedObject = objectInput.readObject();
				String ip = socket.getInetAddress().getHostAddress();
			    if (receivedObject instanceof User) {
			        User user = (User) receivedObject;
					user.setIp(ip);
					System.out.println(user.getNick()+" connected from "+user.getIp());
					
					//Output
					send(user, new String("Usuario conectado " + user));
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Socket listen error: "+e.getMessage());
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Listen error: "+e.getMessage());
			}finally {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("El socket fue cerrado inesperadamente");
				}
			}
			
		}
	}
}


