package com.andy.yunpanDriver.DB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ResourceUtil {
	private static Logger logger = Logger.getLogger(  ResourceUtil.class);
	
	@Test
	public void test() throws SQLException, ParseException{
		Connection conn = getConnection();
//		String insertSQL = "INSERT INTO QQ_WEIBO_STATE (START_TIME,WEIBO_CODE,END_TIME,MESSAGE_STATE) VALUES(datetime(?),?,datetime(?),?)";
//		PreparedStatement prep = conn.prepareStatement(   insertSQL);
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		java.util.Date startDate = sf.parse("2013-02-02 10:10:10");
//		java.util.Date endDate = sf.parse("2013-05-05 10:10:10");
//		logger.info(startDate+"\n\n"+endDate);
//		Timestamp startDate2 = new Timestamp(startDate.getTime());
//		Timestamp endDate2 = new Timestamp(endDate.getTime());
//		logger.info(startDate2+"\n\n"+endDate2);
//		prep.setString(1,   "1900-01-01 0:0:0");
//		prep.setString(2, "asf");
//		prep.setString(3, "1900-01-01 10:10:10");
//		prep.setString(4, "aa");
//		prep.execute();
		
		PreparedStatement stat = conn.prepareStatement("SELECT  * FROM QQ_WEIBO_MESSAGE;"); 
		ResultSet rs = stat.executeQuery();  
		rs.next();
		logger.info(rs.getString("ID"));
		close(rs,stat,conn);
	}


	public static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");   
			String path = System.getProperty("user.dir");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path+File.separator+"DB/databases.db");
			return conn;
		} catch (ClassNotFoundException e) {
			logger.info("failed to register driver.");
			throw new RuntimeException(e);
		} catch (SQLException e) {
			logger.info("failed to execute sql.");
			throw new RuntimeException(e);
		}
	}
	
	public static List<Map<String,String>> resultSetToList(ResultSet rs) {
        if (rs == null)   
            return Collections.emptyList();
        List<Map<String,String>> list = null;
        try{
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
        list = new ArrayList<Map<String,String>>();   
        Map<String, String> rowData = new HashMap<String, String>();   
        while (rs.next()) {   
         rowData = new HashMap<String, String>(columnCount);   
         for (int i = 1; i <= columnCount; i++) {   
        	 Object obj = rs.getObject(i);
        	 if(null != obj){
        		 rowData.put(md.getColumnName(i),obj.toString());   
        	 }else{
        		 rowData.put(md.getColumnName(i),null);   
        	 }
         }   
         list.add(rowData);   
        }   
        }catch(SQLException e){
        	e.printStackTrace();
        }
//        logger.info(list.toString());
        return list;   
}  
	
	public static Connection getTestConnection() {
		try {
			Class.forName("org.sqlite.JDBC");   
			String path = System.getProperty("user.dir");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path+File.separator+"DB/databasesTest.db");
			return conn;
		} catch (ClassNotFoundException e) {
			logger.info("failed to register driver.");
			throw new RuntimeException(e);
		} catch (SQLException e) {
			logger.info("failed to execute sql.");
			throw new RuntimeException(e);
		}
	}


	public static void close(ResultSet rs, Statement st, Connection con) {
		close(rs);
		close(st, con);
	}

	public static void close(Statement st, Connection con) {
		close(st);
		close(con);
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
		}
	}
	
	public static void close(Statement st) {
		try {
			st.close();
		} catch (Exception e) {
		}
	}
	
	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}
}
