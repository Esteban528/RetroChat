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
import java.net.UnknownHostException;
import java.util.Properties;

import users.*;

public class ServerConnect{
	public OutputData outputData;
	private Properties socketProperties;
	private OnReceivedObject receivedListenner;

	
	public ServerConnect () throws ConnectException {
		ReadNetworkData networkFile = new ReadNetworkData();
		
		try {
			socketProperties = networkFile.getConfigSocketData();
			
			String ip = socketProperties.getProperty("ip");
			int port = Integer.parseInt(socketProperties.getProperty("port"));
			
			System.out.println("output charging ");
			//OutputTraffic thread
			outputData = new OutputData(ip, port);
			//Thread outputDataThread = new Thread(outputData);
			//outputDataThread.start();
			
			System.out.println("input charging ");
			//Input traffic thread
			Thread inputDataThread = new Thread(new InputData(this));
			inputDataThread.start();
			
			System.out.println("Sucessfull");
			//System.out.println(InetAddress.getLocalHost().getHostAddress());
			
			//Generate events
			receivedListenner = new OnReceivedObject();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	
		
	}
	
	public OnReceivedObject getReceivedObjectListenner() {
		return receivedListenner;
	}
	

}



