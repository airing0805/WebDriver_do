package com.andy.yunpanDriver.DB;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.andy.yunpanDriver.entity.QQWeibo;
import com.andy.yunpanDriver.entity.constant.QQWeiboConstant;

public class Init {
	
	private static Logger logger = Logger.getLogger(  Init.class);
	
	QQWeiboDao weiboDao = new QQWeiboDao();

	public static void main(String[] args) throws ParseException {
		Init init = new Init();
		String WEIBO_CODE = "http://t.qq.com/wei--bj";
		init.QQWeiboInitMessage(WEIBO_CODE);

	}

	public void QQWeiboInitMessage(String URL) throws ParseException {
		// String WEIBO_CODE = "meilishuyu2012";
		QQWeibo qqs = new QQWeibo();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		qqs.setSTART_TIME(new Date(sf.parse("1900-01-01 00:00:00").getTime()));
		qqs.setEND_TIME(new Date(sf.parse("1900-01-01 00:00:00").getTime()));
		qqs.setURL(URL);
		qqs.setMESSAGE_STATE(QQWeiboConstant.QQWEIBO_INIT);
		weiboDao.insertState(qqs);
		QQWeibo qqs2 = weiboDao.queryStateByUrl(URL);
		logger.info(qqs2.getMESSAGE_STATE());
	}
}
