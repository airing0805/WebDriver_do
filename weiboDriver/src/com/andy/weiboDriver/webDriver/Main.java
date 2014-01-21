package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.XMLConfig;

public class Main {
	public static void main(String[] args) throws ConfigurationException {
//		args = {"1"} ;
		
//		WebDriver fd = new FirefoxDriver();
		List weiboList = XMLConfig.getConfig().getList("weibo.username");
		int listSize  = weiboList.size();
		for(int i=0;i<listSize;i++){
			String username = XMLConfig.getConfig().getString("weibo("+i+").username");
			String password = XMLConfig.getConfig().getString("weibo("+i+").password");
			System.out.println(username);
			String path = System.getProperty("user.dir")+File.separator+username+".txt";
			List addressList = XMLConfig.getConfig().getList("weibo("+i+").address.QQAddress");
			for(int j=0;j<addressList.size();j++){
				System.out.println(XMLConfig.getConfig().getString("weibo("+i+").address.QQAddress("+j+")"));
			}
		}
		int caseNum = Integer.parseInt(args[0]);
	}
	
	public void doSwitch(int caseNum,){
		
		switch (caseNum) {
		case 1:
			weiboQQGetMessage(fd,url,path);
			break;
		case 2:
			weiboSendAtPP(fd, username, password, messArr);
			break;
		default:
			break;
		}
	}

	private void  weiboQQGetMessage(WebDriver fd,String url,String path) throws InterruptedException {
		String message = new WeiboQQ().getMessageFlow(fd, url);
		FileUtil.write2FileEnd(path, message);
	}
		

	private static void weiboSendAtPP(WebDriver fd,String username,String password,String[][] messArr) throws InterruptedException {
		new WeiboSendAtPP().faWeiFlowT(fd, username, password, messArr);
	}
}
