package com.andy.weiboDriver.fansApp;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class Tuitu {
	private static Logger logger = Logger.getLogger(  Tuitu.class);
	

	// http://tuitu.sinaapp.com/weibo/?ref=appmy
	public static void main(String[] args) {
		WebDriver fd = new FirefoxDriver();
		String username = "yitest0805@sina.com";
		String password = "andy0805";
		new WeiboSina().login(fd, username, password);
		Tuitu tu = new Tuitu();
		// 先弄积分再继续推
		tu.getScoreFlow(fd);
		logger.info(10);
	}
	
	public boolean getScoreFlow(WebDriver fd){
		boolean flag = oneKeyScore(fd);
		startSpread(fd);
		logger.info("完成一个app关注");
		return flag;
	}

	private void startSpread(WebDriver fd) {
		String url = "http://tuitu.sinaapp.com/weibo/space";
		fd = WebDriverUtil.getUrl(fd, url);
		WebElement startWe = WebDriverUtil.findElement4Wait(fd, By.id("play_0_968770"),1);
		if(null != startWe && startWe.isDisplayed()){
			startWe.click();
		}
	}

	//一键关注全部，然后翻页
	private boolean oneKeyScore(WebDriver fd) {
		String url1 = "http://apps.weibo.com/tuituoo";
		fd = WebDriverUtil.getUrl(fd, url1);
		String url2 = "http://tuitu.sinaapp.com/weibo/task";
		fd = WebDriverUtil.getUrl(fd, url2);
		WebElement followAllBtnWe = WebDriverUtil.findElement4Wait(fd,By.id("follow_all_btn"),20);
		followAllBtnWe.click();
		List<WebElement> userWEList = fd.findElements(By.cssSelector("div.fusers.clearfix > div.fuser.left"));
		logger.info("将要批量关注 : "+ userWEList.size());
		while (true) {
			WebElement overWe = WebDriverUtil.findElement4Wait(fd,By.cssSelector("div.tu_msg_wrap.tu_msg_ico_2"),1);
			if(null !=overWe && overWe.isDisplayed()&& overWe.getText().contains("太多了")){
				logger.info("今天关注的太多了，明天再试试吧");
				return false;
			}
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
		return true;
	}
}
