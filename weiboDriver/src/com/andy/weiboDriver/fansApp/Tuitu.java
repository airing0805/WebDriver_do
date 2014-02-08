package com.andy.weiboDriver.fansApp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class Tuitu {

	// http://tuitu.sinaapp.com/weibo/?ref=appmy
	public static void main(String[] args) {
		WebDriver fd = new FirefoxDriver();
		String username = "yitest0805@sina.com";
		String password = "andy0805";
		new WeiboSina().login(fd, username, password);
		Tuitu tu = new Tuitu();
		// 先弄积分再继续推
		tu.getScoreFlow(fd);
		System.out.println(10);
	}
	
	public void getScoreFlow(WebDriver fd){
		oneKeyScore(fd);
		startSpread(fd);
		System.out.println("完成一个app关注");
	}

	private void startSpread(WebDriver fd) {
		String url = "http://tuitu.sinaapp.com/weibo/space";
		fd = WebDriverUtil.getUrl(fd, url);
		WebElement startWe = fd.findElement(By.id("play_0_968770"));
		if(startWe.isDisplayed()){
			startWe.click();
		}
	}

	//一键关注全部，然后翻页
	private void oneKeyScore(WebDriver fd) {
		String url = "http://tuitu.sinaapp.com/weibo/task";
		fd = WebDriverUtil.getUrl(fd, url);
		WebElement followAllBtnWe = fd.findElement(By.id("follow_all_btn"));
		followAllBtnWe.click();
		while (true) {
			try {
				Thread.sleep(1000);
				followAllBtnWe = fd.findElement(By.id("loading"));
				if (!followAllBtnWe.isDisplayed()) {
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}

		}
		fd.findElement(By.cssSelector("a[class=\"next\"]")).click();
	}
}
