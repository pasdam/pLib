package com.pasdam.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseWrapper {
	
	private Connection connection;

	public DatabaseWrapper(String driver, String url, String user, String password, boolean autoCommit) throws ClassNotFoundException, SQLException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Unable to load driver: " + driver);
		}
		try {
			this.connection = DriverManager.getConnection(url, user, password);
			this.connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw new SQLException("Unable to connect to " + url);
		}
	}
	
	public DatabaseWrapper(String driver, String url, String user, String password) throws ClassNotFoundException, SQLException {
		this(driver, url, user, password, true);
	}
	
	public ResultSet execSQL(String sql) throws SQLException{
		boolean isClosed = false;
		try {
			isClosed = this.connection.isClosed();
		} catch (SQLException e) {
			isClosed = true;
		}
		if (isClosed) {
			throw new SQLException("Unable to access to DB");
		}
		
		Statement statement = this.connection.createStatement();
		if (statement.execute(sql)) {
			return statement.getResultSet();
		}
		return null;
	}
	
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.connection.setAutoCommit(autoCommit);
	}
	
	public void commit() throws SQLException {
		this.connection.commit();
	}
	
	public void rollback() throws SQLException {
		this.connection.rollback();
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		this.connection.close();
	}

}
