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

		int caseNum = 1;
		if (null != args && args.length > 0) {
			caseNum = Integer.parseInt(args[0]);
		} 
		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.username");
		WebDriver fd = new FirefoxDriver();
		if(caseNum==0){
			 getMessage(fd, weiboList);
		}else if(caseNum==1){
			sendAtPP(fd, weiboList);
		}else{
			getMessage(fd, weiboList);
			sendAtPP(fd, weiboList);
			
		}
		
		
		fd.quit();
	}
	
	public static void sendAtPP(WebDriver fd,List<Object> weiboList ) throws ConfigurationException, InterruptedException{
		int listSize = weiboList.size();
		for (int i = 0; i < listSize; i++) {
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").username");
			String password = XMLConfig.getConfig().getString("weibo(" + i + ").password");
			System.out.println(username);
			String path = System.getProperty("user.dir") + File.separator + username + ".txt";
			List<Object> addressList = XMLConfig.getConfig().getList("weibo(" + i + ").address.QQAddress");
				StringBuffer sb = FileUtil.readFileByLines(path);
				String[][] messArr = str2Arr(sb.toString());
				weiboSendAtPP(fd, username, password, messArr);
		}
	}
	
	public static void getMessage(WebDriver fd,List<Object> weiboList ) throws ConfigurationException, InterruptedException{
		int listSize = weiboList.size();
		for (int i = 0; i < listSize; i++) {
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").username");
			System.out.println(username);
			String path = System.getProperty("user.dir") + File.separator + username + ".txt";
			List<Object> addressList = XMLConfig.getConfig().getList("weibo(" + i + ").address.QQAddress");
				new File(path).delete();
				for (int j = 0; j < addressList.size(); j++) {
					String url = XMLConfig.getConfig().getString("weibo(" + i + ").address.QQAddress(" + j + ")");
					weiboQQGetMessage(fd, url, path);
					Thread.sleep(5000);
				}
		}
	}

	private static String[][] str2Arr(String message) {
		String[] messArr = message.split("~mashang~");
		String[][] messArr2 = new String[messArr.length][2];
		for (int i = 0; i < messArr.length; i++) {
			String[] messArr3 = messArr[i].split("~laiqian~");
			messArr2[i][0] = messArr3[0];
			if (messArr3.length > 1) {
				messArr2[i][1] = messArr3[1];
			} else {
				messArr2[i][1] = "";
			}
		}
		return messArr2;
	}

	private static void weiboQQGetMessage(WebDriver fd, String url, String path) throws InterruptedException, ConfigurationException {
		fd.get(url);
		String message = new WeiboQQ().getMessageFlow(fd);
		System.out.println(message);
		FileUtil.write2FileEnd(path, message);
	}

	private static void weiboSendAtPP(WebDriver fd, String username, String password, String[][] messArr) throws InterruptedException, ConfigurationException {
		new WeiboSendAtPP().faWeiFlowT(fd, username, password, messArr);
	}
}
