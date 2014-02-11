package com.andy.weiboDriver.fansApp;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

//互粉加加
public class Qiuzf {

	public static void main(String[] args) {
		WebDriver fd = new FirefoxDriver();
		String username = "yitest0805@sina.com";
		String password = "andy0805";
		new WeiboSina().login(fd, username, password);
		Qiuzf tu = new Qiuzf();
		tu.getScoreFlow(fd);

		// tu.switchToIframe(fd);
		// tu.oneKeyAttention(fd);
		// tu.nextOneKeyAttention(fd);
		System.out.println(10);
	}

	//最多只到十页，有一页成功就退出
	public void getScoreFlow(WebDriver fd) {
		for(int i=0 ; i<=10 ;i++){
			gotoOneKeyPage(fd);
			switchToIframe(fd);
			boolean flag = oneKeyAttention(fd);
			nextOneKeyAttention(fd,i+2);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i==10){
				System.out.println("没有可以一键关注的了");
				break;
			}
			if(flag ){
				break;
			}else{
				continue;
			}
		}
	}

	public Qiuzf() {
		super();
	}

	public void gotoOneKeyPage(WebDriver fd) {
		// String url = "http://apps.weibo.com/tuimimi";
		// fd.get(url);
		// WebDriverUtil.findElement4Wait(fd, By.id("apps"), 10);
		fd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String url = "https://api.weibo.com/oauth2/authorize?client_id=245891426&redirect_uri=http%3A%2F%2Fqiuzf.sinaapp.com%2Fcallback.php&response_type=code&state=sina";
		fd.get(url);
		url = "http://qiuzf.sinaapp.com/onekeyfl";
		fd.get(url);
	}

	public void switchToIframe(WebDriver fd) {
		while (true) {
			String buttonParentText = "";
			try {
				Thread.sleep(100);
				// 一直等到页面加载完成
				WebElement oneKeyAttentionWe = WebDriverUtil.findElement4Wait(fd, By.cssSelector("div[id=\"oneKeyAttention\"]"), -1);
				// 等到可以找到iframe,然后进入到iframe里面,还真的必须要等，
				WebElement iframeWe = WebDriverUtil.findElement4Wait(oneKeyAttentionWe, By.tagName("iframe"), 10);
				fd.switchTo().frame(iframeWe);

				// 要处理iframe加载的时间
				WebElement buttonParentWe = WebDriverUtil.findElement4Wait(fd, By.cssSelector("div[class=\"tsina_batconcern\"]"), 100);
				buttonParentText = buttonParentWe.getText();
			} catch (InterruptedException e) {
			} catch (StaleElementReferenceException e) {
			}
			if (buttonParentText.contains("一键关注")) {
				break;
			}
			fd.switchTo().defaultContent();
		}
	}

	public boolean oneKeyAttention(WebDriver fd) {
		// 点击一键关注
		WebElement divWe = WebDriverUtil.findElement4Wait(fd, By.cssSelector("div[class=\"btn_con\"]"), 100);
		WebElement oneKeyButton = WebDriverUtil.findElement4Wait(divWe, By.cssSelector("a[class=\"btngreen_l\"]"), 100);
		oneKeyButton.click();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 确认关注
		Alert alert = fd.switchTo().alert();
		alert.accept();
		// 等待关注成功
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String buttonText = divWe.getText();
			if (buttonText.contains("已关注")) {
				break;
			}else if (buttonText.contains("请重试")) {
				System.out.println("请重试");
				return false;
			}
		}
		// 跳转到iframe外部，领取积分
		fd.switchTo().defaultContent();
		fd.findElement(By.id("addMark")).click();
		// 确认积分
		WebElement alertWe = WebDriverUtil.findElement4Wait(fd, By.id("tu_dialog_body"), 10);
		String alertStr = alertWe.getText();
		boolean flag = !(alertStr.contains("领分无效"));
		System.out.println(alertStr+"\n"+ flag);
		alertWe.findElement(By.cssSelector("a[class=\"btn\"]")).click();
		return flag;
	}

	public void nextOneKeyAttention(WebDriver fd,int page) {
		WebElement wraperWe = fd.findElement(By.id("wraper"));
		WebElement nextWe = wraperWe.findElement(By.cssSelector("a[class=\"next\"]"));
		nextWe.click();
		
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		WebElement paginationWe = fd.findElement(By.cssSelector("div.pagination.pagination-centered.f16"));
//		WebElement paginationLi = paginationWe.findElements(By.cssSelector("ul>li")).get(0);
//		paginationLi.getText().contains(page+"");
	}

}
