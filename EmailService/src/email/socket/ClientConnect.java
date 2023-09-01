package email.socket;

import java.io.*;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.mail.MessagingException;

import email.run.Start;
import email.socket.*;
import users.*;
import email.*;

public class ClientConnect implements Runnable {
	// Socket socketOutput;
	// ObjectOutputStream objectOutput;

	public ClientConnect() {
		Thread threadSocket = new Thread(this);
		threadSocket.start();
		System.out.println("Servidor de emails encendido");

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			int port = Integer.parseInt(Start.configData.getProperty("port"));
			if (port != 0) {
				try (ServerSocket serverSocket = new ServerSocket(port)) {
					while (true) {
						// Input
						Thread clientThread = new Thread(new InputHandler(serverSocket.accept()));
						clientThread.start();
					}
				}
			} else {
				System.out.println("Invalid port. Please verify server configuration");
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Listen error: " + e.getMessage());
		}
	}

	private class InputHandler implements Runnable {
		Socket socket;

		public InputHandler(Socket socket) throws IOException, ClassNotFoundException {
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ObjectInputStream objectInput;
			try {
				objectInput = new ObjectInputStream(socket.getInputStream());
				Object receivedObject = objectInput.readObject();			
				System.out.println(".");
				if (receivedObject != null) {
					
					String [] received = (String []) receivedObject;
					
					System.out.println(received.length);
					
					String username = received[1];
					String password = received[2];
					String usernameHost = Start.configData.getProperty("user");
					String passwordHost = Start.configData.getProperty("user");
					
					if (username.equals(passwordHost) && password.equals(usernameHost)) {
						String action = received[0];
						
						if (action.equals("send-email")) {
							String email = received[3], subject=received[4], message=received[5];
							
							System.out.println("Sending email to: "+ email);
							try {
								Start.email.send(email, subject, message);
								System.out.println("Sucessfull");
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								System.out.println("Could not send message ");
								System.out.print(e.getMessage()+"\n");
							}
							
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Socket listen error: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Listen error: " + e.getMessage());
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("This socket has been closed: "+e.getMessage());
				}
			}

		}
	}
}
