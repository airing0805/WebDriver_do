package com.andy.weiboDriver.doMain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class DelAttentions {
	private static Logger logger = Logger.getLogger(DelAttentions.class);

	public static void main(String[] args) {
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}
		Map<String, String> weiboMap = getWeibo();

		iterateDelAttentions(fd, weiboMap);

		fd.quit();
	}

	public void test() {
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}
		Map<String, String> weiboMap = new HashMap<String, String>();
		weiboMap.put("yitest0805@sina.com", "andy0805");

		iterateDelAttentions(fd, weiboMap);

		fd.quit();

	}

	private static Map<String, String> getWeibo() {
		List<Object> weiboXmlList = XMLConfig.getConfig().getList("weibo.weibo_username");
		int weiboNum = weiboXmlList.size();
		Map<String, String> weiboMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < weiboNum; i++) {
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_username");
			String password = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_password");
			weiboMap.put(username, password);
		}
		return weiboMap;
	}

	private static void iterateDelAttentions(WebDriver fd, Map<String, String> weiboMap) {

		for (Entry<String, String> weiboMapEntry : weiboMap.entrySet()) {
			String username = weiboMapEntry.getKey();
			String password = weiboMapEntry.getValue();
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("start delAttentions:" + sf1.format(new Date()));
			new WeiboSina().login(fd, username, password);

			delDeadAttentions(fd);
			delEarliestAttentions(fd);
			Threads.sleep(0);
		}
	}

	private static void delEarliestAttentions(WebDriver fd) {
		for (int i = 0; i < 2; i++) {
			String url = "http://apps.weibo.com/cancels?ref=appmy";
			WebDriverUtil.getUrl(fd, url, By.id("apps"));
			WebElement iframeWe = fd.findElement(By.id("apps"));
			fd.switchTo().frame(iframeWe);
			WebElement buttonEl = fd.findElement(By.cssSelector("div.pl10.pr20"));
			WebElement pageEl = buttonEl.findElement(By.cssSelector("span.fr.page-info"));
			while (true) {
				WebElement linkEl = pageEl.findElements(By.tagName("a")).get(0);
				Threads.sleep(1000);
				if (linkEl.getText().contains("上一页")) {
					break;
				}
				linkEl.click();
			}
			List<WebElement> buttonList = buttonEl.findElements(By.tagName("a"));
			buttonList.get(0).click();
			// 等待选择要删除的关注
			Threads.sleep(200);
			buttonList.get(2).click();
			WebDriverUtil.waitAlert(fd);
			fd.switchTo().alert().accept();
			// 等待提交
			Threads.sleep(200);
		}
	}

	private static void delDeadAttentions(WebDriver fd) {
		String url = "http://apps.weibo.com/killdie?ref=appmy";
		WebDriverUtil.getUrl(fd, url, By.id("apps"));
		WebElement iframeWe = fd.findElement(By.id("apps"));
		fd.switchTo().frame(iframeWe);
		fd.findElement(By.cssSelector("a.btn")).click();
		WebElement linkWe = fd.findElement(By.cssSelector("dl[node-type=\"clear\"] > dd")).findElements(By.tagName("a")).get(0);
		WebDriverUtil.waitDisplay(fd, linkWe, 20);
		linkWe.click();
		logger.info(linkWe.isDisplayed());
		// new WebDriverWait(fd,1);
		fd.findElement(By.cssSelector("a[action-type=\"submit_cancel\"]")).click();
		WebDriverUtil.waitAlert(fd);
		fd.switchTo().alert().accept();

	}

}
