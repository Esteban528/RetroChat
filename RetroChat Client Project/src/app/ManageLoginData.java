package app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import run.Start;
import users.UserLogin;


public class ManageLoginData {
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Conver hash to legible text
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean isRealEmail(String email) {
        if(email.length() > 80) {
        	return false;
        }
    	
    	
        String patronEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        
        Pattern pattern = Pattern.compile(patronEmail);

        
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public void logout() {
    	UserLogin userL;
    	try {
    		userL = AppData.i().getUserL();;
    		userL.setAction("logout");  
			Start.api.outputData.send(userL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		} catch (NullPointerException e2) {
			
		} finally {			
			System.exit(0);
		}
    	
    }
}
