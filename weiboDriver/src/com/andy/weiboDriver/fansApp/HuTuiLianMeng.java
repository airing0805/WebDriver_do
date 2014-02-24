package com.andy.weiboDriver.fansApp;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class HuTuiLianMeng {
	private static Logger logger = Logger.getLogger(  HuTuiLianMeng.class);
	

	/*
	 * TODO,只是复制过来了，还没有修改代码
	 * 一键关注页面：http://hufen.tlianmeng.com/welcome/getjifen?action=follows
	 * 一键关注代码：
	 * <a href="javascript:;" class="btn btn-info right" id="follow_all_btn" style="z-index:1011;position:relative;">一键关注</a>
	 * 推广页面：http://hufen.tlianmeng.com/welcome/mytg
	 * 暂停代码：
	 * <a href="javascript:;" id="stop_0_61373" title="推广中,点击暂停推广">
	 *        <img alt="现在推广中,点击暂停推广" class="png_bg" src="../img/play.png"></a>            
	 * 继续代码：
	 * <a class="hide" href="javascript:;" id="play_0_61373" title="停止中,点击开启推广">
	 *             <img alt="停止中,点击开启推广" class="png_bg" src="../img/stop.png"></a>
	 *             
	 */
	// http://tuitu.sinaapp.com/weibo/?ref=appmy
	public static void main(String[] args) {
		WebDriver fd = new FirefoxDriver();
		String username = "yitest0805@sina.com";
		String password = "andy0805";
		new WeiboSina().login(fd, username, password);
		HuTuiLianMeng tu = new HuTuiLianMeng();
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
		String url = "http://hufen.tlianmeng.com/welcome/mytg";
		WebDriverUtil.getUrl(fd, url,By.cssSelector("div[id=\"myfollow\"]"));
		WebElement startWe = fd.findElement(By.cssSelector("div[id=\"myfollow\"] > table > tbody > tr >td > a[title=\"停止中,点击开启推广\"]"));
//		WebElement startWe = WebDriverUtil.findElement4Wait(fd,By.id("play_0_61373"),1);
		if(null !=startWe && startWe.isDisplayed()){
			startWe.click();
		}
	}

	//一键关注全部，然后翻页
	private boolean oneKeyScore(WebDriver fd) {
//		String url1 = "http://apps.weibo.com/wbhutui";
//		WebDriverUtil.getUrl(fd, url1);
		String url = "http://hufen.tlianmeng.com/welcome/getjifen?action=follows";
		WebDriverUtil.getUrl(fd, url,By.id("follow_all_btn"));
		WebElement followAllBtnWe = WebDriverUtil.findElement4Wait(fd,By.id("follow_all_btn"),3);
		followAllBtnWe.click();
		while (true) {
			WebElement overWe = WebDriverUtil.getElementOrNot(fd,By.cssSelector("div.tu_msg_window"));
			if(null !=overWe && overWe.isDisplayed() && overWe.getText().contains("上限")){
				logger.info("今天关注的太多了，明天再试试吧");
				return false;
			}
				Threads.sleep(1000);
				followAllBtnWe = fd.findElement(By.id("loading"));
				if (!followAllBtnWe.isDisplayed()) {
					break;
				}

		}
		fd.findElement(By.cssSelector("a[class=\"next\"]")).click();
		return true;
	}
}
