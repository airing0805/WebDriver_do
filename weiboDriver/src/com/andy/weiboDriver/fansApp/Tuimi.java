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
		tu.gotoOneKeyPage(fd);
		tu.oneKeyAttention(fd);
		tu.nextOneKeyAttention(fd);
	}
	
	
	public Tuimi() {
		super();
	}
	
	public void gotoOneKeyPage(WebDriver fd){
		String url = "http://apps.weibo.com/tuimimi";
		fd.get(url);
		WebDriverUtil.findElement4Wait(fd, By.id("apps"), 10);
		url = "http://tuimi.sinaapp.com/onekeyfl";
		fd.get(url);
	}
	
	public void oneKeyAttention(WebDriver fd) {
		//一直等到页面加载完成
		WebElement oneKeyAttentionWe = WebDriverUtil.findElement4Wait(fd,By.cssSelector("div[id=\"oneKeyAttention\"]"),10);
		//还真的必须要等，等到可以找到iframe,然后进入到iframe里面
		WebElement iframeWe =  WebDriverUtil.findElement4Wait(oneKeyAttentionWe,By.tagName("iframe"),-1);
		System.out.println(6);
		fd.switchTo().frame(iframeWe);

		System.out.println(7);
		WebElement divWe = fd.findElement(By.cssSelector("div[class=\"btn_con\"]"));
		WebElement oneKeyButton = divWe.findElement(By.cssSelector("a[class=\"btngreen_l\"]"));
		System.out.println(8);
		oneKeyButton.sendKeys(Keys.DOWN);
		System.out.println(oneKeyButton.isEnabled());
		oneKeyButton.click();
		System.out.println(9);
		fd.switchTo().alert().accept();
		//跳转到iframe外部，
		fd.switchTo().defaultContent();
		fd.findElement(By.id("addMark")).click();
		
		WebElement alertWe = WebDriverUtil.findElement4Wait(fd, By.id("tu_dialog_body"), 10);
		alertWe.findElement(By.cssSelector("a[class=\"btn\"]")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(10);
		
		
//		driver.switchTo().defaultContent();
	}
	
	public void nextOneKeyAttention(WebDriver fd){
		WebElement wraperWe = fd.findElement(By.id("wraper"));
		WebElement nextWe = wraperWe.findElement(By.cssSelector("a[class=\"next\"]"));
		nextWe.click();
	}

}
