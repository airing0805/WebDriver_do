package com.andy.weiboDriver.fansApp;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
		tu.oneKeyAttention(fd);
		tu.nextOneKeyAttention(fd);
		System.out.println(10);
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
		//等到可以找到iframe,然后进入到iframe里面,还真的必须要等，
		WebElement iframeWe =  WebDriverUtil.findElement4Wait(oneKeyAttentionWe,By.tagName("iframe"),-1);
		fd.switchTo().frame(iframeWe);
		
		//要处理iframe加载的时间
		WebElement buttonParentWe  = WebDriverUtil.findElement4Wait(fd, By.cssSelector("div[class=\"tsina_batconcern\"]"), 100);
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String buttonParentText = buttonParentWe.getText();
			System.out.println(buttonParentText);
			if(buttonParentText.contains("一键关注")){
				break;
			}
		}
		//点击一键关注
		WebElement divWe = WebDriverUtil.findElement4Wait(fd,By.cssSelector("div[class=\"btn_con\"]"),100);
		WebElement oneKeyButton = WebDriverUtil.findElement4Wait(divWe,By.cssSelector("a[class=\"btngreen_l\"]"),100);
		oneKeyButton.click();
		//确认关注
		Alert alert = fd.switchTo().alert();
		alert.accept();
		//等待关注成功
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String buttonText = divWe.getText();
			if(buttonText.contains("已关注")){
				break;
			}
		}
		//跳转到iframe外部，领取积分
		fd.switchTo().defaultContent();
		fd.findElement(By.id("addMark")).click();
		//确认积分
		WebElement alertWe = WebDriverUtil.findElement4Wait(fd, By.id("tu_dialog_body"), 10);
		alertWe.findElement(By.cssSelector("a[class=\"btn\"]")).click();
		System.out.println("完成一键");
	}
	
	public void nextOneKeyAttention(WebDriver fd){
		WebElement wraperWe = fd.findElement(By.id("wraper"));
		WebElement nextWe = wraperWe.findElement(By.cssSelector("a[class=\"next\"]"));
		nextWe.click();
	}

}
