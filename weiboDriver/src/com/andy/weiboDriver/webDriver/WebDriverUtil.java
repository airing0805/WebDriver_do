package com.andy.weiboDriver.webDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.andy.weiboDriver.util.XMLConfig;

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
		for (int i = 0; i < num * 10; i++) {
			try {
				we = wd.findElement(by);
				if (null != we)
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(100);
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
		for (int i = 0; i < num * 10; i++) {
			try {
				we2 = we.findElement(by);
				if (null != we)
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					continue;
				}
			}
		}
		return we2;
	}
	
	public static WebDriver getUrl(WebDriver fd,String url){
		while(true){
			String currentUrl = fd.getCurrentUrl();
			if(currentUrl.contains(url)){
				return fd;
			}else{
				fd.get(url);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					continue;
				}
			}
		}
	}
	
	public static int getNumInfoAtUrl(WebDriver fd,String url){
		getUrl(fd,url);
		WebElement infoDiv =  findElement4Wait(fd,By.cssSelector("ul.user_atten.clearfix.user_atten_l"),5);
		WebElement followWe = findElement4Wait(infoDiv,By.cssSelector("a.S_func1 > strong[node-type=\"follow\"]"),5);
		int num = Integer.parseInt(followWe.getText());
		System.out.println("关注:" + num);
		WebElement fansWe = infoDiv.findElement(By.cssSelector("a.S_func1 > strong[node-type=\"fans\"]"));
		System.out.println("粉丝:" + fansWe.getText());
		return num;
	}
	
	public static int getNumInfoAtLogin(WebDriver fd){
		WebElement infoDiv = findElement4Wait(fd,  By.cssSelector("div[id=\"pl_rightmod_myinfo\"] > ul"),5);
		WebElement followWe = findElement4Wait(infoDiv,By.cssSelector("a.S_func1 > strong[node-type=\"follow\"]"),5);
		int num = Integer.parseInt(followWe.getText());
		System.out.println("关注:" + num);
		WebElement fansWe = infoDiv.findElement(By.cssSelector("a.S_func1 > strong[node-type=\"fans\"]"));
		System.out.println("粉丝:" + fansWe.getText());
		return num;
	}

}
