package gui;

import java.awt.*;
import app.*;
import java.util.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;


import users.*;
import run.Start;

public class AppFrame extends JFrame implements WindowListener  {
	private boolean notification;
	private String nickname;
	private String ip;
	private ArrayList<User> contactsArray;
	
	public AppFrame (ArrayList<User> contactsArray) {
		setTitle("RetroChat V0.01");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
	    GraphicsConfiguration[] gc = gs[0].getConfigurations();
		Rectangle gcBounds = gc[0].getBounds();
		this.contactsArray = contactsArray;
		
		int width = gcBounds.width;
		int height = gcBounds.height;

		setBounds(width/4, height/4, width/2, height/2);
		
		ImageIcon icon = new ImageIcon (Start.ROUTE_DEFAULT_FILES_FOLDER+"/retroChat.png");
		setIconImage(icon.getImage());
		

	}
	
	public void open() {
		Panels panels = new Panels(this.contactsArray);
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

class Panels extends JPanel {
	public Panels (ArrayList<User> contactsArray) {
		setLayout(new BorderLayout());	
		
		JPanel contactZone = new ContactsZone(contactsArray);
		JPanel chatZone = new ChatZone();

		add(new MenuBar(), BorderLayout.NORTH);
		add(contactZone, BorderLayout.WEST);
		add(chatZone, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contactZone, chatZone);
		add(splitPane); // Agregue el panel dividido a su panel principal.
	}
}

class MenuBar extends JMenuBar{
	public MenuBar () {
		super();
		
		JMenu userMenu = new JMenu("User");
		userMenu.add(new JMenuItem("Change Name"));
		this.add(userMenu);
	}
}

// Contacts area
class ContactsZone extends JPanel {
	private User contactSelected;	
	private JList<User> contactsList;
	DefaultListModel<User> model = new DefaultListModel<>();
	
	public ContactsZone (ArrayList<User> contactsArray) {
		setLayout(new BorderLayout());
		
		JMenuBar contactsMenuBar = new JMenuBar();
		
		JMenu contactsMenu = new JMenu("Menú Contactos");
		contactsMenuBar.add(contactsMenu);
		
		JMenuItem addContactOption = new JMenuItem("Agregar contactos");
		contactsMenu.add(addContactOption);
		
		JMenuItem removeContactOption = new JMenuItem("Remover contacto seleccionado");
		contactsMenu.add(removeContactOption);
		
		removeContactOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (contactSelected != null) {
					Iterator<User> iterator = contactsArray.iterator();
					while (iterator.hasNext()) {
						User user = iterator.next();
						if(user.equals(contactSelected)) {
							iterator.remove();
							model.removeElement(user);
						}
					}
					new ContactsData(contactsArray);
					
				}else {
					JOptionPane.showMessageDialog(null, "Debes seleccionar un contacto", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		addContactOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nick = JOptionPane.showInputDialog("Nombre del contacto:");
				while (nick != null && nick.length() < 5) {
					nick = JOptionPane.showInputDialog(null, "El nombre de contacto debe tener almenos 5 carácteres", "Advert", JOptionPane.WARNING_MESSAGE);
				}
				if (nick != null) {
					String email = JOptionPane.showInputDialog("Escriba el email");
					while (email != null && email.length() < 5) {
						email = JOptionPane.showInputDialog("Escriba una direccion correcta");
					}
					
					if (nick != null && email != null) {
						User user = new User (nick, email);
						contactsArray.add(user);
						model.removeAllElements();
						for (User s : contactsArray) {
						      model.addElement(s);
						      System.out.println(s);
						}
						contactsList.setModel(model);
						new ContactsData(contactsArray);
					}
				}
				}
				
			
		});
		
		// Contacts List 
		
	    for (User s : contactsArray) {
	      model.addElement(s);
	    }
	    contactsList = new JList<>(model);
	    JScrollPane scrollPane = new JScrollPane(contactsList);
		
		contactsList.addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		    	this.contactSelected = ((User) contactsList.getSelectedValue());
		    }
		});
		
		// Style
		contactsMenuBar.setBackground(new Color(220, 255, 220, 255));
		contactsMenu.setFont(new Font("Dialog", Font.BOLD, 12));
		contactsList.setBackground(new Color(220, 245, 220, 255));
		contactsList.setFont(new Font("Dialog", Font.PLAIN, 12));
		contactsList.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		/*Border border = new BasicBorders.ButtonBorder (Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY);
		setBorder(border);*/
		
		//Add
		add(contactsMenuBar, BorderLayout.NORTH);
		add(contactsList, BorderLayout.CENTER);
		contactsList.add(scrollPane);
	}
	
}

class ChatZone extends JPanel {
	public ChatZone () {
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		JLabel contactName = new JLabel("Contacto1");
		topPanel.add(contactName);
		
		JPanel chatPanel = new JPanel(new GridBagLayout());
		JTextField chatField = new JTextField();
		chatField.setEditable(false);
		
		//Style ChatPanel
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        chatPanel.add(chatField, constraints);
		chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		JTextArea messageField = new JTextArea(1, 30);
		JButton sendMessageButton = new JButton(" Enviar ");
		messagePanel.add(messageField, BorderLayout.WEST);
		messagePanel.add(sendMessageButton,BorderLayout.EAST);
		messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		
		
		Border border = new BasicBorders.ButtonBorder (Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY);
		//topPanel.setBorder(border);
		
		add(topPanel, BorderLayout.NORTH);
		add(chatPanel, BorderLayout.CENTER);
		add(messagePanel, BorderLayout.SOUTH);
	}
	
}