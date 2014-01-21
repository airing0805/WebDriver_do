package com.andy.weiboDriver.webDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebDriverUtil {

	/**
	 * driver范围内，在一定时间内查找元素，传入秒时间
	 * @param wd
	 * @param by
	 * @param num
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement findElement4Wait(WebDriver wd, By by, int num) throws InterruptedException {
		WebElement we = null;
		for (int i = 0; i < num * 2; i++) {
			try {
				we = wd.findElement(by);
			} catch (NoSuchElementException e) {
				Thread.sleep(500);
			}
		}
		return we;
	}

	/**
	 * 元素范围内，在一定时间内查找元素，传入秒时间
	 * @param we
	 * @param by
	 * @param num
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement findElement4Wait(WebElement we, By by, int num) throws InterruptedException {
		WebElement we2 = null;
		for (int i = 0; i < num * 2; i++) {
			try {
				we2 = we.findElement(by);
			} catch (NoSuchElementException e) {
				Thread.sleep(500);
			}
		}
		return we2;
	}

}
