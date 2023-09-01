package email.run;

import java.io.IOException;

import email.EmailManager;
import email.socket.ClientConnect;

public class Start {
	public static ReadConfigData configData;
	public static ClientConnect networkData;
	public static EmailManager email;
	
	public static void main(String[] args) {
		configData = new ReadConfigData();
		networkData = new ClientConnect();
		try {
			email = new EmailManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Email server error: "+e.getMessage());
		}
	}

}

