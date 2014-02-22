package com.andy.weiboDriver.doMain;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.DriverWeiboQQ;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSendAtPP;

public class GetMessage {
	private static Logger logger = Logger.getLogger(GetMessage.class);

	public static void main(String[] args) throws ConfigurationException, InterruptedException {
		int caseNum = 1;
		if (null != args && args.length > 0) {
			caseNum = Integer.parseInt(args[0]);
		}
		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.weibo_username");
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		int weiboNum = weiboList.size();
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}
		try {
			if (caseNum == 0) {
				new DriverWeiboQQ().getMessageFlow(fd, weiboNum);
			} else if (caseNum == 1) {
				new WeiboSendAtPP().sendAtPPFlow(fd, weiboNum);
			} else if (caseNum == 2) {
				new DriverWeiboQQ().getMessageFlow(fd, weiboNum);
				new WeiboSendAtPP().sendAtPPFlow(fd, weiboNum);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			WebDriverUtil.takeScreenShot(fd);
		}
		fd.quit();
	}

}
