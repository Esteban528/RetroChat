package app;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;

import javax.swing.*;

import run.Start;

public class Notification {
	private static Notification notification;
	private ArrayList<BodyMessage> notifications;

	private Notification() {
		notifications = new ArrayList<BodyMessage>();
	}

	public static Notification i() {
		if (notification == null) {
			notification = new Notification();
		}

		return notification;
	}

	public void send(String title, String message, boolean onlyBeep) {
		if (onlyBeep)
			add(new BodyMessage(title, message));
		else
			Toolkit.getDefaultToolkit().beep();
	}

	private void add(BodyMessage bmsg) {
		notifications.add(bmsg);
		bmsg.pos = notifications.size();
		Thread thread = new Thread(new FrameNotification(bmsg));
		thread.start();
	}

	private class FrameNotification implements Runnable {
		private BodyMessage bodyMessage;
		
		
		public FrameNotification(BodyMessage bodyMessage) {
			this.bodyMessage = bodyMessage;
		}

		@Override
		public void run() {
			Toolkit.getDefaultToolkit().beep();
			String mensaje = bodyMessage.toString();

			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setUndecorated(true); 

			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createEtchedBorder());
			JLabel label = new JLabel(mensaje);
			ImageIcon logoIcon = new ImageIcon("files/retroChat.png");
			
			Image resizedImage = logoIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			
	        JLabel logoLabel = new JLabel(new ImageIcon(resizedImage));
			panel.add(logoLabel);
			panel.add(label);

			frame.getContentPane().add(panel);
			frame.pack();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = (int) screenSize.getWidth();
			int screenHeight = (int) screenSize.getHeight();

			int windowWidth = frame.getWidth();
			int windowHeight = frame.getHeight();

			int x = screenWidth - windowWidth - 10;
			int y = screenHeight - windowHeight - (frame.getHeight()*(bodyMessage.pos));

			frame.setLocation(x, y);

			// Show frame
			frame.setVisible(true);

			int visibleTime = 5000;
			Timer timer = new Timer(visibleTime, e -> {
				frame.dispose();
				notifications.remove(bodyMessage);
			});
			timer.setRepeats(false);
			timer.start();
			Thread.currentThread().interrupt();
			
		}

	}
}

class BodyMessage {
	public String title, text;
	public int pos;

	public BodyMessage(String title, String text) {
		this.title = title;
		this.text = text;
	}

	@Override
	public String toString() {
		return title + " " + text;
	}

}


