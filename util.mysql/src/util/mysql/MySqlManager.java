package util.mysql;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	protected Statement stmt = null;
	
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

	public PreparedStatement prepareStatement(String q) throws SQLException {
		con = DriverManager.getConnection(url, un, pw);
		PreparedStatement p = con.prepareStatement(q);
		stmt = p;
		return p;
	}
	
	public ResultSet query(String q) throws SQLException {
		con = DriverManager.getConnection(url, un, pw);
		stmt = con.createStatement();
		return stmt.executeQuery(q);
	}
	
	public int update(String q) throws SQLException {
		try (Connection c = DriverManager.getConnection(url, un, pw)) {
			return c.createStatement().executeUpdate(q);
		}
	}
	
	public Connection getConnection() throws SQLException {
		con = DriverManager.getConnection(url, un, pw);
		return con;
	}

	@Override
	public void close() throws IOException {
		if (con!=null) try { con.close(); } catch (SQLException e) {}	
		con=null;
		stmt=null;
	}

}
