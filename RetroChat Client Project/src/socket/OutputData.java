package socket;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import app.User;
import app.UserLogin;

public class OutputData implements Runnable {
		private Socket socketOutput;
		private ObjectOutputStream objectOutput;
		private String ip;
		private int port;
		private Thread threadSocket;
		
		public OutputData (String ip, int port) {
			this.ip = ip;
			this.port=port;
		}
		
		@Override
		public void run() {
		
			// TODO Auto-generated method stub
			try {
				this.socketOutput = new Socket(this.ip, this.port);
				this.objectOutput = new ObjectOutputStream(socketOutput.getOutputStream());
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println("Output :"+ e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Output error: "+ e.getMessage());
			}	
			
			
		}
		private void send () throws IOException {
			/*if (threadSocket == null) {
				threadSocket = new Thread(this);
				threadSocket.start();
			}else {
				stopSocket ();
				threadSocket.start();
			}*/
			
			this.socketOutput = new Socket(this.ip, this.port);
			this.objectOutput = new ObjectOutputStream(socketOutput.getOutputStream());
			
		}
		
		public void send(User user) throws IOException {
			send ();
			if (user != null)
				objectOutput.writeObject(user);
			else
				System.out.println("User no esta definido");
			this.objectOutput.flush();
		}
		
		public void send(String send) throws IOException {
			send ();
			objectOutput.writeUTF(send);
			this.objectOutput.flush();
		}
		
		public void send(UserLogin userL) throws IOException {
			send ();
			
			objectOutput.writeObject(userL);
			this.objectOutput.flush();
		}
		
		public void stopSocket () throws IOException {
			send ();
			if(socketOutput.isConnected()) {
				objectOutput.close();
				socketOutput.close();
			}
			threadSocket.interrupt();
		}
		
	}