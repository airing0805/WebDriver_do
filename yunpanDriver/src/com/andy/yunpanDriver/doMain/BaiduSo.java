package com.andy.yunpanDriver.doMain;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.yunpanDriver.util.FileUtil;
import com.andy.yunpanDriver.webDriver.WebDriverUtil;

public class BaiduSo {
	
	private static Logger logger = Logger.getLogger(  BaiduSo.class);
	
	@Test
	public void test(){
		WebDriver fd = new FirefoxDriver();
//		fd.get("http://www.baidu.com");
		By by = By.cssSelector("input[name=\"wd\"]");
		WebDriverUtil.getUrl(fd, "http://www.baidu.com", by);
		fd.findElement(by).click();
		fd.findElement(By.cssSelector("input[name=\"wd\"]")).sendKeys("site:03wx.cc");
		fd.findElement(By.id("su")).click();
		
		
		List<WebElement> tableElList = fd.findElement(By.id("content_left")).findElements(By.tagName("table"));
		for(WebElement tableEl : tableElList){
			WebElement contentEl = tableEl.findElement(By.tagName("td"));
			String name = contentEl.findElement(By.tagName("h3")).getText().replace("理想文学", "");
			String content = contentEl.findElement(By.cssSelector("div.c-abstract")).getText();
			String path = System.getProperty("user.dir")+File.separator+name+".txt";
			FileUtil.write2FileEnd(path, content);
		}
	}
	
}
