package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.ManageLoginData;
import run.Start;
import socket.ObjectReceivedListenner;
import users.UserLogin;

public class LoginPanel extends ManageUserJPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createUserButton;

    public LoginPanel() {
        // Set layout for the panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Logo
        ImageIcon logoIcon = new ImageIcon("files/retroChat.png");
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(logoLabel, gbc);

        // Title
        JLabel titleLabel = new JLabel("RetroChat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        add(titleLabel, gbc);

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Email:");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gbc);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginButton, gbc);
        
        //Add Event at received data
        
        
        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	boolean accert = true;
                String email = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (!ManageLoginData.isRealEmail(email)) {
                	JOptionPane.showMessageDialog(LoginPanel.this, "El email es inválido", "error", JOptionPane.ERROR_MESSAGE);
                	accert = false;
                }
                if (accert) {
                	String passwordHashed = ManageLoginData.hashPassword(password);
                	UserLogin userL = new UserLogin("", email, passwordHashed);
                	userL.setAction("login");
                	Start.api.getReceivedObjectListenner().addEventListenner(LoginPanel.this);
                	try {
						Start.api.outputData.send(userL);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
	                	JOptionPane.showMessageDialog(LoginPanel.this, "A ocurrido un error al iniciar sesión: "+e1.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
					}
                }
                
            }
        });

        // Create User Button
        createUserButton = new JButton("Create User");
        gbc.gridy = 5;
        add(createUserButton, gbc);

        // Add action listener to the create user button
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Start.guiFactory.createWindow(GUIFactory.REGISTER);
            }
        });
    }

	@Override
	public void actionPerformed(Object receivedObject) {
		
		if (receivedObject instanceof UserLogin) {
			
			UserLogin userL = (UserLogin) receivedObject;
			
			switch (userL.getAction()){
			case "user-logged":
				windowFather.dispose();
				Start.userLogin = userL;
				Start.api.getReceivedObjectListenner().removeEventListenner(this);
				Start.guiFactory.createWindow(GUIFactory.APP);
				break;
			case "email-incorrect":
				JOptionPane.showMessageDialog(LoginPanel.this, "El email no está registrado", "error", JOptionPane.ERROR_MESSAGE);

				break;
			case "password-incorrect":
				JOptionPane.showMessageDialog(LoginPanel.this, "Contraseña incorrecta", "error", JOptionPane.ERROR_MESSAGE);

			}
			
		}
		
	}


}
