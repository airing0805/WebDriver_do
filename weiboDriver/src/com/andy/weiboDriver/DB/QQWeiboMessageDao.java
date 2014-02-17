package com.andy.weiboDriver.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.andy.weiboDriver.entity.QQWeiboMessage;

public class QQWeiboMessageDao {

	public boolean insert(QQWeiboMessage qqMessage) {
		boolean flag = false;
		try {
			Connection conn = ResourceUtil.getConnection();
			String insertSQL = "Insert Into QQ_WEIBO_MESSAGE(FORM_URL,MESSAGE_CONTENT,PIC_HREF,MESSAGE_TIME,STATE) VALUES(?,?,?,datetime(?),?)";
			PreparedStatement prep = conn.prepareStatement(insertSQL);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			prep.setString(1, qqMessage.getFORM_URL());
			prep.setString(2, qqMessage.getMESSAGE_CONTENT());
			prep.setString(3, qqMessage.getPIC_HREF());
			String dateStr = sf.format(qqMessage.getMESSAGE_TIME());
			prep.setString(4, dateStr);
			prep.setString(5, qqMessage.getSTATE());
			flag = prep.execute();
			ResourceUtil.close(prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean update(QQWeiboMessage qqMess) {
		boolean flag = false;
		try {
			Connection conn = ResourceUtil.getConnection();
			String updateSQL = " UPDATE QQ_WEIBO_MESSAGE SET FORM_URL=?,MESSAGE_CONTENT=?,PIC_HREF=?,STATE=? WHERE ID=?";
			PreparedStatement prep = conn.prepareStatement(updateSQL);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			prep.setString(1, sf.format(qqMess.getFORM_URL()));
			prep.setString(2, sf.format(qqMess.getMESSAGE_CONTENT()));
			prep.setString(3, qqMess.getPIC_HREF());
			prep.setString(4, qqMess.getSTATE());
			prep.setString(5, qqMess.getID()+"");
			flag = prep.execute();
			ResourceUtil.close(prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public List<QQWeiboMessage> queryByUrl(String FORM_URL) {
		List<QQWeiboMessage> qqs = null;
		try {
			Connection conn = ResourceUtil.getConnection();
			String updateSQL = " SELECT * FROM  QQ_WEIBO_MESSAGE WHERE FORM_URL=? ";
			PreparedStatement prep = conn.prepareStatement(updateSQL);
			prep.setString(1, FORM_URL);
			ResultSet rs = prep.executeQuery();
			if (null == rs)
				return null;
			qqs = getSefList(ResourceUtil.resultSetToList(rs));
			ResourceUtil.close(rs, prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qqs;
	}
	
	public List<QQWeiboMessage> queryAll() {
		List<QQWeiboMessage> qqs = new ArrayList<QQWeiboMessage>();
		try {
			Connection conn = ResourceUtil.getConnection();
			String updateSQL = " select * from QQ_WEIBO_MESSAGE  ";
			PreparedStatement prep = conn.prepareStatement(updateSQL);
			ResultSet rs = prep.executeQuery();
			if (null == rs)
				return null;
			qqs = getSefList(ResourceUtil.resultSetToList(rs));
			ResourceUtil.close(rs, prep, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qqs;
	}

	@Test
	public void getSefListTest() {
//		List<QQWeiboMessage> qqsList = queryAll();
//		logger.info(qqsList.toString());
	}

	public List<QQWeiboMessage> getSefList(List<Map<String, String>> rsList) {
		List<QQWeiboMessage> qqWeiboList = new ArrayList<QQWeiboMessage>();
		for (Map<String, String> map : rsList) {
			QQWeiboMessage qqMess = new QQWeiboMessage();
			qqMess.setID(Integer.parseInt(map.get("ID")));
			String FORM_URL = map.get("FORM_URL");
			String MESSAGE_CONTENT = map.get("MESSAGE_CONTENT");
			String PIC_HREF = map.get("PIC_HREF");
			String STATE = map.get("STATE");
			qqMess.setFORM_URL(FORM_URL);
			qqMess.setMESSAGE_CONTENT(MESSAGE_CONTENT);
			if(null != PIC_HREF){
				qqMess.setPIC_HREF(PIC_HREF);
			}else{
				qqMess.setPIC_HREF("");
			}
			if(null != STATE){
				qqMess.setSTATE(STATE);
			}else{
				qqMess.setSTATE("");
			}
			qqWeiboList.add(qqMess);
		}
		return qqWeiboList;
	}
}
