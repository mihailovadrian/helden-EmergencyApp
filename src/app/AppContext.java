package app;

import java.sql.*;

public class AppContext {
	private Connection connection;

	public AppContext(Connection connection) {
		this.connection = connection;

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
