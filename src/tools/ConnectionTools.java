package tools;

import java.sql.*;

public class ConnectionTools {
	public static Connection openConnection(String username, String password) {
		Connection result = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

			result = DriverManager.getConnection("jdbc:oracle:thin:@80.96.123.131:1521:ora09", username, password);

			if (result != null) {
				result.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return result;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
	//

}
