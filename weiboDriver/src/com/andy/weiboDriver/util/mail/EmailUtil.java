package com.andy.weiboDriver.util.mail;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class EmailUtil {
	public static void main(String[] args) throws Exception {
		// 取得pop3协议的邮件服务器
		// 连接pop.qq.com邮件服务器
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props);
		Store store = session.getStore("pop3");
		store.connect("pop3.163.com", "zhaoyuan0805", "zhao1984zhao88");
		System.out.println(store.isConnected());

		// 返回文件夹对象
		Folder folder = store.getFolder("INBOX");
		
		// 设置仅读
		folder.open(Folder.READ_WRITE);
		// 获取信息
		Message message[] = folder.getMessages();
		System.out.println(message.length);
		
		for (int i = 0; i < message.length; i++) {
			// 打印主题
			System.out.println(message[i].getSubject());
			System.out.println(message[i].getContentType());
			System.out.println(message[i].getContent().toString());
			System.out.println("-----------------------------------------");
		}
		folder.close(true);
		store.close();
	}

}