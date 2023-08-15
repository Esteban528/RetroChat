package email;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import app.ReadConfigData;
import run.Run;

public class EmailManager {
	private Session session;
	private String username;
	public GenerateEvent emailEvent;
	
	public EmailManager () throws FileNotFoundException, IOException {
		ReadConfigData properties = Run.configData;
		username = properties.getProperty("emailUrl");
        final String password = properties.getProperty("emailPassword");
        final String auth = properties.getProperty("emailAuth");
        final String emailHost = properties.getProperty("emailHost");
        final String emailPort = properties.getProperty("emailPort");
        final String starttls = properties.getProperty("emailStarttls");
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtp.port", emailPort);
        
        emailEvent = new GenerateEvent();

        session = Session.getInstance(props,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(username, password);
              }
        });
	}
	
	public void send (String email, String subject, String text) throws MessagingException {
		Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(subject);
       
        message.setText("RetroChat\n" + text);

        Transport.send(message);
        System.out.println("Email to "+email+" Succesfull");
        emailEvent.executeEvent(email);
	}
}
