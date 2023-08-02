package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import app.User;

public class ConnectToServer implements Runnable{
	Properties socketProperties;
	public ConnectToServer () throws ConnectException {
		ReadNetworkData networkFile = new ReadNetworkData();
		try {
			socketProperties = networkFile.getConfigSocketData();
			
			String ip = socketProperties.getProperty("ip");
			int port = Integer.parseInt(socketProperties.getProperty("port"));
			
			String myIp = InetAddress.getLocalHost().getHostAddress();
			
			Socket socketSend = new Socket(ip, port);
			
			ObjectOutputStream objectOutput = new ObjectOutputStream(socketSend.getOutputStream());

			
			
			objectOutput.writeObject(new User("Test", "testHola", myIp.toString()));
			
			
			System.out.println(ip+":"+port);
			
			socketSend.close();
			
			Thread inputDataThread = new Thread(this);
			inputDataThread.run();
			
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		try {
			
			ServerSocket clientSocket = new ServerSocket(9191);
			while(true) {
				Socket socket = clientSocket.accept();
				DataInputStream dataInput = new DataInputStream(socket.getInputStream());
				ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
				
				System.out.println(dataInput);
				
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

