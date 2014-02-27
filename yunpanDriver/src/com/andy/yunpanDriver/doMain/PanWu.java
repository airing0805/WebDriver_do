package com.andy.yunpanDriver.doMain;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.yunpanDriver.webDriver.WebDriverUtil;

public class PanWu {
	
	public static void main(String[] args) {
		WebDriver fd = new FirefoxDriver();
		String url = "http://www.wangpanwu.com/s-all/zhongziheji/";
		WebDriverUtil.getUrl(fd, url);
		List<WebElement> liElList = fd.findElement(By.id("flist")).findElements(By.tagName("li"));
		for(int i=0;i<liElList.size();i++){
			WebElement liEl= liElList.get(i);
			WebElement linkEl =liEl.findElement(By.tagName("a"));
			String text = linkEl.getText();
			String href = linkEl.getAttribute("href");
			System.out.println(text+"\n"+href);
		}
	}

}
