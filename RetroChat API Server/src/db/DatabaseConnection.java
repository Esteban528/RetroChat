package db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

import run.Run;

public class DatabaseConnection {
	private Connection connection;
	private Statement statement;
	
	public DatabaseConnection () throws SQLException, FileNotFoundException, IOException {
		setConnection(connectToDB());
		setStatement(connection.createStatement());
	}
			
	private Connection connectToDB () throws SQLException, FileNotFoundException, IOException {
		String user = Run.configData.getProperty("dbUser");
		String password = Run.configData.getProperty("dbPassword");
		String url = "jdbc:mysql://127.0.1.1:3306/javaTest?useSSL=false";
		Connection connection = DriverManager.getConnection(url, user, password);
			
		return connection;
	}
	
	public Connection getConnection() {
		return connection;
	}

	private void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	private void setStatement(Statement statement) {
		this.statement = statement;
	}
}