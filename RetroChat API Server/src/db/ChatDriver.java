package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import run.Run;
import users.SendObject;
import users.User;
import users.UserLogin;

public class ChatDriver {
	
	private UserDBDriver usersController;
	
	public ChatDriver() {
		this.usersController = new UserDBDriver();
	}

	public ArrayList<SendObject> getMessages(User user1, User user2) throws SQLException {

		int chatId = getChatId(user1, user2);
		System.out.println(chatId);
		
		ArrayList<SendObject> messagesList = new ArrayList<>();
		
		if (chatId>0) {

			Connection connection = Run.database.getConnection();

			String sql = "SELECT sender,message, time FROM messages WHERE chat_id=?";
			
			PreparedStatement pStatement = connection.prepareStatement(sql);

			pStatement.setInt(1, chatId);
			
			ResultSet rs = pStatement.executeQuery();
			
			while(rs.next()) {
				SendObject messageObj = new SendObject(usersController.getUserFromID(rs.getInt(1)));
				messageObj.setText(rs.getString(2));
				messageObj.setTime(rs.getString(3));
				
				
				messagesList.add(messageObj);
				
			}
			rs.close();
			pStatement.close();
		}
		return messagesList;
	}
	
	public boolean addMessage(User sender, User receptor, String message) throws SQLException {

		int chatId = getChatId(sender, receptor);
		
		if (chatId<0) {
			createChat (sender, receptor);
		}
		int messageId = insertMessage(sender, message, chatId);
		
		return messageId > -1;
	}
	
	private int insertMessage(User sender, String message, int chat_id) throws SQLException {
		int id = -1;
		Connection connection = Run.database.getConnection();
		String sql = "INSERT INTO messages (sender, message, chat_id) VALUES (?,?,?)";
		PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		int idUser_1;
		idUser_1 = usersController.getUserIdFromEmail(sender.getEmail());
		pStatement.setInt(1, idUser_1);
//		pStatement.setString(1, sender.getEmail());
		pStatement.setString(2, message);
		pStatement.setInt(3, chat_id);

		int rowsInserted = pStatement.executeUpdate();

		if (rowsInserted>0) {
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
              id = generatedKeys.getInt(1);
			}
			generatedKeys.close();
		}
		else
			System.out.println("Error al crear el usuario ");
		pStatement.close();
		return id;
	}
	
	public boolean isChatExist(User user1, User user2) throws SQLException {
		return (getChatId(user1, user2) > -1);
	}
	
	
	protected int createChat (User user1, User user2) throws SQLException {
		int id = -1;
		Connection connection = Run.database.getConnection();
		String sql = "INSERT INTO chats (user_1, user_2) VALUES (?,?)";
		PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		int idUser_1, idUser_2;
		idUser_1 = usersController.getUserIdFromEmail(user1.getEmail());
		idUser_2 = usersController.getUserIdFromEmail(user2.getEmail());
		
		if (idUser_1 > 0 && idUser_2 > 0) {
			pStatement.setInt(1, idUser_1);
			pStatement.setInt(2, idUser_2);

			int rowsInserted = pStatement.executeUpdate();

			if (rowsInserted>0) {
				ResultSet generatedKeys = pStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
	              id = generatedKeys.getInt(1);
				}
				generatedKeys.close();
			}
			else
				System.out.println("Error al crear el chat ");
			
		}
		
		return id;

		//connection.close();		
	}
	
	public int getChatId(User user1, User user2) throws SQLException {
		int id = -1;
		
		id = getChatIdFromDB(user1, user2);
		
		if (id == -1) {
			return getChatIdFromDB(user2, user1);
		}
		
		return id;
	}
	
	public int getChatIdFromDB(User user1, User user2) throws SQLException {
		
		Connection connection = Run.database.getConnection();
		String sql;
		int id = -1;

		sql = "SELECT id FROM chats WHERE user_1 = ? AND user_2 = ?";

		PreparedStatement pStatement = connection.prepareStatement(sql);
		
		int idUser_1, idUser_2;
		idUser_1 = usersController.getUserIdFromEmail(user1.getEmail());
		idUser_2 = usersController.getUserIdFromEmail(user2.getEmail());
		
		if (idUser_1 > 0 && idUser_2 > 0) {
			pStatement.setInt(1, idUser_1);
			pStatement.setInt(2, idUser_2);

			ResultSet rs = pStatement.executeQuery();

			if (rs.next()) {
				id = rs.getInt("id");
			}
			rs.close();
		}
		pStatement.close();
				
		return id;
	}
}
