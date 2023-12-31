package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.DefaultStyledDocument;

import app.AppData;
import app.ContactsData;
import run.Start;
import users.User;

public class AppPanels extends CustomJPanel {
	private static ContactsZone contactZone;
	private ChatZone chatZone;
	private JSplitPane splitPane;
	
	public static ContactPanel getContactZone() {
		return contactZone;
	}

	public void setContactZone(ContactsZone contactZone) {
		AppPanels.contactZone = contactZone;
	}

	public ChatZone getChatZone() {
		return chatZone;
	}

	public void setChatZone(ChatZone chatZone) {
		this.chatZone = chatZone;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public AppPanels() {
		setLayout(new BorderLayout());

		contactZone = new ContactsZone(this);
		chatZone = new ChatZone(this);

		add(new MenuBar(), BorderLayout.NORTH);
		
		add(chatZone, BorderLayout.CENTER);
		add(contactZone, BorderLayout.WEST);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contactZone, chatZone);
		add(splitPane);
		setBackground(AppFrame.COLORB);

	}
}

class MenuBar extends CustomMenuBar {
	public MenuBar() {
		super();
		JMenu userMenu = new CustomMenu("User");
		userMenu.add(new CustomMenuItem("Cambiar contraseña"));
		add(userMenu);
	}
}

// Contacts area
class ContactsZone extends CustomJPanel implements ContactPanel{
	public static ArrayList<User> contactsArray;
	private User contactSelected;
	private CustomJList<User> contactsList;
	private DefaultListModel<User> model = new DefaultListModel<>();
	
	public void addContact(User user) {
		Start.updateContactsList.addContact(model, contactsList, user);
	}
	
	public CustomJList<User> getContactsList() {
		return contactsList;
	}

	public void setContactsList(CustomJList<User> contactsList) {
		this.contactsList = contactsList;
	}

	public DefaultListModel<User> getModel() {
		return model;
	}

	public void setModel(DefaultListModel<User> model) {
		this.model = model;
	}

	private AppPanels father;

	public User getContactSelected() {
		return contactSelected;
	}

	public void setContactSelected(User contactSelected) {
		this.contactSelected = contactSelected;
		AppData.i().setTemporalUserSelected(contactSelected);
	}
	
	public ContactsZone(AppPanels father) {
		contactsArray = Start.updateContactsList.getContactsList();
		this.father=father;
		setLayout(new BorderLayout());

		JMenuBar contactsMenuBar = new CustomMenuBar();

		JMenu contactsMenu = new CustomMenu("Menú Contactos");
		contactsMenuBar.add(contactsMenu);

		JMenuItem addContactOption = new CustomMenuItem("Agregar contactos");
		contactsMenu.add(addContactOption);

		JMenuItem removeContactOption = new CustomMenuItem("Remover contacto seleccionado");
		contactsMenu.add(removeContactOption);

		// Remove contact event (On click in "Remover contacto"
		removeContactOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (contactSelected != null) {
					Iterator<User> iterator = contactsArray.iterator();
					while (iterator.hasNext()) {
						User user = iterator.next();
						if (user.equals(contactSelected)) {
							iterator.remove();
							model.removeElement(user);
						}
					}
					new ContactsData(contactsArray);

				} else {
					JOptionPane.showMessageDialog(null, "Debes seleccionar un contacto", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		addContactOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nick, email;
				nick = JOptionPane.showInputDialog("Nombre del contacto:");
				while (nick != null && nick.length() < 5) {
					nick = JOptionPane.showInputDialog(null, "El nombre de contacto debe tener almenos 5 carácteres",
							"Advert", JOptionPane.WARNING_MESSAGE);
				}
				if (nick != null) {
					email = JOptionPane.showInputDialog("Escriba el email");
					while (!app.ManageLoginData.isRealEmail(email)) {
						email = JOptionPane.showInputDialog("Escriba una dirección correcta");
					}

					if (nick != null && email != null) {
						User user = new User(nick, email);
						addContact(user);
					}
				}
			}
		});

		// Contacts List

		for (User s : contactsArray) {
			model.addElement(s);
		}
		contactsList = new CustomJList(model);
		JScrollPane scrollPane = new JScrollPane(contactsList);

		contactsList.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				User user = (User) contactsList.getSelectedValue();
				if (user != null) {
					setContactSelected(user);
					Start.driverMessages.updateTextArea(father.getChatZone().getChatArea());	
					this.father.getChatZone().setTextContactText(user.getNick()+ ": " + user.getEmail());
				}
			}
		});

		// Style
