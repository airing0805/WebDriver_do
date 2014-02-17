package com.andy.weiboDriver.DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.andy.weiboDriver.entity.QQWeibo;

public class QQWeiboDao {
	
	private static Logger logger = Logger.getLogger(  QQWeiboDao.class);

	public void insertState(QQWeibo qqState) {
		try {
			Connection conn = ResourceUtil.getConnection();
			String insertSQL = "INSERT INTO QQ_WEIBO_STATE (START_TIME,URL,END_TIME,MESSAGE_STATE) VALUES(datetime(?),?,datetime(?),?)";
			PreparedStatement prep = conn.prepareStatement(insertSQL);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			prep.setString(1, sf.format(qqState.getSTART_TIME()));
			prep.setString(2, qqState.getURL());
			prep.setString(3, sf.format(qqState.getEND_TIME()));
			prep.setString(4, qqState.getMESSAGE_STATE());
			prep.execute();
			ResourceUtil.close(prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateState(QQWeibo qqState) {
		try {
			Connection conn = ResourceUtil.getConnection();
			String updateSQL = " UPDATE QQ_WEIBO_STATE SET START_TIME=datetime(?),END_TIME=datetime(?),MESSAGE_STATE=? WHERE ID=?";
			PreparedStatement prep = conn.prepareStatement(updateSQL);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			prep.setString(1, sf.format(qqState.getSTART_TIME()));
			prep.setString(2, sf.format(qqState.getEND_TIME()));
			prep.setString(3, qqState.getMESSAGE_STATE());
			prep.setString(4, qqState.getID()+"");
			prep.execute();
			ResourceUtil.close(prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public QQWeibo queryStateByUrl(String url) {
		QQWeibo qqs = null;
		try {
			Connection conn = ResourceUtil.getConnection();
			String updateSQL = " SELECT * FROM  QQ_WEIBO_STATE WHERE URL=? ";
			PreparedStatement prep = conn.prepareStatement(updateSQL);
			prep.setString(1, url);
			ResultSet rs = prep.executeQuery();
			if (null == rs)
				return null;
			qqs = getSefList(ResourceUtil.resultSetToList(rs)).get(0);
			ResourceUtil.close(rs, prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qqs;
	}

	@Test
	public void getSefListTest() {
		 List<QQWeibo>  qqsList = queryByState("初始");
		 logger.info(qqsList.size());
	}

	public List<QQWeibo> queryByState(String MESSAGE_STATE) {
		List<QQWeibo> qqsList = null;
		try {
			Connection conn = ResourceUtil.getConnection();
			String updateSQL = " SELECT * FROM  QQ_WEIBO_STATE WHERE MESSAGE_STATE=? ";
			PreparedStatement prep = conn.prepareStatement(updateSQL);
			prep.setString(1, MESSAGE_STATE);
			ResultSet rs = prep.executeQuery();
			if (null == rs)
				return null;
			qqsList = getSefList(ResourceUtil.resultSetToList(rs));
			ResourceUtil.close(rs, prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qqsList;
	}

	public List<QQWeibo> getSefList(List<Map<String, String>> rsList) {
		List<QQWeibo> qqWeiboList = new ArrayList<QQWeibo>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			for (Map<String, String> map : rsList) {
				QQWeibo qqs = new QQWeibo();
				qqs.setID(Integer.parseInt(map.get("ID")));
				qqs.setURL(map.get("URL").toString());
				String dateStr = map.get("START_TIME");
				if (dateStr.contains("-")) {
					sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				}else if(dateStr.contains("/")){
					sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				}
				Date START_TIME = new Date(sf.parse(map.get("START_TIME")).getTime());
				qqs.setSTART_TIME(START_TIME);
				Date END_TIME = new Date(sf.parse(map.get("END_TIME")).getTime());
				qqs.setEND_TIME(END_TIME);
				qqs.setMESSAGE_STATE(map.get("MESSAGE_STATE").toString());
				qqWeiboList.add(qqs);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return qqWeiboList;
	}
}
