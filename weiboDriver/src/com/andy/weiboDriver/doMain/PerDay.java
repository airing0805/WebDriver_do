package com.andy.weiboDriver.doMain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class PerDay {
	public static void main(String[] args) throws ConfigurationException, InterruptedException {

		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.weibo_username");
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		int weiboNum = weiboList.size();
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}

		iterateGetPage(fd, weiboNum);

		fd.quit();
	}

	// TODO对于一键关注16个的应用要区分标注出来，半小时的限制会突破30
	public static void iterateGetPage(WebDriver fd, int weiboNum) {
		for (int i = 0; i < weiboNum; i++) {
			String path = System.getProperty("user.dir") + File.separator;
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_username");
			String password = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_password");
			String weiboNO = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_no");
			String  weiboUrl = "http://weibo.com/u/"+weiboNO;
			String weiboFans = "http://weibo.com/"+weiboNO+"/myfans";
			String weiboFollow = "http://weibo.com/"+weiboNO+"/myfollow";
			String atMe = "http://weibo.com/at/weibo";
			String mess = "http://weibo.com/messages";
			String comment = "http://weibo.com/comment/inbox";
			String notesboard = "http://weibo.com/notesboard";
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sf1.format(new Date()));
			path += username + sf.format(new Date()) + ".txt";
			String fileMess = username + "\n";
			FileUtil.write2FileEnd(path, fileMess);
			new WeiboSina().login(fd, username, password);
			System.out.println(sf1.format(new Date()));
			fd.get(weiboUrl);
			System.out.println(sf1.format(new Date()));
			fd.get(weiboFans);
			System.out.println(sf1.format(new Date()));
			fd.get(weiboFollow);
			System.out.println(sf1.format(new Date()));
			fd.get(atMe);
			System.out.println(sf1.format(new Date()));
			fd.get(mess);
			System.out.println(sf1.format(new Date()));
			fd.get(comment);
			System.out.println(sf1.format(new Date()));
			fd.get(notesboard);
			System.out.println(sf1.format(new Date()));
		}
	}

}
