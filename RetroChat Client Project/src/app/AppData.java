package app;

import users.User;
import users.UserLogin;

public class AppData {
	private static AppData appData;
	private UserLogin userL;
	private User temporalUserSelected;
	
	private AppData () {
		
	};
	
	public static AppData i() {
		if (appData == null) {
			appData = new AppData();
		}
		
		return appData;
	}

	public UserLogin getUserL() {
		return userL;
	}

	public void setUserL(UserLogin userL) {
		this.userL = userL;
	}

	public User getTemporalUserSelected() {
		return temporalUserSelected;
	}

	public void setTemporalUserSelected(User temporalUserSelected) {
		this.temporalUserSelected = temporalUserSelected;
	}
	
}
