package com.andy.weiboDriver.fansApp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class HuFenBang {

	/*
	 * TODO,只是复制过来了，还没有修改代码
	 * 一键关注页面：http://www.weibo01.com/reward_website/earn_score
	 * 一键关注代码：
	 * <button class="mutualFollowBtn1" style="margin:0px; vertical-align:middle; float:right;" onclick="batchFollowWeibo();">一键关注</button>
	 */
	// http://tuitu.sinaapp.com/weibo/?ref=appmy
	public static void main(String[] args) {
		WebDriver fd = new FirefoxDriver();
		String username = "yitest0805@sina.com";
		String password = "andy0805";
		new WeiboSina().login(fd, username, password);
		HuFenBang tu = new HuFenBang();
		// 先弄积分再继续推
		tu.getScoreFlow(fd);
		System.out.println(10);
	}
	
	public boolean getScoreFlow(WebDriver fd){
		boolean flag = oneKeyScore(fd);
		startSpread(fd);
		System.out.println("完成一个app关注");
		return flag;
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
	private boolean oneKeyScore(WebDriver fd) {
		String url = "http://tuitu.sinaapp.com/weibo/task";
		fd = WebDriverUtil.getUrl(fd, url);
		WebElement followAllBtnWe = WebDriverUtil.findElement4Wait(fd,By.id("follow_all_btn"),2);
		followAllBtnWe.click();
		WebElement overWe = WebDriverUtil.findElement4Wait(fd,By.cssSelector("div.tu_msg_wrap.tu_msg_ico_2"),1);
		if(null !=overWe && overWe.isDisplayed()){
			System.out.println("今天关注的太多了，明天再试试吧");
			return false;
		}
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
		return true;
	}
}
