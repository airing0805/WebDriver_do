package com.andy.weiboDriver.webDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebDriverUtil {

	public static WebElement findElement4Wait(WebDriver wd, By by, int num) throws InterruptedException {
		WebElement we = null;
		for (int i = 0; i < num * 2; i++) {
			try {
				we = wd.findElement(by);
			} catch (NoSuchElementException e) {
				Thread.sleep(500);
			}
		}
		if (null == we)
			throw new NoSuchElementException("\n没有找到元素: " + by.toString());
		return we;
	}

	public static WebElement findElement4WaitClick(WebDriver wd, By by1, By by2, int num) throws InterruptedException {
		WebElement we = null;
		for (int i = 0; i < num * 2; i++) {
			try {
				we = wd.findElement(by1);
				wd.findElement(by2).click();
			} catch (NoSuchElementException e) {
				Thread.sleep(500);
			}
		}
		if (null == we)
			throw new NoSuchElementException("\n没有找到元素: " + by1.toString());
		return we;
	}

	public static WebElement findElement4Wait(WebElement we, By by, int num) throws InterruptedException {
		WebElement we2 = null;
		for (int i = 0; i < num * 2; i++) {
			try {
				we2 = we.findElement(by);
			} catch (NoSuchElementException e) {
				Thread.sleep(500);
			}
		}
		if (null == we2)
			throw new NoSuchElementException("\n没有找到元素: " + by.toString());
		return we2;
	}

}
