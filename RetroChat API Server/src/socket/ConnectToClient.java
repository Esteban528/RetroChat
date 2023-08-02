package socket;

import java.io.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import app.*;

public class ConnectToClient implements Runnable{
	
	
	public ConnectToClient () {
		Thread threadSocket = new Thread(this);
		threadSocket.start();
		System.out.println("Servidor encendido");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket serverSocket = new ServerSocket(9090);
			
			while(true) {
				//Input
				Socket socket = serverSocket.accept();
				DataInputStream dataInput = new DataInputStream(socket.getInputStream());
				ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
				
				Object receivedObject = objectInput.readObject();
			    if (receivedObject instanceof User) {
			        User user = (User) receivedObject;
					
					System.out.println(user.getNick()+" connected from "+user.getIp());
					
					//Output
					Socket clientSocket = new Socket(user.getIp(), 9191);
					ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
					objectOutput.writeUTF("Conectado");
				}
				socket.close();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
