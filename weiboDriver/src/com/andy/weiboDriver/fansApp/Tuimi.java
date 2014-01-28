package com.andy.weiboDriver.fansApp;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class Tuimi {

	public static void main(String[] args) {
		Tuimi tu = new Tuimi();
		WebDriver fd = new FirefoxDriver();
		String username = "yitest0805@sina.com";
		String password = "andy0805";
		new WeiboSina().login(fd,username,password);
		tu.oneKeyAttention(fd);
	}
	
	
	public Tuimi() {
		super();
	}
	
	public void oneKeyAttention(WebDriver fd) {
		
		String url = "http://apps.weibo.com/tuimimi";
		fd.get(url);
		WebDriverUtil.findElement4Wait(fd, By.id("apps"), 10);
		url = "http://tuimi.sinaapp.com/onekeyfl";
		System.out.println(4);
		fd.get(url);
		System.out.println(5);
		WebElement oneKeyAttentionWe = WebDriverUtil.findElement4Wait(fd,By.cssSelector("div[id=\"oneKeyAttention\"]"),10);
		WebElement iframeWe =  WebDriverUtil.findElement4Wait(oneKeyAttentionWe,By.tagName("iframe"),-1);
		System.out.println(6);
		fd.switchTo().frame(iframeWe);

		System.out.println(7);
		WebElement divWe = fd.findElement(By.cssSelector("div[class=\"btn_con\"]"));
		WebElement oneKeyButton = divWe.findElement(By.cssSelector("a[class=\"btngreen_l\"]"));
		System.out.println(8);
		oneKeyButton.sendKeys(Keys.DOWN);
		System.out.println(oneKeyButton.isEnabled());
		System.out.println(oneKeyButton.getText());
		System.out.println(oneKeyButton.getLocation());
//		oneKeyButton.click();
		System.out.println(9);
//		fd.switchTo().alert().accept();
		fd.switchTo().defaultContent();
		fd.findElement(By.id("addMark"));
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			System.out.println("\nthread exception\n");
//			e.printStackTrace();
//		}
//		
//		fd.findElement(By.id("addMark"));
		
		
//		driver.switchTo().defaultContent();
	}

}
