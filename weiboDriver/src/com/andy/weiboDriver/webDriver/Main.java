package com.andy.weiboDriver.webDriver;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.util.XMLConfig;

public class Main {
	public static void main(String[] args) throws ConfigurationException, InterruptedException {


		int caseNum = 1;
		if (null != args && args.length > 0) {
			caseNum = Integer.parseInt(args[0]);
		} 
		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.username");
		int weiboNum = weiboList.size();
		WebDriver fd = new FirefoxDriver();
		if(caseNum==0){
			 new WeiboQQ().getMessageFlow(fd, weiboNum);
		}else if(caseNum==1){
			new WeiboSendAtPP().sendAtPPFlow(fd, weiboNum);
		}else{
			new WeiboQQ().getMessageFlow(fd, weiboNum);
			new WeiboSendAtPP().sendAtPPFlow(fd, weiboNum);
		}
		
		fd.quit();
	}
	
	
	




}
