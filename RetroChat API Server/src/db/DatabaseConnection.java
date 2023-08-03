package db;

import java.sql.*;

public class DatabaseConnection {
	private Connection connection;
	private Statement statement;
	
	public DatabaseConnection () {
		try {
			setConnection(connectToDB());
			setStatement(connection.createStatement());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Conexión a la base de datos rechazada");
		}
	}
			
	private Connection connectToDB () throws SQLException {
		String url = "jdbc:mysql://127.0.1.1:3306/javaTest?useSSL=false";
		Connection connection = DriverManager.getConnection(url, "root", "password");
			
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