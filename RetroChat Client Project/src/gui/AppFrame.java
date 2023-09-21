package gui;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;

import app.ManageLoginData;
import users.*;
import run.Start;

@SuppressWarnings("serial")
public class AppFrame extends JFrame implements WindowListener  {
	public static final Color COLORB = new Color(20,20,20);
	public static final Color COLORW = new Color(200,200,200);
	private boolean notification;
	private AppPanels panels;
	
	public AppFrame () {
		setTitle("RetroChat V0.01");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
	    GraphicsConfiguration[] gc = gs[0].getConfigurations();
		Rectangle gcBounds = gc[0].getBounds();
		//this.contactsArray = contactsArray;
		
		int width = gcBounds.width;
		int height = gcBounds.height;

		setBounds(width/6, height/6, 1280, 720);
		
		ImageIcon icon = new ImageIcon (Start.ROUTE_DEFAULT_FILES_FOLDER+"/retroChat.png");
		setIconImage(icon.getImage());
		panels = new AppPanels();
		setBackground(COLORB);
		addWindowListener(this);
		
	}
	
	public void open() {
		add(panels);
		setVisible(true);	
	}

	public boolean isNotificable () {
		return this.notification;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		this.notification = false;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		Thread.currentThread().interrupt();
		Start.lg.logout();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		this.notification = true;
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		this.notification = false;
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

