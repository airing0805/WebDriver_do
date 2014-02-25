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
<<<<<<< HEAD
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
		
=======

public class BaiduSo {
	
	private static Logger logger = Logger.getLogger(  BaiduSo.class);
	
	@Test
	public void test(){
		WebDriver fd = new FirefoxDriver();
		fd.get("http://www.baidu.com/s?wd=site%3A03wx.cc&rsv_bp=0&ch=&tn=baidu&bar=&rsv_spt=3&ie=utf-8");
//		fd.findElement(By.id("kw")).click();
//		fd.findElement(By.id("kw1")).sendKeys("site:03wx.cc");
//		fd.findElement(By.id("su")).click();
>>>>>>> refs/remotes/origin/master
		
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
