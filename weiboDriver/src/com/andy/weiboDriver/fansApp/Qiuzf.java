package com.andy.weiboDriver.fansApp;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

//互粉加加
public class Qiuzf {
	private static Logger logger = Logger.getLogger(Qiuzf.class);

	int getPage = 0;

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
		logger.info(10);
	}

	// 最多只到十页，有一页成功就退出
	public boolean getScoreFlow(WebDriver fd) {
		int temPage = 0;
		gotoOneKeyPage(fd);
		for (int i = 0; i <= 10; i++) {
			temPage += 1;
			switchToIframe(fd);
			boolean flag = oneKeyAttention(fd);
			nextOneKeyAttention(fd, i + 2);
				Threads.sleep(500);
			if (i == 10) {
				logger.info("没有可以一键关注的了");
				break;
			}
			if (flag) {
				if ((getPage != 0 && temPage == getPage) || getPage == 0) {
					return flag;
				} else {
					continue;
				}
			} else {
				temPage -= 1;
				continue;
			}
		}
		return false;
	}

	public Qiuzf() {
		super();
	}

	public Qiuzf(int i) {
		getPage = i;
	}

	public void gotoOneKeyPage(WebDriver fd) {
		// 好奇怪，非要点一下通过微博登录
		// String url1 = "http://qiuzf.sinaapp.com";
		// fd.get(url1);
		// WebDriverUtil.findElement4Wait(fd, By.id("apps"), 10);
		fd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String url2 = "https://api.weibo.com/oauth2/authorize?client_id=245891426&redirect_uri=http%3A%2F%2Fqiuzf.sinaapp.com%2Fcallback.php&response_type=code&state=sina";
		fd.get(url2);
		String url3 = "http://qiuzf.sinaapp.com/onekeyfl";
		fd.get(url3);
	}

	public void switchToIframe(WebDriver fd) {
		while (true) {
			String buttonParentText = "";
				Threads.sleep(100);
				// 一直等到页面加载完成
				WebElement oneKeyAttentionWe = WebDriverUtil.findElement4Wait(fd, By.id("oneKeyAttention"), 10);
				// 等到可以找到iframe,然后进入到iframe里面,还真的必须要等，
				WebElement iframeWe = WebDriverUtil.findElement4Wait(oneKeyAttentionWe, By.tagName("iframe"), 10);
				fd.switchTo().frame(iframeWe);

				// 要处理iframe加载的时间
				WebElement buttonParentWe = WebDriverUtil.findElement4Wait(fd, By.cssSelector("div[class=\"tsina_batconcern\"]"), 100);
				buttonParentText = buttonParentWe.getText();
				if (buttonParentText.contains("一键关注")) {
					Threads.sleep(1000);
					break;
				}
			fd.switchTo().defaultContent();
		}
	}

	public boolean oneKeyAttention(WebDriver fd) {
		// 点击一键关注
		WebElement divWe = WebDriverUtil.findElement4Wait(fd, By.cssSelector("div.btn_con"), 100);
		WebElement oneKeyButton = WebDriverUtil.findElement4Wait(divWe, By.cssSelector("a.btngreen_l"), 100);
		oneKeyButton.click();
		// 确认关注
		WebDriverUtil.waitAlert(fd);
		// fd.switchTo().activeElement().click();
		Alert alert = fd.switchTo().alert();
		alert.accept();
		// 等待关注成功
		while (true) {
				Threads.sleep(100);
			String buttonText = divWe.getText();
			if (buttonText.contains("已关注")) {
				break;
			} else if (buttonText.contains("请重试")) {
				logger.info("请重试");
				fd.switchTo().defaultContent();
				return false;
			}
		}
			Threads.sleep(100);
		// 跳转到iframe外部，领取积分
		fd.switchTo().defaultContent();
		WebElement addMarkWe = fd.findElement(By.id("addMark"));
		addMarkWe.click();
			Threads.sleep(200);
		while (true) {
			if (addMarkWe.getText().contains("稍候")) {
				addMarkWe.click();
			} else {
				break;
			}
		}
		// 确认积分
		WebElement alertWe = WebDriverUtil.findElement4Wait(fd, By.id("tu_dialog_body"), 10);
		String alertStr = alertWe.getText();
		boolean flag = !(alertStr.contains("领分无效"));
		logger.info("结果: " + flag);
		alertWe.findElement(By.cssSelector("a.btn")).click();
		return flag;
	}

	public void nextOneKeyAttention(WebDriver fd, int page) {
		WebElement wraperWe = fd.findElement(By.id("wraper"));
		WebElement nextWe = wraperWe.findElement(By.cssSelector("a.next"));
		nextWe.click();
	}

}
