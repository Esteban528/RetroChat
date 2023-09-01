package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import app.UserConnected;
import run.Run;
import users.SendObject;
import users.User;
import users.UserLogin;

public class ClientConnect implements Runnable {
	// Socket socketOutput;
	// ObjectOutputStream objectOutput;

	public ClientConnect() {
		Thread threadSocket = new Thread(this);
		threadSocket.start();
		System.out.println("Servidor encendido");

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			int port = Integer.parseInt(Run.configData.getProperty("port"));
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

	public void send(User user, Object send) {
		Send s = new Send (user, send);
		Thread thread = new Thread(s);
		s.setThread(thread);
		thread.start();
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
				String ip = socket.getInetAddress().getHostAddress();
				objectInput = new ObjectInputStream(socket.getInputStream());
				Object receivedObject = objectInput.readObject();
				System.out.println("-------------------------");
				System.out.println("Packet received from "+ip);
				System.out.println("-------------------------");
				
				if (receivedObject instanceof UserLogin) {
					UserLogin userL = (UserLogin) receivedObject;
					userL.setIp(ip);

					try {
						Run.userData.action(userL);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println("Database error: "+e.getMessage());
					}
					
				} else if (receivedObject instanceof SendObject) {
					SendObject received = (SendObject) receivedObject;
					switch (received.getAction()) {
					case "email-verificate":
						UserLogin userL = received.getUserL();
						userL.setIp(ip);
						if (userL.getAction().equals("createUser")) {
							System.out.println("Se puede crear un usuario");
						}
					}
				} else if (receivedObject instanceof User) {
					User user = (User) receivedObject;
					user.setIp(ip);
					System.out.println(user.getNick() + " connected from " + user.getIp());

					// Output
					send(user, new String("Usuario conectado " + user));
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
					System.out.println("El socket fue cerrado inesperadamente");
				}
			}

		}
	}
}
