package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.LoginPanel;
import users.*;

import run.Start;

public class ContactsData implements Serializable {

	private static final long serialVersionUID = 1L;
	static String s = Start.s;
	public static final String ROUTE_OF_CONTACTSLIST = Start.ROUTE_PERSONAL_DATA+s+"contact.dat";
	
	private ArrayList<User> contactsArray;
	
	@SuppressWarnings("unchecked")
	public ContactsData () {
		File file = new File(ROUTE_OF_CONTACTSLIST);
		if (file.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(ROUTE_OF_CONTACTSLIST);
				ObjectInputStream ois = new ObjectInputStream(fis);
				contactsArray = (ArrayList<User>) ois.readObject();
			    ois.close();
			    fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				
			}
			
		}else {
			System.out.println("El file no existe");
			this.contactsArray = new ArrayList<User>();
			
			setContactsFile (contactsArray);
		}
		
	}
	
	public ContactsData (ArrayList<User> contactsArray) {
		setContactsFile (contactsArray);
	}
	
	public ArrayList<User> getContactsFile () {
		return contactsArray;
	}
	
	public void setContactsFile (ArrayList<User> contactsArray) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(ROUTE_OF_CONTACTSLIST);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(contactsArray);
			oos.close();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error al guardar contacto "+ e.getMessage() + " Path" +ROUTE_OF_CONTACTSLIST, "error", JOptionPane.ERROR_MESSAGE);

		}
		
	}
}