//		contactsMenuBar.setBackground(new Color(220, 255, 220, 255));
		contactsMenu.setFont(new Font("Dialog", Font.BOLD, 12));
		//contactsList.setBackground(new Color(220, 245, 220, 255));
		contactsList.setFont(new Font("Dialog", Font.PLAIN, 12));
		contactsList.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		/*
		 * Border border = new BasicBorders.ButtonBorder (Color.GRAY, Color.GRAY,
		 * Color.GRAY, Color.GRAY); setBorder(border);
		 */

		// Add
		add(contactsMenuBar, BorderLayout.NORTH);
		add(contactsList, BorderLayout.CENTER);
		contactsList.add(scrollPane);
	}

}

class ChatZone extends CustomJPanel {
	private JButton sendButton;
    public JLabel contactNameLabel;
    private CustomJPanel jPanel1;
    private CustomJPanel jPanel2;
    private CustomJPanel jPanel3;
    private JScrollPane jScrollPane1;
    private CustomTextPane ChatArea;
    private JTextField messageField;
    private AppPanels father;
    
    public CustomTextPane getChatArea() {
    	return this.ChatArea;
    }
    public JTextField getMessageField() {
    	return this.messageField;
    }
    public JButton getSendButton() {
    	return this.sendButton;
    }
    
	public ChatZone(AppPanels father) {
		this.father = father;
        setLayout(new BorderLayout());

        jPanel1 = new CustomJPanel();
        jPanel2 = new CustomJPanel();
        contactNameLabel = new CustomLabel();
        jPanel3 = new CustomJPanel();
        jScrollPane1 = new JScrollPane();
        ChatArea = new CustomTextPane();
        messageField = new CustomTextField();
        sendButton = new CustomButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = getMessageField().getText();
					if (message.length() > 1) {
						Start.driverMessages.sendMessage(message);
					sendButton.setEnabled(false);
	                
	                int visibleTime = 2000;
	    			Timer timer = new Timer(visibleTime, ed -> {
	    				sendButton.setEnabled(true);
	    			});
	    			timer.setRepeats(false);
	    			timer.start();
				}
				else 
					JOptionPane.showMessageDialog(ChatZone.this, "Mensaje demasiado corto", "error", JOptionPane.ERROR_MESSAGE);
			}
        	
        });

        contactNameLabel.setText("CONTACT NAME");
        
        ChatArea.setEditable(false);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(contactNameLabel, GroupLayout.PREFERRED_SIZE, 732, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contactNameLabel, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

//        ChatArea.setColumns(20);
//        ChatArea.setRows(5);
        jScrollPane1.setViewportView(ChatArea);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        messageField.setText("");
        

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageField, GroupLayout.PREFERRED_SIZE, 780, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(messageField, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
	
	public void setTextContactText(String text) {
		this.contactNameLabel.setText(text);
	}

}

// Components
class CustomMenuItem extends JMenuItem {
    private Color customFontColor = AppFrame.COLORW;

    public CustomMenuItem(String text) {
        super(text);
        setBackground(AppFrame.COLORB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}
class CustomMenu extends JMenu{
    private Color customFontColor = AppFrame.COLORW;

    public CustomMenu(String text) {
        super(text);
        setBackground(AppFrame.COLORB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

class CustomMenuBar extends JMenuBar{
    private Color customFontColor = AppFrame.COLORW;

    public CustomMenuBar() {
        super();
        setBackground(AppFrame.COLORB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

class CustomJPanel extends JPanel{
    private Color customFontColor = AppFrame.COLORW;

    public CustomJPanel() {
        super();
        setBackground(AppFrame.COLORB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

class CustomLabel extends JLabel{
    private Color customFontColor = AppFrame.COLORW;

    public CustomLabel() {
        super();
        setForeground(customFontColor);
    }

   
}

class CustomButton extends JButton{
    private Color customFontColor = AppFrame.COLORW;

    public CustomButton(String text) {
        super(text);
        setBackground(AppFrame.COLORW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

class CustomTextArea extends JTextArea{
    private Color customFontColor = AppFrame.COLORW;

    public CustomTextArea() {
        super();
        setBackground(AppFrame.COLORW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

class CustomTextPane extends CustomDocument{
    private Color customFontColor = AppFrame.COLORW;

    public CustomTextPane() {
        super();
        setBackground(AppFrame.COLORW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

class CustomTextField extends JTextField{
    private Color customFontColor = AppFrame.COLORW;

    public CustomTextField() {
        super();
        setBackground(AppFrame.COLORW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

class CustomJList <t> extends JList{
    private Color customFontColor = AppFrame.COLORW;

    public CustomJList(DefaultListModel<User> model) {
        super (model);
        setBackground(AppFrame.COLORW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(customFontColor); // Set the custom font color
        super.paintComponent(g);
    }
}

