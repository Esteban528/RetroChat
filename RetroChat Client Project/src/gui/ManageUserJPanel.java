package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import socket.ObjectReceivedListenner;

public class ManageUserJPanel extends JPanel implements ObjectReceivedListenner{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JFrame windowFather;
	
    public JFrame getWindowFather() {
		return windowFather;
	}

	public void setWindowFather(JFrame windowFather) {
		this.windowFather = windowFather;
	}

	@Override
	public void actionPerformed(Object receivedObject) {
		// TODO Auto-generated method stub
		
	}
}
