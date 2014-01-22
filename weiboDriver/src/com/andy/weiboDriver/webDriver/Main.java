package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.XMLConfig;

public class Main {
	public static void main(String[] args) throws ConfigurationException, InterruptedException {
		
		int caseNum = 0;
		if(null != args && args.length >0){
			Integer.parseInt(args[0]);
		}else{
			caseNum =1;
		}
		WebDriver fd = new FirefoxDriver();
		List weiboList = XMLConfig.getConfig().getList("weibo.username");
		int listSize = weiboList.size();
		for (int i = 0; i < listSize; i++) {
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").username");
			String password = XMLConfig.getConfig().getString("weibo(" + i + ").password");
			System.out.println(username);
			String path = System.getProperty("user.dir") + File.separator + username + ".txt";
			List addressList = XMLConfig.getConfig().getList("weibo(" + i + ").address.QQAddress");
			if (caseNum == 1) {
				for (int j = 0; j < addressList.size(); j++) {
					String url = XMLConfig.getConfig().getString("weibo(" + i + ").address.QQAddress(" + j + ")");
					//TODO 有时候没有原创哦
					weiboQQGetMessage(fd, url, path);
					Thread.sleep(5000);
					
				}
			}else{
				//TODO 分解成数组或集合
//				weiboSendAtPP(fd, username, password, messArr);
			}
		}
		fd.quit();
	}

	private static void weiboQQGetMessage(WebDriver fd, String url, String path) throws InterruptedException {
		String message = new WeiboQQ().getMessageFlow(fd, url);
		System.out.println(message);
		FileUtil.write2FileEnd(path, message);
	}

	private static void weiboSendAtPP(WebDriver fd, String username, String password, String[][] messArr) throws InterruptedException {
		new WeiboSendAtPP().faWeiFlowT(fd, username, password, messArr);
	}
}
