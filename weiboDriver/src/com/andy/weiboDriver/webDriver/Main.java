package com.andy.weiboDriver.webDriver;

import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.fansApp.Qiuzf;
import com.andy.weiboDriver.fansApp.Tuimi;
import com.andy.weiboDriver.fansApp.Tuitu;
import com.andy.weiboDriver.util.XMLConfig;

public class Main {
	public static void main(String[] args) throws ConfigurationException, InterruptedException {

		int caseNum = 0;
		if (null != args && args.length > 0) {
			caseNum = Integer.parseInt(args[0]);
		} 
		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.weibo_username");
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		int weiboNum = weiboList.size();
		WebDriver fd = null;
		if("false".equals(firefoxRun)){
			fd = new HtmlUnitDriver();
		}else{
			fd = new FirefoxDriver();
			
		}
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
		
		while(true){
			for(int i=0;i<weiboNum;i++){
				String username = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_username");
				String password = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_password");
				new WeiboSina().login(fd, username, password);
				//最多只到十页，有一页成功就退出
				new Tuitu().getScoreFlow(fd);
				new Qiuzf().getScoreFlow(fd);
				new Tuimi().getScoreFlow(fd);
			}
			try {
				//进入等待
				System.out.println("进入等待" + new Date().toString());
				Thread.sleep(1800000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
