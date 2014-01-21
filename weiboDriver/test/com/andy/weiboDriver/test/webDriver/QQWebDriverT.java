package com.andy.weiboDriver.test.webDriver;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.tencent.GetMessage;

public  class QQWebDriverT {

	 @Test
	public void faWeiFlowT() throws InterruptedException {
		WebDriver fd = new FirefoxDriver();
		String url  = "http://t.qq.com/xiaohuacom123";
		long start = System.currentTimeMillis();
		new GetMessage().getMessage(fd, url);;
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);

		// fd.quit();
	}

}
