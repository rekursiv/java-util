package util.mysql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MySqlManager implements Closeable {
	
	@Inject protected Logger log;
	
	protected String url = null;
	protected String un = null;
	protected String pw = null;
	protected Connection con = null;
	
	public void init(String url, String un, String pw) {
		this.url = url;
		this.un = un;
		this.pw = pw;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public void open() throws SQLException {
		con = DriverManager.getConnection(url, un, pw);
	}
	
	public PreparedStatement prepareStatement(String q) throws SQLException {
		return con.prepareStatement(q);
	}
	
	public ResultSet query(String q) throws SQLException {
		return con.createStatement().executeQuery(q);
	}
	
	public int update(String q) throws SQLException {
		return con.createStatement().executeUpdate(q);
	}

	public Connection getConnection() throws SQLException {
		return con;
	}
	
	public Connection getNewConnection() throws SQLException {
		return DriverManager.getConnection(url, un, pw);
	}

	@Override
	public void close() {
		if (con!=null) try { con.close(); } catch (SQLException e) {}	
		con=null;
	}

}
