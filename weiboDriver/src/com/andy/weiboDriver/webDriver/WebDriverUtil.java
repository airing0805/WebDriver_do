package com.andy.weiboDriver.webDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebDriverUtil {

	/**
	 * driver范围内，在一定时间内查找元素，传入秒时间
	 * 
	 * @param wd
	 * @param by
	 * @param num
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement findElement4Wait(WebDriver wd, By by, int num) {
		WebElement we = null;
		if (num < 0) {
			num = 1000;
		}
		for (int i = 0; i < num * 2; i++) {
			try {
				we = wd.findElement(by);
				if (null != we)
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					continue;
				}
			}
		}
		return we;
	}

	public static boolean hasElement(WebElement wddddd, By by) {
		try {
			wddddd.findElement(by);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}

	/**
	 * 元素范围内，在一定时间内查找元素，传入秒时间
	 * 
	 * @param we
	 * @param by
	 * @param num
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement findElement4Wait(WebElement we, By by, int num) {
		WebElement we2 = null;
		if (num < 0) {
			num = 1000;
		}
		for (int i = 0; i < num * 2; i++) {
			try {
				we2 = we.findElement(by);
				if (null != we)
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					continue;
				}
			}
		}
		return we2;
	}

	public static WebDriver getUrl(WebDriver fd, String url) {
		String urlSub = url.split("\\u003F")[0];
		if(urlSub.endsWith("/")){
			urlSub = urlSub.substring(0, urlSub.length()-1);
		}
		for(int i=0;i<100;i++){
			try {
				Thread.sleep(500);
				String currentUrl = fd.getCurrentUrl();
				String currentUrlSub = currentUrl.split("\\u003F")[0];
				//地址要相等
				if(currentUrlSub.endsWith("/")){
					currentUrlSub = currentUrlSub.substring(0, currentUrlSub.length()-1);
				}
				if (urlSub.equals(currentUrlSub)) {
					fd.navigate().refresh();
					Thread.sleep(3000);
					return fd;
				} else {
					fd.get(url);
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				continue;
			}
		}
		return fd;
	}

	public static int getNumInfoAtUrl(WebDriver fd, String url) {
		getUrl(fd, url);
		WebElement infoDiv = findElement4Wait(fd, By.id("Pl_Official_Header__1"), 10);
		WebElement followWe = findElement4Wait(infoDiv, By.cssSelector("strong[node-type=\"follow\"]"), 10);
		int num = Integer.parseInt(followWe.getText());
		System.out.println("关注:" + num);
		WebElement fansWe = infoDiv.findElement(By.cssSelector("strong[node-type=\"fans\"]"));
		System.out.println("粉丝:" + fansWe.getText());
		return num;
	}

}
