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

import app.User;

public class LoginFrame extends JFrame  {
	
	public LoginFrame () {
		setTitle("RetroChat Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
	    GraphicsConfiguration[] gc = gs[0].getConfigurations();
		Rectangle gcBounds = gc[0].getBounds();
		
		int width = gcBounds.width;
		int height = gcBounds.height;

		setBounds(width/4, height/4, width/2, height/2);
		
		ImageIcon icon = new ImageIcon ("files/retroChat.png");
		setIconImage(icon.getImage());
		

	}
	
	public void open() {
		LoginPanels panels = new LoginPanels();
		add(panels);
		pack();
		
		setVisible(true);	
	}
}

class LoginPanels extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createUserButton;

    public LoginPanels() {
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

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // TODO: Add your login logic here
                // For this example, let's just display the entered credentials
                JOptionPane.showMessageDialog(LoginPanels.this, "Username: " + username + "\nPassword: " + password);
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
                // TODO: Add your logic for creating a new user here
                // For this example, let's just display a message
                JOptionPane.showMessageDialog(LoginPanels.this, "Create User button clicked");
            }
        });
    }
}