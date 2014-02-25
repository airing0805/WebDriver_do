package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.andy.weiboDriver.util.Threads;

public class WebDriverUtil {

	private static Logger logger = Logger.getLogger(WebDriverUtil.class);

	/**
	 * driver范围内，在一定时间内查找元素，传入秒时间
	 * 
	 * @param wd
	 * @param by
	 * @param num
	 * @return @
	 */
	public static WebElement findElement4Wait(WebDriver wd, By by, int num) {
		WebElement we = null;
		if (num < 0) {
			num = 1000;
		}
		for (int i = 0; i < num; i++) {
			try {
				we = wd.findElement(by);
				if (null != we && we.isDisplayed()) {
					we = wd.findElement(by);
					break;
				}
			} catch (Exception e) {
				Threads.sleep(1000);
				continue;
			}
		}
		return we;
	}

	public static WebElement getElementOrNot(WebElement wddddd, By by) {
		WebElement we = null;
		try {
			we = wddddd.findElement(by);
		} catch (RuntimeException e) {
			return null;
		}
		return we;
	}

	public static WebElement getElementOrNot(WebDriver wddddd, By by) {
		WebElement we = null;
		try {
			we = wddddd.findElement(by);
		} catch (RuntimeException e) {
			return null;
		}
		return we;
	}

	public static boolean hasElement(WebElement wddddd, By by) {
		try {
			WebElement weEl = wddddd.findElement(by);
			if (null != weEl && weEl.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static boolean hasElement(WebDriver wddddd, By by) {
		try {
			WebElement weEl = wddddd.findElement(by);
			if (null != weEl && weEl.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 元素范围内，在一定时间内查找元素，传入秒时间
	 * 
	 * @param we
	 * @param by
	 * @param num
	 * @return @
	 */
	public static WebElement findElement4Wait(WebElement we, By by, int num) {
		WebElement we2 = null;
		if (num < 0) {
			num = 1000;
		}
		for (int i = 0; i < num; i++) {
			try {
				we2 = we.findElement(by);
				if (null != we2 && we2.isDisplayed()) {
					we2 = we.findElement(by);
					break;
				}
			} catch (Exception e) {
				Threads.sleep(500);
				continue;
			}
		}
		return we2;
	}

	/**
	 * 防止timeout，防止地址不一样，防止页面元素没有加载
	 * @param driver
	 * @param url
	 * @param by
	 */
	public static void getUrl(WebDriver driver, String url, By by) {
		int actionCount = 100;
		boolean inited = false;
		int maxLoadTime = 100;
		int index = 0, timeout = 20;
		while (!inited && index < actionCount) {
			timeout = (index == actionCount - 1) ? maxLoadTime : 20;// 最后一次跳转使用最大的默认超时时间
			inited = navigateAndLoad(driver, url, timeout);
			if (!inited) {
				logger.info("timeout");
				continue;
			}
			inited = isCurrentUrl(driver, url);
			if (!inited) {
				continue;
			}
			//等到元素出现
			for(int i=0;i<10;i++){
				inited = hasElement(driver, by);
				if (!inited) {
					logger.info("没有找到 element");
				}else{
					break;
				}
				Threads.sleep(1000);
			}
			index++;
		}
		if (!inited && index == actionCount) {// 最终跳转失败则抛出运行时异常，退出运行
			throw new RuntimeException("can not get the url [" + url + "] after retry " + actionCount + "times!");
		}
	}

	public static void waitDisplay(WebDriver driver, By by, int timeout) {
		for (int i = 0; i < timeout; i++) {
			try {
				if (driver.findElement(by).isDisplayed())
					break;
			} catch (Exception e) {
				Threads.sleep(1000);
				continue;
			}
		}
	}

	public static void waitDisplay(WebDriver driver, WebElement webEl, By by, int timeout) {
		for (int i = 0; i < timeout; i++) {

			try {
				if (webEl.findElement(by).isDisplayed())
					break;
			} catch (Exception e) {
				Threads.sleep(1000);
				continue;
			}
		}
	}

	public static void waitDisplay(WebDriver driver, WebElement webEl, int timeoutSecond) {
		for (int i = 0; i < timeoutSecond; i++) {
			Threads.sleep(1000);
			if (webEl.isDisplayed())
				break;
		}
	}

	public static void waitAlert(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	private static boolean isCurrentUrl(WebDriver driver, String url) {

		String urlSub = url.split("\\u003F")[0];
		if (urlSub.endsWith("/")) {
			urlSub = urlSub.substring(0, urlSub.length() - 1);
		}

		String currentUrl = driver.getCurrentUrl();
		String currentUrlSub = currentUrl.split("\\u003F")[0];
		// 地址要相等
		if (currentUrlSub.endsWith("/")) {
			currentUrlSub = currentUrlSub.substring(0, currentUrlSub.length() - 1);
		}
		if (!urlSub.equals(currentUrlSub)) {
			logger.info("地址不相等哦，请求地址：" + urlSub + "；当前地址：" + currentUrl);
			return false;
		} else {
			return true;
		}
	}

	private static boolean navigateAndLoad(WebDriver driver, String url, int timeoutSecond) {
		try {
			driver.manage().timeouts().pageLoadTimeout(timeoutSecond, TimeUnit.SECONDS);
			driver.get(url);

		} catch (TimeoutException e) {
			return false;// 超时的情况下返回false
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			return false;
		} finally {
			driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		}
		return true;
	}

	// 对iframe的内容单独显示在浏览器的时候，有时候容易跑回到微博主题的iframe内。
	public static WebDriver getUrl1(WebDriver fd, String url) {
		String urlSub = url.split("\\u003F")[0];
		if (urlSub.endsWith("/")) {
			urlSub = urlSub.substring(0, urlSub.length() - 1);
		}
		for (int i = 0; i < 100; i++) {
			Threads.sleep(500);
			if (!isCurrentUrl(fd, url)) {
				continue;
			}
		}
		return fd;
	}

	public static Map<String, Integer> getNumInfoAtUrl(WebDriver fd, String url) {
		getUrl(fd, url, By.id("Pl_Official_Header__1"));
		WebElement infoDiv = findElement4Wait(fd, By.id("Pl_Official_Header__1"), 10);
		WebElement followWe = findElement4Wait(infoDiv, By.cssSelector("strong[node-type=\"follow\"]"), 10);
		int num = Integer.parseInt(followWe.getText());
		logger.info("关注:" + num);
		WebElement fansWe = infoDiv.findElement(By.cssSelector("strong[node-type=\"fans\"]"));
		logger.info("粉丝:" + fansWe.getText());
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("关注", num);
		map.put("粉丝", Integer.parseInt(fansWe.getText()));
		return map;
	}

	public static void takeScreenShot(WebDriver fd) {
		String path = System.getProperty("user.dir") + File.separator + "screenShot_";
		File scrFile = ((TakesScreenshot) fd).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path + System.currentTimeMillis() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
