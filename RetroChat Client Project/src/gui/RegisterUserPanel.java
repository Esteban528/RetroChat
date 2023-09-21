package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.ManageLoginData;
import users.*;
import run.Start;
import socket.ObjectReceivedListenner;
import socket.ServerConnect;

public class RegisterUserPanel extends ManageUserJPanel implements ObjectReceivedListenner{
		private JTextField usernameField;
		private JTextField emailField;
	    private JPasswordField passwordField;
	    private JPasswordField passwordConfirmField;
	    private JButton confirmButton;
	    private JButton createUserButton;
	    private ServerConnect server;

	    public RegisterUserPanel() {
	    	// trigger event when a packet arrives from the server
	    	server = Start.api;
	    	server.getReceivedObjectListenner().addEventListenner(this);
	    	
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
	        JLabel titleLabel = new JLabel("Crea tu usuario", SwingConstants.CENTER);
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
	        gbc.gridy = 1;
	        add(titleLabel, gbc);

	        // Username Label and Field
	        JLabel usernameLabel = new JLabel("Nombre de usuario:");
	        gbc.gridy = 2;
	        gbc.gridwidth = 1;
	        gbc.anchor = GridBagConstraints.LINE_END;
	        add(usernameLabel, gbc);

	        usernameField = new JTextField(20);
	        gbc.gridx = 1;
	        gbc.anchor = GridBagConstraints.LINE_START;
	        add(usernameField, gbc);
	        

	        // Username Label and Field
	        JLabel emailLabel = new JLabel("Email");
	        gbc.gridx = 0;
	        gbc.gridy = 3;
	        gbc.gridwidth = 1;
	        gbc.anchor = GridBagConstraints.LINE_END;
	        add(emailLabel, gbc);

	        emailField = new JTextField(20);
	        gbc.gridx = 1;
	        gbc.anchor = GridBagConstraints.LINE_START;
	        add(emailField, gbc);

	        // Password Label and Field
	        JLabel passwordLabel = new JLabel("Password:");
	        gbc.gridx = 0;
	        gbc.gridy = 4;
	        gbc.anchor = GridBagConstraints.LINE_END;
	        add(passwordLabel, gbc);

	        passwordField = new JPasswordField(20);
	        gbc.gridx = 1;
	        gbc.anchor = GridBagConstraints.LINE_START;
	        add(passwordField, gbc);
	        
	     // passwordConfirmField Label and Field
	        JLabel passwordConfirmLabel = new JLabel("Repite tu password:");
	        gbc.gridx = 0;
	        gbc.gridy = 5;
	        gbc.anchor = GridBagConstraints.LINE_END;
	        add(passwordConfirmLabel, gbc);

	        passwordConfirmField = new JPasswordField(20);
	        gbc.gridx = 1;
	        gbc.anchor = GridBagConstraints.LINE_START;
	        add(passwordConfirmField, gbc);

	        // Login Button
	        confirmButton = new JButton("Crear usuario");
	        gbc.gridx = 0;
	        gbc.gridy = 6;
	        gbc.gridwidth = 2;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        add(confirmButton, gbc);

	        // Add action listener to the login button
	        confirmButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	Boolean accert = true;
	            	String username = usernameField.getText();
	                String email = emailField.getText();
	                String password1 = new String(passwordField.getPassword());
	                String password2 = new String(passwordConfirmField.getPassword());
	                
	                if(username.length() < 5 || username.length() > 20) {
	                	JOptionPane.showMessageDialog(RegisterUserPanel.this, "El nombre de usuario debe tener más de 5 y menos de 20 carácteres", "error", JOptionPane.ERROR_MESSAGE);
	                	accert = false;
	                }
	                
	                if (!password1.equals(password2)) {
	                	JOptionPane.showMessageDialog(RegisterUserPanel.this, "El password no coincide", "error", JOptionPane.ERROR_MESSAGE);
	                	accert = false;
	                }
	                
	                if (password1.length() < 5 || password1.length() > 40) {
	                	JOptionPane.showMessageDialog(RegisterUserPanel.this, "Tu contraseña debe ser tener más de 4 y menos de 40 caracteres", "error", JOptionPane.ERROR_MESSAGE);
	                	accert = false;
	                }
	                
	                if (!ManageLoginData.isRealEmail(email)) {
	                	JOptionPane.showMessageDialog(RegisterUserPanel.this, "El email es inválido", "error", JOptionPane.ERROR_MESSAGE);
	                	accert = false;
	                }
	                
	                if(accert) {
	                	String password = ManageLoginData.hashPassword(password1);
	                	
		                try {
		                	System.out.println(password);
		                	UserLogin userL = new UserLogin(username, email, password);
		                	userL.setAction("createUser");
		                	server.outputData.send(userL);
		                	
		                	//server.outputData.send(new User(username, email));
		                	
							System.out.println("Enviado");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							System.out.println("Error al conectarse con el servidor: "+ e1.getMessage());
							
						}
	            	}
	            }
	        });
	    }

		@Override
		public void actionPerformed(Object receivedObject) {
			// TODO Auto-generated method stub
			if (receivedObject instanceof UserLogin) {
				UserLogin userL = (UserLogin) receivedObject;
				

				switch(userL.getAction()) {
				
				case "verification-code":
					
					boolean accert = false;
					int intCode = 0;
					
					while (!accert) {
						String inputCode = JOptionPane.showInputDialog("Enviamos un código de verificación a tu correo");
						if (inputCode == null || inputCode.equals("")) {
							JOptionPane.showMessageDialog(RegisterUserPanel.this, "Cancelaste la operacion.", "Error", JOptionPane.ERROR_MESSAGE);								

							break;
						}
						try {
							intCode = Integer.parseInt(inputCode);
							
							if (intCode == userL.getId()) {
								JOptionPane.showMessageDialog(RegisterUserPanel.this, "Correo verificado con éxito. Ya puedes acceder a tu cuenta.", "information",JOptionPane.INFORMATION_MESSAGE);
								userL.setAction("email-verificated");
								accert = true;
								
								server.getReceivedObjectListenner().removeEventListenner(this);
								windowFather.dispose();
								
								try {
									server.outputData.send(userL);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									System.out.println("Error al procesar: "+e.getMessage());
								}
								
								break;
							} else
								JOptionPane.showMessageDialog(RegisterUserPanel.this, "Código incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);								
						}catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(RegisterUserPanel.this, "Dato inválido, debe ingresar un número", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				
					break;
				case "email-occuped":
					JOptionPane.showMessageDialog(RegisterUserPanel.this, "Este correo electrónico está ocupado.", "Error", JOptionPane.ERROR_MESSAGE);								
					
					break;
				}
				
			}
		}
}
