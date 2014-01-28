package com.andy.weiboDriver.fansApp;

import java.awt.RenderingHints.Key;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

	public void attentionOne(WebDriver fd, String username, String password){
		
		String url = "http://apps.weibo.com/tuimimi";
		fd.get(url);
		fd.findElement(By.id("onekey_btn")).click();
		
		
	}
	
	public void oneKeyAttention(WebDriver fd) {
		
		String url = "http://apps.weibo.com/tuimimi";
		fd.get(url);
		WebElement searchWe = WebDriverUtil.findElement4Wait(fd, By.cssSelector("input[node-type=\"searchInput\"]"), -1);
		searchWe.sendKeys("搜");
//		fd.findElement(By.id("desc")).click();
		System.out.println(1);
		WebElement appsIframe =WebDriverUtil.findElement4Wait(fd,By.id("apps"),-1);
		System.out.println(2);
		fd.switchTo().frame(appsIframe);
		System.out.println(3);
		fd.findElement(By.id("onekey_btn")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fd = fd.switchTo().defaultContent();
		WebElement appsIframeTwo =fd.findElement(By.id("apps"));
		fd = fd.switchTo().frame(appsIframeTwo);
		WebElement iframeWe = fd.findElement(By.cssSelector("div[id=\"oneKeyAttention\"] > iframe"));
//		WebElement iframeWe =  WebDriverUtil.findElement4Wait(oneKeyAttentionWe,By.tagName("iframe"),-1);
		fd.switchTo().frame(iframeWe);

		System.out.println(7);
		WebElement divWe = fd.findElement(By.cssSelector("div[class=\"btn_con\"]"));
		WebElement oneKeyButton = divWe.findElement(By.cssSelector("a[class=\"btngreen_l\"]"));
		System.out.println(8);
		oneKeyButton.sendKeys(Keys.DOWN);
		oneKeyButton.click();
//		oneKeyButton.findElement(By.tagName("span")).click();
		System.out.println(9);
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
