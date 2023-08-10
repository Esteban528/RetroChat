package run;

import socket.*;
import app.*;

public class Run {
	public static ReadConfigData configData;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		configData = new ReadConfigData(); 
		ClientConnect connection = new ClientConnect();
	}

}
