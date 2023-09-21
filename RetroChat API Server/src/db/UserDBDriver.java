package db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import run.Run;
import users.User;
import users.UserLogin;

public class UserDBDriver {
	//private DatabaseConnection Run.database;


	public boolean createUser (UserLogin userL) throws SQLException {
		boolean accert = false;
		Connection connection = Run.database.getConnection();
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

		//connection.close();
		pStatement.close();
		return accert;
	}
	
	public boolean isUser (String email) throws SQLException {
		boolean accert = false;
		
		Connection connection = Run.database.getConnection();
		
		String sql = "SELECT COUNT(*) AS count FROM users WHERE email = ?";

        PreparedStatement pStatement = connection.prepareStatement(sql);

        pStatement.setString(1, email);

        ResultSet rs = pStatement.executeQuery();
        rs.next();

        int count = rs.getInt("count");

        accert = count > 0;

        rs.close();
		//connection.close();
        pStatement.close();
        
		return accert;
	}
	
 	public User getUserFromID(int id) throws SQLException {
 		Connection connection = Run.database.getConnection();
		String sql;
		User user = null;

		sql = "SELECT name, email FROM users WHERE id = ?";

		PreparedStatement pStatement = connection.prepareStatement(sql);

		pStatement.setInt(1, id);

		ResultSet rs = pStatement.executeQuery();
		
		if (rs.next()) {
			user = new User(rs.getString(1), rs.getString(2));
		}

		rs.close();
		//connection.close();
		pStatement.close();
		
		return user;
 	}
	
	public boolean isUser (User user) throws SQLException {
		return isUser (user.getEmail());
	}

	public User getUserFromEmail(String email) throws SQLException {
		Connection connection = Run.database.getConnection();
		String nick,sql;

		sql = "SELECT name FROM users WHERE email = ?";

		PreparedStatement pStatement = connection.prepareStatement(sql);

		pStatement.setString(1, email);

		ResultSet rs = pStatement.executeQuery();

		nick = rs.next() ? rs.getString("name") : null;

		User user = new User(nick, email);

		rs.close();
		//connection.close();
		pStatement.close();
		
		return user;
	}
	
	public int getUserIdFromEmail(String email) throws SQLException {
		int user_id = -1;
		
		if(isUser (email)) {
			Connection connection = Run.database.getConnection();
			
			String sql = "SELECT id FROM users WHERE email=?";
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, email);

			ResultSet rs = pStatement.executeQuery();
			
			if(rs.next()) 
				user_id = rs.getInt(1);

			rs.close();
			pStatement.close();
		}
		
		return user_id;
	}

	public UserLogin getUserLoginFromEmail(String email) throws SQLException {
		UserLogin userL;
		Connection connection = Run.database.getConnection();

		String sql = "SELECT * FROM users WHERE email=?";

		PreparedStatement pStatement = connection.prepareStatement(sql);

		pStatement.setString(1, email);

		ResultSet rs = pStatement.executeQuery();

		userL = new UserLogin(rs.getString("name"), rs.getString("email"), rs.getString("password"));


		rs.close();
		pStatement.close();
		//connection.close();

		return userL;
	}

	public boolean setPasswordFromEmail (String email, String password) throws SQLException {
		boolean accert = false;

		if (isUser(new User("temp", email))) {
			String sql = "UPDATE user SET password=? WHERE=?";

			Connection connection = Run.database.getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			
			pStatement.setString(1, password);
			pStatement.setString(2, email);

			pStatement.executeUpdate();

			accert=true;
			pStatement.close();
		}

		return accert;
	}
	
	public String getPasswordFromEmail (String email) throws SQLException {
		System.out.println(email);
		Connection connection = Run.database.getConnection();

		String sql = "SELECT password FROM users WHERE email=?";
		

		PreparedStatement pStatement = connection.prepareStatement(sql);

		pStatement.setString(1, email);

		ResultSet rs = pStatement.executeQuery();
		
		String password = null;

		if (rs.next()) {
			password = rs.getString("password");
		}
		
	
		rs.close();
		pStatement.close();
		return password;
		
		
	}//connection.close();
	

}
