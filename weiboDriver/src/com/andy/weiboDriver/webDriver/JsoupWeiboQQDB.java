package com.andy.weiboDriver.webDriver;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.andy.weiboDriver.DB.QQWeiboDao;
import com.andy.weiboDriver.DB.QQWeiboMessageDao;
import com.andy.weiboDriver.entity.QQWeibo;
import com.andy.weiboDriver.entity.QQWeiboMessage;
import com.andy.weiboDriver.entity.constant.QQWeiboConstant;

public class JsoupWeiboQQDB {
	private static Logger logger = Logger.getLogger(  JsoupWeiboQQDB.class);
	
	QQWeiboDao weiboDao = new QQWeiboDao();
	QQWeiboMessageDao weiboMessageDao = new QQWeiboMessageDao();

	//TODO 排除广告等
	public static void main(String[] args) throws ClientProtocolException, IOException {
		JsoupWeiboQQDB weiboDb = new JsoupWeiboQQDB();
		// String url = "http://t.qq.com/zhichangyy";
		List<QQWeibo> qqsList = weiboDb.weiboDao.queryByState(QQWeiboConstant.QQWEIBO_INIT);
		QQWeibo qqs = qqsList.get(0);
		String url = qqs.getURL();
		weiboDb.geturl(1, url, url);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = sf.format(new Date());
		qqs.setMESSAGE_STATE(QQWeiboConstant.QQWEIBO_GETMESSAGE + QQWeiboConstant.SEPARTOR + dateStr);
		qqs.setLAST_CRAWL_TIME(new java.sql.Date(new Date().getTime()));
		weiboDb.weiboDao.updateState(qqs);
		// logger.info(doc.toString());
	}

	public void geturl(int num, String urlHead, String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements page = doc.select("[id=pageNav]");
		Elements a = page.get(0).select("a");
		getMessage(doc, urlHead, url);
		for (int i = 0; i < a.size(); i++) {
			String text = a.get(i).text();
			if (text.contains("下一页") || text.contains("上一页"))
				continue;
			String href = a.get(i).attr("href");
			String hrefAA = urlHead + href;
			int pageN = Integer.parseInt(a.get(i).text());
			//TODO 要删除判断条件
			if (pageN == num + 1) {
				logger.info(pageN);
				geturl(pageN, urlHead, hrefAA);
				break;
			}
		}
	}

	public void getMessage(Document doc, String urlHead, String url) throws IOException {
		// Document doc = Jsoup.connect(url).get();

		Elements els = doc.select("ul[id=talkList] > li");
		for (int i = 0; i < els.size(); i++) {
			String mess = "";
			String picHref = "";
			String timeStr = "";
			QQWeiboMessage qqMessage = new QQWeiboMessage();

			Elements q1 = els.get(i).select("div[class=msgCnt]");
			if (null != q1 && q1.size() > 0) {
				Element el = q1.get(0);
				mess = el.text();
				qqMessage.setMESSAGE_CONTENT(mess);
			}
			Elements q2 = els.get(i).select("div[class=mediaWrap] > div >a.pic");
			if (null != q2 && q2.size() > 0) {
				Element el2 = q2.get(0);
				picHref = el2.attr("href");
				qqMessage.setPIC_HREF(picHref);

			}
			Element q3 = els.get(i).select("a[class=time]").get(0);
			if (null != q3  ) {
				timeStr = q3.text();
				java.sql.Date dateTime = getDate(timeStr);
				qqMessage.setMESSAGE_TIME(dateTime);
			}
			qqMessage.setFORM_URL(urlHead);
			qqMessage.setSTATE(QQWeiboConstant.QQWEIBO_MESS_INIT);
			weiboMessageDao.insert(qqMessage);
		}
	}

	public java.sql.Date getDate(String text) {
		text = text.trim();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年M月d日 hh:mm");
		if(text.contains("刚刚")){
			
		}else if (text.contains("前")) {
			int minDis = 0;
			minDis = Integer.parseInt(text.replace("分钟前", ""));
			int min = cal.get(Calendar.MINUTE);
			cal.set(Calendar.MINUTE, min - minDis);
		}else if (text.contains("今天")) {
			text = text.replace("今天", "").trim();
			String[] aa = text.split(":");
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aa[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(aa[1]));
		} else if (text.contains("昨天")) {
			text = text.replace("昨天", "").trim();
			String[] aa = text.split(":");
			int day = cal.get(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, day - 1);
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aa[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(aa[1]));
		} else {
			if (!text.contains("年")) {
				int year = cal.get(Calendar.YEAR);
				text = year + "年" + text.trim();
			}
			Date dateD = null;
			try {
				dateD = sf.parse(text);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.setTime(dateD);
		}
		return new java.sql.Date(cal.getTime().getTime());
	}

	@Test
	public void t() {
		logger.info(Integer.parseInt("00"));

	}

	// @Test
	public void demo() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://t.qq.com/meilishuyu2012").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements els = doc.select("ul[id=talkList] > li");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i).select("div[class=msgCnt]").get(0);
			Element el2 = els.get(i).select("div[class=mediaWrap] > div >a.pic").get(0);
			Element el3 = els.get(i).select("a[class=time]").get(0);
			logger.info(el.text() + "\n\n");
			logger.info(el2.attr("href") + "\n\n");
			logger.info(el3.attr("title") + "\n\n");
			logger.info(el3.parent().html() + "\n\n");
			logger.info(el3.text() + "\n\n");
			if (i > 0) {
				break;
			}
		}
	}
}
