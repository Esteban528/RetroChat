package socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import users.User;

public class Send implements Runnable {
	
	private String ip;
	private int port;
	private Object send;
	private Thread thread;
	
	public Send (User user, Object send) {
		this.send = send;
		this.port = 9191;
		this.ip = user.getIp();
	}
	
	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	@Override
	public void run () {
		Socket clientSocket;
		ObjectOutputStream objectOutput;
		
		try {
			clientSocket = new Socket(ip, port);
			System.out.println("Sending data to " + ip + ":" + port);
			
			objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOutput.writeObject(send);	
			
			objectOutput.flush();
			clientSocket.close();
			thread.interrupt();
			
		} catch (UnknownHostException e) {

			System.out.println("La ip ya no está conectada");
		} catch (IOException e) {

			System.out.println("Send error: " + e.getMessage());
		}
	}
}
