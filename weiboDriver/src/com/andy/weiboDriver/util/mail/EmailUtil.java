package com.andy.weiboDriver.util.mail;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.WeiboSina;

public class EmailUtil {
	private static Logger logger = Logger.getLogger(  EmailUtil.class);
	
	public static void main(String[] args) throws Exception {
		String username="zy166688";
		String password ="andy0805";
		String href = getUrl( username, password);
		WebDriver fd = new FirefoxDriver();
		logger.info(href);
		new WeiboSina().login(fd, "zy166688@163.com", password);
		fd.get(href);
		
	}
	public static String getUrl(String username,String password) throws MessagingException, IOException {
		String href = "";
		// 取得pop3协议的邮件服务器
		// 连接pop.qq.com邮件服务器
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props);
		Store store = session.getStore("pop3");
		store.connect("pop3.163.com", username, password);
		logger.info(store.isConnected());

		// 返回文件夹对象
		Folder folder = store.getFolder("INBOX");
		
		// 设置仅读
		folder.open(Folder.READ_WRITE);
		// 获取信息
		Message message[] = folder.getMessages();
		
		for (int i = 0; i < message.length; i++) {
			// 打印主题
			String subject = message[i].getSubject();
			if(null != subject && subject.contains("开通确认")){
				String content = message[i].getContent().toString();
				Document doc = Jsoup.parse(content);
				href = doc.getElementsByTag("a").get(0).attr("href");
//				logger.info(href);
				break;
			}
//			logger.info(message[i].getSubject());
//			logger.info(message[i].getContentType());
//			logger.info(message[i].getContent().toString());
//			logger.info("-----------------------------------------");
		}
		folder.close(true);
		store.close();
		return href;
	}

}