package gui;

import java.awt.*;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import app.ManageLoginData;
import users.*;
import run.Start;

public class LoginFrame extends JFrame implements WindowListener   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ManageUserJPanel panel;
	
	public LoginFrame (ManageUserJPanel panel) {
		setTitle("RetroChat Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
	    GraphicsConfiguration[] gc = gs[0].getConfigurations();
		Rectangle gcBounds = gc[0].getBounds();
		
		int width = gcBounds.width;
		int height = gcBounds.height;

		setBounds(width/4, height/4, width/2, height/2);
		
		ImageIcon icon = new ImageIcon (Start.ROUTE_DEFAULT_FILES_FOLDER+"/retroChat.png");
		setIconImage(icon.getImage());
		
		this.panel = panel;
		this.addWindowListener (this);
	}
	
	public void open() {
		
		add(panel);
		pack();
		panel.setWindowFather(this);

		setVisible(true);	
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		Start.lg.logout();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		Start.api.getReceivedObjectListenner().removeEventListenner(panel);
		Thread.currentThread().interrupt();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}


