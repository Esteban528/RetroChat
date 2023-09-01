package db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import run.Run;

public class DatabaseConnection {
	private Connection connection;
	private Statement statement;

	public DatabaseConnection () throws SQLException, FileNotFoundException, IOException {
		boolean enabled = connectToDB();
		if (enabled)
			setStatement(connection.createStatement());
		else
			System.out.println("Error");
	}

	private boolean connectToDB () throws SQLException, FileNotFoundException, IOException {
		String user = Run.configData.getProperty("dbUser");
		String password = Run.configData.getProperty("dbPassword");
		String ip = Run.configData.getProperty("dbIP");
		String port = Run.configData.getProperty("dbPort");
		String name = Run.configData.getProperty("dbName");
		String url = "jdbc:mysql://"+ip+":"+port+"/"+name+"?allowPublicKeyRetrieval=true&useSSL=false";
		Connection connection = DriverManager.getConnection(url, user, password);
		this.connection = connection;
		return !connection.isClosed();
	}

	public Connection getConnection() {
		return connection;
	}

	public Statement getStatement() {
		return statement;
	}

	private void setStatement(Statement statement) {
		this.statement = statement;
	}
}