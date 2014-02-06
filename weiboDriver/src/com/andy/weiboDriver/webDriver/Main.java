package com.andy.weiboDriver.webDriver;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.fansApp.Qiuzf;
import com.andy.weiboDriver.fansApp.Tuimi;
import com.andy.weiboDriver.fansApp.Tuitu;
import com.andy.weiboDriver.util.XMLConfig;

public class Main {
	public static void main(String[] args) throws ConfigurationException, InterruptedException {

		int caseNum = 1;
		if (null != args && args.length > 0) {
			caseNum = Integer.parseInt(args[0]);
		} 
		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.weibo_username");
		int weiboNum = weiboList.size();
		WebDriver fd = new FirefoxDriver();
		if(caseNum==0){
			 new DriverWeiboQQ().getMessageFlow(fd, weiboNum);
		}else if(caseNum==1){
			new WeiboSendAtPP().sendAtPPFlow(fd, weiboNum);
		}else if(caseNum==2){
			new DriverWeiboQQ().getMessageFlow(fd, weiboNum);
			new WeiboSendAtPP().sendAtPPFlow(fd, weiboNum);
		}else if(caseNum==3){
			iterateGetScore(fd,weiboNum);
		}
		
		fd.quit();
	}
	
	public static void iterateGetScore(WebDriver fd,int weiboNum){
		for(int i=0;i<weiboNum;i++){
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_username");
			String password = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_password");
			new WeiboSina().login(fd, username, password);
			new Qiuzf().getScoreFlow(fd);
			new Tuimi().getScoreFlow(fd);
			new Tuitu().getScoreFlow(fd);
		}
	}
	
	
	




}
