package com.andy.weiboDriver.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Test {
	
private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
private static String protocol = "jdbc:derby:";
private static String basedir = System.getProperty("user.dir") ;
private static String dataDir = basedir + File.separator+"data" ;
private static String dbName = dataDir + "test.db";

static void loadDriver() {
	try {
	Class.forName(driver).newInstance();
	System.out.println("Loaded the appropriate driver");
	} catch (Exception e) {
	e.printStackTrace();
	}
}
public void doIt() {
	Connection conn = null;
	Statement s = null;
	ResultSet rs = null;
	System.out.println("starting");
	try {
	conn = DriverManager.getConnection(protocol + dbName
	+ ";create=true");
	} catch (SQLException e) {
	e.printStackTrace();
	}
	System.out.println(dbName);
	try {
	s=conn.createStatement();
	s.executeUpdate("create table firsttable(id int primary key, name varchar(20))");
	rs=s.executeQuery("select * from firsttable");
	while (rs.next()) {
	System.out.println(rs.getInt(1));
	System.out.println(rs.getString(2));
	}
	} catch (SQLException e1) {
	e1.printStackTrace();
	}
	try {
	conn.close();
	conn = null;
	s.close();
	s = null;
	rs.close();
	rs = null;
	} catch (Exception e) {
	e.printStackTrace();
	}
}
public static void main(String[] args) {
	Test t = new Test();
	t.loadDriver();
	t.doIt();
	}
}