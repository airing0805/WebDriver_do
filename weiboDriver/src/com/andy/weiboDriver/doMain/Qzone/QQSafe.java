package com.andy.weiboDriver.doMain.Qzone;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.util.XMLConfig;

public class QQSafe {
	private static Logger logger = Logger.getLogger(QQSafe.class);

	public static void main(String[] args) throws ConfigurationException, InterruptedException {
		List<Object> weiboList = XMLConfig.getConfig("QQSafeConfig").getList("QQ.qqno");
		int weiboNum = weiboList.size();
		logger.info(weiboNum);
		WebDriver webDriver = null;
			webDriver = new HtmlUnitDriver(true);
		QQSafe qqsafe  = new QQSafe();
		qqsafe.getLogin(webDriver);
		webDriver.quit();
	}
	
	public void getLogin(WebDriver webDriver ){
		webDriver.get("https://aq.qq.com/cn2/manage/my_mb");
	}
	
//	public void get
//	Select hourSelect = new Select(fd.findElement(By.cssSelector("select[id=\"hour_1\"]")));
//	WebElement hourSelectedWe = hourSelect.getFirstSelectedOption();
//	if("1".equals(hourSelectedWe.getText())){
//		hourSelect.selectByVisibleText("7");
//		Select minuteSelect = new Select(fd.findElement(By.cssSelector("select[id=\"minute_1\"]")));
//		minuteSelect.selectByVisibleText("30");
//	}
	

}
