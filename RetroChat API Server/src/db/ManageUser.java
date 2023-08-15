package db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

import users.User;
import users.UserLogin;

public class ManageUser {
	DatabaseConnection db; 
	
	public ManageUser () throws SQLException, FileNotFoundException, IOException {
		db = new DatabaseConnection();
	}
	
	public boolean createUser (UserLogin userL) throws SQLException {
		Boolean accert = false;
		Connection connection = db.getConnection();
		String sql = "INSERT INTO users (name, email, password) VALUES (?,?,?)";
		PreparedStatement pStatement = connection.prepareStatement(sql);
		
		pStatement.setString(1, userL.getNick());
		pStatement.setString(2, userL.getEmail());
		pStatement.setString(3, userL.getPassword());
		
		int rowsInserted = pStatement.executeUpdate();
		
		if (rowsInserted > 0) {
			System.out.println("Usuario creado. "+userL.getEmail());
			accert = true;
		}
		else
			System.out.println("Error al crear el usuario "+userL.getEmail());
		
		connection.close();
		return accert;
	}
	
	public boolean isUser (User user) throws SQLException {
		Boolean accert = false;
		
		Connection connection = db.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM users WHERE email = ?";
				
        PreparedStatement pStatement = connection.prepareStatement(sql);
        
        pStatement.setString(1, user.getEmail());
        
        ResultSet rs = pStatement.executeQuery();
        rs.next();

        int count = rs.getInt("count");
        
        accert = count > 0;
		
        rs.close();
		connection.close();
		return accert;
	}
	
	public User getUserFromEmail(String email) throws SQLException {
		Connection connection = db.getConnection();
		String nick,sql;
		
		sql = "SELECT name FROM users WHERE email = ?";
		
		PreparedStatement pStatement = connection.prepareStatement(sql);
		
		pStatement.setString(1, email);
		
		ResultSet rs = pStatement.executeQuery();
		
		rs.next();
		nick = rs.getString("name");
		
		User user = new User(nick, email);
		
		rs.close();
		connection.close();
		
		return user;
	}
	
	public UserLogin getUserLoginFromEmail(String email) throws SQLException {
		UserLogin userL;
		Connection connection = db.getConnection();
		
		String sql = "SELECT * FROM users WHERE email = ?";
		
		PreparedStatement pStatement = connection.prepareStatement(sql);
		
		pStatement.setString(1, email);
		
		ResultSet rs = pStatement.executeQuery();
		
		userL = new UserLogin(rs.getString("name"), rs.getString("email"), rs.getString("password"));
		
		
		rs.close();
		connection.close();
		
		return userL;
	}
	
	public boolean setPasswordFromEmail (String email, String password) throws SQLException {
		Boolean accert = false;
		
		if (isUser(new User("temp", email))) {
			String sql = "UPDATE user SET password=? WHERE=?";
			
			Connection connection = db.getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			
			pStatement.setString(1, password);
			pStatement.setString(2, email);
			
			pStatement.executeUpdate();
			
			accert=true;
		}
		
		return accert;		
	}
	
}
