package email;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import users.SendObject;
import users.UserLogin;

public class EmailConnect implements Runnable{
	private String ip, user, password;
	private int port;
	private boolean error;
	public GenerateEvent emailEvent;
	private String [] send;
	private Thread thread;

	public EmailConnect (Properties properties) {
		this.ip = properties.getProperty("ipEmailHost");
		this.port = Integer.parseInt(properties.getProperty("portEmailHost"));
		this.user = properties.getProperty("userEmailHost");
		this.password = properties.getProperty("passwordEmailHost");

		if (ip == null || port <= 0 || user == null || password == null) {
			System.out.println("Verify email configuration of parameters");
			error = true;
		}
		emailEvent = new GenerateEvent();
	}

	public void send (String email, String subject, String message) {
		//SendObject send = new SendObject(new UserLogin("RetroChat", this.user, this.password) , "send-email");

		String [] emailData = {"send-email", this.user, this.password, email, subject, message};
		
		send = emailData;
		
		thread = new Thread(this);
		thread.start();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket clientSocket;
		int port = this.port;
		String ip = this.ip;

		if (!error) {
			try {
				clientSocket = new Socket(ip, port);
				System.out.println("Sending email info to " + ip + ":" + port);
				//clientSocket.setSoTimeout(5000);
				ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
				objectOutput.writeObject(send);
				
				objectOutput.flush();
				clientSocket.close();
				
				System.out.println("Sucessfull");
				this.emailEvent.executeEvent(send[3]);
				thread.interrupt();

			} catch (UnknownHostException e) {

				System.out.println("La ip ya no está conectada");
				error = true;
			} catch (IOException e) {
				error = true;
				System.out.println("El email no fue enviado, hubo un problema: "+e.getMessage());
			}
		} else {
			System.out.println("Verify email configuration of parameters");
		}
	}
}
