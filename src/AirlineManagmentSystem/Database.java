package AirlineManagmentSystem;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database {

	private String url = "jdbc:mysql://localhost:3306/airline managment system";
	private String user = "user";
	private String password = "1Tn3tM07e0.8A)gJ";
	private Statement statement;

	public Database() throws SQLException {
		Connection connection = DriverManager.getConnection(url, user, password);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
	}
	
	public Statement getStatement() {
		return statement;
	}
	
}
