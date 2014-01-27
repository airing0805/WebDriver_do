package com.andy.weiboDriver.fansApp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.WeiboSina;

public class Tuimi {

	public static void main(String[] args) {
		Tuimi tu = new Tuimi();
		WebDriver fd = new FirefoxDriver();
		String username = "yitest0805@sina.com";
		String password = "andy0805";
		tu.oneKeyAttention(fd, username, password);
	}
	
	
	public Tuimi() {
		super();
	}

	public void attentionOne(WebDriver fd, String username, String password){
		
		new WeiboSina().login(fd, username, password);
		String url = "http://apps.weibo.com/tuimimi";
		fd.get(url);
		fd.findElement(By.id("onekey_btn")).click();
		
		
	}
	
	public void oneKeyAttention(WebDriver fd, String username, String password) {
		new WeiboSina().login(fd, username, password);
		String url = "http://apps.weibo.com/tuimimi";
		fd.get(url);
		fd.findElement(By.id("onekey_btn")).click();
		WebElement iframeWe = fd.findElement(By.id("oneKeyAttention")).findElement(By.tagName("iframe"));
		fd.switchTo().frame(iframeWe);
		fd.findElement(By.cssSelector("a[class=\"btngreen_l\"]")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println("\nthread exception\n");
			e.printStackTrace();
		}
		
		fd.findElement(By.id("addMark"));
		
		
//		driver.switchTo().defaultContent();
	}

}
