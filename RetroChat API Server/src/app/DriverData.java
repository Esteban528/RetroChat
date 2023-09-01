package app;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import run.Run;

public class DriverData{
	private static HashMap<String, Integer> emailsCodes = new HashMap<>() ;

	public static void addEmailCode (String email, int code) {
		emailsCodes.put(email, code);
		Thread thread = new Thread(new TimerThread(email));
		thread.start();
	}

	public static void removeEmailCode (String email) {
		emailsCodes.remove(email);
	}

	public static int generateVerificationCode(String email) {
        int code = (int) (Math.random() * 10000);
        addEmailCode(email, code);
        
        return code;
    }
}

class TimerThread implements Runnable {

	private TimerTask process;

	public TimerThread (String email) {
		this.process = new TimerFinished(email);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		int minutes = 5 * (60*1000);
		timer.scheduleAtFixedRate(process, 0, minutes);
	}

}

class TimerFinished extends TimerTask{
	private String email;

	public TimerFinished(String email) {
		this.email = email;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DriverData.removeEmailCode(email);
		UsersManager usersM = Run.userData;
		usersM.removeUser(usersM.getUserFromEmail(email));
		//userTemporals
	}

}