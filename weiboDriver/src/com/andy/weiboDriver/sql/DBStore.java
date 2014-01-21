package com.andy.weiboDriver.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBStore {

	private static String dbName = "test.db";
	private static String basedir = System.getProperty("user.dir");
	private static String dataDir = basedir + File.separator + "data";

	public DBStore() {
		dbName = dataDir + dbName;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class DBStoreHolder {
		static DBStore instance = new DBStore();
	}

	public static DBStore getInstance() {
		return DBStoreHolder.instance;
	}

	public synchronized void init() {
		File dbDir = new File(dataDir);
		if (!dbDir.exists()) {
			dbDir.mkdirs();
		}
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File dbFile = new File(dbName);
		if (!dbFile.exists()) {

		}
	}
	public Connection getConnection(){
		Connection conn =null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public int ececute(String sql ,Connection connection){
		Statement statement;
		int result =0;
		try {
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			result= statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return result;
	}
	
	public void createTable(){
		Connection conn = getConnection();
		String sql="create table sys_user(id integer primary key autoincrement ,username string ,password string)";
		ececute(sql,conn);
		close(conn);
	}
	
	public ResultSet query(String sql ,Connection connection){
		Statement statement;
		ResultSet result =null;
		try {
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			result= statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return result;
	}
	
	public int deletee(String sql ,Connection connection){
		Statement statement;
		int result =0;
		try {
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			result= statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return result;
	}
	public void close(Connection conn){
		try {
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		DBStore dbStore = DBStore.getInstance();
		Connection conn = dbStore.getConnection();
		System.out.println(conn);
	}
}

